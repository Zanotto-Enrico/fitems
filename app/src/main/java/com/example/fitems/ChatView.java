package com.example.fitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.Chat;
import com.example.fitems.Classes.CustomListAdapter_Chat;
import com.example.fitems.Classes.CustomListAdapter_Home;
import com.example.fitems.Classes.Messaggio;
import com.example.fitems.Classes.Post;
import com.example.fitems.Classes.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatView extends AppCompatActivity {

    private ImageButton btnAccount;
    private ImageButton refresh;
    private Button send;
    private ListView listViewMessaggi;
    private TextView textTitle;
    private EditText txtMessaggio;
    private Chat chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primaryDark));
        setContentView(R.layout.activity_chat_view);

        Bundle bundle = getIntent().getExtras();

        chat = new Chat(bundle.getString("username"));

        connectWithGraphic();
        addListenerToWidgets();
        setGraphics();

    }

    private void addListenerToWidgets()
    {
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UserArea.class);
                view.getContext().startActivity(i);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateChat();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtMessaggio.getText().toString().equals("")) return;
                ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                Call<JsonObject> call = apiInterface.sendMessage(chat.getUsername(), txtMessaggio.getText().toString());
                txtMessaggio.setText("");
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        updateChat();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(ChatView.this, "[Home]" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    private void connectWithGraphic() {
        this.btnAccount = findViewById(R.id.btnAccount_homepage3);
        this.textTitle = findViewById(R.id.titleTxt);
        this.listViewMessaggi = findViewById(R.id.listViewMessaggi);
        this.btnAccount = findViewById(R.id.btnAccount_homepage3);
        this.send = findViewById(R.id.send);
        this.refresh = findViewById(R.id.btnRefresh);
        this.txtMessaggio = findViewById(R.id.txtMessaggio);
    }

    private void setGraphics(){

        this.textTitle.setText(chat.getUsername());

        updateChat();

    }
    private void updateChat(){
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.chat(chat.getUsername(),20);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                synchronized (chat) {

                    ArrayList<Messaggio> messaggi = new ArrayList<>();
                    JsonArray array = response.body().getAsJsonArray("chat");
                    for (JsonElement var : array)
                    {
                        Gson gson=  new GsonBuilder().setDateFormat("EEE, dd MMM yyyy HH:mm:ss z").create();
                        messaggi.add( gson.fromJson(var, Messaggio.class));
                    }
                    chat.setMessaggi(messaggi);
                    printChatOnTxtView();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ChatView.this, "[Home]" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void printChatOnTxtView()
    {
        Collections.reverse(chat.getMessaggi());
        CustomListAdapter_Chat adapter = new CustomListAdapter_Chat(getApplicationContext(), chat.getMessaggi());
        listViewMessaggi.setAdapter(adapter);
    }

}