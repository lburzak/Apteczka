package com.github.polydome.apteczka.di.module;

import android.content.Context;

import com.github.polydome.apteczka.BuildConfig;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Context applicationContext;

    public ApplicationModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    @Named("applicationContext")
    public Context applicationContext() {
        return applicationContext;
    }

    @Provides
    @Named("remedyUrl")
    public String remedyUrl() {
        return BuildConfig.REMEDY_URL;
    }
}
