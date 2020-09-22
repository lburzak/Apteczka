package com.github.polydome.apteczka.view.ui.medicineeditor;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.github.polydome.apteczka.domain.usecase.FetchProductDataUseCase;
import com.github.polydome.apteczka.domain.usecase.structure.ProductData;
import com.github.polydome.apteczka.view.test.util.FakeMaybe;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MedicineEditorViewModelTest {
    @Rule public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    FetchProductDataUseCase fetchProductDataUseCase = mock(FetchProductDataUseCase.class);
    MedicineEditorViewModel SUT;

    final String EXISTING_PRODUCT_EAN = "92716282736152";

    @Before
    public void setUp() {
        SUT = new MedicineEditorViewModel(fetchProductDataUseCase, Schedulers.trampoline(), Schedulers.trampoline());
    }

    @Test
    public void onEanInput_productExists_liveDataReflectsProduct() {
        // given
        ProductData PRODUCT_DATA = ProductData.builder()
                .potency("test potency")
                .packagingUnit("test unit")
                .packagingSize(12)
                .form("test form")
                .commonName("test common name")
                .name("test name")
                .build();

        when(fetchProductDataUseCase.byEan(EXISTING_PRODUCT_EAN))
                .thenReturn(Maybe.just(PRODUCT_DATA));

        // when
        SUT.getEan().setValue(EXISTING_PRODUCT_EAN);
        SUT.onEanInputFinished();

        // then
        assertThat(SUT.getCommonName().getValue(), equalTo(PRODUCT_DATA.getCommonName()));
        assertThat(SUT.getPotency().getValue(), equalTo(PRODUCT_DATA.getPotency()));
        assertThat(SUT.getPackagingUnit().getValue(), equalTo(PRODUCT_DATA.getPackagingUnit()));
        assertThat(SUT.getPackagingSize().getValue(), equalTo(String.valueOf(PRODUCT_DATA.getPackagingSize())));
        assertThat(SUT.getForm().getValue(), equalTo(PRODUCT_DATA.getForm()));
        assertThat(SUT.getName().getValue(), equalTo(PRODUCT_DATA.getName()));
        assertThat(SUT.getEan().getValue(), equalTo(EXISTING_PRODUCT_EAN));
        assertThat(SUT.productExists().getValue(), equalTo(true));
    }

    @Test
    public void onEanCleared_liveDataReflectsMissingProduct() {
        // given
        ProductData PRODUCT_DATA = ProductData.builder()
                .potency("test potency")
                .packagingUnit("test unit")
                .packagingSize(12)
                .form("test form")
                .commonName("test common name")
                .name("test name")
                .build();

        when(fetchProductDataUseCase.byEan(EXISTING_PRODUCT_EAN))
                .thenReturn(Maybe.just(PRODUCT_DATA));

        // when
        SUT.getEan().setValue(EXISTING_PRODUCT_EAN);
        SUT.onEanInputFinished();
        SUT.onEanCleared();

        // then
        assertThat(SUT.getCommonName().getValue(), equalTo(""));
        assertThat(SUT.getPotency().getValue(), equalTo(""));
        assertThat(SUT.getPackagingUnit().getValue(), equalTo(""));
        assertThat(SUT.getPackagingSize().getValue(), equalTo(""));
        assertThat(SUT.getForm().getValue(), equalTo(""));
        assertThat(SUT.getName().getValue(), equalTo(""));
        assertThat(SUT.getEan().getValue(), equalTo(""));
        assertThat(SUT.productExists().getValue(), equalTo(false));
    }

    @Test
    public void onCleared_duringProductFetch_doesNotPostValues() throws InterruptedException {
        // given
        String EXISTING_PRODUCT_EAN = "92716282736152";
        ProductData PRODUCT_DATA = ProductData.builder()
                .potency("test potency")
                .packagingUnit("test unit")
                .packagingSize(12)
                .form("test form")
                .commonName("test common name")
                .name("test name")
                .build();

        when(fetchProductDataUseCase.byEan(EXISTING_PRODUCT_EAN))
                .thenReturn(Maybe.just(PRODUCT_DATA).delay(20, TimeUnit.MILLISECONDS));

        // when
        SUT.getEan().setValue(EXISTING_PRODUCT_EAN);
        SUT.onEanInputFinished();
        SUT.onCleared();

        // then

        // Make sure that fetch operation delay passed
        Thread.sleep(25);

        assertThat(SUT.getCommonName().getValue(), equalTo(""));
        assertThat(SUT.getPotency().getValue(), equalTo(""));
        assertThat(SUT.getPackagingUnit().getValue(), equalTo(""));
        assertThat(SUT.getPackagingSize().getValue(), equalTo(""));
        assertThat(SUT.getForm().getValue(), equalTo(""));
        assertThat(SUT.getName().getValue(), equalTo(""));
        assertThat(SUT.getEan().getValue(), equalTo(EXISTING_PRODUCT_EAN));
        assertThat(SUT.productExists().getValue(), equalTo(false));
    }

    @Test
    public void onEatInput_productExists_productStatusReflectsSequentialChanges() {
        FakeMaybe<ProductData> fakeMaybe = new FakeMaybe<>();

        // given
        ProductData PRODUCT_DATA = ProductData.builder()
                .potency("test potency")
                .packagingUnit("test unit")
                .packagingSize(12)
                .form("test form")
                .commonName("test common name")
                .name("test name")
                .build();

        // given
        when(fetchProductDataUseCase.byEan(EXISTING_PRODUCT_EAN))
                .thenReturn(fakeMaybe);

        // when
        ProductStatus statusBeforeFetch = SUT.getProductStatus().getValue();
        SUT.getEan().setValue(EXISTING_PRODUCT_EAN);
        SUT.onEanInputFinished();
        ProductStatus statusDuringFetch = SUT.getProductStatus().getValue();
        fakeMaybe.pushSuccess(PRODUCT_DATA);
        ProductStatus statusAfterFetch = SUT.getProductStatus().getValue();

        // then
        assertThat(statusBeforeFetch, equalTo(ProductStatus.EMPTY));
        assertThat(statusDuringFetch, equalTo(ProductStatus.FETCHING));
        assertThat(statusAfterFetch, equalTo(ProductStatus.LINKED));
    }

    @Test
    public void onEatInput_productNotExists_productStatusReflectsSequentialChanges() {
        FakeMaybe<ProductData> fakeMaybe = new FakeMaybe<>();

        // given
        when(fetchProductDataUseCase.byEan(EXISTING_PRODUCT_EAN))
                .thenReturn(fakeMaybe);

        // when
        ProductStatus statusBeforeFetch = SUT.getProductStatus().getValue();
        SUT.getEan().setValue(EXISTING_PRODUCT_EAN);
        SUT.onEanInputFinished();
        ProductStatus statusDuringFetch = SUT.getProductStatus().getValue();
        fakeMaybe.pushComplete();
        ProductStatus statusAfterFetch = SUT.getProductStatus().getValue();

        // then
        assertThat(statusBeforeFetch, equalTo(ProductStatus.EMPTY));
        assertThat(statusDuringFetch, equalTo(ProductStatus.FETCHING));
        assertThat(statusAfterFetch, equalTo(ProductStatus.UNRECOGNIZED));
    }

    @Test
    public void onEanCleared_productStatusLinked_changesToProductStatusEmpty() {
        // given
        String EXISTING_PRODUCT_EAN = "92716282736152";
        ProductData PRODUCT_DATA = ProductData.builder()
                .potency("test potency")
                .packagingUnit("test unit")
                .packagingSize(12)
                .form("test form")
                .commonName("test common name")
                .name("test name")
                .build();

        when(fetchProductDataUseCase.byEan(EXISTING_PRODUCT_EAN))
                .thenReturn(Maybe.just(PRODUCT_DATA));

        // when
        SUT.getEan().setValue(EXISTING_PRODUCT_EAN);
        SUT.onEanInputFinished();
        SUT.onEanCleared();

        // then
        assertThat(SUT.getProductStatus().getValue(), equalTo(ProductStatus.EMPTY));
    }
}