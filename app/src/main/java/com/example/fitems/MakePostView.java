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
import com.example.fitems.Classes.LatLonGenerator;
import com.example.fitems.Classes.RetrofitClient;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakePostView extends AppCompatActivity {

    private Button btnPubblica;
    private TextView txtTitolo;
    private TextView txtDescrizione;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_make_post_view);

        connectWithGraphic();
        addListenerToWidgets();
    }

    private void connectWithGraphic() {
        this.btnPubblica = findViewById(R.id.btnPubblica);
        this.txtDescrizione = findViewById(R.id.txtDescrizione);
        this.txtTitolo = findViewById(R.id.txtTitolo);
        this.btnBack = findViewById(R.id.btnBack_MakePostView);
    }

    private void addListenerToWidgets() {
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MakePostView.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        this.btnPubblica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createNewPost(txtTitolo.getText().toString(),txtDescrizione.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createNewPost( String titolo, String descrizione )throws IOException {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<JsonObject> call;

        call = apiInterface.makePost(titolo,descrizione);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.body().get("status").toString().equals("\"SUCCESS\"")) {
                    Toast.makeText(MakePostView.this, "Il post è stato pubblicato!", Toast.LENGTH_LONG).show();

                    finish();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MakePostView.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}