package com.github.polydome.domain.usecase;

import com.github.polydome.domain.service.MedicineDetails;
import com.github.polydome.domain.service.MedicineDetailsEndpoint;

import io.reactivex.Maybe;

public class GetMedicineDetailsUseCase {
    private final MedicineDetailsEndpoint medicineDetailsEndpoint;

    public GetMedicineDetailsUseCase(MedicineDetailsEndpoint medicineDetailsEndpoint) {
        this.medicineDetailsEndpoint = medicineDetailsEndpoint;
    }

    Maybe<MedicineDetails> execute(String ean) {
        return medicineDetailsEndpoint.fetchMedicineDetails(ean);
    }
}
