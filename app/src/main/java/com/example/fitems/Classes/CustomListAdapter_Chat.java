package com.example.fitems.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fitems.R;

import java.util.List;

public class CustomListAdapter_Chat extends BaseAdapter {

    Context c;
    LayoutInflater l;
    List<Messaggio> chat;

    public CustomListAdapter_Chat(Context c, List<Messaggio> chat) {
        this.c = c;
        this.chat = chat;
        this.l = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return chat.size();
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
        view = l.inflate(R.layout.activity_list_messages, null);
        TextView lblContenuto = (TextView) view.findViewById(R.id.lblContenuto);
        TextView lblTime = (TextView) view.findViewById(R.id.lblTime);
        if(chat.get(i).getMittente().equals(User.loggedUser.getUsername()))
        {
            lblContenuto.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            lblContenuto.setPadding(300,0,0,0);
            lblTime.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            lblTime.setPadding(300,0,0,0);
        }
        else
        {
            lblContenuto.setPadding(0,0,300,0);
            lblTime.setPadding(0,0,300,0);
        }
        lblContenuto.setText(chat.get(i).getContenuto());
        lblTime.setText(chat.get(i).getTime());
        return view;
    }
}
