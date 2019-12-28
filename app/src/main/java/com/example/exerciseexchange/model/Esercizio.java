package com.example.exerciseexchange.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
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



    //Costruttore
    public Esercizio() {
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

    public String getCaricatoDa() {
        return caricatoDa;
    }


    //Setters
    public void setCodiceIdentificativo(String codiceIdentificativo) {
        this.codiceIdentificativo = codiceIdentificativo;
    }

    public void setCapitolo(String capitolo) {
        Capitolo = capitolo;
    }

    public void setCaricatoDa(String caricatoDa) {
        this.caricatoDa = caricatoDa;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setNumTentativi(int numTentativi) {
        this.numTentativi = numTentativi;
    }

    public void setTempoSvolgimento(String tempoSvolgimento) {
        this.tempoSvolgimento = tempoSvolgimento;
    }

    public void addVoto(Voto voto){
        Voti.add(voto);
    }

    public void addFotografia(String url){
        Fotografie.add(url);
    }
}
