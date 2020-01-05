package com.example.exerciseexchange.Statistiche_esercizi;

import java.util.ArrayList;
import java.util.List;

public class itemStrutturaEsame {
    private ArrayList<String> Categorie = new ArrayList<>();

    //La lista all'inizio è vuota, quindi il costruttore non ha parametri

    public itemStrutturaEsame() {
    }

    //Metodo per aggiungere le categorie
    public void addCategoria(String c){
        Categorie.add(c);
    }

    //Metodo per prelevare le categorie
    public ArrayList<String> getCategorie(){
        return Categorie;
    }
}
