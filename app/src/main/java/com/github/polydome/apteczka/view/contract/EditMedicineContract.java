package com.github.polydome.apteczka.view.contract;

import com.github.polydome.apteczka.view.contract.common.Contract;

public interface EditMedicineContract {
    interface View extends Contract.View {
        void showEan(String ean);
    }

    interface Presenter extends Contract.Presenter<View> {
        void onCurrentDataRequest(long medicineId);
    }
}
