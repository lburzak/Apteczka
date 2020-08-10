package com.github.polydome.apteczka.data.test;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.test.core.app.ApplicationProvider;

public abstract class RoomTest <D extends RoomDatabase> {
    private final Class<D> dbClass;
    private D db;

    protected RoomTest(Class<D> dbClass) {
        this.dbClass = dbClass;
    }

    protected void rebuildDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room
                .inMemoryDatabaseBuilder(context, dbClass)
                .build();
    }

    protected D getDatabase() {
        return db;
    }
}
