package com.github.polydome.apteczka.network;

import com.github.polydome.apteczka.domain.service.MedicineDetails;
import com.github.polydome.apteczka.domain.service.MedicineDetailsEndpoint;
import com.github.polydome.apteczka.network.model.RemedyPackaging;
import com.github.polydome.apteczka.network.model.RemedyProduct;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemedyMedicineDetailsEndpoint implements MedicineDetailsEndpoint {
    private final RemedyService remedyService;

    public RemedyMedicineDetailsEndpoint(RemedyService remedyService) {
        this.remedyService = remedyService;
    }

    @Override
    public Maybe<MedicineDetails> fetchMedicineDetails(final String ean) {
        return Maybe.create(new MaybeOnSubscribe<MedicineDetails>() {
            @Override
            public void subscribe(final MaybeEmitter<MedicineDetails> emitter) {
                remedyService.getPackaging(ean).enqueue(new Callback<RemedyPackaging>() {
                    @Override
                    public void onResponse(@NotNull Call<RemedyPackaging> call, @NotNull final Response<RemedyPackaging> packaging) {
                        if (packaging.isSuccessful())
                            remedyService.getProduct(packaging.body().getProductId()).enqueue(new Callback<RemedyProduct>() {
                                @Override
                                public void onResponse(@NotNull Call<RemedyProduct> call,@NotNull Response<RemedyProduct> product) {
                                    if (product.isSuccessful())
                                        emitter.onSuccess(createMedicineDetails(packaging.body(), product.body()));
                                    else
                                        emitter.onComplete();
                                }

                                @Override
                                public void onFailure(@NotNull Call<RemedyProduct> call, @NotNull Throwable t) {
                                    emitter.onError(t);
                                }
                            });
                        else
                            emitter.onComplete();
                    }

                    @Override
                    public void onFailure(@NotNull Call<RemedyPackaging> call,@NotNull Throwable t) {
                        emitter.onError(t);
                    }
                });
            }
        });
    }

    private MedicineDetails createMedicineDetails(RemedyPackaging packaging, RemedyProduct product) {
        return MedicineDetails.builder()
                .commonName(product.getCommonName())
                .form(product.getForm())
                .name(product.getName())
                .packagingSize(packaging.getSize())
                .packagingUnit(packaging.getUnit())
                .potency(product.getPotency())
                .build();
    }
}
