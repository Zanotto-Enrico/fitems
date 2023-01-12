package com.example.fitems.Classes;
import java.util.List;
import java.util.Objects;

public class Chat {
    private String username;
    private String ultimoMessaggio;
    List<Messaggio> messaggi;

    public Chat(String username) {
        this.username = username;
    }

    public Chat(String username,  List<Messaggio> messaggi, String ultimoMessaggio) {
        this.username = username;
        this.messaggi = messaggi;
        this.ultimoMessaggio = ultimoMessaggio;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Messaggio> getMessaggi() {
        return this.messaggi;
    }

    public void setMessaggi(List<Messaggio> messaggi) {
        this.messaggi = messaggi;
    }

    public String getUltimoMessaggio() {
        return this.ultimoMessaggio;
    }

    public void setUltimoMessaggio(String username) {
        this.ultimoMessaggio = ultimoMessaggio;
    }

    @Override
    public String toString() {
        return "Post {" +
                "username='" + username + '\'' +
                ", num_messaggi='" + messaggi.size() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return this.username == chat.getUsername();
    }
}

