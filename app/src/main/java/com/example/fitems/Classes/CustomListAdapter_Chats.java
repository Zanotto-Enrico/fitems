package com.example.fitems.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fitems.R;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter_Chats extends BaseAdapter {

    Context c;
    LayoutInflater l;
    List<Chat> chats;

    public CustomListAdapter_Chats(Context c, List<Chat> chats) {
        this.c = c;
        this.chats = chats;
        this.l = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = l.inflate(R.layout.activity_list_view_posts_cells, null);
        TextView lblTitolo = (TextView) view.findViewById(R.id.lblTitoloPost);
        TextView lblDescrizione = (TextView) view.findViewById(R.id.lblDescrizionePost);
        lblTitolo.setText(chats.get(i).getUsername());
        //lblDescrizione.setText(chats.get(i).getMessaggi().get(0).getContenuto());
        lblDescrizione.setText("-- Ultimo messaggio inviato --");
        return view;
    }
}
