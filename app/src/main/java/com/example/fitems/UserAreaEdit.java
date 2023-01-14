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
import com.example.fitems.Classes.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAreaEdit extends AppCompatActivity {

    private TextView editUsername;
    private TextView editName, editLastName, editEmail, editIndirizzo;
    private ImageButton btnCheck, btnClose;
    private TextInputLayout inName, inLastName, inEmail, inLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_user_area_edit);
        connectWithGraphic();
        addListenerToWidgets();

        inName.setHint(UserArea.currentUser.getNome());
        inLastName.setHint(UserArea.currentUser.getCognome());
        inEmail.setHint(UserArea.currentUser.getEmail());
        inLocation.setHint(UserArea.currentUser.getIndirizzo());
        editUsername.setText(UserArea.currentUser.getUsername());
    }

    /**
     * Metodo che ha il compito di fare il binding con tutte le View all'interno della
     * nostra activity
     */
    private void connectWithGraphic(){
        editUsername = (TextView) findViewById(R.id.edUsername);

        editName = (TextView) findViewById(R.id.edName);
        editLastName = (TextView) findViewById(R.id.edLastName);
        editEmail = (TextView) findViewById(R.id.edEmail);
        editIndirizzo = (TextView) findViewById(R.id.edIndirizzo) ;

        btnCheck = (ImageButton) findViewById(R.id.btnEdit);
        btnClose = (ImageButton) findViewById(R.id.btnClose);

        inName = (TextInputLayout) findViewById(R.id.inputName);
        inLastName = (TextInputLayout) findViewById(R.id.inputLastName);
        inEmail = (TextInputLayout) findViewById(R.id.inputEmail);
        inLocation = (TextInputLayout) findViewById(R.id.inputIndirizzo);
    }

    /**
     * Metodo che ha il compito di chiamare il metodo updateMyInfo dell'API per aggiornare
     * i dati dell'utente con quelli nuovamente inseriti. Se alcuni argomenti non vengono dati dall'utent, verranno utilizzati
     * i dati inseriti in precedenza
     * @param nome nuovo nome utente da inserire
     * @param cognome
     * @param email
     * @la
     */
    private void updateInfoAPI(String nome, String cognome, String email, Double latitudine, Double longitudine)throws IOException{
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

                    UserArea.currentUser.setNome(nome);
                    UserArea.currentUser.setCognome(cognome);
                    UserArea.currentUser.setEmail(email);
                       try {
                        UserArea.currentUser.setIndirizzo(LatLonGenerator.getAddressFromCoordinates(latitudine, longitudine, getBaseContext()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    finish();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(UserAreaEdit.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Metodo che ha il compito di raggruppare e inizializzare i listeners
     * sugli elementi dell'activity
     */
    private void addListenerToWidgets(){

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(editIndirizzo.getText().toString().equals(""))
                        updateInfoAPI(editName.getText().toString(), editLastName.getText().toString(), editEmail.getText().toString(),
                                        LatLonGenerator.getCoordinatesFromAddress(UserArea.currentUser.getIndirizzo(), getApplicationContext()).first,
                                        LatLonGenerator.getCoordinatesFromAddress(UserArea.currentUser.getIndirizzo(), getApplicationContext()).second);
                    else
                        updateInfoAPI(editName.getText().toString(), editLastName.getText().toString(), editEmail.getText().toString(),
                                LatLonGenerator.getCoordinatesFromAddress(editIndirizzo.getText().toString(), getApplicationContext()).first,
                                LatLonGenerator.getCoordinatesFromAddress(editIndirizzo.getText().toString(), getApplicationContext()).second);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}