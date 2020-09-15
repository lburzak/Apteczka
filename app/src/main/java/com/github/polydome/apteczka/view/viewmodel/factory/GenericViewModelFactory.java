package com.github.polydome.apteczka.view.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.polydome.apteczka.view.ui.medicineeditor.MedicineEditorViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class GenericViewModelFactory implements ViewModelProvider.Factory {
    private final Provider<MedicineEditorViewModel> medicineEditorViewModelProvider;

    @Inject
    public GenericViewModelFactory(Provider<MedicineEditorViewModel> medicineEditorViewModelProvider) {
        this.medicineEditorViewModelProvider = medicineEditorViewModelProvider;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MedicineEditorViewModel.class) {
            return (T) medicineEditorViewModelProvider.get();
        } else {
            throw new RuntimeException(String.format("Unsupported ViewModel: %s", modelClass.toString()));
        }
    }
}
