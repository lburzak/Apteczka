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
                .name(medicine.getName())
                .commonName(medicine.getCommonName())
                .form(medicine.getForm())
                .packagingSize(medicine.getPackagingSize())
                .packagingUnit(medicine.getPackagingUnit())
                .potency(medicine.getPotency())
                .build();
    }

    public Medicine toMedicine(long id) {
        return Medicine.builder()
                .name(getName())
                .commonName(getCommonName())
                .form(getForm())
                .packagingSize(getPackagingSize())
                .packagingUnit(getPackagingUnit())
                .potency(getPotency())
                .ean(getEan())
                .id(id)
                .build();
    }
}
