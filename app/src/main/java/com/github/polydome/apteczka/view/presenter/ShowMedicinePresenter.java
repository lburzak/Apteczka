package com.github.polydome.apteczka.view.presenter;

import com.github.polydome.apteczka.domain.usecase.GetMedicineDataUseCase;
import com.github.polydome.apteczka.domain.usecase.structure.MedicineData;
import com.github.polydome.apteczka.view.contract.ShowMedicineContract;
import com.github.polydome.apteczka.view.presenter.common.Presenter;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

public class ShowMedicinePresenter extends Presenter<ShowMedicineContract.View> implements ShowMedicineContract.Presenter {
    private final GetMedicineDataUseCase getMedicineDataUseCase;
    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;

    private final CompositeDisposable comp = new CompositeDisposable();

    @Inject
    public ShowMedicinePresenter(GetMedicineDataUseCase getMedicineDataUseCase,
                                 @Named("ioScheduler") Scheduler ioScheduler,
                                 @Named("uiScheduler") Scheduler uiScheduler) {
        this.getMedicineDataUseCase = getMedicineDataUseCase;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void onIdChanged(long medicineId) {
        comp.add(
            getMedicineDataUseCase.execute(medicineId)
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .subscribe(this::showFields)
        );
    }

    @Override
    public void detach() {
        comp.dispose();
        super.detach();
    }

    private void showFields(MedicineData medicineData) {
        requireView().showName(medicineData.getName());
    }
}
