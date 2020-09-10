package com.github.polydome.apteczka.di.module;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.polydome.apteczka.view.ui.medicinelist.MedicineListAdapter;
import com.github.polydome.apteczka.view.ui.medicinelist.MedicineViewHolder;
import com.github.polydome.apteczka.view.viewmodel.factory.GenericViewModelFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

// TODO: Rename to AndroidModule
@Module(includes = {PresentationModule.class})
public class FrameworkModule {
    private final Context presentationContext;

    public FrameworkModule(Context presentationContext) {
        this.presentationContext = presentationContext;
    }

    @Provides
    @Named("presentationContext")
    public Context presentationContext() {
        return presentationContext;
    }

    @Provides
    public RecyclerView.LayoutManager layoutManager(@Named("presentationContext") Context presentationContext) {
        return new LinearLayoutManager(presentationContext);
    }

    @Provides
    public LayoutInflater layoutInflater() {
        return LayoutInflater.from(presentationContext);
    }

    @Provides
    public ViewModelProvider.Factory viewModelFactory(GenericViewModelFactory genericViewModelFactory) {
        return genericViewModelFactory;
    }
}
