package com.github.polydome.apteczka.view.ui.medicineeditor;

import androidx.lifecycle.LiveData;

public enum ProductStatus {
    EMPTY,
    FETCHING,
    LINKED,
    UNRECOGNIZED;

    public interface Owner {
        LiveData<ProductStatus> getProductStatus();
    }
}
