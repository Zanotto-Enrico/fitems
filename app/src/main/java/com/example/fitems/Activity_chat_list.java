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
import com.example.fitems.Classes.Chat;
import com.example.fitems.Classes.CustomListAdapter_Chats;
import com.example.fitems.Classes.CustomListAdapter_Home;
import com.example.fitems.Classes.Messaggio;
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

public class Activity_chat_list extends AppCompatActivity {

    private ImageButton btnAccount;
    private ListView listViewChats;
    private TextView txtNoChats;
    private List<Chat> chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        chats = new ArrayList<>();

        connectWithGraphic();
        addListenerToWidgets();
        setChats();
    }

    private void connectWithGraphic() {
        this.btnAccount = findViewById(R.id.btnAccount_homepage2);
        this.listViewChats = findViewById(R.id.listChats);
        this.txtNoChats = findViewById(R.id.txtNoChat);
    }

    private void addListenerToWidgets() {
        this.btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UserArea.class);
                view.getContext().startActivity(i);
            }
        });


        this.listViewChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Activity_chat_list.this, ChatView.class);

                // sto cercando di avviare una activity da un punto esterno a quello del contesto corrente
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("username", chats.get(i).getUsername());
                view.getContext().startActivity(intent);
            }
        });
    }

    private void setChats() {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.myChats();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                synchronized (chats) {

                    JsonArray array = response.body().getAsJsonArray("chats");
                    if (array.size() != 0)
                        txtNoChats.setVisibility(View.INVISIBLE);
                    for (JsonElement var : array)
                    {
                        chats.add( new Gson().fromJson(var, Chat.class));
                    }
                    CustomListAdapter_Chats adapter = new CustomListAdapter_Chats(getApplicationContext(), chats);
                    listViewChats.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Activity_chat_list.this, "[Chats]" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}