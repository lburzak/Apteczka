package com.github.polydome.apteczka.network;

import com.github.polydome.apteczka.domain.model.Product;
import com.github.polydome.apteczka.network.model.RemedyPackaging;
import com.github.polydome.apteczka.network.model.RemedyProduct;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import io.reactivex.Maybe;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RemedyProductEndpointTest {
    RemedyService remedyService = Mockito.mock(RemedyService.class);
    RemedyProductEndpoint SUT = new RemedyProductEndpoint(remedyService);

    String EAN = "8367138201222";
    int PRODUCT_ID = 381;

    @Test
    public void fetchMedicineDetails_packagingAndProductExist_emitsMedicineDetails() {
        Product expected = createProduct();

        Mockito.when(remedyService.getPackaging(EAN)).thenReturn(Maybe.just(
                createRemedyPackaging(expected)
        ));

        Mockito.when(remedyService.getProduct(PRODUCT_ID)).thenReturn(Maybe.just(
                createRemedyProduct(expected)
        ));

        SUT.fetchMedicineDetails(EAN).test().assertValue(expected);
    }

    @Test
    public void fetchMedicineDetails_packagingNotExists_empty() {
        Mockito.when(remedyService.getPackaging(EAN)).thenReturn(Maybe.<RemedyPackaging>empty());

        SUT.fetchMedicineDetails(EAN).test().assertNoValues().assertComplete();
    }

    @Test
    public void fetchMedicineDetails_productNotExists_empty() {
        Mockito.when(remedyService.getPackaging(EAN)).thenReturn(Maybe.just(
                RemedyPackaging.builder().productId(PRODUCT_ID).build()
        ));

        Mockito.when(remedyService.getProduct(PRODUCT_ID)).thenReturn(Maybe.<RemedyProduct>empty());

        SUT.fetchMedicineDetails(EAN).test().assertNoValues().assertComplete();
    }
    
    private Product createProduct() {
        return Product.builder()
                .commonName("test common name")
                .form("test form")
                .name("test name")
                .packagingSize(12)
                .packagingUnit("test unit")
                .potency("test potency")
                .build();
    }
    
    private RemedyProduct createRemedyProduct(Product product) {
        return RemedyProduct.builder()
                .activeSubstance("-")
                .atc("-")
                .commonName(product.getCommonName())
                .form(product.getForm())
                .name(product.getName())
                .id(1128)
                .potency(product.getPotency())
                .build();
    }
    
    private RemedyPackaging createRemedyPackaging(Product product) {
        return RemedyPackaging.builder()
                .ean(EAN)
                .id(112)
                .productId(PRODUCT_ID)
                .size(product.getPackagingSize())
                .unit(product.getPackagingUnit())
                .build();
    }
}