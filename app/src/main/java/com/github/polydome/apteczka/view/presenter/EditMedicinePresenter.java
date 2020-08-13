package com.github.polydome.apteczka.view.presenter;

import com.github.polydome.apteczka.domain.usecase.GetMedicineDataUseCase;
import com.github.polydome.apteczka.domain.usecase.structure.MedicineData;
import com.github.polydome.apteczka.view.contract.EditMedicineContract;
import com.github.polydome.apteczka.view.presenter.common.Presenter;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class EditMedicinePresenter extends Presenter<EditMedicineContract.View> implements EditMedicineContract.Presenter {
    private final GetMedicineDataUseCase getMedicineDataUseCase;
    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;

    private final CompositeDisposable comp = new CompositeDisposable();

    @Inject
    public EditMedicinePresenter(GetMedicineDataUseCase getMedicineDataUseCase, @Named("ioScheduler") Scheduler ioScheduler, @Named("uiScheduler") Scheduler uiScheduler) {
        this.getMedicineDataUseCase = getMedicineDataUseCase;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void onCurrentDataRequest(long medicineId) {
        comp.add(
            getMedicineDataUseCase.execute(medicineId)
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .subscribe(new Consumer<MedicineData>() {
                        @Override
                        public void accept(MedicineData medicineData) {
                            fillViewFields(medicineData);
                        }
                    })
        );
    }

    @Override
    public void onEanScanned(String ean) {
        // TODO: Implement
    }

    @Override
    public void detach() {
        super.detach();
        comp.dispose();
    }

    private void fillViewFields(MedicineData medicineData) {
        requireView().showEan(medicineData.getEan());
        requireView().showCommonName(medicineData.getCommonName());
        requireView().showForm(medicineData.getForm());
        requireView().showName(medicineData.getName());
        requireView().showPackagingSize(medicineData.getPackagingSize());
        requireView().showPackagingUnit(medicineData.getPackagingUnit());
        requireView().showPotency(medicineData.getPotency());
    }
}
