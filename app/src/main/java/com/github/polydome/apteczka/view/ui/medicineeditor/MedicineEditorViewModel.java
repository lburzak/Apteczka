package com.github.polydome.apteczka.view.ui.medicineeditor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.polydome.apteczka.domain.usecase.FetchProductDataUseCase;
import com.github.polydome.apteczka.domain.usecase.structure.ProductData;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MedicineEditorViewModel extends ViewModel implements EanInputListener, ProductModel, ProductStatus.Owner {
    private final MutableLiveData<Boolean> productExistence = new MutableLiveData<>(false);
    private final MutableLiveData<String> ean = new MutableLiveData<>("");
    private final MutableLiveData<String> name = new MutableLiveData<>("");
    private final MutableLiveData<String> commonName = new MutableLiveData<>("");
    private final MutableLiveData<String> form = new MutableLiveData<>("");
    private final MutableLiveData<String> packagingSize = new MutableLiveData<>("");
    private final MutableLiveData<String> packagingUnit = new MutableLiveData<>("");
    private final MutableLiveData<String> potency = new MutableLiveData<>("");
    private final MutableLiveData<ProductStatus> productStatus = new MutableLiveData<>(ProductStatus.EMPTY);

    private final CompositeDisposable comp = new CompositeDisposable();

    private final FetchProductDataUseCase fetchProductDataUseCase;

    public MedicineEditorViewModel(FetchProductDataUseCase fetchProductDataUseCase) {
        this.fetchProductDataUseCase = fetchProductDataUseCase;
    }

    @Override
    public void onEanInputFinished() {
        comp.add(
            fetchProductDataUseCase.byEan(ean.getValue())
                    .doOnSubscribe(this::onFetchStart)
                    .doOnSuccess(this::onFetchSuccess)
                    .doOnComplete(this::onFetchFailed)
                    .subscribe(this::postProductData)
        );
    }

    private void onFetchSuccess(ProductData productData) {
        productStatus.postValue(ProductStatus.LINKED);
    }

    private void onFetchFailed() {
        productStatus.postValue(ProductStatus.UNRECOGNIZED);
    }

    @Override
    public void onEanCleared() {
        clearProductData();
    }

    @Override
    public LiveData<String> getName() {
        return name;
    }

    @Override
    public LiveData<String> getCommonName() {
        return commonName;
    }

    @Override
    public LiveData<String> getForm() {
        return form;
    }

    @Override
    public LiveData<String> getPackagingSize() {
        return packagingSize;
    }

    @Override
    public LiveData<String> getPackagingUnit() {
        return packagingUnit;
    }

    @Override
    public LiveData<String> getPotency() {
        return potency;
    }

    @Override
    public MutableLiveData<String> getEan() {
        return ean;
    }

    @Override
    public LiveData<Boolean> productExists() {
        return productExistence;
    }

    @Override
    public LiveData<ProductStatus> getProductStatus() {
        return productStatus;
    }

    @Override
    protected void onCleared() {
        comp.dispose();
    }

    private void postProductData(ProductData productData) {
        name.postValue(productData.getName());
        commonName.postValue(productData.getCommonName());
        form.postValue(productData.getForm());
        potency.postValue(productData.getPotency());
        packagingUnit.postValue(productData.getPackagingUnit());
        packagingSize.postValue(String.valueOf(productData.getPackagingSize()));
        productExistence.postValue(true);
    }

    private void clearProductData() {
        name.postValue("");
        commonName.postValue("");
        form.postValue("");
        potency.postValue("");
        packagingUnit.postValue("");
        packagingSize.postValue("");
        productExistence.postValue(false);
        this.ean.postValue("");
    }

    private void onFetchStart(Disposable disposable) {
        productStatus.postValue(ProductStatus.FETCHING);
    }
}
