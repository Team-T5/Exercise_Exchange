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
import android.widget.ImageView;
import android.widget.Toast;


import com.example.exerciseexchange.Utilità.fileHandler;
import com.squareup.picasso.Picasso;

import java.util.Map;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static com.example.exerciseexchange.Utilità.MyApplication.AUTH_URL;
import static com.example.exerciseexchange.Utilità.MyApplication.REALM_URL;
import static com.example.exerciseexchange.Utilità.MyApplication.credentialsFile;

public class MainActivity extends AppCompatActivity {

    //Elementi di realm
    private Realm realm;
    private RealmConfiguration config;

    //Elementi dell'interfaccia
    private EditText editUsername, editPassword;
    private CheckBox checkRememberMe;
    private Button btnLogin, btnRegister;
    private ImageView imgLogo;

    //Varaibili
    private String username, password, credentials;
    private int atPosition;

    //Contesto
    private Context context = null;

    //File handler
    private fileHandler fh = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(R.string.strLogin);

        context = getApplicationContext();
        fh = new fileHandler(context);

        realm.init(this);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        checkRememberMe = findViewById(R.id.checkRememberMe);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        imgLogo = findViewById(R.id.imgLogo);

        //Importo il logo utilizzando Picasso
        Picasso.get().load(R.drawable.logo).fit().centerInside().into(imgLogo);

        /*
        Se ci sono ancora degli utenti nella sessione realm può dare problemi, quindi mi assicuro
        di eliminare tutti gli utenti nella sessione all'avvio dell'app.
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
                gotoSignUp();
            }
        });

        /*
        Se l'utente ha deciso di memorizzare le credenziali l'app effettua automaticamente il login.
        Se il login automatico fallisce l'utente vede la schermata di login.
         */
        try{
            credentials = fh.read(credentialsFile);
            /*
            Le credenziali sono memorizzate come username@password, quindi devo localizzare la
            @ e dividere la stringa.
             */
            atPosition = credentials.indexOf('@');

            /*
            Se la stringa delle credenziali non contiene la @ allora atPosition = -1 e le
            credenziali non sono valide. Di conseguenza non provo a fare il login.
             */
            if(atPosition != -1){
                username = credentials.substring(0, atPosition);
                password = credentials.substring(atPosition + 1);
                SyncCredentials credentials = SyncCredentials.usernamePassword(username, password, false);
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
                        gotoHomepage();
                    }

                    @Override
                    public void onError(ObjectServerError error) {
                        Log.e("Login error - ", error.toString());
                        //Le credenziali non sono valide
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
            //L'errore può essere causato da credenziali non valide quindi le cancello per sicurezza
            fh.emptyFile(credentialsFile);
        }
    }

    private void Login(){
        username = editUsername.getText().toString().trim();
        password = editPassword.getText().toString().trim();
        try{
            //Mi assicuro ceh tutti i campi non siano vuoti
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

                            // Creo la configurazione
                            user = SyncUser.current();
                            config = user.createConfiguration(REALM_URL).fullSynchronization().build();

                            // Ottengo un'istanza
                            realm = Realm.getInstance(config);

                            //Imposto la configurazione di default per prenderla nelle altre classi
                            Realm.setDefaultConfiguration(config);

                            //Istruzione utile per il debug
                            Log.i("Login status: ", "Successful");

                            //Se la query ha avuto successo e la checkbox è stata spuntata bisogna inserire i dati in un file
                            if (checkRememberMe.isChecked()) {
                                fh.write(credentialsFile, editUsername.getText().toString().trim() + "@" + editPassword.getText().toString().trim());
                            } else{
                                //Devo memorizzare almeno lo username per poterlo mostrare nella homepage
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

    private void gotoSignUp(){
        //Funzione per andare alla finestra di registrazione
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    private void gotoHomepage(){
        //Funzione per andare alla homepage
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }
}