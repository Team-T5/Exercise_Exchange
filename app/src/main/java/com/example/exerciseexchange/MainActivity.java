package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static com.example.exerciseexchange.MyApplication.AUTH_URL;
import static com.example.exerciseexchange.MyApplication.REALM_URL;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    RealmConfiguration config;
    EditText editUsername, editPassword;
    CheckBox checkRicordami;
    Button btnAccedi, btnRegistrati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Istanzio i riferimenti agli elementi
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        checkRicordami = findViewById(R.id.checkRicordami);
        btnAccedi = findViewById(R.id.btnAccedi);
        btnRegistrati = findViewById(R.id.btnRegistrati);

        btnAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(editUsername.getText().toString().trim(), editPassword.getText().toString().trim());
            }
        });

        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegistrati();
            }
        });
        
    }


    private void Login(String username, String password) {
        try{
            Boolean Ricordami;

            if(checkRicordami.isChecked()){
                Ricordami = true;
            }
            else{
                Ricordami = false;
            }

            //Mi assicuro che i campi siano inseriti
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
                            //String url = REALM_URL;
                            config = user.createConfiguration(REALM_URL).fullSynchronization().build();

                            // Open the remote Realm
                            realm = Realm.getInstance(config);

                            //I set the default configuration so that i can retrieve it in other classes
                            Realm.setDefaultConfiguration(config);

                            //This log instruction is useful to debug
                            Log.i("Login status: ", "Successful");
                        }

                        @Override
                        public void onError(ObjectServerError error) {
                            Log.e("Login error - ", error.toString());
                        }
                    });


                    /*
                    Se la query ha avuto successo e la checkbox è stata spuntata bisogna inserire i dati in una sessione
                     */

                    /*
                    Passare alla schermata principale dell'app
                     */
                }
            }


        } catch( Error e){
            e.getMessage();
        }
        finally{
            realm.close();
        }
    }

    private void gotoRegistrati(){
        //Funzione per andare alla finestra di registrazione
        Intent intent = new Intent(this, Registrazione.class);
        startActivity(intent);
    }





}
