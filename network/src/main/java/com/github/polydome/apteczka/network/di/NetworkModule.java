package com.github.polydome.apteczka.network.di;

import com.github.polydome.apteczka.domain.service.MedicineDetailsEndpoint;
import com.github.polydome.apteczka.network.RemedyMedicineDetailsEndpoint;
import com.github.polydome.apteczka.network.RemedyService;

import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule {
    @Provides
    public MedicineDetailsEndpoint medicineDetailsEndpoint(RemedyService remedyService) {
        return new RemedyMedicineDetailsEndpoint(remedyService);
    }

    @Provides
    public RemedyService remedyService(Retrofit retrofit) {
        return retrofit.create(RemedyService.class);
    }

    @Provides
    @Singleton
    public Retrofit retrofit(@Named("remedyUrl") String remedyUrl) {
        return new Retrofit.Builder()
                .baseUrl(remedyUrl)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }
}
