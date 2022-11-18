package com.example.fitems.Classes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/login")
    Call<String> makeLogIn(@Field("username") String username, @Field("password") String password);
}
