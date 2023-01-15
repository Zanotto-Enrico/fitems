package com.example.fitems.Classes;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
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

/**
 * Classe rappresentante un utente del sistema.
 * Essa è dotata di diversi campi interni in grado di identificare tale utente e in più una coppia
 * di getter e setter per ciascun campo per potervi accedere sia in lettura che in eventuale scrittura.
 * L'information hiding per questa classe è massimizzato
 */
public class User {
    private String username, nome, cognome, password, email, indirizzo;
    private String dataNascita;
    private int points;
    /**
     * Campo statico identificante le informazioni dell'utente attualmente loggato nel sistema
     */
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

    @Deprecated
    private User() { }

    /**
     * Metodo statico avente l'obiettivo di inizializzare le informazioni relative all'utente attualmente
     * autenticato nel sistema, ovvero il campo statico <code>loggedUser</code>
     * @param c contesto sul quale si esegue il metodo
     * @param view view dalla quale avviare la nuova activity
     */
    public static void initializeLoggedUser(Context c, View view) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.getMyInfo();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loggedUser = new User();
                double lat, lon;
                lat = Double.parseDouble(response.body().get("latitudine").getAsString());
                lon = Double.parseDouble(response.body().get("longitudine").getAsString());

                User.loggedUser.setUsername(response.body().get("username").getAsString());
                User.loggedUser.setPassword("IMPOSSIBLE TO KNOW HERE");
                User.loggedUser.setEmail(response.body().get("email").getAsString());
                User.loggedUser.setNome(response.body().get("nome").getAsString());
                User.loggedUser.setCognome(response.body().get("cognome").getAsString());
                User.loggedUser.setDataNascita(response.body().get("nascita").getAsString());

                try {
                    User.loggedUser.setIndirizzo(LatLonGenerator.getAddressFromCoordinates(lat, lon, c));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (response.body().has("punteggio"))
                    User.loggedUser.setPoints(Integer.valueOf(response.body().get("punteggio").getAsString()));
                else
                    User.loggedUser.setPoints(-1);

                Intent i = new Intent(view.getContext(), MainActivity.class);
                // sto cercando di avviare una activity da un punto esterno a quello del contesto corrente
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(i);
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
