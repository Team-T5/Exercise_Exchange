package com.example.exerciseexchange.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Voto extends RealmObject {

    @PrimaryKey
    private String Timestamp; //Concatenation of Username @ Esercizio

    private String Username;
    private int Valutazione;


    //Costruttore
    public Voto() {
    }

    //Getters
    public String getTimestamp() {
        return Timestamp;
    }

    public String getUsername() {
        return Username;
    }

    public int getValutazione() {
        return Valutazione;
    }

    //Setter della valutazione
    public void setValutazione(int valutazione) {
        Valutazione = valutazione;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
