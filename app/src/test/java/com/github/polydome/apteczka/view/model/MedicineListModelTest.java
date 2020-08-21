package com.github.polydome.apteczka.view.model;

import com.github.polydome.apteczka.domain.usecase.ObserveMedicineIdsUseCase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Collections;

import io.reactivex.Observable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class MedicineListModelTest {
    ObserveMedicineIdsUseCase observeMedicineIdsUseCase = mock(ObserveMedicineIdsUseCase.class);
    MedicineListModel SUT = new MedicineListModel(observeMedicineIdsUseCase);

    @Nested
    public class WhenNotReady {
        @Test
        void getIdAtPosition_notReady_throwsIllegalStateException() {
            Assertions.assertThrows(IllegalStateException.class, () ->
                    SUT.getIdAtPosition(33), "Model not ready. Method `onReady` is expected to be called first");
        }

        @Test
        void getSize_throwsIllegalStateException() {
            when(observeMedicineIdsUseCase.execute())
                    .thenReturn(Observable.just(Collections.singletonList(213L)));

            Assertions.assertThrows(IllegalStateException.class, () ->
                    SUT.getSize(), "Model not ready. Method `onReady` is expected to be called first");
        }
    }

    @Nested
    public class WhenReady {
        @Test
        void getIdAtPosition_positionEqualToListSize_throwsIllegalArgumentException() {
            when(observeMedicineIdsUseCase.execute())
                    .thenReturn(Observable.just(Collections.emptyList()));

            SUT.onReady();

            Assertions.assertThrows(IllegalArgumentException.class, () ->
                    SUT.getIdAtPosition(1), "Illegal position [1]");
        }

        @Test
        void getIdAtPosition_positionWithinBounds_returnsId() {
            when(observeMedicineIdsUseCase.execute())
                    .thenReturn(Observable.just(Collections.singletonList(213L)));

            SUT.onReady();

            long id = SUT.getIdAtPosition(0);

            assertThat(id, equalTo(213L));
        }

        @Test
        void getSize_returnsIdsCount() {
            when(observeMedicineIdsUseCase.execute())
                    .thenReturn(Observable.just(Collections.singletonList(213L)));

            SUT.onReady();

            int size = SUT.getSize();
            assertThat(size, equalTo(1));
        }
    }
}