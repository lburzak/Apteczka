package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.model.Product;
import com.github.polydome.apteczka.domain.model.ProductLinkedMedicine;
import com.github.polydome.apteczka.domain.repository.MedicineRepository;
import com.github.polydome.apteczka.domain.service.ProductEndpoint;
import com.github.polydome.apteczka.domain.usecase.exception.NoSuchMedicineException;
import com.github.polydome.apteczka.domain.usecase.exception.NoSuchProductException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.Maybe;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class LinkProductUseCaseTest {
    MedicineRepository medicineRepository = mock(MedicineRepository.class);
    ProductEndpoint productEndpoint = mock(ProductEndpoint.class);
    LinkProductUseCase SUT = new LinkProductUseCase(medicineRepository, productEndpoint);

    long ID = 13;
    String EAN = "19273716276351";

    @Test
    public void execute_medicineAlreadyLinked_emitsException() {
        when(medicineRepository.findById(ID))
                .thenReturn(Maybe.just(createLinkedMedicine(ID)));

        SUT.execute(ID, EAN)
                .test()
                .assertError(IllegalArgumentException.class)
                .assertErrorMessage("Medicine already linked");
    }

    @Test
    public void execute_medicineDoesNotExist_emitsException() {
        when(medicineRepository.findById(ID))
                .thenReturn(Maybe.<Medicine>empty());

        SUT.execute(ID, EAN)
                .test()
                .assertError(NoSuchMedicineException.class)
                .assertErrorMessage(String.format("Medicine identified by [id=%d] does not exist", ID));
    }

    @Test
    public void execute_medicineNotLinked_updatesMedicineWithProduct() {
        final Medicine inMedicine = createUnlinkedMedicine(ID);
        final Product product = createProduct();

        when(medicineRepository.findById(ID))
                .thenReturn(Maybe.just(inMedicine));

        when(medicineRepository.update(Mockito.any(Medicine.class)))
                .thenReturn(Completable.complete());

        when(productEndpoint.fetchMedicineDetails(EAN))
                .thenReturn(Maybe.just(product));

        SUT.execute(ID, EAN)
                .test()
                .assertNoErrors()
                .assertComplete();

        ArgumentCaptor<Medicine> medicineCpt = ArgumentCaptor.forClass(Medicine.class);

        verify(medicineRepository).update(medicineCpt.capture());

        Medicine outMedicine = medicineCpt.getValue();

        assertThat(outMedicine, Matchers.<Medicine>instanceOf(ProductLinkedMedicine.class));
        assertThat(outMedicine, equalTo(inMedicine));
        assertThat(((ProductLinkedMedicine) outMedicine).getProduct(), equalTo(product));
    }

    @Test
    public void execute_productNotAvailableAtEndpoint_emitsNoSuchProductError() {
        final Medicine inMedicine = createUnlinkedMedicine(ID);

        when(medicineRepository.findById(ID))
                .thenReturn(Maybe.just(inMedicine));

        when(productEndpoint.fetchMedicineDetails(EAN))
                .thenReturn(Maybe.<Product>empty());

        SUT.execute(ID, EAN)
                .test()
                .assertError(NoSuchProductException.class)
                .assertErrorMessage(String.format(Locale.getDefault(),
                        "Product identified by [ean=%s] is not available", EAN));
    }

    private Product createProduct() {
        return Product.builder().build();
    }

    private Medicine createUnlinkedMedicine(long id) {
        return Medicine.builder().id(id).build();
    }

    private Medicine createLinkedMedicine(long id) {
        return new ProductLinkedMedicine.Builder()
                .id(id)
                .title("test title")
                .product(Product.builder().build())
                .build();
    }
}