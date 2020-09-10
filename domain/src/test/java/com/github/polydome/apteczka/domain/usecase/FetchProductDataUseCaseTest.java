package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.model.Product;
import com.github.polydome.apteczka.domain.service.ProductEndpoint;
import com.github.polydome.apteczka.domain.usecase.structure.ProductData;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import io.reactivex.Maybe;
import io.reactivex.functions.Predicate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FetchProductDataUseCaseTest {
    ProductEndpoint productEndpoint = Mockito.mock(ProductEndpoint.class);
    FetchProductDataUseCase SUT = new FetchProductDataUseCase(productEndpoint);

    String EAN = "826194738292";

    @Test
    public void execute_medicineUnknown_emitsEmpty() {
        Mockito.when(productEndpoint.fetchMedicineDetails(Mockito.eq(EAN))).thenReturn(Maybe.<Product>empty());

        SUT.byEan(EAN).test().assertNoValues();
    }

    @Test
    public void execute_medicineDetailsAvailable_emitsMedicineDetails() {
        final Product PRODUCT = Product.builder()
                .name("testmed")
                .build();

        Mockito.when(productEndpoint.fetchMedicineDetails(Mockito.eq(EAN))).thenReturn(Maybe.just(PRODUCT));

        SUT.byEan(EAN).test().assertValue(new Predicate<ProductData>() {
            @Override
            public boolean test(ProductData productData) throws Exception {
                return productData.getName().equals(PRODUCT.getName());
            }
        });
    }
}