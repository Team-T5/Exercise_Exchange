package com.example.exerciseexchange.Utilità;

import android.app.Application;


public class MyApplication extends Application {

    private static final String INSTANCE_ADDRESS = "exerciseexchange.us1.cloud.realm.io";
    private static final String SCHEMA_NAME = "Exercise_Exchange_7";
    public static final String AUTH_URL = "https://" + INSTANCE_ADDRESS + "/auth";
    public static final String REALM_URL = "realms://" + INSTANCE_ADDRESS + "/" + SCHEMA_NAME;

    //Percorsi dei file
    public static final String credentialsFile = "Credentials.txt";
    public static final String criteriRicercaFile = "criteriRicerca.txt";
}
