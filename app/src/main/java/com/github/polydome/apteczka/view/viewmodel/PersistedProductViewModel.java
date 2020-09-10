package com.github.polydome.apteczka.view.viewmodel;

import com.github.polydome.apteczka.domain.usecase.GetProductForMedicineUseCase;
import com.github.polydome.apteczka.domain.usecase.structure.ProductData;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PersistedProductViewModel extends ProductViewModel {

    private final GetProductForMedicineUseCase getProductForMedicineUseCase;

    private final CompositeDisposable comp = new CompositeDisposable();

    @Inject
    public PersistedProductViewModel(GetProductForMedicineUseCase getProductForMedicineUseCase) {
        this.getProductForMedicineUseCase = getProductForMedicineUseCase;
    }

    public void setMedicineId(long medicineId) {
        comp.add(
                getProductForMedicineUseCase.getProductData(medicineId)
                        .subscribe(this::postNewData)
        );
    }

    @Override
    protected void onCleared() {
        comp.dispose();
    }

    private void postNewData(ProductData productData) {
        name.postValue(productData.getName());
        commonName.postValue(productData.getCommonName());
        form.postValue(productData.getForm());
        potency.postValue(productData.getPotency());
        packagingSize.postValue(String.valueOf(productData.getPackagingSize()));
        packagingUnit.postValue(productData.getPackagingUnit());
    }
}
