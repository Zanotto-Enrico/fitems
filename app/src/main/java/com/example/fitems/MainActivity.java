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
import android.widget.ListView;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.CustomListAdapter_Home;
import com.example.fitems.Classes.LatLonGenerator;
import com.example.fitems.Classes.Post;
import com.example.fitems.Classes.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnAccount;
    private ImageButton btnNewPost;
    private ListView listViewPost;

    private List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_main);
        this.posts = new ArrayList<>();

        setPosts();

        connectWithGraphic();
        addListenerToWidgets();
    }


    private void connectWithGraphic() {
        this.btnAccount = findViewById(R.id.btnAccount_homepage);
        this.listViewPost = findViewById(R.id.listPosts_home);
        this.btnNewPost = findViewById(R.id.btnAddPost);
    }


    private void addListenerToWidgets() {
        this.btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UserArea.class);
                view.getContext().startActivity(i);
            }
        });

        this.btnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MakePostView.class);
                view.getContext().startActivity(i);
            }
        });
    }

    private void setPosts() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.post();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                synchronized (posts) {

                    Type listType = new TypeToken<ArrayList<Post>>(){}.getType();
                    ArrayList<Post> post = new Gson().fromJson(response.body().getAsJsonArray("post"), listType);
                    JsonArray array = response.body().getAsJsonArray("post");
                    for (JsonElement var : array)
                    {
                        posts.add( new Gson().fromJson(var, Post.class));
                    }
                    CustomListAdapter_Home adapter = new CustomListAdapter_Home(getApplicationContext(), post);
                    listViewPost.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "[Home]" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}