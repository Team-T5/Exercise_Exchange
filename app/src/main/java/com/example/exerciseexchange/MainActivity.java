package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static com.example.exerciseexchange.MyApplication.AUTH_URL;
import static com.example.exerciseexchange.MyApplication.REALM_URL;
import static com.example.exerciseexchange.MyApplication.credentialsFile;

public class MainActivity extends AppCompatActivity {

    //Realm elements
    Realm realm;
    RealmConfiguration config;

    //Layout elements
    EditText editUsername, editPassword;
    CheckBox checkRememberMe;
    Button btnLogin, btnRegister;

    //Variables
    String username, password, credentials;
    int atPosition;

    //Context
    Context context = null;

    //File handler
    fileHandler fh = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        fh = new fileHandler(context);

        realm.init(this);

        //I instantiate the elements references
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        checkRememberMe = findViewById(R.id.checkRememberMe);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);


        /*
        If there are still users in the session realm may throw exceptions so I make sure I delete
        the users in the session when I start the app
         */
        try {
            for (Map.Entry<String, SyncUser> user : SyncUser.all().entrySet()) {
                user.getValue().logOut();
            }
        } catch(Exception e){
            Log.e("Users map error: ",e.getMessage());
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegistrati();
            }
        });

        /*
        If the user has decided to store the credentials the app should automatically log in.
        If the login in the onCreate method fails the user will see the login screen.
         */
        //FileInputStream fis = null;
        try{
            credentials = fh.read(credentialsFile);

            //The line is made of username@password then i need to locate the @ and split the string
            atPosition = credentials.indexOf('@');

            /*
            If @ is not in the credentials string then atPosition = -1 and the credentials are
            not valid and therefore I don't try to login
             */
            if(atPosition != -1){
                username = credentials.substring(0, atPosition);
                password = credentials.substring(atPosition + 1);
                SyncCredentials credentials = SyncCredentials.usernamePassword(username, password, false);
                SyncUser.logInAsync(credentials, AUTH_URL, new SyncUser.Callback<SyncUser>() {
                    @Override
                    public void onSuccess(SyncUser user) {

                        // Create the configuration
                        user = SyncUser.current();
                        config = user.createConfiguration(REALM_URL).fullSynchronization().build();

                        // Open the remote Realm
                        realm = Realm.getInstance(config);

                        //I set the default configuration so that i can retrieve it in other classes
                        Realm.setDefaultConfiguration(config);

                        //This log instruction is useful to debug
                        Log.i("Login status: ", "Successful");
                        gotoHomepage();
                    }

                    @Override
                    public void onError(ObjectServerError error) {
                        Log.e("Login error - ", error.toString());
                        //The credentials are not valid
                        fh.emptyFile(credentialsFile);
                    }
                });
            }
            else{
                Log.e("File error: ", "Invalid credentials");
                fh.emptyFile(credentialsFile);
            }
        }catch(Exception e){
            Log.e("File error: ", e.getMessage());
            //The error might be caused by invalid credentials so I delete them for safety
            fh.emptyFile(credentialsFile);
        }
    }

    private void Login(){
        username = editUsername.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        try{
            //I make sure that all the fields aren't empty
            if(username.isEmpty()){
                String toastMessage = getString(R.string.insertUsername);
                Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                if(password.isEmpty()){
                    String toastMessage = getString(R.string.insertPassword);
                    Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    SyncCredentials credentials = SyncCredentials.usernamePassword(username, password, false);
                    SyncUser.logInAsync(credentials, AUTH_URL, new SyncUser.Callback<SyncUser>() {
                        @Override
                        public void onSuccess(SyncUser user) {

                            // Create the configuration
                            user = SyncUser.current();
                            config = user.createConfiguration(REALM_URL).fullSynchronization().build();

                            // Open the remote Realm
                            realm = Realm.getInstance(config);

                            //I set the default configuration so that i can retrieve it in other classes
                            Realm.setDefaultConfiguration(config);

                            //This log instruction is useful to debug
                            Log.i("Login status: ", "Successful");

                            //Se la query ha avuto successo e la checkbox è stata spuntata bisogna inserire i dati in un file
                            if (checkRememberMe.isChecked()) {
                                fh.write(credentialsFile, editUsername.getText().toString().trim() + "@" + editPassword.getText().toString().trim());
                            } else{
                                //I need to store at least the username to show it in the homepage
                                fh.write(credentialsFile, editUsername.getText().toString().trim());
                            }
                            gotoHomepage();
                        }

                        @Override
                        public void onError(ObjectServerError error) {
                            Log.e("Login error - ", error.toString());
                            Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }
            }
        } catch( Error e){
            e.getMessage();
        }
    }

    private void gotoRegistrati(){
        //Funzione per andare alla finestra di registrazione
        Intent intent = new Intent(this, Registrazione.class);
        startActivity(intent);
    }

    private void gotoHomepage(){
        //Funzione per andare alla homepage
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }


}