package com.github.polydome.apteczka.view.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.github.polydome.apteczka.Apteczka;
import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.di.component.ApplicationComponent;
import com.github.polydome.apteczka.di.component.DaggerApplicationComponent;
import com.github.polydome.apteczka.di.module.ApplicationModule;
import com.github.polydome.apteczka.test.StubProductModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import it.cosenonjaviste.daggermock.DaggerMockRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SmallTest
@RunWith(AndroidJUnit4.class)
public class ProductFragmentTest {
    @Rule public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    @Rule public DaggerMockRule<ApplicationComponent> rule =
            new DaggerMockRule<>(ApplicationComponent.class)
            .customizeBuilder((DaggerMockRule.BuilderCustomizer<DaggerApplicationComponent.Builder>) builder ->
                    builder.applicationModule(new ApplicationModule(
                    ApplicationProvider.getApplicationContext()
            )))
            .set(component -> ((Apteczka) ApplicationProvider.getApplicationContext())
                    .setApplicationComponent(component));

    StubProductModel productModel;

    @Before
    public void startFragment() {
        productModel = new StubProductModel();

        FragmentFactory fragmentFactory = Mockito.mock(FragmentFactory.class);
        when(fragmentFactory.instantiate(any(), eq(ProductFragment.class.getName())))
                .thenReturn(new ProductFragment(productModel));

        FragmentScenario.launchInContainer(ProductFragment.class, null, R.style.AppTheme, fragmentFactory);
    }

    @Test
    public void persistedProduct_showsAllProperties() {
        productModel.setCommonName("test common name");
        productModel.setName("test name");
        productModel.setForm("test form");
        productModel.setPackagingUnit("test unit");
        productModel.setPackagingSize("82");
        productModel.setPotency("test potency");

        onView(withId(R.id.productView_name)).check(matches(withText("test name")));
        onView(withId(R.id.productView_commonName)).check(matches(withText("test common name")));
        onView(withId(R.id.productView_form)).check(matches(withText("test form")));
        onView(withId(R.id.productView_packagingSize)).check(matches(withText("82")));
        onView(withId(R.id.productView_packagingUnit)).check(matches(withText("test unit")));
        onView(withId(R.id.productView_potency)).check(matches(withText("test potency")));
    }
}