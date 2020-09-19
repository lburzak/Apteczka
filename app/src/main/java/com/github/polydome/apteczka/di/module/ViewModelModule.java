package com.github.polydome.apteczka.di.module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.github.polydome.apteczka.view.ui.medicineeditor.MedicineEditorViewModel;

import dagger.Module;
import dagger.Provides;

@Module(includes = {FrameworkModule.class, ViewModelFactoryModule.class})
public class ViewModelModule {
    @Provides
    public MedicineEditorViewModel medicineEditorViewModel(ViewModelProvider provider) {
        return provider.get(MedicineEditorViewModel.class);
    }

    @Provides
    public ViewModelProvider viewModelProvider(ViewModelStoreOwner storeOwner, ViewModelProvider.Factory factory) {
        return new ViewModelProvider(storeOwner, factory);
    }

    @Provides
    public ViewModelStoreOwner viewModelStoreOwner(AppCompatActivity activity) {
        return activity;
    }
}
