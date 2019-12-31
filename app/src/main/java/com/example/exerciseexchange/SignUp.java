package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static com.example.exerciseexchange.MyApplication.AUTH_URL;
import static com.example.exerciseexchange.MyApplication.REALM_URL;
import static com.example.exerciseexchange.MyApplication.credentialsFile;

public class SignUp extends AppCompatActivity {

    //Elementi di realm
    private Realm realm;
    private RealmConfiguration config;

    //Elementi dell'interfaccia
    private EditText editUsername, editPassword, editConfermaPassword;
    private Button btnRegistrati;
    private ImageView imgLogo;

    //Variabili
    private String username, password, confirmPassword;

    //File handler
    private Context context = null;
    private fileHandler fh = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        realm.init(this);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editConfermaPassword = findViewById(R.id.editConfermaPassword);
        btnRegistrati = findViewById(R.id.btnRegister);
        imgLogo = findViewById(R.id.imgLogo);

        context = getApplicationContext();
        fh = new fileHandler(context);

        //Importo il logo utilizzando Picasso
        Picasso.get().load(R.drawable.logo).fit().centerInside().into(imgLogo);

        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrati();
            }
        });

    }

    private void Registrati(){

        //Prelevo i dati dall'interfaccia
        username = editUsername.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        confirmPassword = editConfermaPassword.getText().toString().trim();

        //Mi assicuro che tutti i campi siano stati inseriti
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
                    //Tutti i campi sono stati inseriti, procedo con l'algoritmo

                    //I campi "password" e "confirmPassword" devono coincidere
                    if(!password.equals(confirmPassword)){
                        String toastMessage = getString(R.string.differentPasswordInputs);
                        Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
                        toast.show();
                    } else{
                    //Le password coincidono, ora devo cercare la @
                    if(username.indexOf('@') == -1 && password.indexOf('@')==-1){
                    SyncCredentials credentials = SyncCredentials.usernamePassword(username, password, true);
                    SyncUser.logInAsync(credentials, AUTH_URL, new SyncUser.Callback<SyncUser>() {
                        @Override
                        public void onSuccess(SyncUser user) {

                            // Creo la configurazione
                            user = SyncUser.current();
                            config = user.createConfiguration(REALM_URL).fullSynchronization().build();

                            // Ottengo un'istanza
                            realm = Realm.getInstance(config);

                            //Imposto la configurazione di default per prenderla nelle altre classi
                            Realm.setDefaultConfiguration(config);

                            //Istruzione utile per il debug
                            Log.i("Login status: ", "Successful");

                            //Devo memorizzare almeno lo username per mostrarlo nella homepage
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
