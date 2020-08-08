package com.github.polydome.apteczka.view.presenter;

import com.github.polydome.apteczka.domain.usecase.AddMedicineUseCase;
import com.github.polydome.apteczka.view.contract.AddMedicineContract;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class AddMedicinePresenter implements AddMedicineContract.Presenter {
    private final AddMedicineUseCase addMedicineUseCase;
    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;
    private AddMedicineContract.View view;

    private final CompositeDisposable compDisposable = new CompositeDisposable();

    @Inject
    public AddMedicinePresenter(
            AddMedicineUseCase addMedicineUseCase,
            @Named("ioScheduler") Scheduler ioScheduler,
            @Named("uiScheduler") Scheduler uiScheduler)
    {
        this.addMedicineUseCase = addMedicineUseCase;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void onCreateMedicine(String ean) {
        compDisposable.add(
            addMedicineUseCase.execute(ean)
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long medicineId) {
                            requireView().showMedicineEditor(medicineId);
                        }
                    })
        );
    }

    @Override
    public void attach(AddMedicineContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        compDisposable.dispose();
        this.view = null;
    }

    private AddMedicineContract.View requireView() {
        if (this.view == null)
            throw new IllegalStateException("No view attached");
        else
            return view;
    }
}
