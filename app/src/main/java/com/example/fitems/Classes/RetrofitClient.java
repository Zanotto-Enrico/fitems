package com.example.fitems.Classes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://fitems.giize.com:33331/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit;
    }
}
