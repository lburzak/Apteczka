package com.github.polydome.apteczka.view.ui.medicinelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.view.contract.ListMedicineContract;

import javax.inject.Inject;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineViewHolder> implements ListMedicineContract.View {
    private final LayoutInflater inflater;
    private final MedicineViewHolder.Factory viewHolderFactory;
    private final ListMedicineContract.Presenter presenter;

    private int medicineCount = 0;

    @Inject
    public MedicineListAdapter(LayoutInflater inflater, MedicineViewHolder.Factory viewHolderFactory, ListMedicineContract.Presenter presenter) {
        this.inflater = inflater;
        this.viewHolderFactory = viewHolderFactory;
        this.presenter = presenter;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        presenter.attach(this);
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
        presenter.onMedicineCountRequested();
        return medicineCount;
    }

    @Override
    public void updateMedicineCount(int count) {
        medicineCount = count;
        notifyDataSetChanged();
    }
}
