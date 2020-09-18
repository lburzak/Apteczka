package com.github.polydome.apteczka.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.github.polydome.apteczka.data.dao.MedicineDao;
import com.github.polydome.apteczka.data.entity.MedicineEntity;

@Database(entities = {MedicineEntity.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MedicineDao medicineDao();

    public static AppDatabase build(Context applicationContext) {
        return Room.databaseBuilder(applicationContext, AppDatabase.class, "apteczka")
                .fallbackToDestructiveMigration()
                .build();
    }
}
