package com.example.fitems;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.CustomListAdapter_Home;
import com.example.fitems.Classes.LatLonGenerator;
import com.example.fitems.Classes.Post;
import com.example.fitems.Classes.RetrofitClient;
import com.example.fitems.Classes.User;
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
    private ImageButton btnNewPost, btnGuide;
    private ListView listViewPost;
    private TextView txtBonusPoints;
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

        setBonusPoints();
    }

    private void setBonusPoints() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(" ");
        ssb.setSpan(new ImageSpan(this, R.drawable.ic_baseline_bonus_points_24), ssb.length() - 1, ssb.length(), 0);
        ssb.append(" ");

        if (User.loggedUser != null && User.loggedUser.getPoints() != -1) {
            ssb.append(String.valueOf(User.loggedUser.getPoints()));
            this.txtBonusPoints.setText(ssb);
        } else
            this.txtBonusPoints.setVisibility(View.INVISIBLE);
    }


    private void connectWithGraphic() {
        this.btnAccount = findViewById(R.id.btnAccount_homepage);
        this.listViewPost = findViewById(R.id.listPosts_home);
        this.btnNewPost = findViewById(R.id.btnAddPost);
        this.txtBonusPoints = findViewById(R.id.txtBonusPoints);
        this.btnGuide = findViewById(R.id.btnGuide);

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

        this.listViewPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PostView.class);

                // sto cercando di avviare una activity da un punto esterno a quello del contesto corrente
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("username", posts.get(i).getUsername());
                intent.putExtra("stato", posts.get(i).getStato());
                intent.putExtra("data", posts.get(i).getData());
                intent.putExtra("descrizione", posts.get(i).getDescrizione());
                intent.putExtra("titolo", posts.get(i).getTitolo());
                intent.putExtra("id", posts.get(i).getId_post());
                view.getContext().startActivity(intent);
            }
        });

        this.btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Guide.class);
                startActivity(i);
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

    @Override
    public void onBackPressed() { }
}