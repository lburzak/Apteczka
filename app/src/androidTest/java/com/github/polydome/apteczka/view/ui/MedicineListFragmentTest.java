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

import io.reactivex.Maybe;
import io.reactivex.Single;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MedicineListFragmentTest {
    @Before
    public void setUp() {
        MedicineRepository medicineRepository = new MedicineRepository() {
            @Override
            public Single<Long> create(Medicine medicine) {
                return null;
            }

            @Override
            public Single<Boolean> exists(String ean) {
                return null;
            }

            @Override
            public Maybe<Medicine> findById(long id) {
                return null;
            }
        };

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
        FragmentScenario.launchInContainer(MedicineListFragment.class);

        Espresso.onView(withText(NAME)).check(
                matches(isDisplayed())
        );
    }
}