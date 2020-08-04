package com.github.polydome.domain.service;

import com.github.polydome.domain.model.MedicineDetails;

import io.reactivex.Maybe;

public interface MedicineDetailsEndpoint {
    Maybe<MedicineDetails> fetchMedicineDetails(String ean);
}
