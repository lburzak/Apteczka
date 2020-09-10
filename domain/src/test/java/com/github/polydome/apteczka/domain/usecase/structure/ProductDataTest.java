package com.github.polydome.apteczka.domain.usecase.structure;

import com.github.polydome.apteczka.domain.model.Product;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ProductDataTest {
    @Test
    public void fromProduct_returnsValidProduct() {
        Product product = Product.builder()
                .commonName("test common name")
                .ean("test ean")
                .form("test form")
                .id(11)
                .name("test name")
                .packagingSize(12)
                .packagingUnit("test unit")
                .potency("test potency")
                .build();

        ProductData productData = ProductData.fromProduct(product);

        assertThat(productData.getCommonName(), equalTo(product.getCommonName()));
        assertThat(productData.getForm(), equalTo(product.getForm()));
        assertThat(productData.getPotency(), equalTo(product.getPotency()));
        assertThat(productData.getPackagingSize(), equalTo(product.getPackagingSize()));
        assertThat(productData.getPackagingUnit(), equalTo(product.getPackagingUnit()));
        assertThat(productData.getName(), equalTo(product.getName()));
    }
}