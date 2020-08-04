package com.github.polydome.domain.usecase;

import com.github.polydome.domain.model.MedicineDetails;
import com.github.polydome.domain.service.MedicineDetailsEndpoint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import io.reactivex.Maybe;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetMedicineDetailsUseCaseTest {
    MedicineDetailsEndpoint medicineDetailsEndpoint = Mockito.mock(MedicineDetailsEndpoint.class);
    GetMedicineDetailsUseCase SUT = new GetMedicineDetailsUseCase(medicineDetailsEndpoint);

    String EAN = "826194738292";

    @Test
    public void execute_medicineUnknown_emitsEmpty() {
        Mockito.when(medicineDetailsEndpoint.fetchMedicineDetails(Mockito.eq(EAN))).thenReturn(Maybe.<MedicineDetails>empty());

        SUT.execute(EAN).test().assertNoValues();
    }

    @Test
    public void execute_medicineDetailsAvailable_emitsMedicineDetails() {
        MedicineDetails DETAILS = new MedicineDetails("Some medicine");

        Mockito.when(medicineDetailsEndpoint.fetchMedicineDetails(Mockito.eq(EAN))).thenReturn(Maybe.just(DETAILS));

        SUT.execute(EAN).test().assertValue(DETAILS);
    }
}