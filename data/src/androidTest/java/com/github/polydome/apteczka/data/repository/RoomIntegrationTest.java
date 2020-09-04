package com.github.polydome.apteczka.data.repository;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.MediumTest;

import com.github.polydome.apteczka.data.dao.MedicineDao;
import com.github.polydome.apteczka.data.db.AppDatabase;
import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.model.Product;
import com.github.polydome.apteczka.domain.model.ProductLinkedMedicine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.reactivex.CompletableSource;
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
        roomMedicineRepository.create(createMedicine(2L)).subscribe();
        TestObserver<List<Long>> test = roomMedicineRepository.ids().test();
        test.awaitCount(1)
                .assertValue(ids -> ids.size() == 1 && ids.contains(2L));
    }

    @Test
    public void update_idExists_updatesMedicine() {
        Medicine medicine = createMedicine(2L);
        Medicine newMedicine = medicine.toBuilder()
                .title("new title")
                .build();
        roomMedicineRepository.create(medicine)
            .flatMapCompletable(l -> roomMedicineRepository.update(newMedicine))
            .andThen(roomMedicineRepository.findById(2L))
            .test()
            .assertValue(newMedicine);
    }

    @Test
    public void update_medicineWithProduct_updatesMedicine() {
        long ID = 2L;
        Medicine initialMedicine = createMedicine(ID);
        ProductLinkedMedicine productMedicine = new ProductLinkedMedicine.Builder()
                .id(ID)
                .title("test title")
                .product(createProduct(8L))
                .build();

        roomMedicineRepository.create(initialMedicine)
            .flatMapCompletable(l -> roomMedicineRepository.update(productMedicine))
            .andThen(roomMedicineRepository.findById(ID))
            .test()
            .assertValue(medicine -> medicine instanceof ProductLinkedMedicine
                                  && medicine.equals(productMedicine));
    }

    private Product createProduct(long id) {
        return Product.builder()
                .id(id)
                .build();
    }

    private Medicine createMedicine(long id) {
        return Medicine.builder()
                .id(id)
                .title(TITLE)
                .build();
    }
}
