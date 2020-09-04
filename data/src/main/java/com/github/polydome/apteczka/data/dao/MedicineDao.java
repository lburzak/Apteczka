package com.github.polydome.apteczka.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.github.polydome.apteczka.data.entity.MedicineEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface MedicineDao {
    @Insert
    Single<Long> create(MedicineEntity medicine);

    @Query("SELECT * FROM medicine WHERE id = :id")
    Maybe<MedicineEntity> findById(long id);

    @Query("SELECT id FROM medicine")
    Observable<List<Long>> ids();

    @Update
    Completable update(MedicineEntity medicine);
}
