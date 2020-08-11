package com.github.polydome.apteczka.network;

import com.github.polydome.apteczka.network.model.RemedyPackaging;
import com.github.polydome.apteczka.network.model.RemedyProduct;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RemedyService {
    @GET("packaging")
    Maybe<RemedyPackaging> getPackaging(@Query("ean") String ean);

    @GET("product/{id}")
    Maybe<RemedyProduct> getProduct(@Path("id") int id);
}
