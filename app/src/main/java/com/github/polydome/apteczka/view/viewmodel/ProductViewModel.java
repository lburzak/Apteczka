package com.github.polydome.apteczka.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class ProductViewModel extends ViewModel {
    private final MutableLiveData<String> name = new MutableLiveData<>("");
    private final MutableLiveData<String> commonName = new MutableLiveData<>("");
    private final MutableLiveData<String> form = new MutableLiveData<>("");
    private final MutableLiveData<String> packagingSize = new MutableLiveData<>("");
    private final MutableLiveData<String> packagingUnit = new MutableLiveData<>("");
    private final MutableLiveData<String> potency = new MutableLiveData<>("");

    @Inject
    public ProductViewModel() {
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

    }
}
