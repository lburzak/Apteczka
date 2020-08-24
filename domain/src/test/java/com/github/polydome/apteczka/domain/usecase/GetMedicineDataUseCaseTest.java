package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.common.MedicineRepositoryStub;
import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.usecase.structure.MedicineData;
import com.sun.tools.javac.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Collections;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetMedicineDataUseCaseTest {
    private final MedicineRepositoryStub medicineRepository = new MedicineRepositoryStub();
    private final GetMedicineDataUseCase SUT = new GetMedicineDataUseCase(medicineRepository);

    String TITLE = "test title";
    int ID = 137;

    @Test
    public void execute_medicineNotExists_empty() {
        medicineRepository.data = Collections.emptyList();

        SUT.execute(ID).test().assertNoValues();
    }

    @Test
    public void execute_medicineExists_emitsMedicineData() {
        medicineRepository.data = List.of(Medicine.builder().id(ID).title(TITLE).build());

        SUT.execute(ID).test().assertValue(
                MedicineData.builder()
                .title(TITLE)
                .build()
        );
    }
}