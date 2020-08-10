package com.github.polydome.apteczka.view.contract;

import com.github.polydome.apteczka.view.contract.common.Contract;

public interface AddMedicineContract {
    interface View extends Contract.View {
        void showMedicineEditor(long medicineId);
    }

    interface Presenter extends Contract.Presenter<View> {
        void onCreateMedicine(String ean);
    }
}
