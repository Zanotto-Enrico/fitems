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

    /**
     * Metodo che ha il compito di fare il binding con tutte le View all'interno della
     * nostra activity
     */
    private void connectWithGraphic() {
        this.btnIndex = findViewById(R.id.btnIndex);
        this.btnAccedi = findViewById(R.id.btnAccedi);
        this.txtUsername = findViewById(R.id.txtUsername);
        this.txtPassword = findViewById(R.id.txtPassword);
    }

    /**
     * Metodo che ha il compito di raggruppare e inizializzare i listeners
     * sugli elementi dell'activity
     */
    private void addListenerToWidgets() {
        this.btnIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.btnAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usrInserito = txtUsername.getText().toString();
                String pwdInserta = txtPassword.getText().toString();

                if (usrInserito.equals("") || pwdInserta.equals(""))
                    Toast.makeText(getApplicationContext(), "Compila tutti i campi richiesti!", Toast.LENGTH_LONG).show();
                else {
                    ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                    Call<JsonObject> call = apiInterface.makeLogIn(usrInserito, pwdInserta);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.body().get("status").toString().equals("\"LOGGED IN\"")) {
                                Toast.makeText(LoginPage.this, "Benvenuto!", Toast.LENGTH_LONG).show();
                                checkRecLogUsr.logIn(usrInserito, pwdInserta, MyDate.getToday());

                                User.initializeLoggedUser(getApplicationContext(), view);
                            } else {
                                Toast.makeText(LoginPage.this, "Errore!\nLe credenziali potrebbero essere errate oppure si è verificato un errore.\nRiprova più tardi.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(LoginPage.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }


}