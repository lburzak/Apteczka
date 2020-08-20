package com.github.polydome.apteczka.view.ui.medicinelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.view.contract.ListMedicineContract;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class MedicineListAdapterTest {
    LayoutInflater inflater = Mockito.mock(LayoutInflater.class);
    ViewGroup viewGroup = Mockito.mock(ViewGroup.class);
    MedicineViewHolder.Factory medicineViewHolderFactory = mock(MedicineViewHolder.Factory.class);
    ListMedicineContract.Presenter presenter = Mockito.mock(ListMedicineContract.Presenter.class);
    MedicineListAdapter SUT = new MedicineListAdapter(inflater, medicineViewHolderFactory, presenter);

    @Test
    void onCreateViewHolder_createsViewHolderWithInflatedView() {
        View inflatedView = mock(View.class);
        MedicineViewHolder createdHolder = new MedicineViewHolder(inflatedView, null);

        when(medicineViewHolderFactory.create(Mockito.any(View.class)))
                .thenReturn(createdHolder);

        when(inflater.inflate(R.layout.entry_medicine, viewGroup, false))
                .thenReturn(inflatedView);

        RecyclerView.ViewHolder holder = SUT.onCreateViewHolder(viewGroup, 0);

        assertThat(holder.itemView, is(inflatedView));
    }

    @Test
    void onCreateViewHolder_returnsViewHolderFromFactory() {
        when(inflater.inflate(R.layout.entry_medicine, viewGroup, false))
                .thenReturn(mock(View.class));

        MedicineViewHolder expectedHolder = mock(MedicineViewHolder.class);
        when(medicineViewHolderFactory.create(Mockito.any(View.class)))
                .thenReturn(expectedHolder);

        RecyclerView.ViewHolder holder = SUT.onCreateViewHolder(viewGroup, 0);

        assertThat(holder, Matchers.sameInstance(expectedHolder));
    }

    @Test
    void onBindViewHolder_setsViewHolderMedicineId() {
        MedicineViewHolder holder = mock(MedicineViewHolder.class);

        SUT.onBindViewHolder(holder, 11);

        verify(holder).setMedicineId(11);
    }

    @Test
    void getItemCount_noUpdates_returns0() {
        int count = SUT.getItemCount();
        assertThat(count, equalTo(0));
    }

    @Test
    void getItemCount_requestsUpdatedCount() {
        SUT.getItemCount();
        verify(presenter).onMedicineCountRequested();
    }
}