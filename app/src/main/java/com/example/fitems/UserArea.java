package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.CheckRecentlyLoggedUser;
import com.example.fitems.Classes.MyDate;
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
    private ImageButton btnBack, btnEdit, btnLogOut;
    static User currentUser;

    private CheckRecentlyLoggedUser checkRecLogUsr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_user_area);

        this.checkRecLogUsr = new CheckRecentlyLoggedUser(this.getSharedPreferences("fitems", Context.MODE_PRIVATE));

        connectWithGraphic();
        getInfoAPI();
        addListenerToWidgets();
    }


    //Binding degli elementi grafici
    private void connectWithGraphic(){
        this.txtName = findViewById(R.id.txtName);
        this.txtLastName = findViewById(R.id.txtLastName);
        this.txtEmail = findViewById(R.id.txtEmail);
        this.txtLocation = findViewById(R.id.txtLocation);
        this.txtPost = findViewById(R.id.txtPost);
        this.txtUserName = findViewById(R.id.txtUsername);

        this.btnLogOut = findViewById(R.id.btnLogOut_UserArea);
        this.btnBack = findViewById(R.id.btnBack);
        this.btnEdit = findViewById(R.id.btnEdit);
    }


    // Inizializzazione dei listener per i due bottomi presenti nella schermata
    private void addListenerToWidgets(){

        this.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UserAreaEdit.class);
                view.getContext().startActivity(i);
            }
        });

        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRecLogUsr.setAsLoggedOrNot(currentUser.getUsername(), MyDate.getToday(), false);

                Intent i = new Intent(UserArea.this, FirstPage.class);
                view.getContext().startActivity(i);
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