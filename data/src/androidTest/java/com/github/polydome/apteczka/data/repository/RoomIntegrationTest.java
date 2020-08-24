package com.github.polydome.apteczka.data.repository;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.MediumTest;

import com.github.polydome.apteczka.data.dao.MedicineDao;
import com.github.polydome.apteczka.data.db.AppDatabase;
import com.github.polydome.apteczka.domain.model.Medicine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.reactivex.MaybeSource;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertNotNull;

@MediumTest
public class RoomIntegrationTest {
    MedicineDao medicineDao;
    AppDatabase db;
    RoomMedicineRepository roomMedicineRepository;

    final String TITLE = "test title";

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room
                .inMemoryDatabaseBuilder(context, AppDatabase.class)
                .build();
        medicineDao = db.medicineDao();
        roomMedicineRepository = new RoomMedicineRepository(medicineDao);
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void createMedicine_idEquals0_createdMedicineIdEmitted() {
        roomMedicineRepository.create(Medicine.builder().title(TITLE).build()).test()
                .assertValue(1L);
    }

    @Test
    public void createMedicine_idEquals0_medicineCreated() {
        roomMedicineRepository.create(Medicine.builder().title(TITLE).build())
                .flatMapMaybe(id -> roomMedicineRepository.findById(id))
                .test()
                .assertValue(medicine -> medicine.getTitle().equals(TITLE));
    }

    @Test
    public void createMedicine_idEquals0_medicinePropertiesProperlySaved() {
        roomMedicineRepository.create(createMedicine(0))
                .flatMapMaybe((Function<Long, MaybeSource<Medicine>>) aLong -> roomMedicineRepository.findById(1L))
                .test()
                .assertValue(medicine -> {
                    assertNotNull(medicine.getTitle());

                    return medicine.getTitle().equals(TITLE)
                            && medicine.getId() == 1L;
                });
    }

    @Test
    public void findById_medicineNotExists_empty() {
        medicineDao.findById(1L).test()
                .assertNoValues()
                .assertComplete();
    }

    @Test
    public void ids_noMedicineInRepository_emitsEmptyList() {
        TestObserver<List<Long>> test = roomMedicineRepository.ids().test();
        test.awaitCount(1).assertValue(List::isEmpty);
    }

    @Test
    public void ids_1medicineInRepository_emitsListContainingMedicineId() {
        TestObserver<List<Long>> test = roomMedicineRepository.ids().test();
        roomMedicineRepository.create(createMedicine(2L)).blockingGet();
        test.assertValue(ids -> ids.contains(2L));
    }

    private Medicine createMedicine(long id) {
        return Medicine.builder()
                .id(id)
                .title(TITLE)
                .build();
    }
}
