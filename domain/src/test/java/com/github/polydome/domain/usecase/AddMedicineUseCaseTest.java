package com.github.polydome.domain.usecase;

import com.github.polydome.domain.common.MedicineRepositoryStub;
import com.github.polydome.domain.model.Medicine;
import com.github.polydome.domain.usecase.exception.DuplicateMedicineException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.functions.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToObject;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddMedicineUseCaseTest {
    MedicineRepositoryStub medicineRepository = new MedicineRepositoryStub();
    AddMedicineUseCase SUT = new AddMedicineUseCase(medicineRepository);

    String EAN = "826194738292";

    @Test
    void execute_noMedicinesInRepository_medicineCreated() {
        medicineRepository.data = new ArrayList<>();

        SUT.execute(EAN).test().assertComplete();

        assertThat(medicineRepository.data, Matchers.<Medicine>hasSize(1));
        assertThat(((ArrayList<Medicine>) medicineRepository.data).get(0), equalToObject(new Medicine(EAN)));
    }

    @Test
    void execute_medicineWithGivenEANAlreadyExists_exceptionThrown() {
        medicineRepository.data = Collections.singletonList(new Medicine(EAN));

        SUT.execute(EAN).test().assertError(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                return throwable instanceof DuplicateMedicineException && throwable.getMessage().equals(String.format("Medicine identified with EAN %s already exists", EAN));
            }
        });
    }
}