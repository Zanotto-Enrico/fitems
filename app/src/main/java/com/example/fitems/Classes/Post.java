package com.example.fitems.Classes;

import java.util.Objects;

/**
 * Classe rappresentante un post del sistema.
 * Essa è dotata di diversi campi interni in grado di identificare tale post e in più una coppia
 * di getter e setter per ciascun campo per potervi accedere sia in lettura che in eventuale scrittura.
 * L'information hiding per questa classe è massimizzato
 */
public class Post {
    private String titolo, descrizione, data, username, id_post;
    private int stato;

    public Post(String titolo, String descrizione) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.stato = 0;
    }

    public Post(String titolo, String descrizione, int stato, String data, String username, String id_post ) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.stato = stato;
        this.data = data;
        this.username = username;
        this.id_post = id_post;
    }

    public String getId_post() {return this.id_post;}

    public void setId_post(String id_post) {this.id_post = id_post;}

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
