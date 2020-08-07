package com.github.polydome.apteczka.domain.usecase.structure;

import com.github.polydome.apteczka.domain.model.Medicine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicineData {
    private final String ean;
    private final String name;
    private final String commonName;
    private final String potency;
    private final String form;
    private final int packagingSize;
    private final String packagingUnit;

    public static MedicineData fromMedicine(Medicine medicine) {
        return MedicineData.builder()
                .ean(medicine.getEan())
                .build();
    }
}
