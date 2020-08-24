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
    private final GetMedicineDetailsUseCase getMedicineDetailsUseCase;
    private final AddMedicineUseCase addMedicineUseCase;
    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;

    private final CompositeDisposable comp = new CompositeDisposable();

    @Inject
    public EditMedicinePresenter(GetMedicineDataUseCase getMedicineDataUseCase, GetMedicineDetailsUseCase getMedicineDetailsUseCase, AddMedicineUseCase addMedicineUseCase, @Named("ioScheduler") Scheduler ioScheduler, @Named("uiScheduler") Scheduler uiScheduler) {
        this.getMedicineDataUseCase = getMedicineDataUseCase;
        this.getMedicineDetailsUseCase = getMedicineDetailsUseCase;
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
    public void onEanInput(String ean) {
        comp.add(
                getMedicineDetailsUseCase.execute(ean)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe((this::fillViewFields))
        );
    }

    @Override
    public void onSaveMedicine(long id, String ean, String name, String commonName, String potency, String form, int packagingSize, String packagingUnit) {
        MedicineData medicineData = MedicineData.builder()
                .packagingUnit(packagingUnit)
                .potency(potency)
                .packagingSize(packagingSize)
                .name(name)
                .form(form)
                .commonName(commonName)
                .ean(ean)
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
        requireView().showEan(medicineData.getEan());
        requireView().showCommonName(medicineData.getCommonName());
        requireView().showForm(medicineData.getForm());
        requireView().showName(medicineData.getName());
        requireView().showPackagingSize(medicineData.getPackagingSize());
        requireView().showPackagingUnit(medicineData.getPackagingUnit());
        requireView().showPotency(medicineData.getPotency());
    }
    
    private void fillViewFields(MedicineDetails medicineDetails) {
        requireView().showCommonName(medicineDetails.getCommonName());
        requireView().showForm(medicineDetails.getForm());
        requireView().showName(medicineDetails.getName());
        requireView().showPackagingSize(medicineDetails.getPackagingSize());
        requireView().showPackagingUnit(medicineDetails.getPackagingUnit());
        requireView().showPotency(medicineDetails.getPotency());
    }
}
