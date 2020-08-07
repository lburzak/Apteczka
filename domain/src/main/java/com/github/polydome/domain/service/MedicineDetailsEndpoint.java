package com.github.polydome.domain.service;

import io.reactivex.Maybe;

public interface MedicineDetailsEndpoint {
    Maybe<MedicineDetails> fetchMedicineDetails(String ean);
}
