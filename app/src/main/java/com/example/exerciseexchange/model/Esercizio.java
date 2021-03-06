package com.example.exerciseexchange.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Esercizio extends RealmObject {

    @PrimaryKey
    private Timestamp timestamp;

    private String codiceIdentificativo, Capitolo;
    private int numTentativi;
    private Time tempoSvolgimento;
    private RealmList<Voto> Voti;
    private List<String> Fotografie;

    //Costruttore
    public void Esercizio(){}

    //Getters
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getCodiceIdentificativo() {
        return codiceIdentificativo;
    }

    public String getCapitolo() {
        return Capitolo;
    }

    public int getNumTentativi() {
        return numTentativi;
    }

    public Time getTempoSvolgimento() {
        return tempoSvolgimento;
    }

    public RealmList<Voto> getVoti() {
        return Voti;
    }

    public List<String> getFotografie() {
        return Fotografie;
    }

    //Setters
    public void setCodiceIdentificativo(String codiceIdentificativo) {
        this.codiceIdentificativo = codiceIdentificativo;
    }

    public void setCapitolo(String capitolo) {
        Capitolo = capitolo;
    }
}
