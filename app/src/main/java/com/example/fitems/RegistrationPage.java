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
    private ImageButton btnBack;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_registration_page);

        connectWithGraphic();
        addListenerToWidgets();
        initDatePicker();
    }

    /**
     * Metodo che ha il compito di fare il binding con tutte le View all'interno della
     * nostra activity
     */
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
        this.btnBack = findViewById(R.id.btnIndex_registration);
    }

    /**
     * Metodo che ha il compito di raggruppare e inizializzare i listeners
     * sugli elementi dell'activity
     */
    private void addListenerToWidgets() {
        this.txtIndirizzo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(getApplicationContext(), "Per una maggiore precisione consigiamo di utilizzare l'indirizzo di residenza con annesso numero civico.", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Non garantiamo il funzionamento dell'applicativo nel caso di inserimento di indirizzi troppo generici come strade principali o nomi di città", Toast.LENGTH_LONG).show();
                }
            }
        });

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
                        registerUser(user);
                    } catch (Exception e) {
                        Toast.makeText(RegistrationPage.this, "Errore. Il contenuto dei campi forniti non è valido. Controllare.", Toast.LENGTH_LONG).show();
                        Toast.makeText(RegistrationPage.this, "Provare a modificare lo username oppure il formato con cui l'indirizzo è stato inserito", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(RegistrationPage.this, "Attenzione: compila tutti i campi richiesti e controlla l'uguaglianza delle password!", Toast.LENGTH_SHORT).show();

            }
        });


        this.btnBack.setOnClickListener(new View.OnClickListener() {
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

    /**
     * Metodo per formattare la data in maniera corretta secondo lo standard richiesto dal backend
     * @param d data da formattare
     * @return data formattata
     */
    private String formatDate(String d) {
        String[] info = d.split("/");

        if (info.length == 3) {
            return info[2] + "-" + info[1] + "-" + info[0];
        }
        return "";
    }

    /**
     * Metodo avente il compito di impostare correttamente il datepicker della schermata di registrazione
     */
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

        this.datePickerDialog = new DatePickerDialog(this, AlertDialog.BUTTON_POSITIVE, dateSetListener, year, month, day);
        this.datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
    }

    /**
     * Metodo che, interfacciandosi con il backend, permette la creazione di un utente nel sistema.
     * Il metodo è creato a posta per ritornare eventuali errori nel caso in cui la procedura non si concretizzasse con successo
     * @param u utente da registrare
     * @throws IOException
     */
    private void registerUser(User u) throws IOException {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Pair<Double, Double> c = LatLonGenerator.getCoordinatesFromAddress(u.getIndirizzo(), this);

        Call<JsonObject> call = apiInterface.register(u.getUsername(), u.getNome(), u.getCognome(), u.getEmail(), u.getDataNascita(), u.getPassword(), c.first, c.second);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.body().get("status").toString().equals("\"SUCCESS\"")) {
                    Toast.makeText(RegistrationPage.this, "Utente creato con successo! Esegui ora l'accesso al sistema.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(RegistrationPage.this, "Errore: non è stato possibile creare il tuo utente. Riprovare più tardi.", Toast.LENGTH_LONG).show();
                    Toast.makeText(RegistrationPage.this, "Potresti aver utilizzato uno username già esistente, prova a modificarlo!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(RegistrationPage.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}