package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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
    private ImageButton btnBack;
    private ListView listViewMyPosts;
    private TextView txtNoPosts;

    private ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_my_posts);

        this.posts = new ArrayList<>();
        connectWithGraphic();
        addListenerToWidgets();

        this.txtNoPosts.setVisibility(View.INVISIBLE);

        downloadMyPosts();
    }

    //Binding degli elementi grafici
    private void connectWithGraphic() {
        this.listViewMyPosts = findViewById(R.id.listPosts_MyPosts);
        this.btnBack = findViewById(R.id.btnBack_MyPosts);
        this.txtNoPosts = findViewById(R.id.txtNoPosts_MyPosts);
    }

    // Inizializzazione dei listener per i due bottomi presenti nella schermata
    private void addListenerToWidgets() {
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.listViewMyPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int _i_, long l) {
                Intent i = new Intent(MyPosts.this, ManagePost.class);
                // sto cercando di avviare una activity da un punto esterno a quello del contesto corrente
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                System.out.println(posts.get(_i_).getId_post());
                i.putExtra("nome_oggetto", posts.get(_i_).getTitolo());
                i.putExtra("id_oggetto", posts.get(_i_).getId_post());
                i.putExtra("stato", String.valueOf(posts.get(_i_).getStato()));
                view.getContext().startActivity(i);
            }
        });
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
                    if (posts.size() == 0)
                        txtNoPosts.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MyPosts.this, "[MyPosts]" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}