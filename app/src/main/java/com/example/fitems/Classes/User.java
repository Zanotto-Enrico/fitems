package com.example.fitems.Classes;

import android.content.Context;
import android.widget.Toast;

import com.example.fitems.MainActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User {
    private String username, nome, cognome, password, email, indirizzo;
    private String dataNascita;
    private int points;
    public static User loggedUser;

    public User(String username, String nome, String cognome, String password, String email, String indirizzo, String dataNascita, int points) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.email = email;
        this.indirizzo = indirizzo;
        this.dataNascita = dataNascita;
        this.points = points;
    }

    private User() { }

    public static void initializeLoggedUser(Context c) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.getMyInfo();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                User.loggedUser = new User();
                Double lat, lon;
                lat = Double.valueOf(response.body().get("latitudine").getAsString());
                lon = Double.valueOf(response.body().get("longitudine").getAsString());

                User.loggedUser.setUsername(response.body().get("username").getAsString());
                User.loggedUser.setPassword("IMPOSSIBLE TO KNOW HERE");
                User.loggedUser.setEmail(response.body().get("email").getAsString());
                User.loggedUser.setCognome(response.body().get("cognome").getAsString());
                User.loggedUser.setDataNascita(response.body().get("nascita").getAsString());

                try {
                    User.loggedUser.setIndirizzo(LatLonGenerator.getAddressFromCoordinates(lat, lon, c));
                } catch (IOException e) { e.printStackTrace(); }

                if (response.body().has("punteggio"))
                    User.loggedUser.setPoints(Integer.valueOf(response.body().get("punteggio").getAsString()));
                else
                    User.loggedUser.setPoints(-1);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //Toast.makeText(getA, "[Home]" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getDataNascita() {
        return this.dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  Objects.equals(this.username, user.username) &&
                Objects.equals(this.nome, user.nome) &&
                Objects.equals(this.cognome, user.cognome) &&
                Objects.equals(this.password, user.password) &&
                Objects.equals(this.email, user.email) &&
                Objects.equals(this.indirizzo, user.indirizzo);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", dataNascita=" + dataNascita +
                ", points=" + points +
                '}';
    }
}
