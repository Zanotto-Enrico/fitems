package com.example.fitems.Classes;

import java.util.Objects;

public class Post {
    private String titolo, descrizione;
    private boolean hasRicompensa;

    public Post(String titolo, String descrizione) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.hasRicompensa = false;
    }

    public Post(String titolo, String descrizione, boolean hasRicompensa) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.hasRicompensa = hasRicompensa;
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

    public boolean isHasRicompensa() {
        return this.hasRicompensa;
    }

    public void setHasRicompensa(boolean hasRicompensa) {
        this.hasRicompensa = hasRicompensa;
    }

    @Override
    public String toString() {
        return "Post {" +
                "titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", hasRicompensa=" + hasRicompensa +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return this.hasRicompensa == post.hasRicompensa &&
               this.titolo.equals(post.titolo) &&
               this.descrizione.equals(post.descrizione);
    }
}
