package com.github.polydome.apteczka.view.presenter;

import com.github.polydome.apteczka.domain.usecase.CountMedicineUseCase;
import com.github.polydome.apteczka.view.contract.ListMedicineContract;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class ListMedicinePresenterTest {
    CountMedicineUseCase countMedicineUseCase = mock(CountMedicineUseCase.class);
    ListMedicineContract.View view = mock(ListMedicineContract.View.class);
    ListMedicinePresenter SUT = new ListMedicinePresenter(countMedicineUseCase, Schedulers.trampoline(), Schedulers.trampoline());

    @Test
    void onMedicineCountRequested_callsViewUpdateMedicineCount() {
        when(countMedicineUseCase.execute())
                .thenReturn(Single.just(38));

        SUT.attach(view);
        SUT.onMedicineCountRequested();

        verify(view).updateMedicineCount(38);
    }

    @Test
    void onMedicineCountRequested_viewDetached_noInteractionsWithView() throws InterruptedException {
        when(countMedicineUseCase.execute())
                .thenReturn(Single.just(38).delay(20, TimeUnit.MILLISECONDS));

        SUT.attach(view);
        SUT.onMedicineCountRequested();

        Thread.sleep(10);
        SUT.detach();

        Thread.sleep(20);
        verifyNoInteractions(view);
    }
}