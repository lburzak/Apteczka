package com.github.polydome.apteczka.domain.repository;

import com.github.polydome.apteczka.domain.model.Medicine;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MedicineRepository {
    Single<Long> create(Medicine medicine);
    Maybe<Medicine> findById(long id);
    Observable<List<Long>> ids();
    Completable update(Medicine medicine);
}
