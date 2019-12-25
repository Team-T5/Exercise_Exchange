package com.example.exerciseexchange;

import android.app.Application;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import io.realm.Realm;

public class MyApplication extends Application {

    /*Realm realm;*/
    public static final String LOGO_URL = "";
    private static final String INSTANCE_ADDRESS = "exerciseexchange.us1.cloud.realm.io";
    private static final String SCHEMA_NAME = "Exercise_Exchange_2";
    public static final String AUTH_URL = "https://" + INSTANCE_ADDRESS + "/auth";
    public static final String REALM_URL = "realms://" + INSTANCE_ADDRESS + "/" + SCHEMA_NAME;

    //Files paths
    public static final String credentialsFile = "Credentials.txt";

}
