package com.github.polydome.apteczka.view.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.polydome.apteczka.view.viewmodel.PersistedProductViewModel;
import com.github.polydome.apteczka.view.viewmodel.PreviewProductViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class GenericViewModelFactory implements ViewModelProvider.Factory {
    private final Provider<PersistedProductViewModel> persistedProductViewModelProvider;
    private final Provider<PreviewProductViewModel> previewProductViewModelProvider;

    @Inject
    public GenericViewModelFactory(Provider<PersistedProductViewModel> persistedProductViewModelProvider, Provider<PreviewProductViewModel> previewProductViewModelProvider) {
        this.persistedProductViewModelProvider = persistedProductViewModelProvider;
        this.previewProductViewModelProvider = previewProductViewModelProvider;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PersistedProductViewModel.class) {
            return (T) persistedProductViewModelProvider.get();
        } else if (modelClass == PreviewProductViewModel.class) {
            return (T) previewProductViewModelProvider.get();
        } else {
            throw new RuntimeException(String.format("Unsupported ViewModel: %s", modelClass.toString()));
        }
    }
}
