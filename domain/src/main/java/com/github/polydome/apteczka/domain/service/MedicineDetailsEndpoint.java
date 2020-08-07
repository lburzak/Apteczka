package com.github.polydome.apteczka.domain.service;

import io.reactivex.Maybe;

public interface MedicineDetailsEndpoint {
    Maybe<MedicineDetails> fetchMedicineDetails(String ean);
}
