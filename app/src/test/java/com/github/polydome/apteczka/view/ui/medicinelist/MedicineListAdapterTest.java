package com.github.polydome.apteczka.view.ui.medicinelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.view.contract.ShowMedicineContract;
import com.github.polydome.apteczka.view.model.MedicineListModel;

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
    MedicineListModel medicineListModel = Mockito.mock(MedicineListModel.class);
    MedicineListAdapter SUT = new MedicineListAdapter(inflater, medicineViewHolderFactory, medicineListModel);

    @Test
    void onCreateViewHolder_createsViewHolderWithInflatedView() {
        View inflatedView = mock(View.class);
        MedicineViewHolder createdHolder = new MedicineViewHolder(inflatedView, mock(ShowMedicineContract.Presenter.class));

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
    void onCreateViewHolder_callsViewHolderOnAttach() {
        when(inflater.inflate(R.layout.entry_medicine, viewGroup, false))
                .thenReturn(mock(View.class));

        MedicineViewHolder holder = mock(MedicineViewHolder.class);
        when(medicineViewHolderFactory.create(Mockito.any(View.class)))
                .thenReturn(holder);

        SUT.onCreateViewHolder(viewGroup, 0);

        verify(holder).onAttach();
    }

    @Test
    void onBindViewHolder_callsViewHolderBindToPosition() {
        MedicineViewHolder holder = mock(MedicineViewHolder.class);

        SUT.onBindViewHolder(holder, 11);

        verify(holder).bindToPosition(11);
    }

    @Test
    void onViewRecycled_callsViewHolderOnAttach() {
        MedicineViewHolder holder = mock(MedicineViewHolder.class);

        SUT.onViewRecycled(holder);

        verify(holder).onDetach();
    }

    @Test
    void getItemCount_returnsSizeFromModel() {
        when(medicineListModel.getSize()).thenReturn(13);
        int count = SUT.getItemCount();
        assertThat(count, equalTo(13));
    }

    @Test
    void onAttachedToRecyclerView_callsModelOnReady() {
        SUT.onAttachedToRecyclerView(mock(RecyclerView.class));

        verify(medicineListModel).onReady();
    }
}