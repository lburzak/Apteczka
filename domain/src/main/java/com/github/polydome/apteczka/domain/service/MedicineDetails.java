package com.github.polydome.apteczka.domain.service;

import com.github.polydome.apteczka.domain.model.Medicine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicineDetails {
    private final String name;
    private final String commonName;
    private final String potency;
    private final String form;
    private final int packagingSize;
    private final String packagingUnit;

    public Medicine mergeToMedicine(Medicine medicine) {
        return medicine.toBuilder()
                .potency(getPotency())
                .packagingUnit(getPackagingUnit())
                .packagingSize(getPackagingSize())
                .name(getName())
                .form(getForm())
                .commonName(getCommonName())
                .build();
    }
}
