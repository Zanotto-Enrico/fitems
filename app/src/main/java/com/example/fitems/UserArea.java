package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.RetrofitClient;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        txtName = (TextView) findViewById(R.id.txtName);
        txtLastName = (TextView) findViewById(R.id.txtLastName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtLocation = (TextView) findViewById(R.id.txtLocation);
        txtPost = (TextView) findViewById(R.id.txtPost);
        txtUserName = (TextView) findViewById(R.id.txtUsername);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnEdit = (ImageButton) findViewById(R.id.btnEdit);

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.getMyInfo();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                txtUserName.setText(response.body().get("username").getAsString());
                txtName.setText((response.body().get("nome").getAsString()));
                txtLastName.setText((response.body().get("cognome").getAsString()));
                txtEmail.setText((response.body().get("email").getAsString()));
                txtPost.setText((response.body().get("postPubblicati").getAsString()));

                // TODO --> Convertire da Latitudine e Longitudine nella Via per inserirlo nel campo Location

                /*  Geocoder geocoder = new Geocoder(getApplicationContext(),new Locale(Locale.ITALIAN.getISO3Language()));
                *   List<Address> addresses = new ArrayList<>();
                *   try {
                *       addresses = geocoder.getFromLocation(response.body().get("latitudine").getAsDouble(), response.body().get("longitudine").getAsDouble(), 1);
                *   } catch (IOException e) {
                *       e.printStackTrace();
                *   }
                *   addresses.get(0).getThoroughfare();
                */

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

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
}