package com.github.polydome.apteczka.view.ui.medicinelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.view.model.MedicineListModel;

import javax.inject.Inject;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineViewHolder> {
    private final LayoutInflater inflater;
    private final MedicineViewHolder.Factory viewHolderFactory;
    private final MedicineListModel model;

    @Inject
    public MedicineListAdapter(LayoutInflater inflater, MedicineViewHolder.Factory viewHolderFactory, MedicineListModel model) {
        this.inflater = inflater;
        this.viewHolderFactory = viewHolderFactory;
        this.model = model;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.entry_medicine, parent, false);
        MedicineViewHolder holder = viewHolderFactory.create(itemView);
        holder.onAttach();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        holder.bindToPosition(position);
    }

    @Override
    public int getItemCount() {
        return model.getSize();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        model.onReady();
    }

    @Override
    public void onViewRecycled(@NonNull MedicineViewHolder holder) {
        holder.onDetach();
    }
}
