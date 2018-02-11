package com.bilgehankalkan.semihozturkhackathon.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bilgehan on 11.02.2018.
 */

public class ApiClass {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            String BASE_URL = "https://getir-bitaksi-hackathon.herokuapp.com";
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
