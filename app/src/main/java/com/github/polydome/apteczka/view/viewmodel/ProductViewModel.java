package com.github.polydome.apteczka.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class ProductViewModel extends ViewModel {
    protected final MutableLiveData<String> name = new MutableLiveData<>("");
    protected final MutableLiveData<String> commonName = new MutableLiveData<>("");
    protected final MutableLiveData<String> form = new MutableLiveData<>("");
    protected final MutableLiveData<String> packagingSize = new MutableLiveData<>("");
    protected final MutableLiveData<String> packagingUnit = new MutableLiveData<>("");
    protected final MutableLiveData<String> potency = new MutableLiveData<>("");

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
}
