package com.github.polydome.apteczka.view.presenter;

import com.github.polydome.apteczka.view.contract.ShowMedicineContract;
import com.github.polydome.apteczka.view.presenter.common.Presenter;

import javax.inject.Inject;

public class ShowMedicinePresenter extends Presenter<ShowMedicineContract.View> implements ShowMedicineContract.Presenter {
    @Inject
    public ShowMedicinePresenter() {
    }

    @Override
    public void onIdChanged(long medicineId) {

    }
}
