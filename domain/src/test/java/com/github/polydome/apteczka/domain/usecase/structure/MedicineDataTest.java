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
                .commonName("test common name")
                .ean("1836281273612")
                .form("test form")
                .name("test name")
                .packagingSize(12)
                .packagingUnit("test unit")
                .potency("test potency")
                .build();

        MedicineData result = MedicineData.fromMedicine(medicine);

        assertThat(result.getCommonName(), equalTo(medicine.getCommonName()));
        assertThat(result.getEan(), equalTo(medicine.getEan()));
        assertThat(result.getForm(), equalTo(medicine.getForm()));
        assertThat(result.getName(), equalTo(medicine.getName()));
        assertThat(result.getPackagingSize(), equalTo(medicine.getPackagingSize()));
        assertThat(result.getPackagingUnit(), equalTo(medicine.getPackagingUnit()));
        assertThat(result.getPotency(), equalTo(medicine.getPotency()));
    }

    @Test
    void toMedicine_returnsProperMedicine() {
        MedicineData medicineData = MedicineData.builder()
                .commonName("test common name")
                .ean("test ean")
                .form("test form")
                .name("test name")
                .packagingSize(3)
                .packagingUnit("test unit")
                .potency("test potency")
                .build();

        Medicine result = medicineData.toMedicine(13L);

        assertThat(result.getId(), equalTo(13L));
        assertThat(result.getCommonName(), equalTo(medicineData.getCommonName()));
        assertThat(result.getEan(), equalTo(medicineData.getEan()));
        assertThat(result.getForm(), equalTo(medicineData.getForm()));
        assertThat(result.getName(), equalTo(medicineData.getName()));
        assertThat(result.getPackagingSize(), equalTo(medicineData.getPackagingSize()));
        assertThat(result.getPackagingUnit(), equalTo(medicineData.getPackagingUnit()));
        assertThat(result.getPotency(), equalTo(medicineData.getPotency()));
    }
}