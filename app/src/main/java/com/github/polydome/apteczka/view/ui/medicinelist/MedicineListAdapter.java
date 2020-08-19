package com.github.polydome.apteczka.view.ui.medicinelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.view.contract.ListMedicineContract;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineViewHolder> implements ListMedicineContract.View {
    private final LayoutInflater inflater;
    private final MedicineViewHolder.Factory viewHolderFactory;

    private int medicineCount = 0;

    public MedicineListAdapter(LayoutInflater inflater, MedicineViewHolder.Factory viewHolderFactory) {
        this.inflater = inflater;
        this.viewHolderFactory = viewHolderFactory;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.entry_medicine, parent, false);

        return viewHolderFactory.create(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        holder.setMedicineId(position);
    }

    @Override
    public int getItemCount() {
        return medicineCount;
    }

    @Override
    public void updateMedicineCount(int count) {
        medicineCount = count;
        notifyDataSetChanged();
    }
}
