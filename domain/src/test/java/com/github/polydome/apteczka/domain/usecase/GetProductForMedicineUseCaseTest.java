package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.model.Product;
import com.github.polydome.apteczka.domain.model.ProductLinkedMedicine;
import com.github.polydome.apteczka.domain.repository.MedicineRepository;
import com.github.polydome.apteczka.domain.usecase.exception.MedicineNotLinkedException;
import com.github.polydome.apteczka.domain.usecase.exception.NoSuchMedicineException;
import com.github.polydome.apteczka.domain.usecase.structure.ProductData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import io.reactivex.Maybe;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class GetProductForMedicineUseCaseTest {
    MedicineRepository medicineRepository = mock(MedicineRepository.class);

    GetProductForMedicineUseCase SUT = new GetProductForMedicineUseCase(medicineRepository);

    @Test
    public void getProductData_medicineNotExists_emitsSuitableError() {
        long NOT_EXISTING_MEDICINE_ID = 3;

        when(medicineRepository.findById(NOT_EXISTING_MEDICINE_ID))
                .thenReturn(Maybe.<Medicine>empty());

        SUT.getProductData(NOT_EXISTING_MEDICINE_ID).test()
                .assertError(NoSuchMedicineException.class);
    }

    @Test
    public void getProductData_productNotLinkedWithMedicine_emitsSuitableError() {
        long NOT_LINKED_MEDICINE_ID = 8;

        when(medicineRepository.findById(NOT_LINKED_MEDICINE_ID))
                .thenReturn(Maybe.just(Medicine.builder().title("test title").build()));

        SUT.getProductData(NOT_LINKED_MEDICINE_ID).test()
                .assertError(MedicineNotLinkedException.class);
    }

    @Test
    public void getProductData_productLinkedWithMedicine_emitsProductData() {
        long LINKED_MEDICINE_ID = 12;

        ProductData expectedOutput = ProductData.builder()
                .name("test product")
                .build();

        when(medicineRepository.findById(LINKED_MEDICINE_ID))
                .thenReturn(Maybe.just(createLinkedMedicine(LINKED_MEDICINE_ID, "test product")));

        SUT.getProductData(LINKED_MEDICINE_ID).test()
                .assertValue(expectedOutput);
    }

    private Medicine createLinkedMedicine(long id, String productName) {
        return new ProductLinkedMedicine.Builder()
                .product(createProduct(productName))
                .id(id)
                .build();
    }

    private Product createProduct(String name) {
        return Product.builder()
                .name(name)
                .build();
    }
}