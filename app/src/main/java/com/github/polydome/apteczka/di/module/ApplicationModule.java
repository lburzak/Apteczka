package com.github.polydome.apteczka.di.module;

import android.content.Context;

import com.github.polydome.apteczka.BuildConfig;
import com.github.polydome.apteczka.domain.usecase.ObserveMedicineIdsUseCase;
import com.github.polydome.apteczka.view.model.MedicineListModel;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DomainModule.class})
public class ApplicationModule {
    private final Context applicationContext;

    public ApplicationModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    @Named("applicationContext")
    public Context applicationContext() {
        return applicationContext;
    }

    @Provides
    @Named("remedyUrl")
    public String remedyUrl() {
        return BuildConfig.REMEDY_URL;
    }

    @Provides
    @Singleton
    public MedicineListModel medicineListModel(ObserveMedicineIdsUseCase observeMedicineIdsUseCase) {
        return new MedicineListModel(observeMedicineIdsUseCase);
    }
}
