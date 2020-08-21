package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.repository.MedicineRepository;

import java.util.List;

import io.reactivex.Observable;

public class ObserveMedicineIdsUseCase {
    private final MedicineRepository medicineRepository;

    public ObserveMedicineIdsUseCase(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Observable<List<Long>> execute() {
        return medicineRepository.ids();
    }
}
