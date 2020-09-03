package com.github.polydome.apteczka.domain.model;

import lombok.Getter;

@Getter
public class ProductLinkedMedicine extends Medicine {
    private final Product product;

    public ProductLinkedMedicine(long id, String title, Product product) {
        super(id, title);
        this.product = product;
    }

    public static class Builder {
        private long id;
        private String title;
        private Product product;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public ProductLinkedMedicine build() {
            return new ProductLinkedMedicine(
                    id,
                    title,
                    product
            );
        }
    }
}
