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

import io.reactivex.MaybeSource;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import static org.junit.Assert.assertNotNull;

@MediumTest
public class RoomIntegrationTest {
    MedicineDao medicineDao;
    AppDatabase db;
    RoomMedicineRepository roomMedicineRepository;

    final String EAN = "5909990663613";
    final String NAME = "Herbapect";
    final String COMMON_NAME = "Thymi herbae extractum + Primulae radicis tinctura + Sulfogaiacolum";
    final String POTENCY = "(498 mg + 348 mg + 87 mg)/5 ml";
    final String FORM = "syrop";
    final String PACKAGE_UNIT = "butelka 125 ml";
    final int PACKAGE_SIZE = 1;

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
        roomMedicineRepository.create(Medicine.builder().ean(EAN).name(NAME).build()).test()
                .assertValue(1L);
    }

    @Test
    public void createMedicine_idEquals0_medicineCreated() {
        roomMedicineRepository.create(Medicine.builder().ean(EAN).build())
                .flatMap(new Function<Long, SingleSource<?>>() {
                    @Override
                    public SingleSource<?> apply(Long aLong) {
                        return roomMedicineRepository.exists(EAN);
                    }
                })
                .test()
                .assertValue(true);
    }

    @Test
    public void createMedicine_idEquals0_medicinePropertiesProperlySaved() {
        roomMedicineRepository.create(createMedicine(0))
                .flatMapMaybe(new Function<Long, MaybeSource<Medicine>>() {
                    @Override
                    public MaybeSource<Medicine> apply(Long aLong) throws Exception {
                        return roomMedicineRepository.findById(1L);
                    }
                }).test()
                .assertValue(new Predicate<Medicine>() {
                    @Override
                    public boolean test(Medicine medicine) throws Exception {
                        assertNotNull(medicine.getName());
                        assertNotNull(medicine.getEan());
                        assertNotNull(medicine.getCommonName());
                        assertNotNull(medicine.getPotency());
                        assertNotNull(medicine.getForm());
                        assertNotNull(medicine.getPackagingUnit());

                        return medicine.getName().equals(NAME)
                                && medicine.getEan().equals(EAN)
                                && medicine.getCommonName().equals(COMMON_NAME)
                                && medicine.getPotency().equals(POTENCY)
                                && medicine.getForm().equals(FORM)
                                && medicine.getPackagingUnit().equals(PACKAGE_UNIT)
                                && medicine.getPackagingSize() == PACKAGE_SIZE
                                && medicine.getId() == 1L;
                    }
                });
    }

    @Test
    public void findById_medicineNotExists_empty() {
        medicineDao.findById(1L).test()
                .assertNoValues()
                .assertComplete();
    }

    private Medicine createMedicine(long id) {
        return Medicine.builder()
                .id(id)
                .ean(EAN)
                .name(NAME)
                .potency(POTENCY)
                .packagingUnit(PACKAGE_UNIT)
                .packagingSize(PACKAGE_SIZE)
                .form(FORM)
                .commonName(COMMON_NAME)
                .build();
    }
}
