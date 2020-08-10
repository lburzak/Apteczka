package com.github.polydome.apteczka.di.module;

import com.github.polydome.apteczka.view.contract.AddMedicineContract;
import com.github.polydome.apteczka.view.contract.EditMedicineContract;
import com.github.polydome.apteczka.view.presenter.AddMedicinePresenter;
import com.github.polydome.apteczka.view.presenter.EditMedicinePresenter;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DomainModule.class, SchedulerModule.class})
public class PresentationModule {
    @Provides
    public AddMedicineContract.Presenter addMedicinePresenter(AddMedicinePresenter addMedicinePresenter) {
        return addMedicinePresenter;
    }

    @Provides
    public EditMedicineContract.Presenter editMedicinePresenter(EditMedicinePresenter editMedicinePresenter) {
        return editMedicinePresenter;
    }
}
