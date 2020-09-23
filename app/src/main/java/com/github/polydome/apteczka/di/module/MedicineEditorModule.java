package com.github.polydome.apteczka.di.module;

import com.github.polydome.apteczka.view.ui.medicineeditor.EanInputListener;
import com.github.polydome.apteczka.view.ui.medicineeditor.MedicineEditorViewModel;
import com.github.polydome.apteczka.view.ui.medicineeditor.ProductModel;
import com.github.polydome.apteczka.view.ui.medicineeditor.ProductStatus;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ViewModelModule.class})
public class MedicineEditorModule {
    @Provides
    public EanInputListener eanInputListener(MedicineEditorViewModel medicineEditorViewModel) {
        return medicineEditorViewModel;
    }

    @Provides
    public ProductStatus.Owner productStatusOwner(MedicineEditorViewModel medicineEditorViewModel) {
        return medicineEditorViewModel;
    }

    @Provides
    public ProductModel productModel(MedicineEditorViewModel medicineEditorViewModel) {
        return medicineEditorViewModel;
    }
}
