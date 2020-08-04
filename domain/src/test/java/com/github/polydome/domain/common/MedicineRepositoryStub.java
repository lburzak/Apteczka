package com.github.polydome.domain.common;

import com.github.polydome.domain.model.Medicine;
import com.github.polydome.domain.repository.MedicineRepository;

import java.util.Collection;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class MedicineRepositoryStub implements MedicineRepository {
    public Collection<Medicine> data;

    @Override
    public Completable create(final Medicine medicine) {
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                data.add(medicine);
            }
        });
    }

    @Override
    public Single<Boolean> exists(final String ean) {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(SingleEmitter<Boolean> emitter) throws Exception {
                for (final Medicine medicine : data)
                    if (medicine.getEan().equals(ean)) {
                        emitter.onSuccess(true);
                        return;
                    }

                emitter.onSuccess(false);
            }
        });
    }
}
