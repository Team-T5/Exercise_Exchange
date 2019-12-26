package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static com.example.exerciseexchange.MyApplication.AUTH_URL;
import static com.example.exerciseexchange.MyApplication.REALM_URL;
import static com.example.exerciseexchange.MyApplication.credentialsFile;

public class SignUp extends AppCompatActivity {

    //Realm elements
    Realm realm;
    RealmConfiguration config;

    //Interface elements
    EditText editUsername, editPassword, editConfermaPassword;
    Button btnRegistrati;

    //Variables
    String username, password, confirmPassword;
    int atPosition;

    //File handler
    Context context = null;
    fileHandler fh = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        realm.init(this);

        //I instantiate the elements references
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editConfermaPassword = findViewById(R.id.editConfermaPassword);
        btnRegistrati = findViewById(R.id.btnRegister);

        //I instantiate the file handler
        context = getApplicationContext();
        fh = new fileHandler(context);

        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrati();
            }
        });

    }

    private void Registrati(){

        //I retrieve the interface data
        username = editUsername.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        confirmPassword = editConfermaPassword.getText().toString().trim();

        //I make sure that all the fields have been filled
        if(username.isEmpty()){
            String toastMessage = getString(R.string.insertUsername);
            Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
            toast.show();
        } else{
            if(password.isEmpty()){
                String toastMessage = getString(R.string.insertPassword);
                Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                if(confirmPassword.isEmpty()){
                    String toastMessage = getString(R.string.insertConfirmPassword);
                    Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    //All the fields hae been filled, i go on with the algorithm

                    //the "password" must be equal to the "confirmPassword"
                    if(!password.equals(confirmPassword)){
                        String toastMessage = getString(R.string.differentPasswordInputs);
                        Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
                        toast.show();
                    } else{
                    //The passwords match, now i need to look for the @
                    if(username.indexOf('@') == -1 && password.indexOf('@')==-1){
                    SyncCredentials credentials = SyncCredentials.usernamePassword(username, password, true);
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

                            //I need to store at least the username to show it in the homepage
                            fh.write(credentialsFile, username);
                            gotoHomepage();
                        }

                        @Override
                        public void onError(ObjectServerError error) {
                            Log.e("Login error - ", error.toString());
                            Toast toast = Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                    } else{
                        String toastMessage = getString(R.string.atFound);
                        Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                }
            }
        }

    }

    private void gotoHomepage(){
        //Funzione per andare alla homepage
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }
}
