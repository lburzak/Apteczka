package com.github.polydome.apteczka.view.ui;

import android.os.Bundle;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.github.polydome.apteczka.Apteczka;
import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.di.component.ApplicationComponent;
import com.github.polydome.apteczka.di.component.DaggerApplicationComponent;
import com.github.polydome.apteczka.di.module.ApplicationModule;
import com.github.polydome.apteczka.test.MockPersistedProductViewModel;
import com.github.polydome.apteczka.test.MockPreviewProductViewModel;
import com.github.polydome.apteczka.view.viewmodel.PersistedProductViewModel;
import com.github.polydome.apteczka.view.viewmodel.PreviewProductViewModel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import it.cosenonjaviste.daggermock.DaggerMockRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class ProductFragmentTest {
    MockPersistedProductViewModel mockPersistedProductViewModel = new MockPersistedProductViewModel();
    MockPreviewProductViewModel mockPreviewProductViewModel = new MockPreviewProductViewModel();

    @Rule public DaggerMockRule<ApplicationComponent> rule =
            new DaggerMockRule<>(ApplicationComponent.class)
            .customizeBuilder((DaggerMockRule.BuilderCustomizer<DaggerApplicationComponent.Builder>) builder ->
                    builder.applicationModule(new ApplicationModule(
                    ApplicationProvider.getApplicationContext()
            )))
            .set(component -> ((Apteczka) ApplicationProvider.getApplicationContext())
                    .setApplicationComponent(component));
    
    @Mock ViewModelProvider.Factory viewModelFactoryMock;

    final long MEDICINE_ID = 1;
    final String EAN = "92717227161782";

    public void startFragmentWithMedicineId() {
        Mockito.when(viewModelFactoryMock.create(PersistedProductViewModel.class)).thenReturn(mockPersistedProductViewModel);

        Bundle bundle = new Bundle();
        bundle.putLong(ProductFragment.BUNDLE_KEY_MEDICINE_ID, MEDICINE_ID);
        FragmentScenario.launchInContainer(ProductFragment.class, bundle, R.style.AppTheme, null);
    }

    public void startFragmentWithEan() {
        Mockito.when(viewModelFactoryMock.create(PreviewProductViewModel.class)).thenReturn(mockPreviewProductViewModel);

        Bundle bundle = new Bundle();
        bundle.putString(ProductFragment.BUNDLE_KEY_EAN, EAN);
        FragmentScenario.launchInContainer(ProductFragment.class, bundle, R.style.AppTheme, null);
    }

    @Test
    public void persistedProduct_updatesViewModelWithMedicineId() {
        startFragmentWithMedicineId();

        assertThat(mockPersistedProductViewModel.getMedicineId(), equalTo(MEDICINE_ID));
    }

    @Test
    public void previewProduct_updatesViewModelWithEan() {
        startFragmentWithEan();

        assertThat(mockPreviewProductViewModel.getEan(), equalTo(EAN));
    }

    @Test
    public void persistedProduct_showsName() {
        startFragmentWithMedicineId();

        mockPersistedProductViewModel.emitName("test name");

        onView(withId(R.id.productView_name)).check(matches(withText("test name")));
    }

    @Test
    public void persistedProduct_showsAllProperties() {
        startFragmentWithMedicineId();

        mockPersistedProductViewModel.emitName("test name");
        mockPersistedProductViewModel.emitCommonName("test common name");
        mockPersistedProductViewModel.emitForm("test form");
        mockPersistedProductViewModel.emitPackagingSize("82");
        mockPersistedProductViewModel.emitPackagingUnit("test unit");
        mockPersistedProductViewModel.emitPotency("test potency");

        onView(withId(R.id.productView_name)).check(matches(withText("test name")));
        onView(withId(R.id.productView_commonName)).check(matches(withText("test common name")));
        onView(withId(R.id.productView_form)).check(matches(withText("test form")));
        onView(withId(R.id.productView_packagingSize)).check(matches(withText("82")));
        onView(withId(R.id.productView_packagingUnit)).check(matches(withText("test unit")));
        onView(withId(R.id.productView_potency)).check(matches(withText("test potency")));
    }
}