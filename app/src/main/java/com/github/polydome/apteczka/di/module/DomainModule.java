package com.github.polydome.apteczka.di.module;

import com.github.polydome.apteczka.domain.repository.MedicineRepository;
import com.github.polydome.apteczka.domain.service.ProductEndpoint;
import com.github.polydome.apteczka.domain.usecase.AddMedicineUseCase;
import com.github.polydome.apteczka.domain.usecase.GetMedicineDataUseCase;
import com.github.polydome.apteczka.domain.usecase.ObserveMedicineIdsUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {
    @Provides
    public AddMedicineUseCase addMedicineUseCase(MedicineRepository medicineRepository) {
        return new AddMedicineUseCase(medicineRepository);
    }

    @Provides
    public GetMedicineDataUseCase getMedicineDataUseCase(MedicineRepository medicineRepository) {
        return new GetMedicineDataUseCase(medicineRepository);
    }

    @Provides
    public ObserveMedicineIdsUseCase observeMedicineIdsUseCase(MedicineRepository medicineRepository) {
        return new ObserveMedicineIdsUseCase(medicineRepository);
    }
}
