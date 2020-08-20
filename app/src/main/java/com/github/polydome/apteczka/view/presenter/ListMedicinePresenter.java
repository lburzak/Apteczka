package com.github.polydome.apteczka.view.presenter;

import com.github.polydome.apteczka.view.contract.ListMedicineContract;
import com.github.polydome.apteczka.view.presenter.common.Presenter;

import javax.inject.Inject;

public class ListMedicinePresenter extends Presenter<ListMedicineContract.View> implements ListMedicineContract.Presenter {
    @Inject
    public ListMedicinePresenter() {
    }

    @Override
    public void onMedicineCountRequested() {

    }
}
