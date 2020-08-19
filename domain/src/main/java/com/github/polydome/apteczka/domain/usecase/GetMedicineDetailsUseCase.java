package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.service.MedicineDetails;
import com.github.polydome.apteczka.domain.service.MedicineDetailsEndpoint;

import io.reactivex.Maybe;

public class GetMedicineDetailsUseCase {
    private final MedicineDetailsEndpoint medicineDetailsEndpoint;

    public GetMedicineDetailsUseCase(MedicineDetailsEndpoint medicineDetailsEndpoint) {
        this.medicineDetailsEndpoint = medicineDetailsEndpoint;
    }

    public Maybe<MedicineDetails> execute(String ean) {
        return medicineDetailsEndpoint.fetchMedicineDetails(ean);
    }
}
