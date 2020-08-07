package com.github.polydome.apteczka.data.repository;

import com.github.polydome.apteczka.data.dao.MedicineDao;
import com.github.polydome.apteczka.data.entity.MedicineEntity;
import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.repository.MedicineRepository;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class RoomMedicineRepository implements MedicineRepository {
    private final MedicineDao medicineDao;

    public RoomMedicineRepository(MedicineDao medicineDao) {
        this.medicineDao = medicineDao;
    }

    @Override
    public Single<Long> create(Medicine medicine) {
        return medicineDao.create(new MedicineEntity(medicine));
    }

    @Override
    public Single<Boolean> exists(String ean) {
        return medicineDao.exists(ean);
    }

    @Override
    public Maybe<Medicine> findById(int id) {
        return medicineDao.findById(id).map(new Function<MedicineEntity, Medicine>() {
            @Override
            public Medicine apply(MedicineEntity medicineEntity) {
                return medicineEntity.toMedicine();
            }
        });
    }
}
