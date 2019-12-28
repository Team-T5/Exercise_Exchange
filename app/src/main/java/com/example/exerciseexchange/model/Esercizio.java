package com.example.exerciseexchange.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Esercizio extends RealmObject {

    @PrimaryKey
    private String timestamp;

    private String codiceIdentificativo, Capitolo;
    private int numTentativi;
    private String tempoSvolgimento;
    private RealmList<Voto> Voti;
    private RealmList<String> Fotografie;

    //Campo che contiene lo username dell'utente che ha caricato l'esercizio
    private String caricatoDa;

    //Link alla categoria
    @LinkingObjects("Esercizi")
    private final RealmResults<Categoria> categoriaEsercizio;

    //Costruttore
    public Esercizio() {
        categoriaEsercizio = null;
    }


    //Getters
    public String getTimestamp() {
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

    public String getTempoSvolgimento() {
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
