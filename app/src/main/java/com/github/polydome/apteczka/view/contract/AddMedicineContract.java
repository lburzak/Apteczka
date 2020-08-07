package com.github.polydome.apteczka.view.contract;

public interface AddMedicineContract {
    interface View {
        void showMedicineEditor(long medicineId);
    }

    interface Presenter {
        void onCreateMedicine(String ean);

        void attach(View view);
        void detach();
    }
}
