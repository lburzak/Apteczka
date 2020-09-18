package com.github.polydome.apteczka.view.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.MutableLiveData;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.test.MockProductStatusOwner;
import com.github.polydome.apteczka.view.ui.medicineeditor.EanInputListener;
import com.github.polydome.apteczka.view.ui.medicineeditor.ProductStatus;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.MockitoJUnitRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductPromptFragmentTest {
    @Rule public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    @Mock EanInputListener inputListener;
    MockProductStatusOwner mockProductStatusOwner;

    @Before
    public void showFragment() {
        mockProductStatusOwner = new MockProductStatusOwner(ProductStatus.EMPTY);

        FragmentFactory factory = mock(FragmentFactory.class);

        when(factory.instantiate(any(), eq(ProductPromptFragment.class.getName())))
                .thenReturn(new ProductPromptFragment(inputListener, mockProductStatusOwner));

        FragmentScenario.launchInContainer(ProductPromptFragment.class, null, R.style.AppTheme, factory);
    }

    @Test
    public void submitEanCode_productStatusEmpty_callsOnEanInput() {
        // given
        String EAN = "923612763512653";

        MutableLiveData<String> eanInput = new MutableLiveData<>();
        when(inputListener.getEan()).thenReturn(eanInput);

        mockProductStatusOwner.setStatus(ProductStatus.EMPTY);

        // when
        onView(withId(R.id.productPrompt_eanInput)).perform(typeText(EAN));
        onView(withId(R.id.productPrompt_submit)).perform(click());

        // then
        assertThat(eanInput.getValue(), equalTo(EAN));
        verify(inputListener, VerificationModeFactory.times(1)).onEanInputFinished();
    }

    @Test
    public void unlinkProduct_productStatusLinked_callsOnEanInput() {
        // given
        mockProductStatusOwner.setStatus(ProductStatus.LINKED);

        // when
        onView(withId(R.id.productPrompt_unlink)).perform(click());

        // then
        verify(inputListener, VerificationModeFactory.times(1)).onEanCleared();
    }
}