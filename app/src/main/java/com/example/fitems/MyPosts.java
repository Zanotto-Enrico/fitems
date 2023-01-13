package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.CustomListAdapter_Home;
import com.example.fitems.Classes.Post;
import com.example.fitems.Classes.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPosts extends AppCompatActivity {

    private ListView listViewMyPosts;

    private List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_my_posts);

        this.posts = new ArrayList<>();
        connectWithGraphic();
    }

    private void connectWithGraphic() {
        this.listViewMyPosts = findViewById(R.id.listPosts_home);
    }

    private void downloadMyPosts() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.myposts();
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
                    listViewMyPosts.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MyPosts.this, "[MyPosts]" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}