package com.example.fitems;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fitems.Classes.ApiInterface;
import com.example.fitems.Classes.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomDialogManagePost extends AppCompatDialogFragment {
    private EditText txtUsername;
    public Context c;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Restituzione oggetto smarrito")
                .setMessage("Inserisci lo username dell'utente che ti ha restituito l'oggetto che avevi smarrito:")
                .setNegativeButton("Esci", null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
                        Call<JsonObject> call = apiInterface.itemFound(ManagePost.idPost, txtUsername.getText().toString());
                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if(response.body().get("status").toString().equals("\"SUCCESS\"")) {
                                    Toast.makeText(c, "POST chiuso con successo!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(c, "Chiusura POST non avvenuta! Riprovare più tardi!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

        txtUsername = view.findViewById(R.id.txtUsername_Dialog);

        return builder.create();
    }
}
