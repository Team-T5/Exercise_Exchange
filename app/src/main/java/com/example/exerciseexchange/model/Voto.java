package com.example.exerciseexchange.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Voto extends RealmObject {

    @PrimaryKey
    String Unique_String; //Concatenation of Username @ Esercizio

    String Username, Esercizio;
    int Valutazione;

    //Costruttore
    public void Voto(){}

    //Getters
    public String getUnique_String() {
        return Unique_String;
    }

    public String getUsername() {
        return Username;
    }

    public String getEsercizio() {
        return Esercizio;
    }

    public int getValutazione() {
        return Valutazione;
    }

    //Setter della valutazione

    public void setValutazione(int valutazione) {
        Valutazione = valutazione;
    }
}
