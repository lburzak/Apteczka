package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.model.Product;
import com.github.polydome.apteczka.domain.model.ProductLinkedMedicine;
import com.github.polydome.apteczka.domain.repository.MedicineRepository;
import com.github.polydome.apteczka.domain.service.ProductEndpoint;
import com.github.polydome.apteczka.domain.usecase.exception.NoSuchMedicineException;
import com.github.polydome.apteczka.domain.usecase.exception.NoSuchProductException;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Maybe;
import io.reactivex.functions.Function;

public class LinkProductUseCase {
    private final MedicineRepository medicineRepository;
    private final ProductEndpoint productEndpoint;

    public LinkProductUseCase(MedicineRepository medicineRepository, ProductEndpoint productEndpoint) {
        this.medicineRepository = medicineRepository;
        this.productEndpoint = productEndpoint;
    }

    public Completable execute(long medicineId, final String ean) {
        return medicineRepository.findById(medicineId)
                .switchIfEmpty(Maybe.<Medicine>error(new NoSuchMedicineException(medicineId)))
                .flatMapCompletable(new Function<Medicine, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Medicine medicine) {
                        if (medicine instanceof ProductLinkedMedicine)
                            return Completable.error(new IllegalArgumentException("Medicine already linked"));
                        else
                            return linkProductToMedicine(ean, medicine);
                    }
                });
    }

    private Completable linkProductToMedicine(String ean, @NotNull final Medicine medicine) {
        return productEndpoint.fetchMedicineDetails(ean)
                .switchIfEmpty(Maybe.<Product>error(new NoSuchProductException(ean)))
                .flatMapCompletable(new Function<Product, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Product product) {
                        return medicineRepository.update(createLinkedMedicine(medicine, product));
                    }
                });
    }

    private ProductLinkedMedicine createLinkedMedicine(Medicine medicine, Product product) {
        return new ProductLinkedMedicine.Builder()
                .id(medicine.getId())
                .title(medicine.getTitle())
                .product(product)
                .build();
    }
}
