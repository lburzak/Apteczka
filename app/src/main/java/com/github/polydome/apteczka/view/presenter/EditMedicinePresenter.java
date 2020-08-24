package com.github.polydome.apteczka.view.presenter;

import com.github.polydome.apteczka.domain.usecase.AddMedicineUseCase;
import com.github.polydome.apteczka.domain.usecase.GetMedicineDataUseCase;
import com.github.polydome.apteczka.domain.usecase.structure.MedicineData;
import com.github.polydome.apteczka.view.contract.EditMedicineContract;
import com.github.polydome.apteczka.view.presenter.common.Presenter;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

public class EditMedicinePresenter extends Presenter<EditMedicineContract.View> implements EditMedicineContract.Presenter {
    private final GetMedicineDataUseCase getMedicineDataUseCase;
    private final AddMedicineUseCase addMedicineUseCase;
    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;

    private final CompositeDisposable comp = new CompositeDisposable();

    @Inject
    public EditMedicinePresenter(GetMedicineDataUseCase getMedicineDataUseCase, AddMedicineUseCase addMedicineUseCase, @Named("ioScheduler") Scheduler ioScheduler, @Named("uiScheduler") Scheduler uiScheduler) {
        this.getMedicineDataUseCase = getMedicineDataUseCase;
        this.addMedicineUseCase = addMedicineUseCase;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void onCurrentDataRequest(long medicineId) {
        comp.add(
            getMedicineDataUseCase.execute(medicineId)
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .subscribe(this::fillViewFields)
        );
    }

    @Override
    public void onSaveMedicine(long id, String title) {
        MedicineData medicineData = MedicineData.builder()
                .title(title)
                .build();

        if (id > 0) {
            // TODO: Update medicine
        } else {
            addMedicineUseCase.execute(medicineData)
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .subscribe();
        }
    }

    @Override
    public void detach() {
        super.detach();
        comp.dispose();
    }

    private void fillViewFields(MedicineData medicineData) {
        requireView().showTitle(medicineData.getTitle());
    }
}
