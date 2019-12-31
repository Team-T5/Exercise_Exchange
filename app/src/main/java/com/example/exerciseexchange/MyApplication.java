package com.example.exerciseexchange;

import android.app.Application;


public class MyApplication extends Application {

    public static final String LOGO_URL = "";
    private static final String INSTANCE_ADDRESS = "exerciseexchange.us1.cloud.realm.io";
    private static final String SCHEMA_NAME = "Exercise_Exchange_7";
    public static final String AUTH_URL = "https://" + INSTANCE_ADDRESS + "/auth";
    public static final String REALM_URL = "realms://" + INSTANCE_ADDRESS + "/" + SCHEMA_NAME;

    //Percorsi dei file
    public static final String credentialsFile = "Credentials.txt";
    public static final String criteriRicercaFile = "criteriRicerca.txt";
    public static final String noImageURL = "http://teamt5.altervista.org/Exercise_Exchange/no-image-icon.png";

}
