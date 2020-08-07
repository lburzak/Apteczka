package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.repository.MedicineRepository;
import com.github.polydome.apteczka.domain.service.MedicineDetails;
import com.github.polydome.apteczka.domain.service.MedicineDetailsEndpoint;
import com.github.polydome.apteczka.domain.usecase.exception.DuplicateMedicineException;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class AddMedicineUseCase {
    private final MedicineRepository medicineRepository;
    private final MedicineDetailsEndpoint medicineDetailsEndpoint;

    public AddMedicineUseCase(MedicineRepository medicineRepository, MedicineDetailsEndpoint medicineDetailsEndpoint) {
        this.medicineRepository = medicineRepository;
        this.medicineDetailsEndpoint = medicineDetailsEndpoint;
    }

    /**
     *
     * @param ean
     * @return ID of created medicine
     */
    public Single<Long> execute(final String ean) {
        return medicineRepository.exists(ean).flatMap(new Function<Boolean, SingleSource<Long>>() {
            @Override
            public SingleSource<Long> apply(Boolean exists) {
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
                        .flatMap(new Function<Medicine, SingleSource<Long>>() {
                            @Override
                            public SingleSource<Long> apply(Medicine medicine) throws Exception {
                                return medicineRepository.create(medicine);
                            }
                        });
            }
        });
    }
}
