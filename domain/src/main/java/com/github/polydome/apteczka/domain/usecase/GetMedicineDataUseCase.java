package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.repository.MedicineRepository;
import com.github.polydome.apteczka.domain.usecase.structure.MedicineData;

import io.reactivex.Maybe;
import io.reactivex.functions.Function;

public class GetMedicineDataUseCase {
    private final MedicineRepository medicineRepository;

    public GetMedicineDataUseCase(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public Maybe<MedicineData> execute(long id) {
        return medicineRepository.findById(id).map(new Function<Medicine, MedicineData>() {
            @Override
            public MedicineData apply(Medicine medicine) {
                return MedicineData.fromMedicine(medicine);
            }
        });
    }
}
