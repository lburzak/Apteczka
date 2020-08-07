package com.github.polydome.apteczka.di.module;

import com.github.polydome.apteczka.domain.service.MedicineDetails;
import com.github.polydome.apteczka.domain.service.MedicineDetailsEndpoint;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Maybe;

@Module
public class NetworkModule {
    @Provides
    public MedicineDetailsEndpoint medicineDetailsEndpoint() {
        return new MedicineDetailsEndpoint() {
            @Override
            public Maybe<MedicineDetails> fetchMedicineDetails(String ean) {
                return Maybe.empty();
            }
        };
    }
}
