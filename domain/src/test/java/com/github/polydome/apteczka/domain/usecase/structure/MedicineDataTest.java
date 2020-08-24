package com.github.polydome.apteczka.domain.usecase.structure;

import com.github.polydome.apteczka.domain.model.Medicine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MedicineDataTest {
    @Test
    public void fromMedicine_convertedMedicineData() {
        Medicine medicine = Medicine.builder()
                .id(113)
                .title("test title")
                .build();

        MedicineData result = MedicineData.fromMedicine(medicine);

        assertThat(result.getTitle(), equalTo(medicine.getTitle()));
    }

    @Test
    void toMedicine_returnsProperMedicine() {
        MedicineData medicineData = MedicineData.builder()
                .title("test title")
                .build();

        Medicine result = medicineData.toMedicine(13L);

        assertThat(result.getId(), equalTo(13L));
        assertThat(result.getTitle(), equalTo(medicineData.getTitle()));
    }
}