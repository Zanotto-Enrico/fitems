package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.transition.Visibility;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.LatLonGenerator;
import com.example.fitems.Classes.RetrofitClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAreaEdit extends AppCompatActivity {

    private TextView editUsername;
    private EditText editName, editLastName, editEmail, editLocation;
    private ImageButton btnCheck, btnClose;
    private TextInputLayout inName, inLastName, inEmail, inLocation;
    private String name, lastname, email, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_user_area_edit);

        connectWithGraphic();
        addListenerToWidgets();

        editUsername.setText(UserArea.currentUser.getUsername());
    }

    private void connectWithGraphic(){
        editUsername = (TextView) findViewById(R.id.edUsername);

        editName = (EditText) findViewById(R.id.edName);
        editLastName = (EditText) findViewById(R.id.edLastName);
        editEmail = (EditText) findViewById(R.id.edEmail);
        editLocation = (EditText) findViewById(R.id.edLocation);

        btnCheck = (ImageButton) findViewById(R.id.btnEdit);
        btnClose = (ImageButton) findViewById(R.id.btnClose);

        inName = (TextInputLayout) findViewById(R.id.inputName);
        inLastName = (TextInputLayout) findViewById(R.id.inputLastName);
        inEmail = (TextInputLayout) findViewById(R.id.inputEmail);
        inLocation = (TextInputLayout) findViewById(R.id.inputLocation);
    }
    
    private void updateInfoAPI(View v, String nome, String cognome, String email, Double latitudine, Double longitudine)throws IOException{
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<JsonObject> call;

        call = apiInterface.updateMyInfo((nome.equals("")? UserArea.currentUser.getNome() : nome),
                (cognome.equals("")? UserArea.currentUser.getCognome(): cognome), (email.equals("") ? UserArea.currentUser.getEmail() : email),
                ((latitudine == null)? LatLonGenerator.getCoordinatesFromAddress(UserArea.currentUser.getIndirizzo(), this).first: latitudine),
                ((longitudine == null)? LatLonGenerator.getCoordinatesFromAddress(UserArea.currentUser.getIndirizzo(), this).second: longitudine));


        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.body().get("status").toString().equals("\"SUCCESS\"")) {
                    Toast.makeText(UserAreaEdit.this, "I dati sono stati aggiornati con successo!", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(v.getContext(), UserArea.class);
                    v.getContext().startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(UserAreaEdit.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addListenerToWidgets(){

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
                String nome = editName.getText().toString();
                String cognome = editLastName.getText().toString();
                String email = editEmail.getText().toString();
                String indirizzo = editLocation.getText().toString();

                try {
                    updateInfoAPI(view, nome, cognome, email, LatLonGenerator.getCoordinatesFromAddress(indirizzo, getApplicationContext()).first,
                                    LatLonGenerator.getCoordinatesFromAddress(indirizzo, getApplicationContext()).second);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        inName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inName.setHint("Nome: " + UserArea.currentUser.getNome());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {}
        });


        inLastName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inLastName.setHint("Cognome: " + UserArea.currentUser.getCognome());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {}
        });


        inEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inEmail.setHint("Email: " + UserArea.currentUser.getEmail());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()){
                    inEmail.setError(getString(R.string.input_error));
                    inEmail.setErrorEnabled(true);
                } else {
                    inEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });


        inLocation.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inLocation.setHint("Indirizzo: " + UserArea.currentUser.getIndirizzo());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }
}