package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.Chat;
import com.example.fitems.Classes.RetrofitClient;
import com.example.fitems.Classes.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostView extends AppCompatActivity {

    private TextView txtUsername, txtStato, txtData, txtDescrizione, txtTitolo;
    private ImageView postImg;
    private ImageButton btnBack;
    private Button btnContatta;
    private String post_id,proprietario;

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
        this.txtTitolo = findViewById(R.id.txtTitolo_PostView);
        this.postImg = findViewById(R.id.ed_user_image);
        this.btnContatta = findViewById(R.id.button3);
    }

    private void setGraphics(){
        Bundle bundle = getIntent().getExtras();

        this.txtUsername.setText(bundle.getString("username"));
        this.txtStato.setText((new Integer(bundle.getInt("stato"))).toString().equals("1")? "Chiuso" : "Aperto");
        this.txtData.setText(bundle.getString("data"));
        this.txtDescrizione.setText(bundle.getString("descrizione"));
        this.txtTitolo.setText(bundle.getString("titolo"));
        post_id = bundle.getString("id");
        proprietario = bundle.getString("username");
        if(User.loggedUser.getUsername().equals(proprietario))
            btnContatta.setEnabled(false);

        loadPostImage();
    }

    private void setListeners(){
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.btnContatta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostView.this, ChatView.class);
                intent.putExtra("username",proprietario);
                view.getContext().startActivity(intent);
            }
        });
    }

    private void loadPostImage() {

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getImage(post_id);
        call.enqueue(new Callback<okhttp3.ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().contentLength() != 27) {
                        // display the image data in a ImageView or save it
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        postImg.setImageBitmap(bmp);
                    } else {
                        // TODO
                    }
                } else {
                    // TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}