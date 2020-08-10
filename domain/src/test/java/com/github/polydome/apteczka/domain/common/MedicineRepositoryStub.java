package com.github.polydome.apteczka.domain.common;

import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.repository.MedicineRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class MedicineRepositoryStub implements MedicineRepository {
    public Collection<Medicine> data;

    @Override
    public Maybe<Medicine> findById(final long id) {
        return Maybe.create(new MaybeOnSubscribe<Medicine>() {
            @Override
            public void subscribe(MaybeEmitter<Medicine> emitter) {
                Optional<Medicine> medicineOptional = data.stream().filter(new Predicate<Medicine>() {
                    @Override
                    public boolean test(Medicine medicine) {
                        return medicine.getId() == id;
                    }
                }).findFirst();

                if (medicineOptional.isPresent()) {
                    emitter.onSuccess(medicineOptional.get());
                } else {
                    emitter.onComplete();
                }
            }
        });
    }

    @Override
    public Single<Long> create(final Medicine medicine) {
        return Single.create(new SingleOnSubscribe<Long>() {
            @Override
            public void subscribe(SingleEmitter<Long> emitter) {
                data.add(medicine);
                emitter.onSuccess(medicine.getId());
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
