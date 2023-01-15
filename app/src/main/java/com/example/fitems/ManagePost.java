package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.MyDate;
import com.example.fitems.Classes.RetrofitClient;
import com.example.fitems.Classes.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagePost extends AppCompatActivity {
    private ImageButton btnBack;
    private TextView txtNomePost, txtInfo;
    private Button btnTrovatoIo, btnRestituzione;
    protected static int idPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_manage_post);

        connectWithGraphic();
        addListenerToWidgets();

        this.txtNomePost.setText(getIntent().getStringExtra("nome_oggetto"));
        idPost = Integer.parseInt(getIntent().getStringExtra("id_oggetto"));

        if (Integer.parseInt(getIntent().getStringExtra("stato")) == 1) {
            this.btnTrovatoIo.setVisibility(View.INVISIBLE);
            this.btnRestituzione.setVisibility(View.INVISIBLE);
            this.txtInfo.setText("Hai già ritrovato questo oggetto!");
        } else {
            this.txtInfo.setVisibility(View.INVISIBLE);
        }
    }

    //Binding degli elementi grafici
    private void connectWithGraphic() {
        this.btnBack = findViewById(R.id.btnBack_ManagePost);
        this.txtNomePost = findViewById(R.id.txtNomePost_ManagePost);
        this.btnRestituzione = findViewById(R.id.btnConfermaRestituzione_ManagePost);
        this.btnTrovatoIo = findViewById(R.id.btnConfermaRitrovamento_ManagePost);
        this.txtInfo = findViewById(R.id.txtInfo_ManagePost);
    }

    // Inizializzazione dei listener per i due bottomi presenti nella schermata
    private void addListenerToWidgets() {
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.btnRestituzione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogManagePost dialog = new CustomDialogManagePost();
                dialog.c = getApplicationContext();
                dialog.show(getSupportFragmentManager(), "");
            }
        });

        this.btnTrovatoIo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confermaRitrovamento();
            }
        });
    }

    private void confermaRitrovamento() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.itemFound(idPost);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                btnRestituzione.setVisibility(View.INVISIBLE);
                btnTrovatoIo.setVisibility(View.INVISIBLE);
                if(response.body().get("status").toString().equals("\"SUCCESS\"")) {
                    Toast.makeText(getApplicationContext(), "Post chiuso con successo", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    txtInfo.setText("Si è verificato un errore riprova più tardi!");
                    txtInfo.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ManagePost.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}