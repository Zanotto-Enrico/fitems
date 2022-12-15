package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostView extends AppCompatActivity {

    private TextView txtUsername, txtStato, txtData, txtDescrizione;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));

        connectWithGraphic();
        setGraphics();
        setListeners();
    }

    private void connectWithGraphic(){
        txtUsername = findViewById(R.id.txtUsername);
        txtStato = findViewById(R.id.txtStato);
        txtDescrizione = findViewById(R.id.txtSDescrizione);
        txtData = findViewById(R.id.txtDate);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setGraphics(){
        Bundle bundle = getIntent().getExtras();

        txtUsername.setText(bundle.getString("username"));
        txtStato.setText(bundle.getInt("stato"));
        txtData.setText(bundle.getString("data"));
        txtDescrizione.setText(bundle.getString("descrizione"));
    }

    private void setListeners(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostView.this, MainActivity.class);
                finish();
            }
        });
    }
}