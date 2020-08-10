package com.github.polydome.apteczka.data.repository;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;

import com.github.polydome.apteczka.data.dao.MedicineDao;
import com.github.polydome.apteczka.data.db.AppDatabase;
import com.github.polydome.apteczka.data.entity.MedicineEntity;
import com.github.polydome.apteczka.domain.model.Medicine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class RoomMedicineRepositoryTest {
    MedicineDao medicineDao;
    AppDatabase db;
    RoomMedicineRepository roomMedicineRepository;

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
        medicineDao.create(new MedicineEntity(Medicine.builder().build()))
                .test()
                .assertValue(1L);
    }

    @Test
    public void repositoryCreateMedicine_idEquals0_createdMedicineIdEmitted() {
        roomMedicineRepository.create(Medicine.builder().build())
                .test()
                .assertValue(1L);
    }
}