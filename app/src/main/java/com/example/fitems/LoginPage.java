package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.CheckRecentlyLoggedUser;
import com.example.fitems.Classes.MyDate;
import com.example.fitems.Classes.RetrofitClient;
import com.example.fitems.Classes.User;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPage extends AppCompatActivity {

    private ImageButton btnIndex;
    private Button btnAccedi;
    private TextView txtUsername;
    private TextView txtPassword;

    private CheckRecentlyLoggedUser checkRecLogUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_login_page);
        this.checkRecLogUsr = new CheckRecentlyLoggedUser(this.getSharedPreferences("fitems", Context.MODE_PRIVATE));

        connectWithGraphic();
        addListenerToWidgets();
    }

    private void connectWithGraphic() {
        this.btnIndex = findViewById(R.id.btnIndex);
        this.btnAccedi = findViewById(R.id.btnAccedi);
        this.txtUsername = findViewById(R.id.txtUsername);
        this.txtPassword = findViewById(R.id.txtPassword);
    }


    private void addListenerToWidgets() {
        this.btnIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), FirstPage.class);
                view.getContext().startActivity(i);
                finish();
            }
        });

        this.btnAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usrInserito = txtUsername.getText().toString();
                String pwdInserta = txtPassword.getText().toString();

                ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                Call<JsonObject> call = apiInterface.makeLogIn(usrInserito, pwdInserta);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Toast.makeText(LoginPage.this, "Status: " + response.body().get("status"), Toast.LENGTH_LONG).show();

                        if(response.body().get("status").toString().equals("\"LOGGED IN\"")) {
                            checkRecLogUsr.logIn(usrInserito, pwdInserta, MyDate.getToday(), true);

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