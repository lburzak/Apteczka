package com.github.polydome.apteczka.di.component;

import com.github.polydome.apteczka.di.module.FrameworkModule;
import com.github.polydome.apteczka.di.module.PresentationModule;
import com.github.polydome.apteczka.view.ui.EditMedicineActivity;
import com.github.polydome.apteczka.view.ui.MedicineListFragment;
import com.github.polydome.apteczka.view.ui.ProductFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {PresentationModule.class, FrameworkModule.class})
public interface PresentationComponent {
    void inject(EditMedicineActivity editMedicineActivity);
    void inject(MedicineListFragment medicineListFragment);
    void inject(ProductFragment productFragment);
}
