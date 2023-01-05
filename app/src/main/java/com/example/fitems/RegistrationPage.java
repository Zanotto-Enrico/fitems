package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.LatLonGenerator;
import com.example.fitems.Classes.RetrofitClient;
import com.example.fitems.Classes.User;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationPage extends AppCompatActivity {

    private EditText txtUsername, txtNome, txtCognome, txtPassword1, txtPassword2, txtIndirizzo, txtEmail;
    private Button btnRegistrati, btnDatePicker;
    private ImageButton btnIndex;
    private DatePickerDialog datePickerDialog;



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
        this.btnDatePicker = findViewById(R.id.btnDatePicker_registration);
        this.btnIndex = findViewById(R.id.btnIndex_registration);
        initDatePicker();
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
                    !btnDatePicker.getText().toString().equals("") &&
                    txtPassword1.getText().toString().equals(txtPassword2.getText().toString())
                ) {
                    User user = new User(txtUsername.getText().toString(),
                            txtNome.getText().toString(),
                            txtCognome.getText().toString(),
                            txtPassword1.getText().toString(),
                            txtEmail.getText().toString(),
                            txtIndirizzo.getText().toString(),
                            formatDate((String) btnDatePicker.getText()),
                            0);

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


        this.btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
    }

    private String formatDate(String d) {
        String[] info = d.split("/");

        if (info.length == 3) {
            return info[2] + "-" + info[1] + "-" + info[0];
        }
        return "";
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                String data = d + "/" + (m + 1) + "/" + y;
                btnDatePicker.setText(data);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, AlertDialog.BUTTON_POSITIVE, dateSetListener, year, month, day);
    }

    private void registerUser(User u, View v) throws IOException {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Pair<Double, Double> c = LatLonGenerator.getCoordinatesFromAddress(u.getIndirizzo(), this);

        Call<JsonObject> call = apiInterface.register(u.getUsername(), u.getNome(), u.getCognome(), u.getEmail(), u.getDataNascita(), u.getPassword(), c.first, c.second);
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