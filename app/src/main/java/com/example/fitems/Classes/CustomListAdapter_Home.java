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

/**
 * Sottoclasse di <code>BaseAdapter</code> con lo scopo di specializzare le informazioni da visualizzare
 * nella lista di post della schermata home
 */
public class CustomListAdapter_Home extends BaseAdapter {

    Context c;
    LayoutInflater l;
    List<Post> posts;

    public CustomListAdapter_Home(Context c, List<Post> p) {
        this.c = c;
        this.posts = p;
        this.l = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return posts.size();
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
        lblTitolo.setText(posts.get(i).getTitolo());
        lblDescrizione.setText(posts.get(i).getDescrizione());
        return view;
    }
}
