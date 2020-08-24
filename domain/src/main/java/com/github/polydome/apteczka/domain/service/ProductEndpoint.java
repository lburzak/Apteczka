package com.github.polydome.apteczka.domain.service;

import com.github.polydome.apteczka.domain.model.Product;

import io.reactivex.Maybe;

public interface ProductEndpoint {
    Maybe<Product> fetchMedicineDetails(String ean);
}
