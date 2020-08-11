package com.github.polydome.apteczka.network;

import com.github.polydome.apteczka.domain.service.MedicineDetails;
import com.github.polydome.apteczka.network.model.RemedyPackaging;
import com.github.polydome.apteczka.network.model.RemedyProduct;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.mock.Calls;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RemedyMedicineDetailsEndpointTest {
    RemedyService remedyService = Mockito.mock(RemedyService.class);
    RemedyMedicineDetailsEndpoint SUT = new RemedyMedicineDetailsEndpoint(remedyService);

    String EAN = "8367138201222";
    int PRODUCT_ID = 381;

    @Test
    public void fetchMedicineDetails_packagingAndProductExist_emitsMedicineDetails() {
        MedicineDetails expected = createMedicineDetails();

        Mockito.when(remedyService.getPackaging(EAN)).thenReturn(Calls.response(
                createRemedyPackaging(expected)
        ));

        Mockito.when(remedyService.getProduct(PRODUCT_ID)).thenReturn(Calls.response(
                createRemedyProduct(expected)
        ));

        SUT.fetchMedicineDetails(EAN).test().assertValue(expected);
    }

    @Test
    public void fetchMedicineDetails_packagingNotExists_empty() {
        Mockito.when(remedyService.getPackaging(EAN)).thenReturn(Calls.response(
                Response.<RemedyPackaging>error(404,
                        ResponseBody.create(
                                MediaType.get("application/json"),
                                "{ \"error\": \"No such packaging\" }"
                        )
                )
        ));

        SUT.fetchMedicineDetails(EAN).test().assertNoValues().assertComplete();
    }

    @Test
    public void fetchMedicineDetails_productNotExists_empty() {
        Mockito.when(remedyService.getPackaging(EAN)).thenReturn(Calls.response(
                RemedyPackaging.builder().productId(PRODUCT_ID).build()
        ));

        Mockito.when(remedyService.getProduct(PRODUCT_ID)).thenReturn(Calls.response(
                Response.<RemedyProduct>error(404,
                        ResponseBody.create(
                                MediaType.get("application/json"),
                                "{ \"error\": \"No such product\" }"
                        )
                )
        ));

        SUT.fetchMedicineDetails(EAN).test().assertNoValues().assertComplete();
    }
    
    private MedicineDetails createMedicineDetails() {
        return MedicineDetails.builder()
                .commonName("test common name")
                .form("test form")
                .name("test name")
                .packagingSize(12)
                .packagingUnit("test unit")
                .potency("test potency")
                .build();
    }
    
    private RemedyProduct createRemedyProduct(MedicineDetails medicineDetails) {
        return RemedyProduct.builder()
                .activeSubstance("-")
                .atc("-")
                .commonName(medicineDetails.getCommonName())
                .form(medicineDetails.getForm())
                .name(medicineDetails.getName())
                .id(1128)
                .potency(medicineDetails.getPotency())
                .build();
    }
    
    private RemedyPackaging createRemedyPackaging(MedicineDetails medicineDetails) {
        return RemedyPackaging.builder()
                .ean(EAN)
                .id(112)
                .productId(PRODUCT_ID)
                .size(medicineDetails.getPackagingSize())
                .unit(medicineDetails.getPackagingUnit())
                .build();
    }
}