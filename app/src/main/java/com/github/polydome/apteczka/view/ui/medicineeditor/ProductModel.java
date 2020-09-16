package com.github.polydome.apteczka.view.ui.medicineeditor;

import androidx.lifecycle.LiveData;

public interface ProductModel {
    LiveData<Boolean> productExists();
    LiveData<String> getName();
    LiveData<String> getCommonName();
    LiveData<String> getForm();
    LiveData<String> getPackagingSize();
    LiveData<String> getPackagingUnit();
    LiveData<String> getPotency();
}
