package com.github.polydome.apteczka.view.presenter;

import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.usecase.GetMedicineDataUseCase;
import com.github.polydome.apteczka.domain.usecase.structure.MedicineData;
import com.github.polydome.apteczka.view.contract.ShowMedicineContract;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShowMedicinePresenterTest {
    GetMedicineDataUseCase getMedicineDataUseCase = mock(GetMedicineDataUseCase.class);
    ShowMedicineContract.View view = mock(ShowMedicineContract.View.class);
    ShowMedicinePresenter SUT = new ShowMedicinePresenter(getMedicineDataUseCase);

    @Test
    void onIdChanged_medicineExists_writesDataToFields() {
        MedicineData data = MedicineData.builder()
                .commonName("common name")
                .ean("ean")
                .form("test form")
                .name("test name")
                .packagingSize(22)
                .packagingUnit("test unit")
                .potency("test potency")
                .build();

        when(getMedicineDataUseCase.execute(12))
                .thenReturn(Maybe.just(data));

        SUT.attach(view);
        SUT.onIdChanged(12);

        verify(view).showName(data.getName());
    }

    @Test
    void detach_retrievingMedicine_noViewWrites() throws InterruptedException {
        Maybe<MedicineData> dataEmitter = Maybe
                .just(MedicineData.builder().build());

        when(getMedicineDataUseCase.execute(12))
                .thenReturn(dataEmitter.delay(20, TimeUnit.MILLISECONDS));

        SUT.attach(view);
        SUT.onIdChanged(12);

        Thread.sleep(10);
        SUT.detach();

        Thread.sleep(20);
        verifyNoInteractions(view);
    }
}