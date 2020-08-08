package com.github.polydome.apteczka.view.presenter;

import com.github.polydome.apteczka.domain.usecase.AddMedicineUseCase;
import com.github.polydome.apteczka.view.contract.AddMedicineContract;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AddMedicinePresenterTest {
    AddMedicineUseCase addMedicineUseCase = Mockito.mock(AddMedicineUseCase.class);
    AddMedicinePresenter SUT = new AddMedicinePresenter(addMedicineUseCase, Schedulers.trampoline(), Schedulers.trampoline());
    AddMedicineContract.View view = Mockito.mock(AddMedicineContract.View.class);

    String EAN = "83617263716253";
    long ID = 13;

    @Test
    public void onCreateMedicine_callsShowMedicineEditorOnView() {
        Mockito.when(addMedicineUseCase.execute(EAN)).thenReturn(
                Single.just(ID)
        );

        SUT.attach(view);
        SUT.onCreateMedicine(EAN);

        Mockito.verify(view).showMedicineEditor(Mockito.eq(ID));
    }
}