package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.RetrofitClient;
import com.example.fitems.Classes.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserArea extends AppCompatActivity {

    private TextView txtName, txtLastName, txtEmail, txtLocation, txtPost, txtUserName;
    private CircleImageView userImage;
    private ImageButton btnBack, btnEdit;
    static User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        connectWithGraphic();
        getInfoAPI();
        addListenerToWidgets();
    }


    //Binding degli elementi grafici
    private void connectWithGraphic(){
        txtName = (TextView) findViewById(R.id.txtName);
        txtLastName = (TextView) findViewById(R.id.txtLastName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtLocation = (TextView) findViewById(R.id.txtLocation);
        txtPost = (TextView) findViewById(R.id.txtPost);
        txtUserName = (TextView) findViewById(R.id.txtUsername);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnEdit = (ImageButton) findViewById(R.id.btnEdit);
    }


    // Inizializzazione dei listener per i due bottomi presenti nella schermata
    private void addListenerToWidgets(){

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UserAreaEdit.class);
                view.getContext().startActivity(i);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO --> Create the pages to go to
            }
        });
    }


    private void getInfoAPI(){
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.getMyInfo();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                currentUser = new User(response.body().get("username").toString(), response.body().get("nome").toString(),
                                        response.body().get("cognome").toString(), "",response.body().get("email").toString(),
                                        "");

                txtUserName.setText(currentUser.getUsername());
                txtName.setText(currentUser.getNome());
                txtLastName.setText(currentUser.getCognome());
                txtEmail.setText(currentUser.getEmail());
                txtLocation.setText(currentUser.getIndirizzo());
                txtPost.setText(response.body().get("postPubblicati").toString());

                // TODO --> Convertire da Latitudine e Longitudine nella Via per inserirlo nel campo Location



            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(UserArea.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}