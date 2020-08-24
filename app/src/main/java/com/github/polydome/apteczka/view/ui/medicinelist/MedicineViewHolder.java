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
    private TextView titleField;

    public MedicineViewHolder(@NonNull View itemView, ShowMedicineContract.Presenter presenter) {
        super(itemView);
        titleField = itemView.findViewById(R.id.medicineEntry_title);

        this.presenter = presenter;
    }

    @Override
    public void showTitle(String title) {
        titleField.setText(title);
    }

    public void onAttach() {
        presenter.attach(this);
    }

    public void onDetach() {
        presenter.detach();
    }

    public void bindToPosition(int position) {
        presenter.onPositionChanged(position);
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
