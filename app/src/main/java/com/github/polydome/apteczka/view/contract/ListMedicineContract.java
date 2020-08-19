package com.github.polydome.apteczka.view.contract;

import com.github.polydome.apteczka.view.contract.common.Contract;

public interface ListMedicineContract extends Contract {
    interface View extends Contract.View {
        void updateMedicineCount(int count);
    }

    interface Presenter extends Contract.Presenter<View> {
        void onMedicineCountRequested();
    }
}
