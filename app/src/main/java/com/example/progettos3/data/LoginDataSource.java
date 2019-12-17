package com.example.progettos3.data;

import com.example.progettos3.data.model.LoggedInUser;
import com.example.progettos3.data.model.Utente;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            Realm realm = Realm.getDefaultInstance();
            final RealmResults<Utente> utente = realm.where(Utente.class).equalTo("UserName", username).findAll();
            if (utente.size() > 0) {
                if (utente.get(0).getPassword().toString() != password) {
                    LoggedInUser Autenticato =
                            new LoggedInUser(
                                    utente.get(0).getUserName().toString(),
                                    utente.get(0).getFullName().toString());
                    return new Result.Success<>(Autenticato);
                } else {
                    return new Result.Error(new IOException("Errore"));
                }
            } else {
                return new Result.Error(new IOException("Errore"));
            }
            // TODO: handle loggedInUser authentication
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
