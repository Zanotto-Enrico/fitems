package com.example.fitems;

import com.example.fitems.Classes.CheckRecentlyLoggedUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstPage extends AppCompatActivity {

    private Button btnAccedi;
    private Button btnRegistrati;

    private CheckRecentlyLoggedUser checkRecLogUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_first_page);

        this.checkRecLogUsr = new CheckRecentlyLoggedUser(this.getSharedPreferences("fitems", Context.MODE_PRIVATE));

        if (checkRecLogUsr.isLoginValid()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        connectWithGraphic();
        addListenerToWidgets();
    }

    private void connectWithGraphic() {
        this.btnRegistrati = findViewById(R.id.button2);
        this.btnAccedi = findViewById(R.id.button);
    }


    private void addListenerToWidgets() {
        btnAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTo(view, LoginPage.class);
            }
        });

        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTo(view, RegistrationPage.class);
            }
        });
    }

    private void goTo(View view, Class<?> goWhere) {
        Intent i = new Intent(FirstPage.this, goWhere);
        view.getContext().startActivity(i);
    }


    @Override
    public void onBackPressed() { }


}