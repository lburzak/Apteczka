package com.github.polydome.apteczka.view.ui.common;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.github.polydome.apteczka.Apteczka;
import com.github.polydome.apteczka.di.component.ApplicationComponent;
import com.github.polydome.apteczka.di.component.PresentationComponent;
import com.github.polydome.apteczka.di.module.FrameworkModule;

public class PresentationComponentProvider {
    private final PresentationComponent presentationComponent;

    public PresentationComponentProvider(Fragment owner) {
        this.presentationComponent =
                getApplicationComponent(owner.requireActivity())
                        .createPresentationComponent(createFrameworkModule(owner.getContext()));
    }

    public PresentationComponentProvider(Activity owner) {
        this.presentationComponent =
                getApplicationComponent(owner)
                        .createPresentationComponent(createFrameworkModule(owner));
    }

    public PresentationComponent getPresentationComponent() {
        return presentationComponent;
    }

    private ApplicationComponent getApplicationComponent(Context activityContext) {
        return ((Apteczka) activityContext.getApplicationContext())
                .getApplicationComponent();
    }

    private FrameworkModule createFrameworkModule(Context presentationContext) {
        return new FrameworkModule(presentationContext);
    }
}
