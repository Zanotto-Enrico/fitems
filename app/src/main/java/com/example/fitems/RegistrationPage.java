package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.LatLonGenerator;
import com.example.fitems.Classes.RetrofitClient;
import com.example.fitems.Classes.User;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationPage extends AppCompatActivity {

    private EditText txtUsername, txtNome, txtCognome, txtPassword1, txtPassword2, txtIndirizzo, txtEmail;
    private Button btnRegistrati;
    private ImageButton btnIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_registration_page);

        connectWithGraphic();
        addListenerToWidgets();
    }


    private void connectWithGraphic() {
        this.txtUsername = findViewById(R.id.txtUsername_registration);
        this.txtNome = findViewById(R.id.txtNome_registration);
        this.txtCognome = findViewById(R.id.txtCognome_registration);
        this.txtPassword1 = findViewById(R.id.txtPassword1_registration);
        this.txtPassword2 = findViewById(R.id.txtPassword2_registration);
        this.txtIndirizzo = findViewById(R.id.txtIndirizzo_registration);
        this.txtEmail = findViewById(R.id.txtEmail_registration);

        this.btnRegistrati = findViewById(R.id.btnRegistrati_registration);
        this.btnIndex = findViewById(R.id.btnIndex_registration);
    }


    private void addListenerToWidgets() {
        this.btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                    !txtUsername.getText().toString().equals("") &&
                    !txtNome.getText().toString().equals("") &&
                    !txtCognome.getText().toString().equals("") &&
                    !txtIndirizzo.getText().toString().equals("") &&
                    !txtEmail.getText().toString().equals("") &&
                    !txtPassword1.getText().toString().equals("") &&
                    !txtPassword2.getText().toString().equals("") &&
                    txtPassword1.getText().toString().equals(txtPassword2.getText().toString())
                ) {
                    User user = new User(txtUsername.getText().toString(),
                            txtNome.getText().toString(),
                            txtCognome.getText().toString(),
                            txtPassword1.getText().toString(),
                            txtEmail.getText().toString(),
                            txtIndirizzo.getText().toString());

                    try {
                        registerUser(user, view);
                    } catch (Exception e) {
                        Toast.makeText(RegistrationPage.this, "Errore: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(RegistrationPage.this, "Controlla i dati inseriti!", Toast.LENGTH_SHORT).show();

            }
        });


        this.btnIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void registerUser(User u, View v) throws IOException {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Pair<Double, Double> c = LatLonGenerator.getCoordinatesFromAddress(u.getIndirizzo(), this);

        Call<JsonObject> call = apiInterface.register(u.getUsername(), u.getNome(), u.getCognome(), u.getEmail(), u.getPassword(), c.first, c.second);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.body().get("status").toString().equals("\"SUCCESS\"")) {
                    Toast.makeText(RegistrationPage.this, "Utente creato con successo!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(RegistrationPage.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}