package com.example.fitems.Classes;

import android.database.Observable;

import com.google.gson.JsonObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Query;

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
                              @Field("data") String data,
                              @Field("password") String password,
                              @Field("latitudine") Double latitudine,
                              @Field("longitudine") Double longitudine);

    @GET("/post")
    Call<JsonObject> post(@Field("limite") Integer limite,
                          @Field("latitudine") Double latitudine,
                          @Field("longitudine") Double longitudine);

    @GET("/post")
    Call<JsonObject> post();

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

    @FormUrlEncoded
    @POST("/myInfo")
    Call<JsonObject> updateMyInfo(@Field("nome") String nome,
                                  @Field("cognome") String cognome,
                                  @Field("email") String email,
                                  @Field("latitudine") Double latitudine,
                                  @Field("longitudine") Double longitudine);

    @GET("/myInfo")
    Call<JsonObject> getMyInfo();



    @Multipart
    @POST("/uploadImage")
    Call<JsonObject> uploadImage(@Part("idPost") RequestBody idPost,
                                           @Part MultipartBody.Part immagine);




    @GET("/getImage")
    Call<ResponseBody> getImage(@Query("idPost") String idPost);

}
