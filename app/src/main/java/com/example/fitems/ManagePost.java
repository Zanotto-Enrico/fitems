package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ManagePost extends AppCompatActivity {
    private ImageButton btnBack;
    private TextView txtNomePost;
    private int idPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_manage_post);

        connectWithGraphic();
        addListenerToWidgets();

        this.txtNomePost.setText(getIntent().getStringExtra("nome_oggetto"));
        this.idPost = getIntent().getIntExtra("id_oggetto", -1);
    }

    //Binding degli elementi grafici
    private void connectWithGraphic() {
        this.btnBack = findViewById(R.id.btnBack_ManagePost);
        this.txtNomePost = findViewById(R.id.txtNomePost_ManagePost);
    }

    // Inizializzazione dei listener per i due bottomi presenti nella schermata
    private void addListenerToWidgets() {
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}