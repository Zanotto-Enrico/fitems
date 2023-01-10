package com.example.fitems.Classes;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit retrofit;
    static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    static CookieHandler cookieHandler = new CookieManager();
    static OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(interceptor)
            .cookieJar(new JavaNetCookieJar(cookieHandler))
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .build();


    public static Retrofit getRetrofitInstance() {
        if (retrofit == null){
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://fitems.giize.com:33331/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }


        return retrofit;
    }
}
