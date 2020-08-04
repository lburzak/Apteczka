package com.github.polydome.domain.repository;

import com.github.polydome.domain.model.Medicine;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MedicineRepository {
    Completable create(Medicine medicine);
    Single<Boolean> exists(String ean);
}
