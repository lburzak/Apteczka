package com.github.polydome.apteczka.view.ui;

import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.github.polydome.apteczka.Apteczka;
import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.data.db.AppDatabase;
import com.github.polydome.apteczka.data.test.TestDataModule;
import com.github.polydome.apteczka.di.component.DaggerApplicationComponent;
import com.github.polydome.apteczka.di.module.ApplicationModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalToIgnoringCase;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    AppDatabase database;

    final String EAN = "5909990663613";
    final String NAME = "Herbapect";
    final String COMMON_NAME = "Thymi herbae extractum + Primulae radicis tinctura + Sulfogaiacolum";
    final String POTENCY = "(498 mg + 348 mg + 87 mg)/5 ml";
    final String FORM = "syrop";
    final String PACKAGE_UNIT = "butelka 125 ml";
    final String PACKAGE_SIZE = "1";

    @Before
    public void setUp() {
        database = Room.inMemoryDatabaseBuilder(
                        ApplicationProvider.getApplicationContext(),
                        AppDatabase.class)
                    .build();

        ((Apteczka) ApplicationProvider.getApplicationContext()).setApplicationComponent(
                DaggerApplicationComponent.builder()
                        .applicationModule(new ApplicationModule(ApplicationProvider.getApplicationContext()))
                        .dataModule(new TestDataModule(database))
                        .build()
        );
    }

    @Test
    public void createMedicine_eanNotExists_editMedicineLaunched() throws InterruptedException {
        Intents.init();
        ActivityScenario.launch(MainActivity.class);

        createMedicine();

        // Wait for network operation TODO: Replace with IdlingResource implementation
        Thread.sleep(1000);

        intended(hasComponent(EditMedicineActivity.class.getName()));
    }

    @Test
    public void createMedicine_eanExists_editMedicineHasFilledFields() throws InterruptedException {
        ActivityScenario.launch(MainActivity.class);

        createMedicine();

        // Wait for network operation TODO: Replace with IdlingResource implementation
        Thread.sleep(1000);

        onView(withId(R.id.editMedicine_ean)).check(ViewAssertions.matches(withText(EAN)));
        onView(withId(R.id.editMedicine_commonName)).check(ViewAssertions.matches(withText(COMMON_NAME)));
        onView(withId(R.id.editMedicine_potency)).check(ViewAssertions.matches(withText(POTENCY)));
        onView(withId(R.id.editMedicine_form)).check(ViewAssertions.matches(withText(FORM)));
        onView(withId(R.id.editMedicine_name)).check(ViewAssertions.matches(withText(NAME)));
        onView(withId(R.id.editMedicine_packagingSize)).check(ViewAssertions.matches(withText(PACKAGE_SIZE)));
        onView(withId(R.id.editMedicine_packagingUnit)).check(ViewAssertions.matches(withText(PACKAGE_UNIT)));
    }

    private void createMedicine() {
        onView(withId(R.id.mainActivity_fab)).perform(click());

        onView(withId(R.id.editMedicine_ean)).perform(typeText(EAN));
    }

    public AppDatabase getDatabase() {
        return database;
    }
}