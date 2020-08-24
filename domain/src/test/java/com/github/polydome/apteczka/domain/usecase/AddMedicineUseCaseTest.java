package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.common.MedicineRepositoryStub;
import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.usecase.structure.MedicineData;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToObject;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AddMedicineUseCaseTest {
    MedicineRepositoryStub medicineRepository = new MedicineRepositoryStub();
    AddMedicineUseCase SUT = new AddMedicineUseCase(medicineRepository);

    @Test
    void execute_noMedicinesInRepository_medicineCreated() {
        MedicineData medicineData = MedicineData.builder()
            .title("test title")
            .build();

        medicineRepository.data = new ArrayList<>();

        SUT.execute(medicineData).test().assertComplete();

        assertThat(medicineRepository.data, Matchers.<Medicine>hasSize(1));
        assertThat(((ArrayList<Medicine>) medicineRepository.data).get(0), equalToObject(medicineData.toMedicine(0)));
    }
}