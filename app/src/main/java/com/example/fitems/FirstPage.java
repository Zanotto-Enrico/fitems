package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstPage extends AppCompatActivity {

    private Button btnAccedi;
    private Button btnRegistrati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_first_page);

        // assegnamenti di tutti gli elementi della schermata di login
        this.btnRegistrati = findViewById(R.id.button2);
        this.btnAccedi = findViewById(R.id.button);


        // di seguito il codice dei listener di tutti i pulsanti dell'activity
        btnAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FirstPage.this, LoginPage.class);
                view.getContext().startActivity(i);
            }
        });

        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FirstPage.this, RegisterPage.class);
                view.getContext().startActivity(i);
            }
        });
    }
}