package com.github.polydome.apteczka.view.ui.medicinelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.domain.usecase.CountMedicineUseCase;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineViewHolder> {
    private final LayoutInflater inflater;
    private final CountMedicineUseCase countMedicineUseCase;

    public MedicineListAdapter(LayoutInflater inflater, CountMedicineUseCase countMedicineUseCase) {
        this.inflater = inflater;
        this.countMedicineUseCase = countMedicineUseCase;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.entry_medicine, parent, false);

        return new MedicineViewHolder(itemView, null);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        holder.setMedicineId(position);
    }

    @Override
    public int getItemCount() {
        return countMedicineUseCase.execute();
    }
}
