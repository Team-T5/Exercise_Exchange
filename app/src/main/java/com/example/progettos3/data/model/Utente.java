package com.example.progettos3.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Utente extends RealmObject {
    @PrimaryKey
    private long id;

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    private String UserName;
    private String Password;
    private String FullName;

    public long getId() {
        return id;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }

    public String getFullName() {
        return FullName;
    }
}
