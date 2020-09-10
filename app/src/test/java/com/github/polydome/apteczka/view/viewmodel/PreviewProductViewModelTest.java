package com.github.polydome.apteczka.view.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.github.polydome.apteczka.domain.usecase.FetchProductDataUseCase;
import com.github.polydome.apteczka.domain.usecase.structure.ProductData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PreviewProductViewModelTest {
    @Rule public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    FetchProductDataUseCase fetchProductDataUseCase = mock(FetchProductDataUseCase.class);
    PreviewProductViewModel SUT;

    final String EAN = "9172763616252";

    @Before
    public void setUp() {
        SUT = new PreviewProductViewModel(fetchProductDataUseCase);
    }

    @Test
    public void medicineIdNeverChanged_fieldsEmpty() {
        assertThat(SUT.getName().getValue(), equalTo(""));
        assertThat(SUT.getCommonName().getValue(), equalTo(""));
        assertThat(SUT.getForm().getValue(), equalTo(""));
        assertThat(SUT.getPackagingSize().getValue(), equalTo(""));
        assertThat(SUT.getPackagingUnit().getValue(), equalTo(""));
        assertThat(SUT.getPotency().getValue(), equalTo(""));
    }

    @Test
    public void medicineIdChanged_fieldsReflectNewProduct() {
        ProductData productData = ProductData.builder()
                .name("product name")
                .commonName("product common name")
                .form("product form")
                .packagingSize(3)
                .packagingUnit("product unit")
                .potency("product potency")
                .build();

        when(fetchProductDataUseCase.byEan(EAN))
                .thenReturn(Maybe.just(productData));

        SUT.setEan(EAN);

        assertThat(SUT.getName().getValue(), equalTo(productData.getName()));
        assertThat(SUT.getCommonName().getValue(), equalTo(productData.getCommonName()));
        assertThat(SUT.getForm().getValue(), equalTo(productData.getForm()));
        assertThat(SUT.getPackagingSize().getValue(), equalTo(String.valueOf(productData.getPackagingSize())));
        assertThat(SUT.getPackagingUnit().getValue(), equalTo(productData.getPackagingUnit()));
        assertThat(SUT.getPotency().getValue(), equalTo(productData.getPotency()));
    }

    @Test
    public void onCleared_duringMedicineChange_fieldsNotUpdate() throws InterruptedException {
        // given
        ProductData productData = ProductData.builder()
                .name("product name")
                .commonName("product common name")
                .form("product form")
                .packagingSize(3)
                .packagingUnit("product unit")
                .potency("product potency")
                .build();

        when(fetchProductDataUseCase.byEan(EAN))
                .thenReturn(Maybe.just(productData).delay(20, TimeUnit.MILLISECONDS));

        // when
        SUT.setEan(EAN);
        SUT.onCleared();

        Thread.sleep(25);

        // then
        assertThat(SUT.getName().getValue(), equalTo(""));
        assertThat(SUT.getCommonName().getValue(), equalTo(""));
        assertThat(SUT.getForm().getValue(), equalTo(""));
        assertThat(SUT.getPackagingSize().getValue(), equalTo(""));
        assertThat(SUT.getPackagingUnit().getValue(), equalTo(""));
        assertThat(SUT.getPotency().getValue(), equalTo(""));
    }
}