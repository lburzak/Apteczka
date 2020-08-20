package com.github.polydome.apteczka.domain.usecase;

import io.reactivex.Single;

public class CountMedicineUseCase {
    public Single<Integer> execute() {
        return Single.just(0);
    }
}
