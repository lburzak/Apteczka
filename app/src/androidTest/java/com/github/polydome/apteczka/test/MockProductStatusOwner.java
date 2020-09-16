package com.github.polydome.apteczka.test;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.polydome.apteczka.view.ui.medicineeditor.ProductStatus;

public class MockProductStatusOwner implements ProductStatus.Owner {
    MutableLiveData<ProductStatus> status;

    public MockProductStatusOwner(ProductStatus initialStatus) {
        status = new MutableLiveData<>(initialStatus);
    }

    @Override
    public LiveData<ProductStatus> getProductStatus() {
        return status;
    }

    public void setStatus(ProductStatus productStatus) {
        status.setValue(productStatus);
    }
}
