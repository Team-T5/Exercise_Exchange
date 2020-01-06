package com.example.exerciseexchange.Ricerca_esercizi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.exerciseexchange.R;
import com.example.exerciseexchange.Utilità.fileHandler;
import com.example.exerciseexchange.model.Esercizio;
import com.example.exerciseexchange.model.Voto;
import static com.example.exerciseexchange.Ricerca_esercizi.Visualizzazione.adapterListaEsercizi;

import io.realm.Realm;

import static com.example.exerciseexchange.Utilità.MyApplication.credentialsFile;

public class votaEsercizio extends AppCompatActivity {

    Realm realm;

    fileHandler fh;

    Voto objVoto;
    Esercizio objEsercizio;
    RatingBar barraValutazione;

    long ID;
    String username;
    boolean esistente = false; //Flag che mi dice se il voto è stato trovato nel database
    int valutazioneCorrente = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vota_esercizio);

        getSupportActionBar().setTitle(R.string.strExerciseResearch);
        getSupportActionBar().setSubtitle(R.string.votaEsercizio);

        barraValutazione = findViewById(R.id.barraValutazione);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        fh = new fileHandler(getApplicationContext());

        //Prelevo i dati per la query
        ID = getIntent().getLongExtra("ID", 0);
        prelievoUsername();

        /*
        Eseguo una query per verificare se il voto è presente o no all'interno del database e in caso
        affermativo imposto la valutazione iniziale della barra al voto inserito dall'utente così che
        possa aggiornarlo.
        Se l'utente non ha ancora valutato l'esercizio la valutazione iniziale è 0.
         */
        objEsercizio = realm.where(Esercizio.class).equalTo("ID", ID).findFirst();
        objVoto = objEsercizio.getVoti().where().equalTo("Username", username).findFirst();

        if(objVoto != null){
            //Imposto la valutazione iniziale
            valutazioneCorrente = objVoto.getValutazione();
            barraValutazione.setRating(valutazioneCorrente);
            esistente = true;
        }

        /*
        Imposto il listener che si attiva quando si cambia la valutazione
         */
        barraValutazione.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (esistente && rating != valutazioneCorrente) {
                    updateVoto((int) rating);
                } else {
                    insertVoto((int) rating);
                }
            }
        });

    }

    private void prelievoUsername(){
        /*
        Dato che il file Credentials.txt può contenere o solo lo username oppure username e password
        devo distinguere i due casi verificando la presenza della @.
         */
        String credentials = fh.read(credentialsFile);
        int atPosition = credentials.indexOf('@');
        if(atPosition != -1){
            //Il file è composto da username@password
            username = credentials.substring(0, atPosition);
        } else{
            //Il file contiene solo lo username
            username = credentials;
        }
    }

    private void updateVoto(int rating){
        /*
        Se il voto esiste già devo solo aggiornare la valutazione esistente.
        Dato che gli oggetti di realm possono essere modificati solo nel thread in cui
        sono stati creati devo ottenere nuovamente objVoto.
         */
        objEsercizio = realm.where(Esercizio.class).equalTo("ID", ID).findFirst();
        objVoto = objEsercizio.getVoti().where().equalTo("Username", username).findFirst();

        Voto nuovoVoto = new Voto();

        nuovoVoto.setID(objVoto.getID());
        nuovoVoto.setUsername(objVoto.getUsername());
        nuovoVoto.setValutazione(rating);

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(nuovoVoto);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // La transazione ha avuto successo
                valutazioneCorrente = rating;
                adapterListaEsercizi.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), getString(R.string.inserimentoEffettuato), Toast.LENGTH_LONG).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // La transazione non ha avuto successo
                Toast.makeText(getApplicationContext(), getString(R.string.inserimentoFallito), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void insertVoto(int rating){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //Creo il nuovo voto
                Voto nuovoVoto = new Voto();
                nuovoVoto.setID((long) realm.where(Voto.class).max("ID") + 1);
                nuovoVoto.setUsername(username);
                nuovoVoto.setValutazione(rating);
                realm.copyToRealm(nuovoVoto);

                /*
                Inserisco il voto appena creato nella lista di voti correlati all'esercizio.
                Dato che gli oggetti di realm possono essere modificati solo nel thread in cui
                sono stati creati devo ottenere nuovamente objEsercizio
                 */
                objEsercizio = realm.where(Esercizio.class).equalTo("ID", ID).findFirst();
                objEsercizio.addVoto(nuovoVoto);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // La transazione ha avuto successo
                valutazioneCorrente = rating;
                //A seguito dell'inserimento il voto esiste all'interno del database
                esistente = true;
                adapterListaEsercizi.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), getString(R.string.inserimentoEffettuato), Toast.LENGTH_LONG).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // La transazione non ha avuto successo
                Toast.makeText(getApplicationContext(), getString(R.string.inserimentoFallito), Toast.LENGTH_LONG).show();
                Log.e("Errore di inserimento", error.getMessage());
            }
        });
    }
}