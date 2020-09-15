package com.github.polydome.apteczka.test;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.polydome.apteczka.view.ui.medicineeditor.ProductModel;

public class StubProductModel implements ProductModel {
    MutableLiveData<Boolean> productExistence = new MutableLiveData<>();
    MutableLiveData<String> ean = new MutableLiveData<>();
    MutableLiveData<String> name = new MutableLiveData<>();
    MutableLiveData<String> commonName = new MutableLiveData<>();
    MutableLiveData<String> form = new MutableLiveData<>();
    MutableLiveData<String> packagingSize = new MutableLiveData<>();
    MutableLiveData<String> packagingUnit = new MutableLiveData<>();
    MutableLiveData<String> potency = new MutableLiveData<>();

    @Override
    public LiveData<Boolean> productExists() {
        return productExistence;
    }

    @Override
    public LiveData<String> getEan() {
        return ean;
    }

    @Override
    public LiveData<String> getName() {
        return name;
    }

    @Override
    public LiveData<String> getCommonName() {
        return commonName;
    }

    @Override
    public LiveData<String> getForm() {
        return form;
    }

    @Override
    public LiveData<String> getPackagingSize() {
        return packagingSize;
    }

    @Override
    public LiveData<String> getPackagingUnit() {
        return packagingUnit;
    }

    @Override
    public LiveData<String> getPotency() {
        return potency;
    }
    
    public void setProductExists(boolean productExists) {
        productExistence.setValue(productExists);
    }
    
    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setCommonName(String commonName) {
        this.commonName.setValue(commonName);
    }

    public void setForm(String form) {
        this.form.setValue(form);
    }

    public void setPotency(String potency) {
        this.potency.setValue(potency);
    }

    public void setPackagingSize(String packagingSize) {
        this.packagingSize.setValue(packagingSize);
    }

    public void setPackagingUnit(String packagingUnit) {
        this.packagingUnit.setValue(packagingUnit);
    }

    public void setEan(String ean) {
        this.ean.setValue(ean);
    }
}
