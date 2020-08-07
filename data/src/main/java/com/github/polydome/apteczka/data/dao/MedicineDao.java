package com.github.polydome.apteczka.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.github.polydome.apteczka.data.entity.MedicineEntity;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface MedicineDao {
    @Insert
    Single<Long> create(MedicineEntity medicine);

    @Query("SELECT EXISTS (SELECT * FROM medicine WHERE ean = :ean)")
    Single<Boolean> exists(String ean);

    @Query("SELECT * FROM medicine WHERE id = :id")
    Maybe<MedicineEntity> findById(int id);
}
