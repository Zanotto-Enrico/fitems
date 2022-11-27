package com.example.fitems.Classes;

import com.google.gson.JsonObject;

import okhttp3.RequestBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/login")
    Call<JsonObject> makeLogIn(@Field("username") String username,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("/register")
    Call<JsonObject> register(@Field("username") String username,
                              @Field("nome") String nome,
                              @Field("cognome") String cognome,
                              @Field("email") String email,
                              @Field("latitudine") Double latitudine,
                              @Field("longitudine") Double longitudine,
                              @Field("data") String data);

    @GET("/post")
    Call<JsonObject> post(@Field("limite") Integer limite,
                          @Field("latitudine") Double latitudine,
                          @Field("longitudine") Double longitudine);

    @FormUrlEncoded
    @POST("/makepost")
    Call<JsonObject> makePost(@Field("titolo") String titolo,
                              @Field("descrizione") String descrizione);

    @FormUrlEncoded
    @POST("/sendmessage")
    Call<JsonObject> sendMessage(@Field("destinatario") String destinatario,
                                 @Field("contenuto") String contenuto);

    @GET("/mychats")
    Call<JsonObject> myChats();

    @GET("/chat")
    Call<JsonObject> chat(@Field("utente") String utente,
                          @Field("limite") Integer limite);

    @GET("/visualizza")
    Call<JsonObject> visualizza(@Field("mittente") String mittente);



    // ---- TODO -----------------------------------------------------------
    @Multipart
    @POST("/uploadImage")
    Call<JsonObject> uploadImage(@Part("file\"; filename=\"pp.png\" ") RequestBody file);

    @GET("/getImage")
    Call<RequestBody> getImage(@Field("idPost") String idPost);

}