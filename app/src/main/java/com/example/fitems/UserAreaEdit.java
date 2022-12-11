package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Visibility;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.RetrofitClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAreaEdit extends AppCompatActivity {

    private EditText editUsername, editName, editLastName, editEmail, editLocation;
    private ImageButton btnCheck, btnClose;
    private TextInputLayout inUserName, inName, inLastName, inEmail, inLocation;
    private String username, name, lastname, email, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editUsername = (EditText) findViewById(R.id.edUsername);
        editName = (EditText) findViewById(R.id.edName);
        editLastName = (EditText) findViewById(R.id.edLastName);
        editEmail = (EditText) findViewById(R.id.edEmail);
        editLocation = (EditText) findViewById(R.id.edLocation);

        btnCheck = (ImageButton) findViewById(R.id.btnEdit);
        btnClose = (ImageButton) findViewById(R.id.btnClose);

        inUserName = (TextInputLayout) findViewById(R.id.inputUsername);
        inName = (TextInputLayout) findViewById(R.id.inputName);
        inLastName = (TextInputLayout) findViewById(R.id.inputLastName);
        inEmail = (TextInputLayout) findViewById(R.id.inputEmail);
        inLocation = (TextInputLayout) findViewById(R.id.inputLocation);

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.getMyInfo();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                username = response.body().get("username").getAsString();
                name = (response.body().get("nome").getAsString());
                lastname = (response.body().get("cognome").getAsString());
                email = (response.body().get("email").getAsString());

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

        inUserName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inUserName.setHint("Username: " + username);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        inName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inName.setHint("Name: " + name);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        inLastName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inLastName.setHint("Lastname: " + lastname);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        inEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inEmail.setHint("Email: " + email);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()){
                    inEmail.setError(getString(R.string.input_error));
                    inEmail.setErrorEnabled(true);
                } else{
                    inEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // TODO --> Fare TextChangedListener anche per il campo location Location

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UserArea.class);
                view.getContext().startActivity(i);
                finish();
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input_username = editUsername.getText().toString();
                String input_name = editName.getText().toString();
                String input_lastname = editLastName.getText().toString();
                String input_email = editEmail.getText().toString();
                //TODO --> Usare funzione che trasforma l'indirizzo in longitudine e latitudine(decimali)

                ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                Call<JsonObject> call = apiInterface.updateMyInfo(input_name, input_lastname, input_email, 0., 0.);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Intent i = new Intent(view.getContext(), UserArea.class);
                        view.getContext().startActivity(i);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Intent i = new Intent(view.getContext(), UserAreaEdit.class);
                        view.getContext().startActivity(i);
                        finish();
                    }
                });
            }
        });
    }
}