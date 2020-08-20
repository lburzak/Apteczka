package com.github.polydome.apteczka.di.module;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.polydome.apteczka.view.ui.medicinelist.MedicineListAdapter;
import com.github.polydome.apteczka.view.ui.medicinelist.MedicineViewHolder;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(includes = {PresentationModule.class})
public class FrameworkModule {
    private final Context presentationContext;

    public FrameworkModule(Context presentationContext) {
        this.presentationContext = presentationContext;
    }

    @Provides
    @Named("presentationContext")
    Context presentationContext() {
        return presentationContext;
    }

    @Provides
    RecyclerView.LayoutManager layoutManager(@Named("presentationContext") Context presentationContext) {
        return new LinearLayoutManager(presentationContext);
    }

    @Provides
    MedicineListAdapter adapter(LayoutInflater inflater, MedicineViewHolder.Factory viewHolderFactory) {
        return new MedicineListAdapter(inflater, viewHolderFactory);
    }

    @Provides
    LayoutInflater layoutInflater() {
        return LayoutInflater.from(presentationContext);
    }
}
