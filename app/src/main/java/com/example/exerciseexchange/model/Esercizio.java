package com.example.exerciseexchange.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Esercizio extends RealmObject implements Serializable {

    @PrimaryKey
    private int ID;

    private String codiceIdentificativo, Capitolo;
    private int numTentativi;
    private String tempoSvolgimento;
    private Date dataSvolgimento;
    private RealmList<Voto> Voti;
    private RealmList<String> Fotografie;

    //Campo che contiene lo username dell'utente che ha caricato l'esercizio
    private String caricatoDa;

    //Costruttore
    public Esercizio() {
    }

    //Getters
    public int getID(){
        return ID;
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

    public Date getDataSvolgimento() {
        return dataSvolgimento;
    }

    //Setters
    public void setID(int ID) {
        this.ID = ID;
    }

    public void setVoti(RealmList<Voto> voti) {
        Voti = voti;
    }

    public void setFotografie(RealmList<String> fotografie) {
        Fotografie = fotografie;
    }

    public void setCodiceIdentificativo(String codiceIdentificativo) {
        this.codiceIdentificativo = codiceIdentificativo;
    }

    public void setCapitolo(String capitolo) {
        Capitolo = capitolo;
    }

    public void setCaricatoDa(String caricatoDa) {
        this.caricatoDa = caricatoDa;
    }

    public void setNumTentativi(int numTentativi) {
        this.numTentativi = numTentativi;
    }

    public void setTempoSvolgimento(String tempoSvolgimento) {
        this.tempoSvolgimento = tempoSvolgimento;
    }

    public void setDataSvolgimento(Date dataSvolgimento) {
        this.dataSvolgimento = dataSvolgimento;
    }

    public void addVoto(Voto voto){
        Voti.add(voto);
    }

    public void addFotografia(String url){
        Fotografie.add(url);
    }
}
