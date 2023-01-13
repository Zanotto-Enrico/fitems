package com.example.fitems;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.CheckRecentlyLoggedUser;
import com.example.fitems.Classes.MyDate;
import com.example.fitems.Classes.RetrofitClient;
import com.example.fitems.Classes.User;
import com.google.gson.JsonObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstPage extends AppCompatActivity {

    private Button btnAccedi;
    private Button btnRegistrati;
    private ProgressBar spinner;

    private CheckRecentlyLoggedUser checkRecLogUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_first_page);

        connectWithGraphic();
        addListenerToWidgets();

        this.spinner.setVisibility(View.INVISIBLE);
        this.checkRecLogUsr = new CheckRecentlyLoggedUser(this.getSharedPreferences("fitems", Context.MODE_PRIVATE));

        if (checkRecLogUsr.isLoginValid()) {
            this.spinner.setVisibility(View.VISIBLE);
            this.btnAccedi.setVisibility(View.INVISIBLE);
            this.btnRegistrati.setVisibility(View.INVISIBLE);

            Pair<String,String> infos = this.checkRecLogUsr.getInfoUserLogged();

            ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

            Call<JsonObject> call = apiInterface.makeLogIn(infos.first, infos.second);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Toast.makeText(FirstPage.this, "Status: " + response.body().get("status"), Toast.LENGTH_LONG).show();

                    if(response.body().get("status").toString().equals("\"LOGGED IN\"")) {
                        User.initializeLoggedUser(getApplicationContext(), new View(getApplicationContext()));
                        //finish();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(FirstPage.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void connectWithGraphic() {
        this.btnRegistrati = findViewById(R.id.button2);
        this.btnAccedi = findViewById(R.id.button);
        this.spinner = (ProgressBar)findViewById(R.id.loading);
    }


    private void addListenerToWidgets() {
        btnAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTo(view, LoginPage.class);
            }
        });

        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTo(view, RegistrationPage.class);
            }
        });
    }

    private void goTo(View view, Class<?> goWhere) {
        Intent i = new Intent(FirstPage.this, goWhere);
        view.getContext().startActivity(i);
    }


    @Override
    public void onBackPressed() { }


}