package com.github.polydome.apteczka.data.di;

import android.content.Context;

import com.github.polydome.apteczka.data.dao.MedicineDao;
import com.github.polydome.apteczka.data.db.AppDatabase;
import com.github.polydome.apteczka.data.repository.RoomMedicineRepository;
import com.github.polydome.apteczka.domain.repository.MedicineRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {
    @Provides
    public MedicineRepository medicineRepository(MedicineDao medicineDao) {
        return new RoomMedicineRepository(medicineDao);
    }

    @Provides
    public MedicineDao medicineDao(AppDatabase appDatabase) {
        return appDatabase.medicineDao();
    }

    @Provides
    @Singleton
    public AppDatabase appDatabase(@Named("applicationContext") Context applicationContext) {
        return AppDatabase.build(applicationContext);
    }
}
