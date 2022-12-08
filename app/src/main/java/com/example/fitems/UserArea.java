package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserArea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
    }

    public static class UserAreaEdit extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_area_edit);
        }
    }
}