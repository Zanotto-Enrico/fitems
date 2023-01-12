package com.example.fitems.Classes;
import java.sql.Time;
import java.util.Objects;

public class Messaggio {
    private int id;
    private String contenuto;
    private String mittente;
    private String time;

    public Messaggio(int id, String contenuto, String mittente, String time) {
        this.contenuto = contenuto;
        this.id = id;
        this.mittente = mittente;
        this.time = time;
    }

    public int getId() {return this.id;}

    public void setId(String id_post) {this.id = id;}

    public String getContenuto() {
        return this.contenuto;
    }
    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public String getMittente() {
        return this.mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public String getTime() {
        return this.time.toString();
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "Messaggio {" +
                "mittente='" + mittente + '\'' +
                ", contenuto='" + contenuto + '\'' +
                ", time=" + time.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Messaggio messaggio = (Messaggio) o;
        return this.id == messaggio.getId();
    }
}

