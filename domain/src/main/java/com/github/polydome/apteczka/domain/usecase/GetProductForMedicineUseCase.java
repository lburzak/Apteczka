package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.model.Product;
import com.github.polydome.apteczka.domain.model.ProductLinkedMedicine;
import com.github.polydome.apteczka.domain.repository.MedicineRepository;
import com.github.polydome.apteczka.domain.usecase.exception.MedicineNotLinkedException;
import com.github.polydome.apteczka.domain.usecase.exception.NoSuchMedicineException;
import com.github.polydome.apteczka.domain.usecase.structure.ProductData;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class GetProductForMedicineUseCase {
    private final MedicineRepository medicineRepository;

    public GetProductForMedicineUseCase(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Single<ProductData> getProductData(final long medicineId) {
        return medicineRepository.findById(medicineId)
                .switchIfEmpty(Maybe.<Medicine>error(new NoSuchMedicineException(medicineId)))
                .flatMapSingle(new Function<Medicine, SingleSource<ProductLinkedMedicine>>() {
                    @Override
                    public SingleSource<ProductLinkedMedicine> apply(Medicine medicine) {
                        if (medicine instanceof ProductLinkedMedicine)
                            return Single.just((ProductLinkedMedicine) medicine);
                        else
                            return Single.error(new MedicineNotLinkedException(medicineId));
                    }
                })
                .map(new Function<ProductLinkedMedicine, Product>() {
                    @Override
                    public Product apply(ProductLinkedMedicine productLinkedMedicine) {
                        return productLinkedMedicine.getProduct();
                    }
                })
                .map(new Function<Product, ProductData>() {
                    @Override
                    public ProductData apply(Product product) {
                        return ProductData.fromProduct(product);
                    }
                });
    }
}
