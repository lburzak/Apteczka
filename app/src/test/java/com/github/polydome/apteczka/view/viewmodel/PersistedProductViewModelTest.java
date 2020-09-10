package com.github.polydome.apteczka.view.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.github.polydome.apteczka.domain.usecase.GetProductForMedicineUseCase;
import com.github.polydome.apteczka.domain.usecase.structure.ProductData;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersistedProductViewModelTest {
    @Rule public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    GetProductForMedicineUseCase getProductForMedicineUseCase = mock(GetProductForMedicineUseCase.class);
    PersistedProductViewModel SUT = new PersistedProductViewModel(getProductForMedicineUseCase);

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
        long MEDICINE_ID = 39;

        ProductData productData = ProductData.builder()
                .name("product name")
                .commonName("product common name")
                .form("product form")
                .packagingSize(3)
                .packagingUnit("product unit")
                .potency("product potency")
                .build();

        when(getProductForMedicineUseCase.getProductData(MEDICINE_ID))
                .thenReturn(Single.just(productData));

        SUT.setMedicineId(MEDICINE_ID);

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
        long MEDICINE_ID = 39;

        ProductData productData = ProductData.builder()
                .name("product name")
                .commonName("product common name")
                .form("product form")
                .packagingSize(3)
                .packagingUnit("product unit")
                .potency("product potency")
                .build();

        when(getProductForMedicineUseCase.getProductData(MEDICINE_ID))
                .thenReturn(Single.just(productData).delay(20, TimeUnit.MILLISECONDS));

        // when
        SUT.setMedicineId(MEDICINE_ID);
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