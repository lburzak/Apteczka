package com.github.polydome.apteczka.domain.repository;

import com.github.polydome.apteczka.domain.model.Medicine;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MedicineRepository {
    Single<Long> create(Medicine medicine);
    Single<Boolean> exists(String ean);
    Maybe<Medicine> findById(long id);
    Single<Integer> count();
    Observable<List<Long>> ids();
}
