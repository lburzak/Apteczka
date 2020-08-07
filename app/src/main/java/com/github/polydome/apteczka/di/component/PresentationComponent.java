package com.github.polydome.apteczka.di.component;

import com.github.polydome.apteczka.data.di.DataModule;
import com.github.polydome.apteczka.di.module.ApplicationModule;
import com.github.polydome.apteczka.di.module.DomainModule;
import com.github.polydome.apteczka.di.module.PresentationModule;
import com.github.polydome.apteczka.view.ui.EanInputDialog;

import dagger.Subcomponent;

@Subcomponent(modules = {PresentationModule.class})
public interface PresentationComponent {
    void inject(EanInputDialog eanInputDialog);
}
