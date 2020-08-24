package com.github.polydome.apteczka.network;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RemedyIntegrationTest {
    String url = System.getenv("remedyUrl");
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

    RemedyService remedyService = retrofit.create(RemedyService.class);
    RemedyProductEndpoint SUT = new RemedyProductEndpoint(remedyService);

    final String EAN = "5909990663613";
    final String EAN_NOT_EXISTING = "1909990663613";
    final String NAME = "Herbapect";
    final String COMMON_NAME = "Thymi herbae extractum + Primulae radicis tinctura + Sulfogaiacolum";
    final String POTENCY = "(498 mg + 348 mg + 87 mg)/5 ml";
    final String FORM = "syrop";
    final String PACKAGE_UNIT = "butelka 125 ml";
    final int PACKAGE_SIZE = 1;

    @Test
    void fetchMedicineDetails_eanExists_emitsMedicineDetails() {
        SUT.fetchMedicineDetails(EAN).test().assertValue(
                MedicineDetails.builder()
                    .commonName(COMMON_NAME)
                    .form(FORM)
                    .packagingSize(PACKAGE_SIZE)
                    .name(NAME)
                    .packagingUnit(PACKAGE_UNIT)
                    .potency(POTENCY)
                    .build()
        );
    }

    @Test
    void fetchMedicineDetails_eanNotExists_emitsMedicineDetails() {
        SUT.fetchMedicineDetails(EAN_NOT_EXISTING).test().assertValue(
                MedicineDetails.builder().build()
        );
    }
}
