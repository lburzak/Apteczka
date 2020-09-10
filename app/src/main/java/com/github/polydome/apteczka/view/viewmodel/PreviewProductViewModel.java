package com.github.polydome.apteczka.view.viewmodel;

import com.github.polydome.apteczka.domain.usecase.FetchProductDataUseCase;
import com.github.polydome.apteczka.domain.usecase.structure.ProductData;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PreviewProductViewModel extends ProductViewModel {
    private final FetchProductDataUseCase fetchProductDataUseCase;

    private final CompositeDisposable comp = new CompositeDisposable();

    @Inject
    public PreviewProductViewModel(FetchProductDataUseCase fetchProductDataUseCase) {
        this.fetchProductDataUseCase = fetchProductDataUseCase;
    }

    public void setEan(String ean) {
        comp.add(
            fetchProductDataUseCase.byEan(ean)
                    .subscribe(this::postNewData)
        );
    }

    @Override
    protected void onCleared() {
        comp.dispose();
    }

    private void postNewData(ProductData productData) {
        name.postValue(productData.getName());
        commonName.postValue(productData.getCommonName());
        form.postValue(productData.getForm());
        potency.postValue(productData.getPotency());
        packagingSize.postValue(String.valueOf(productData.getPackagingSize()));
        packagingUnit.postValue(productData.getPackagingUnit());
    }
}
