package com.github.polydome.apteczka.view.presenter;

import com.github.polydome.apteczka.domain.usecase.GetMedicineDataUseCase;
import com.github.polydome.apteczka.domain.usecase.structure.MedicineData;
import com.github.polydome.apteczka.view.contract.ShowMedicineContract;
import com.github.polydome.apteczka.view.presenter.common.Presenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ShowMedicinePresenter extends Presenter<ShowMedicineContract.View> implements ShowMedicineContract.Presenter {
    private final GetMedicineDataUseCase getMedicineDataUseCase;

    private final CompositeDisposable comp = new CompositeDisposable();

    @Inject
    public ShowMedicinePresenter(GetMedicineDataUseCase getMedicineDataUseCase) {
        this.getMedicineDataUseCase = getMedicineDataUseCase;
    }

    @Override
    public void onIdChanged(long medicineId) {
        comp.add(
            getMedicineDataUseCase.execute(medicineId)
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
