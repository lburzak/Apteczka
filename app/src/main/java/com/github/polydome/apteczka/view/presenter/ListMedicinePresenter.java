package com.github.polydome.apteczka.view.presenter;

import com.github.polydome.apteczka.domain.usecase.CountMedicineUseCase;
import com.github.polydome.apteczka.view.contract.ListMedicineContract;
import com.github.polydome.apteczka.view.model.MedicineListModel;
import com.github.polydome.apteczka.view.presenter.common.Presenter;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

public class ListMedicinePresenter extends Presenter<ListMedicineContract.View> implements ListMedicineContract.Presenter {
    private final CountMedicineUseCase countMedicineUseCase;
    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;
    private final MedicineListModel model;

    CompositeDisposable comp = new CompositeDisposable();

    @Inject
    public ListMedicinePresenter(CountMedicineUseCase countMedicineUseCase,
                                 @Named("ioScheduler") Scheduler ioScheduler,
                                 @Named("uiScheduler") Scheduler uiScheduler,
                                 MedicineListModel model) {
        this.countMedicineUseCase = countMedicineUseCase;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
        this.model = model;
    }

    @Override
    public void onMedicineCountRequested() {
        comp.add(
            countMedicineUseCase.execute()
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .subscribe(requireView()::updateMedicineCount)
        );
    }

    @Override
    public void attach(ListMedicineContract.View view) {
        model.onReady();
        super.attach(view);
    }

    @Override
    public void detach() {
        comp.dispose();
        super.detach();
    }
}
