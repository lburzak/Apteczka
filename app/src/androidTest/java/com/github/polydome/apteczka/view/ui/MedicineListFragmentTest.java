package com.github.polydome.apteczka.view.ui;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.github.polydome.apteczka.Apteczka;
import com.github.polydome.apteczka.data.dao.MedicineDao;
import com.github.polydome.apteczka.data.di.DataModule;
import com.github.polydome.apteczka.di.component.DaggerApplicationComponent;
import com.github.polydome.apteczka.di.module.ApplicationModule;
import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.repository.MedicineRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Collections;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MedicineListFragmentTest {
    MedicineRepository medicineRepository = Mockito.mock(MedicineRepository.class);

    @Before
    public void setUp() {

        DataModule dataModule = new DataModule() {
            @Override
            public MedicineRepository medicineRepository(MedicineDao medicineDao) {
                return medicineRepository;
            }
        };

        ((Apteczka) ApplicationProvider.getApplicationContext()).setApplicationComponent(
                DaggerApplicationComponent.builder()
                        .applicationModule(new ApplicationModule(ApplicationProvider.getApplicationContext()))
                        .dataModule(dataModule)
                        .build()
        );
    }

    String NAME = "test name";

    @Test
    public void visible_medicineInDatabase_medicineShown() {
        // FIXME: 8/21/20 Stuck before assertion

        Mockito.when(medicineRepository.count())
                .thenReturn(Single.just(1));

        Mockito.when(medicineRepository.ids())
                .thenReturn(Observable.just(Collections.singletonList(6L)));

        Mockito.when(medicineRepository.findById(6L))
                .thenReturn(Maybe.just(Medicine.builder().name(NAME).build()));

        FragmentScenario.launchInContainer(MedicineListFragment.class);

        Espresso.onView(withText(NAME)).check(
                matches(isDisplayed())
        );
    }
}