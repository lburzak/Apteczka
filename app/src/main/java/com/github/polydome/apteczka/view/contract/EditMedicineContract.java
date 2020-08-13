package com.github.polydome.apteczka.view.contract;

import com.github.polydome.apteczka.view.contract.common.Contract;

public interface EditMedicineContract {
    interface View extends Contract.View {
        void showEan(String ean);
        void showName(String name);
        void showCommonName(String commonName);
        void showPotency(String potency);
        void showForm(String form);
        void showPackagingSize(int size);
        void showPackagingUnit(String unit);
    }

    interface Presenter extends Contract.Presenter<View> {
        void onCurrentDataRequest(long medicineId);
        void onEanScanned(String ean);
    }
}
