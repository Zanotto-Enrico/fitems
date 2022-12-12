package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.RetrofitClient;
import com.example.fitems.Classes.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPage extends AppCompatActivity {

    private ImageButton btnIndex;
    private Button btnAccedi;
    private TextView txtUsername;
    private TextView txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_login_page);

        // assegnamenti di tutti gli elementi della schermata di login
        this.btnIndex = (ImageButton)findViewById(R.id.btnIndex);
        this.btnAccedi = (Button) findViewById(R.id.btnAccedi);
        this.txtUsername = (TextView) findViewById(R.id.txtUsername);
        this.txtPassword = (TextView) findViewById(R.id.txtPassword);


        // di seguito il codice dei listener di tutti i pulsanti dell'activity

        this.btnIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), FirstPage.class);
                view.getContext().startActivity(i);
            }
        });

        this.btnAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usrInserito = txtUsername.getText().toString();
                String pwdInserta = txtPassword.getText().toString();
                String url = "login";

                ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                Call<JsonObject> call = apiInterface.makeLogIn(usrInserito, pwdInserta);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Toast.makeText(LoginPage.this, "Status: " + response.body().get("status"), Toast.LENGTH_LONG).show();

                        if(response.body().get("status").toString().equals("\"LOGGED IN\"")) {
                            Intent i = new Intent(view.getContext(), MainActivity.class);
                            view.getContext().startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(LoginPage.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }
}