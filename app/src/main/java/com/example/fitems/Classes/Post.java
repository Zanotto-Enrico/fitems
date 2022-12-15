package com.example.fitems.Classes;

import java.util.Objects;

public class    Post {
    private String titolo, descrizione, data, username;
    private int stato;

    public Post(String titolo, String descrizione) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.stato = 0;
    }

    public Post(String titolo, String descrizione, int stato, String data, String username ) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.stato = stato;
        this.data = data;
        this.username = username;
    }

    public String getTitolo() {
        return this.titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getStato() {
        return this.stato;
    }

    public void setStato(int hasRicompensa) {
        this.stato = hasRicompensa;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Post {" +
                "titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", hasRicompensa=" + stato +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return this.stato == post.stato &&
               this.titolo.equals(post.titolo) &&
               this.descrizione.equals(post.descrizione);
    }
}
