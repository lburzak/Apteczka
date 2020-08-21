package com.github.polydome.apteczka.view.model;

import com.github.polydome.apteczka.domain.usecase.ObserveMedicineIdsUseCase;

import java.util.List;

public class MedicineListModel {
    private final ObserveMedicineIdsUseCase observeMedicineIdsUseCase;
    private List<Long> ids;

    private boolean ready = false;

    public MedicineListModel(ObserveMedicineIdsUseCase observeMedicineIdsUseCase) {
        this.observeMedicineIdsUseCase = observeMedicineIdsUseCase;
    }

    public void onReady() {
        ready = true;
        // TODO: Dispose
        observeMedicineIdsUseCase.execute()
                .subscribe((ids) -> this.ids = ids);
    }

    public long getIdAtPosition(int position) throws IllegalArgumentException {
        if (!ready)
            throw new IllegalStateException("Model not ready");

        if (position < 0 || position > ids.size())
            throw new IllegalArgumentException(String.format("Illegal position [%d]", position));

        return ids.get(position);
    }
}
