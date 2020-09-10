package com.github.polydome.apteczka.domain.usecase.structure;

import com.github.polydome.apteczka.domain.model.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductData {
    private final String name;
    private final String commonName;
    private final String form;
    private final String potency;
    private final String packagingUnit;
    private final int packagingSize;

    public static ProductData fromProduct(Product product) {
        return ProductData.builder()
                .commonName(product.getCommonName())
                .form(product.getForm())
                .name(product.getName())
                .potency(product.getPotency())
                .packagingSize(product.getPackagingSize())
                .packagingUnit(product.getPackagingUnit())
                .build();
    }
}
