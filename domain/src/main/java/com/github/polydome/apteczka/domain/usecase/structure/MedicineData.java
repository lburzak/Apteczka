package com.github.polydome.apteczka.domain.usecase.structure;

import com.github.polydome.apteczka.domain.model.Medicine;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicineData {
    private final String title;

    public static MedicineData fromMedicine(Medicine medicine) {
        return MedicineData.builder()
                .title(medicine.getTitle())
                .build();
    }

    public Medicine toMedicine(long id) {
        return Medicine.builder()
                .title(title)
                .id(id)
                .build();
    }
}
