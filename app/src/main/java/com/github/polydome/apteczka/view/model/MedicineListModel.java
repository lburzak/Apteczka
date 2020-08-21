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

        this.ids = observeMedicineIdsUseCase.execute().blockingFirst();

        // TODO: Dispose
        observeMedicineIdsUseCase.execute()
                .subscribe(ids -> this.ids = ids);
    }

    public long getIdAtPosition(int position) throws IllegalArgumentException {
        if (!ready)
            throw new IllegalStateException("Model not ready. Method `onReady` is expected to be called first");

        if (position < 0 || position > ids.size())
            throw new IllegalArgumentException(String.format("Illegal position [%d]", position));

        return ids.get(position);
    }

    public int getSize() {
        if (!ready)
            throw new IllegalStateException("Model not ready. Method `onReady` is expected to be called first");
        return ids.size();
    }
}
