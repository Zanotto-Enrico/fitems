package com.example.fitems.Classes;

import java.util.Objects;

public class User {
    private String username, nome, cognome, password, email, indirizzo;

    public User(String username, String nome, String cognome, String password, String email, String indirizzo) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.email = email;
        this.indirizzo = indirizzo;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  Objects.equals(this.username, user.username) &&
                Objects.equals(this.nome, user.nome) &&
                Objects.equals(this.cognome, user.cognome) &&
                Objects.equals(this.password, user.password) &&
                Objects.equals(this.email, user.email) &&
                Objects.equals(this.indirizzo, user.indirizzo);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='"    + this.username + '\'' +
                ", nome='"      + this.nome + '\'' +
                ", cognome='"   + this.cognome + '\'' +
                ", password='"  + this.password + '\'' +
                ", email='"     + this.email + '\'' +
                ", indirizzo='" + this.indirizzo + '\'' +
                '}';
    }
}
