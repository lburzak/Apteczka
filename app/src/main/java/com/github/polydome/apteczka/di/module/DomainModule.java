package com.github.polydome.apteczka.di.module;

import com.github.polydome.apteczka.domain.repository.MedicineRepository;
import com.github.polydome.apteczka.domain.service.MedicineDetailsEndpoint;
import com.github.polydome.apteczka.domain.usecase.AddMedicineUseCase;

import dagger.Module;
import dagger.Provides;

@Module(includes = {NetworkModule.class})
public class DomainModule {
    @Provides
    public AddMedicineUseCase addMedicineUseCase(MedicineRepository medicineRepository, MedicineDetailsEndpoint medicineDetailsEndpoint) {
        return new AddMedicineUseCase(medicineRepository, medicineDetailsEndpoint);
    }
}
