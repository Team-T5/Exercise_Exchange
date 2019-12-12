package com.example.exerciseexchange;

import android.app.Application;

import io.realm.Realm;

public class MyApplication extends Application {

    /*Realm realm;*/
    public static final String LOGO_URL ="";
    private static final String INSTANCE_ADDRESS = "exercisesdb.us1a.cloud.realm.io";
    private static final String SCHEMA_NAME = "Exercise_Exchange";
    public static final String AUTH_URL = "https://" + INSTANCE_ADDRESS + "/auth";
    public static final String REALM_URL = "realms://" + INSTANCE_ADDRESS + "/" + SCHEMA_NAME;
//    public static final String username = "Gabriele";
//    public static final String password = "password";
//    public static final boolean createUser = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
