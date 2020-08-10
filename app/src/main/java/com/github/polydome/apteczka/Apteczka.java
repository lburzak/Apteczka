package com.github.polydome.apteczka;

import android.app.Application;

import androidx.annotation.VisibleForTesting;

import com.github.polydome.apteczka.di.component.ApplicationComponent;
import com.github.polydome.apteczka.di.component.DaggerApplicationComponent;
import com.github.polydome.apteczka.di.module.ApplicationModule;

public class Apteczka extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
