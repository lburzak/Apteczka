package com.github.polydome.apteczka.di.component;

import com.github.polydome.apteczka.data.di.DataModule;
import com.github.polydome.apteczka.di.module.ApplicationModule;
import com.github.polydome.apteczka.network.di.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class, NetworkModule.class})
public interface ApplicationComponent {
    PresentationComponent createPresentationComponent();
}
