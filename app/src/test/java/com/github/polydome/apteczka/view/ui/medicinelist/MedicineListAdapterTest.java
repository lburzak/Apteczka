package com.github.polydome.apteczka.view.ui.medicinelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.domain.usecase.CountMedicineUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MedicineListAdapterTest {
    LayoutInflater inflater = Mockito.mock(LayoutInflater.class);
    ViewGroup viewGroup = Mockito.mock(ViewGroup.class);
    CountMedicineUseCase countMedicineUseCase = mock(CountMedicineUseCase.class);
    MedicineListAdapter SUT = new MedicineListAdapter(inflater, countMedicineUseCase);

    @Test
    void onCreateViewHolder_createsViewHolderWithInflatedView() {
        View inflatedView = mock(View.class);

        when(inflater.inflate(R.layout.entry_medicine, viewGroup, false))
                .thenReturn(inflatedView);

        RecyclerView.ViewHolder holder = SUT.onCreateViewHolder(viewGroup, 0);

        assertThat(holder.itemView, is(inflatedView));
    }

    @Test
    void onBindViewHolder_setsViewHolderMedicineId() {
        MedicineViewHolder holder = mock(MedicineViewHolder.class);

        SUT.onBindViewHolder(holder, 11);

        verify(holder).setMedicineId(11);
    }

    @Test
    void getItemCount_medicineInRepository_returnsMedicineInRepositoryCount() {
        when(countMedicineUseCase.execute()).thenReturn(38);

        assertThat(SUT.getItemCount(), equalTo(38));
    }
}