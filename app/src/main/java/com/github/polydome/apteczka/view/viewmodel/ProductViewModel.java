package com.github.polydome.apteczka.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.polydome.apteczka.domain.usecase.GetProductForMedicineUseCase;
import com.github.polydome.apteczka.domain.usecase.structure.ProductData;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ProductViewModel extends ViewModel {
    private final GetProductForMedicineUseCase getProductForMedicineUseCase;

    private final MutableLiveData<String> name = new MutableLiveData<>("");
    private final MutableLiveData<String> commonName = new MutableLiveData<>("");
    private final MutableLiveData<String> form = new MutableLiveData<>("");
    private final MutableLiveData<String> packagingSize = new MutableLiveData<>("");
    private final MutableLiveData<String> packagingUnit = new MutableLiveData<>("");
    private final MutableLiveData<String> potency = new MutableLiveData<>("");

    private final CompositeDisposable comp = new CompositeDisposable();

    @Inject
    public ProductViewModel(GetProductForMedicineUseCase getProductForMedicineUseCase) {
        this.getProductForMedicineUseCase = getProductForMedicineUseCase;
    }

    public LiveData<String> getName() {
        return name;
    }

    public LiveData<String> getCommonName() {
        return commonName;
    }

    public LiveData<String> getForm() {
        return form;
    }

    public LiveData<String> getPackagingSize() {
        return packagingSize;
    }

    public LiveData<String> getPackagingUnit() {
        return packagingUnit;
    }

    public LiveData<String> getPotency() {
        return potency;
    }

    public void changeMedicineId(long medicineId) {
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
