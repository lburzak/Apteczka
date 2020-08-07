package com.github.polydome.domain.usecase;

import com.github.polydome.domain.model.Medicine;
import com.github.polydome.domain.repository.MedicineRepository;
import com.github.polydome.domain.service.MedicineDetails;
import com.github.polydome.domain.service.MedicineDetailsEndpoint;
import com.github.polydome.domain.usecase.exception.DuplicateMedicineException;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class AddMedicineUseCase {
    private final MedicineRepository medicineRepository;
    private final MedicineDetailsEndpoint medicineDetailsEndpoint;

    public AddMedicineUseCase(MedicineRepository medicineRepository, MedicineDetailsEndpoint medicineDetailsEndpoint) {
        this.medicineRepository = medicineRepository;
        this.medicineDetailsEndpoint = medicineDetailsEndpoint;
    }

    public Completable execute(final String ean) {
        return medicineRepository.exists(ean).flatMapCompletable(new Function<Boolean, CompletableSource>() {
            @Override
            public CompletableSource apply(Boolean exists) {
                if (exists)
                    throw new DuplicateMedicineException(ean);

                final Medicine defaultMedicine = Medicine.builder()
                        .ean(ean)
                        .build();

                return medicineDetailsEndpoint.fetchMedicineDetails(ean)
                        .switchIfEmpty(Single.just(MedicineDetails.builder().build()))
                        .map(new Function<MedicineDetails, Medicine>() {
                            @Override
                            public Medicine apply(MedicineDetails medicineDetails) {
                                return medicineDetails.mergeToMedicine(defaultMedicine);
                            }
                        })
                        .flatMapCompletable(new Function<Medicine, CompletableSource>() {
                            @Override
                            public CompletableSource apply(Medicine medicine) {
                                return medicineRepository.create(medicine);
                            }
                        });
            }
        });
    }
}
