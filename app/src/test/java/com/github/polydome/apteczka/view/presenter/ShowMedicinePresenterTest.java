package com.github.polydome.apteczka.view.presenter;

import com.github.polydome.apteczka.domain.usecase.GetMedicineDataUseCase;
import com.github.polydome.apteczka.domain.usecase.structure.MedicineData;
import com.github.polydome.apteczka.view.contract.ShowMedicineContract;
import com.github.polydome.apteczka.view.model.MedicineListModel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class ShowMedicinePresenterTest {
    GetMedicineDataUseCase getMedicineDataUseCase = mock(GetMedicineDataUseCase.class);
    ShowMedicineContract.View view = mock(ShowMedicineContract.View.class);
    MedicineListModel model = mock(MedicineListModel.class);
    ShowMedicinePresenter SUT = new ShowMedicinePresenter(getMedicineDataUseCase, Schedulers.trampoline(), Schedulers.trampoline(), model);

    @Test
    void onPositionChanged_legalPosition_writesDataToFields() {
        MedicineData data = MedicineData.builder()
                .commonName("common name")
                .ean("ean")
                .form("test form")
                .name("test name")
                .packagingSize(22)
                .packagingUnit("test unit")
                .potency("test potency")
                .build();

        when(model.getIdAtPosition(12))
                .thenReturn(38L);

        when(getMedicineDataUseCase.execute(38L))
                .thenReturn(Maybe.just(data));


        SUT.attach(view);
        SUT.onPositionChanged(12);

        verify(view).showName(data.getName());
    }

    @Test
    void detach_retrievingMedicine_noViewWrites() throws InterruptedException {
        Maybe<MedicineData> dataEmitter = Maybe
                .just(MedicineData.builder().build());

        when(model.getIdAtPosition(12))
                .thenReturn(12L);

        when(getMedicineDataUseCase.execute(12))
                .thenReturn(dataEmitter.delay(20, TimeUnit.MILLISECONDS));

        SUT.attach(view);
        SUT.onPositionChanged(12);

        Thread.sleep(10);
        SUT.detach();

        Thread.sleep(20);
        verifyNoInteractions(view);
    }
}