package com.example.exerciseexchange;

import java.util.List;

public class esercizio_item {
    private int ID;
    private String imgURL;
    private String caricatoDa;
    private double votoMedio;

    //Costruttore
    public esercizio_item(int ID, String imgURL, String caricatoDa, double votoMedio) {
        this.ID = ID;
        this.imgURL = imgURL;
        this.caricatoDa = caricatoDa;
        this.votoMedio = votoMedio;
    }

    //Getters
    public int getID() {
        return ID;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getCaricatoDa() {
        return caricatoDa;
    }

    public double getVotoMedio() {
        return votoMedio;
    }
}
