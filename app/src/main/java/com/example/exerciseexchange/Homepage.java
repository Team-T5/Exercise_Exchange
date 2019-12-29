package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.realm.SyncUser;

import static com.example.exerciseexchange.MyApplication.credentialsFile;

public class Homepage extends AppCompatActivity {

    Button btnRicercaEsercizi, btnInserimentoEsercizi, btnStatisticheEsercizi, btnLogout;
    TextView txtUsername;

    Context context = null;
    fileHandler fh = null;

    int atPosition;
    String username, credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        btnRicercaEsercizi = findViewById(R.id.btnRicercaEsercizi);
        btnInserimentoEsercizi = findViewById(R.id.btnInserimentoEsercizi);
        btnStatisticheEsercizi = findViewById(R.id.btnStatisticheEsercizi);
        btnLogout = findViewById(R.id.btnLogout);
        txtUsername = findViewById(R.id.txtUsername);

        context = getApplicationContext();
        fh = new fileHandler(context);

        /*
        Metto lo username nella casella di testo in alto a sinistra.
        Dato che il file Credentials.txt può contenere o solo lo username oppure username e password
        devo distinguere i due casi verificando la presenza della @.
         */
        credentials = fh.read(credentialsFile);
        atPosition = credentials.indexOf('@');
        if(atPosition != -1){
            //The file has username@password
            username = credentials.substring(0, atPosition);
        } else{
            //The file only has the username
            username = credentials;
        }
        //I write the username in teh text field
        txtUsername.setText(username);

        btnRicercaEsercizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRicercaEsercizi();
            }
        });

        btnInserimentoEsercizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoInserimentoEsercizi();
            }
        });

        btnStatisticheEsercizi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoStatisticheEsercizi();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }

    private void gotoRicercaEsercizi(){
        /*
        Inserire qui l'intent
         */
    }

    private void gotoInserimentoEsercizi(){
        Intent intent = new Intent(this, CronometroEsercizioActivity.class);
        startActivity(intent);
    }

    private void gotoStatisticheEsercizi(){
        Intent intent = new Intent(this, Statistiche_Esercizi.class);
        startActivity(intent);
    }

    private void Logout(){
        /*
        Prima effettuo il logout e poi svuoto il file delle credenziali
         */
        SyncUser.current().logOut();
        fh.emptyFile(credentialsFile);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
