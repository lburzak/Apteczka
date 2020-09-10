package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.model.Product;
import com.github.polydome.apteczka.domain.service.ProductEndpoint;
import com.github.polydome.apteczka.domain.usecase.structure.ProductData;

import io.reactivex.Maybe;
import io.reactivex.functions.Function;

public class FetchProductDataUseCase {
    private final ProductEndpoint productEndpoint;

    public FetchProductDataUseCase(ProductEndpoint productEndpoint) {
        this.productEndpoint = productEndpoint;
    }

    public Maybe<ProductData> byEan(String ean) {
        return productEndpoint.fetchMedicineDetails(ean).map(new Function<Product, ProductData>() {
            @Override
            public ProductData apply(Product product) {
                return ProductData.fromProduct(product);
            }
        });
    }
}
