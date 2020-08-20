package com.github.polydome.apteczka.domain.usecase;

import com.github.polydome.apteczka.domain.repository.MedicineRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import io.reactivex.Single;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class CountMedicineUseCaseTest {
    MedicineRepository medicineRepository = mock(MedicineRepository.class);
    CountMedicineUseCase SUT = new CountMedicineUseCase(medicineRepository);

    @Test
    void execute_medicineInRepository_emitsCount() {
        when(medicineRepository.count())
                .thenReturn(Single.just(42));

        SUT.execute().test().assertValue(42);
    }
}