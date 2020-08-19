package com.github.polydome.apteczka.di.module;

import com.github.polydome.apteczka.domain.repository.MedicineRepository;
import com.github.polydome.apteczka.domain.service.MedicineDetailsEndpoint;
import com.github.polydome.apteczka.domain.usecase.AddMedicineUseCase;
import com.github.polydome.apteczka.domain.usecase.GetMedicineDataUseCase;
import com.github.polydome.apteczka.domain.usecase.GetMedicineDetailsUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {
    @Provides
    public AddMedicineUseCase addMedicineUseCase(MedicineRepository medicineRepository, MedicineDetailsEndpoint medicineDetailsEndpoint) {
        return new AddMedicineUseCase(medicineRepository, medicineDetailsEndpoint);
    }

    @Provides
    public GetMedicineDataUseCase getMedicineDataUseCase(MedicineRepository medicineRepository) {
        return new GetMedicineDataUseCase(medicineRepository);
    }

    @Provides
    public GetMedicineDetailsUseCase getMedicineDetailsUseCase(MedicineDetailsEndpoint medicineDetailsEndpoint) {
        return new GetMedicineDetailsUseCase(medicineDetailsEndpoint);
    }
}
