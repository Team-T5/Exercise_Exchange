package com.example.exerciseexchange.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Voto extends RealmObject {

    @PrimaryKey
    private int ID;

    private String Username;
    private int Valutazione;


    //Costruttore
    public Voto() {
    }

    //Getters
    public int getID() {
        return ID;
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

    public void setUsername(String username) {
        Username = username;
    }
}
