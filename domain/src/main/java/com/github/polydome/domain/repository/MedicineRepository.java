package com.github.polydome.domain.repository;

import com.github.polydome.domain.model.Medicine;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface MedicineRepository {
    Completable create(Medicine medicine);
    Single<Boolean> exists(String ean);
    Maybe<Medicine> findById(int id);
}
