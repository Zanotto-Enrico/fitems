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
        this.txtUsername = findViewById(R.id.txtUsername);
        this.txtStato = findViewById(R.id.txtStato);
        this.txtDescrizione = findViewById(R.id.txtSDescrizione);
        this.txtData = findViewById(R.id.txtDate);
        this.btnBack = findViewById(R.id.btnBack_PostView);
    }

    private void setGraphics(){
        Bundle bundle = getIntent().getExtras();

        this.txtUsername.setText(bundle.getString("username"));
        this.txtStato.setText(bundle.getInt("stato"));
        this.txtData.setText(bundle.getString("data"));
        this.txtDescrizione.setText(bundle.getString("descrizione"));
    }

    private void setListeners(){
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostView.this, MainActivity.class);
                finish();
            }
        });
    }
}