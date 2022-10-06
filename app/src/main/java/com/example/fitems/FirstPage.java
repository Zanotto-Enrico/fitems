package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstPage extends AppCompatActivity {

    Button btnAccedi;
    Button btnRegistrati;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_first_page);

        btnRegistrati = findViewById(R.id.button2);
        btnAccedi = findViewById(R.id.button);

        btnAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent myIntent = new Intent(FirstPage.this, LoginPage.class);
                FirstPage.this.startActivity(myIntent);
            }
        });

        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent myIntent = new Intent(FirstPage.this, RegisterPage.class);
                FirstPage.this.startActivity(myIntent);
            }
        });
    }
}