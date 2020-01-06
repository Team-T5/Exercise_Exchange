package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exerciseexchange.Inserimento_esercizi.CronometroEsercizioActivity;
import com.example.exerciseexchange.Ricerca_esercizi.ricercaEsercizi;
import com.example.exerciseexchange.Statistiche_esercizi.Statistiche_Esercizi;
import com.example.exerciseexchange.Utilità.fileHandler;
import com.squareup.picasso.Picasso;

import io.realm.SyncUser;

import static com.example.exerciseexchange.Utilità.MyApplication.credentialsFile;

public class Homepage extends AppCompatActivity {

    private Button btnRicercaEsercizi, btnInserimentoEsercizi, btnStatisticheEsercizi, btnLogout;
    private TextView txtUsername;
    private ImageView imgLogo;

    private Context context = null;
    private fileHandler fh = null;

    private int atPosition;
    private String username, credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        getSupportActionBar().setTitle(getString(R.string.strHome));

        btnRicercaEsercizi = findViewById(R.id.btnRicercaEsercizi);
        btnInserimentoEsercizi = findViewById(R.id.btnInserimentoEsercizi);
        btnStatisticheEsercizi = findViewById(R.id.btnStatisticheEsercizi);
        btnLogout = findViewById(R.id.btnLogout);
        txtUsername = findViewById(R.id.txtUsername);
        imgLogo = findViewById(R.id.imgLogo);

        context = getApplicationContext();
        fh = new fileHandler(context);

        //Importo il logo utilizzando Picasso
        Picasso.get().load(R.drawable.logo).fit().centerInside().into(imgLogo);

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
        //I write the username in the text field
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
        Intent intent = new Intent(this, ricercaEsercizi.class);
        startActivity(intent);
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
