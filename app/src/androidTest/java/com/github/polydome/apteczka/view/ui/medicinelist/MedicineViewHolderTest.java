package com.github.polydome.apteczka.view.ui.medicinelist;

import android.os.Handler;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.test.ViewContainer;
import com.github.polydome.apteczka.view.contract.ShowMedicineContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Objects;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MedicineViewHolderTest {
    MedicineViewHolder SUT;
    ShowMedicineContract.Presenter presenter;

    @Before
    public void setUp() {
        presenter = Mockito.mock(ShowMedicineContract.Presenter.class);

        FragmentScenario<EntryViewContainer> scenario = FragmentScenario.launchInContainer(EntryViewContainer.class);

        scenario.onFragment(fragment -> {
            SUT = new MedicineViewHolder(Objects.requireNonNull(fragment.getView()), presenter);
        });
    }

    @Test
    public void medicineIdChanged_medicineExists_callsPresenterOnIdChanged() {
        SUT.setMedicineId(19);

        Mockito.verify(presenter).onIdChanged(19);
    }

    @Test
    public void showName_showsName() {
        String NAME = "test name";

        Handler handler = new Handler(ApplicationProvider.getApplicationContext().getMainLooper());
        handler.post(() -> SUT.showName(NAME));

        onView(withId(R.id.medicineEntry_name)).check(
                matches(withText(NAME))
        );
    }

    @Test
    public void onAttach_attachesPresenter() {
        SUT.onAttach();
        Mockito.verify(presenter).attach(SUT);
    }

    @Test
    public void onDetach_detachesPresenter() {
        SUT.onDetach();
        Mockito.verify(presenter).detach();
    }

    public static class EntryViewContainer extends ViewContainer {
        @Override
        protected int getViewResId() {
            return R.layout.entry_medicine;
        }
    }
}