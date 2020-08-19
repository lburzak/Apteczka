package com.github.polydome.apteczka.view.ui.medicinelist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.view.contract.ShowMedicineContract;

public class MedicineViewHolder extends RecyclerView.ViewHolder implements ShowMedicineContract.View {
    private final ShowMedicineContract.Presenter presenter;

    public MedicineViewHolder(@NonNull View itemView, ShowMedicineContract.Presenter presenter) {
        super(itemView);
        this.presenter = presenter;
    }

    public void setMedicineId(long id) {
        presenter.onIdChanged(id);
    }

    @Override
    public void showName(String name) {
        TextView nameField = itemView.findViewById(R.id.medicineEntry_name);
        nameField.setText(name);
    }
}
