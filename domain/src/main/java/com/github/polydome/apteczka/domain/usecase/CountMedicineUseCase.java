package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.repository.MedicineRepository;

import io.reactivex.Single;

public class CountMedicineUseCase {
    private final MedicineRepository medicineRepository;

    public CountMedicineUseCase(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Single<Integer> execute() {
        return medicineRepository.count();
    }
}
