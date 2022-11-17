package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class RegisterPage extends AppCompatActivity {

    private ImageButton btnIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_register_page);

        // assegnamenti di tutti gli elementi della schermata di login
        this.btnIndex = (ImageButton) findViewById(R.id.btnIndex2);


        // di seguito il codice dei listener di tutti i pulsanti dell'activity

        this.btnIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), FirstPage.class);
                view.getContext().startActivity(i);
            }
        });

    }
}