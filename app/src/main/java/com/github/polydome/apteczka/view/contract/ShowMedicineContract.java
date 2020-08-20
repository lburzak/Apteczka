package com.github.polydome.apteczka.view.contract;

import com.github.polydome.apteczka.view.contract.common.Contract;

public interface ShowMedicineContract extends Contract {
    interface View extends Contract.View {
        void showName(String name);
    }

    interface Presenter extends Contract.Presenter<View> {
        void onPositionChanged(int position);
    }
}
