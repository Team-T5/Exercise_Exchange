package com.example.exerciseexchange;

import android.app.Application;

import io.realm.Realm;

public class MyApplication extends Application {

    /*Realm realm;*/

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
