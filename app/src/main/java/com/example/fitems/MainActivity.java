package com.example.fitems;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.LatLonGenerator;
import com.example.fitems.Classes.Post;
import com.example.fitems.Classes.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnAccount;

    private List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_main);

        connectWithGraphic();
        addListenerToWidgets();

        setPosts();
    }


    private void connectWithGraphic() {
        this.btnAccount = findViewById(R.id.btnAccount_homepage);
    }


    private void addListenerToWidgets() {
        this.btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UserArea.class);
                view.getContext().startActivity(i);
            }
        });

    }

    private void setPosts() {
        this.posts = new ArrayList<>();
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.post();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                int i = 1;

                while (i <= response.body().size()) {
                    Post p = new Gson().fromJson(response.body().get(Integer.toString(i)).toString(), Post.class);
                    posts.add(p);

                    i++;
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "[Home]" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}