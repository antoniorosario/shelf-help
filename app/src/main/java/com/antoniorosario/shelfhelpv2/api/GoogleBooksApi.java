package com.antoniorosario.shelfhelpv2.api;


import com.antoniorosario.shelfhelpv2.models.BaseJsonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleBooksApi {

    @GET("/books/v1/volumes")
    Call<BaseJsonResponse> executeQuery(
            @Query("key") String apiKey,
            @Query("q") String queryString);
}
