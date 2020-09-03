package com.github.polydome.apteczka.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductLinkedMedicine extends Medicine {
    private final Product product;

    @Builder
    public ProductLinkedMedicine(long id, String title, Product product) {
        super(id, title);
        this.product = product;
    }
}
