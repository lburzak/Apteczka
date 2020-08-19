package com.github.polydome.apteczka.di.component;

import com.github.polydome.apteczka.di.module.PresentationModule;
import com.github.polydome.apteczka.view.ui.EditMedicineActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {PresentationModule.class})
public interface PresentationComponent {
    void inject(EditMedicineActivity editMedicineActivity);
}
