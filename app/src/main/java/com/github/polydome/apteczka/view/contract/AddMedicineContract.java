package com.github.polydome.apteczka.view.contract;

public interface AddMedicineContract {
    interface View {
        void showMedicineEditor(int medicineId);
    }

    interface Presenter {
        void onCreateMedicine(String ean);
    }
}
