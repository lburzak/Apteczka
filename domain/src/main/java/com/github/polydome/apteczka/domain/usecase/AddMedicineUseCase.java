package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.repository.MedicineRepository;
import com.github.polydome.apteczka.domain.usecase.structure.MedicineData;

import io.reactivex.Single;

public class AddMedicineUseCase {
    private final MedicineRepository medicineRepository;

    public AddMedicineUseCase(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    /**
     *
     *
     * @param medicineData
     * @return ID of created medicine
     */
    public Single<Long> execute(final MedicineData medicineData) {
        return medicineRepository.create(medicineData.toMedicine(0));
    }
}
