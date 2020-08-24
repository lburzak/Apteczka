package com.github.polydome.apteczka.view.contract;

import com.github.polydome.apteczka.view.contract.common.Contract;

public interface EditMedicineContract {
    interface View extends Contract.View {
        void showTitle(String title);
    }

    interface Presenter extends Contract.Presenter<View> {
        void onCurrentDataRequest(long medicineId);
        void onSaveMedicine(long id, String title);
    }
}
