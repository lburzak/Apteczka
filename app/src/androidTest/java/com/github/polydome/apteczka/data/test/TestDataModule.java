package com.github.polydome.apteczka.data.test;

import android.content.Context;

import com.github.polydome.apteczka.data.dao.MedicineDao;
import com.github.polydome.apteczka.data.db.AppDatabase;
import com.github.polydome.apteczka.data.di.DataModule;

public class TestDataModule extends DataModule {
    private final AppDatabase appDatabase;

    public TestDataModule(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public MedicineDao medicineDao(AppDatabase appDatabase) {
        return appDatabase.medicineDao();
    }

    @Override
    public AppDatabase appDatabase(Context applicationContext) {
        return appDatabase;
    }
}
