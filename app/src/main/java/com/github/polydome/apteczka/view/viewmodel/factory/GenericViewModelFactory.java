package com.github.polydome.apteczka.view.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.polydome.apteczka.view.viewmodel.ProductViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class GenericViewModelFactory implements ViewModelProvider.Factory {
    private final Provider<ProductViewModel> productViewModelProvider;

    @Inject
    public GenericViewModelFactory(Provider<ProductViewModel> productViewModelProvider) {
        this.productViewModelProvider = productViewModelProvider;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == ProductViewModel.class) {
            return (T) productViewModelProvider.get();
        } else {
            throw new RuntimeException(String.format("Unsupported ViewModel: %s", modelClass.toString()));
        }
    }
}
