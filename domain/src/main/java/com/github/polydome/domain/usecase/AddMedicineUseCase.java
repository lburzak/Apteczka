package com.github.polydome.domain.usecase;

import com.github.polydome.domain.model.Medicine;
import com.github.polydome.domain.repository.MedicineRepository;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.functions.Function;

public class AddMedicineUseCase {
    MedicineRepository medicineRepository;

    public AddMedicineUseCase(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Completable execute(final String ean) {
        return medicineRepository.exists(ean).flatMapCompletable(new Function<Boolean, CompletableSource>() {
            @Override
            public CompletableSource apply(Boolean exists) {
                if (exists)
                    throw new DuplicateMedicineException(ean);

                Medicine medicine = new Medicine(ean);
                return medicineRepository.create(medicine);
            }
        });
    }
}
