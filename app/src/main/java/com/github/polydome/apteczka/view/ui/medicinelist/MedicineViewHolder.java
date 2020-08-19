package com.github.polydome.apteczka.view.ui.medicinelist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.view.contract.ShowMedicineContract;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Provider;

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

    public static class Factory {
        private final Provider<ShowMedicineContract.Presenter> presenterProvider;

        @Inject
        public Factory(Provider<ShowMedicineContract.Presenter> presenterProvider) {
            this.presenterProvider = presenterProvider;
        }

        MedicineViewHolder create(@NotNull View itemView) {
            return new MedicineViewHolder(itemView, presenterProvider.get());
        }
    }
}
