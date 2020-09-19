package com.github.polydome.apteczka.di.module;

import com.github.polydome.apteczka.domain.usecase.FetchProductDataUseCase;
import com.github.polydome.apteczka.view.ui.medicineeditor.MedicineEditorViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module(includes = DomainModule.class)
public class ViewModelFactoryModule {
    @Provides
    @Named("Factory")
    public MedicineEditorViewModel medicineEditorViewModel(
            FetchProductDataUseCase fetchProductDataUseCase,
            @Named("ioScheduler") Scheduler ioScheduler,
            @Named("uiScheduler") Scheduler uiScheduler)
    {
        return new MedicineEditorViewModel(fetchProductDataUseCase, ioScheduler, uiScheduler);
    }
}
