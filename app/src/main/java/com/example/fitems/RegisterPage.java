package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_register_page);
    }
}