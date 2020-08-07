package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.common.MedicineRepositoryStub;
import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.service.MedicineDetails;
import com.github.polydome.apteczka.domain.service.MedicineDetailsEndpoint;
import com.github.polydome.apteczka.domain.usecase.exception.DuplicateMedicineException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import io.reactivex.Maybe;
import io.reactivex.functions.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToObject;
import static org.hamcrest.Matchers.hasItem;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AddMedicineUseCaseTest {
    MedicineRepositoryStub medicineRepository = new MedicineRepositoryStub();
    MedicineDetailsEndpoint medicineDetailsEndpoint = Mockito.mock(MedicineDetailsEndpoint.class);
    AddMedicineUseCase SUT = new AddMedicineUseCase(medicineRepository, medicineDetailsEndpoint);

    String EAN = "826194738292";

    @BeforeEach
    public void setUpOne() {
        Mockito.when(medicineDetailsEndpoint.fetchMedicineDetails(EAN))
                .thenReturn(
                        Maybe.<MedicineDetails>empty()
                );
    }

    @AfterEach
    public void tearDownOne() {
        Mockito.reset(medicineDetailsEndpoint);
    }

    @Test
    void execute_noMedicinesInRepository_medicineCreated() {
        medicineRepository.data = new ArrayList<>();

        SUT.execute(EAN).test().assertComplete();

        assertThat(medicineRepository.data, Matchers.<Medicine>hasSize(1));
        assertThat(((ArrayList<Medicine>) medicineRepository.data).get(0), equalToObject(Medicine.builder().id(0).ean(EAN).build()));
    }

    @Test
    void execute_medicineWithGivenEANAlreadyExists_exceptionThrown() {
        medicineRepository.data = Collections.singletonList(Medicine.builder().ean(EAN).build());

        SUT.execute(EAN).test().assertError(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                return throwable instanceof DuplicateMedicineException && throwable.getMessage().equals(String.format("Medicine identified with EAN %s already exists", EAN));
            }
        });
    }

    @Test
    void execute_medicineDetailsAvailable_medicineWithDetailsCreated() {
        medicineRepository.data = new LinkedList<>();
        MedicineDetails details = createMedicineDetails();

        Mockito.when(medicineDetailsEndpoint.fetchMedicineDetails(EAN)).thenReturn(
                Maybe.just(details)
        );

        SUT.execute(EAN).test().assertComplete();

        assertThat(medicineRepository.data, Matchers.<Medicine>hasSize(1));
        assertThat(medicineRepository.data, hasItem(
                Medicine.builder()
                    .id(0)
                    .ean(EAN)
                    .commonName(details.getCommonName())
                    .form(details.getForm())
                    .name(details.getName())
                    .packagingSize(details.getPackagingSize())
                    .packagingUnit(details.getPackagingUnit())
                    .potency(details.getPotency())
                    .build()
        ));
    }

    @Test
    void execute_medicineDetailsNotAvailable_medicineWithDefaultDetailsCreated() {
        medicineRepository.data = new LinkedList<>();

        Mockito.when(medicineDetailsEndpoint.fetchMedicineDetails(EAN)).thenReturn(
                Maybe.<MedicineDetails>empty()
        );

        SUT.execute(EAN).test().assertComplete();

        assertThat(medicineRepository.data, Matchers.<Medicine>hasSize(1));
        assertThat(medicineRepository.data, hasItem(
                Medicine.builder()
                        .id(0)
                        .ean(EAN)
                        .build()
        ));
    }

    private MedicineDetails createMedicineDetails() {
        return MedicineDetails.builder()
                .form("test form")
                .commonName("test common name")
                .name("test name")
                .packagingSize(2)
                .packagingUnit("test unit")
                .potency("test potency")
                .build();
    }
}