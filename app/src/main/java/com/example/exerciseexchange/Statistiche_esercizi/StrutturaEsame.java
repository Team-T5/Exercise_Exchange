package com.example.exerciseexchange.Statistiche_esercizi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exerciseexchange.R;
import com.example.exerciseexchange.Utilità.fileHandler;
import com.example.exerciseexchange.model.Categoria;
import com.example.exerciseexchange.model.Esercizio;
import com.example.exerciseexchange.model.Materia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.realm.Realm;
import io.realm.Sort;

import static com.example.exerciseexchange.Utilità.MyApplication.credentialsFile;
import static com.example.exerciseexchange.Utilità.MyApplication.criteriRicercaFile;

public class StrutturaEsame extends AppCompatActivity {

    //Prealloco la lista disezioni con un oggetto nullo
//    private ArrayList<itemStrutturaEsame> Sezioni = new ArrayList<>(Collections.nCopies(1, null));
    private ArrayList<itemStrutturaEsame> Sezioni = new ArrayList<>();

    private ArrayList<String> nomiCategorie;

    private Realm realm;

    private fileHandler fh;

    private String materia;
    private ArrayList<Categoria> categorie;

    /*
    Utilizzo una mappa per contenere i tempi di caso peggiore per ogni categoria.
    Se non utilizzassi questa mappa dovrei calcolare il tempo di caso peggiore per tutte le
    categorie anche se queste sono state inserite più di una volta.
    La mappa viene istanziata con una categoria non presente all'interno della tabella delle categorie
    per evitare di riferirsi ad un oggetto nullo.
     */
    private Map<String, Integer> Categoria_Tempo = Stream.of(new Object[][]{{"Categoria riempitiva", 1}}).collect(Collectors.toMap(p -> (String)p[0], p -> (Integer)p[1]));

    private EditText editDurataEsame;
    private Button btnAggiungiSezione, btnAggiungiCategoria, btnInviaStruttura;
    private Spinner spinnerCategorie;

    private RecyclerView strutturaEsameRecyclerView;
    private RecyclerView.Adapter adattatoreStrutturaEsame;
    private RecyclerView.LayoutManager layoutManagerStrutturaEsame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_struttura_esame2);

        getSupportActionBar().setTitle(R.string.strutturaEsame);
        getSupportActionBar().setSubtitle(R.string.inserimentoStruttura);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        editDurataEsame = findViewById(R.id.editDurataEsame);
        spinnerCategorie = findViewById(R.id.spinnerCategorie);
        strutturaEsameRecyclerView = findViewById(R.id.strutturaEsameRecyclerView);
        btnAggiungiSezione = findViewById(R.id.btnAggiungiSezione);
        btnAggiungiCategoria = findViewById(R.id.btnAggiungiCategoria);
        btnInviaStruttura = findViewById(R.id.btnInviaStruttura);

        prelievoNomiCategorie();

        //Inserisco i nomi delle categorie nello spinner
        ArrayAdapter<String> adapterCategorie = new ArrayAdapter(this,
                R.layout.drp_layout, nomiCategorie);
        spinnerCategorie.setAdapter(adapterCategorie);

        //Adttatore della struttura d'esame
        strutturaEsameRecyclerView.setHasFixedSize(true);
        layoutManagerStrutturaEsame = new LinearLayoutManager(this);
//        Sezioni.clear();
        adattatoreStrutturaEsame = new AdattatoreStrutturaEsame(Sezioni);

        strutturaEsameRecyclerView.setLayoutManager(layoutManagerStrutturaEsame);
        strutturaEsameRecyclerView.setAdapter(adattatoreStrutturaEsame);

        btnAggiungiSezione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Sezioni.isEmpty() || Sezioni.size() > 0 && !Sezioni.get(Sezioni.size()-1).getCategorie().isEmpty()){
                    //Questo metodo genera una carta
                    Sezioni.add(new itemStrutturaEsame());
                    //Notifico all'adattatore il cambiamento avvenuto
                    adattatoreStrutturaEsame.notifyItemInserted(Sezioni.size());
                } else {
                    //Notifico all'utente di inserire almeno una categoria nell'ultima sezione
                    Toast.makeText(getApplicationContext(), getString(R.string.erroreSezioneVuota), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAggiungiCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Questo metodo aggiunge una riga alla tabella delle categorie dell'ultima sezione.
                Devo assicurarmi che sia presente almeno una sezione.
                 */
                if(!Sezioni.isEmpty()){
                    /*
                    Aggiungo il valore selezionato nello spinner delle categorie in una nuova riga
                    della tabella della sezione corrente
                     */
                    int indiceSezioneCorrente = Sezioni.size()-1;
                    String categoriaSelezioanta = spinnerCategorie.getSelectedItem().toString();
                    TableLayout tabellaCategorie;

                    //Inserisco la categoria all'interno della sezione corrente (Lista in java)
                    Sezioni.get(indiceSezioneCorrente).addCategoria(categoriaSelezioanta);

                    //Prelevo la tabella dall'elemento della RecyclerView
                    tabellaCategorie = strutturaEsameRecyclerView.findViewHolderForAdapterPosition(indiceSezioneCorrente).itemView.findViewById(R.id.tblSezione);

                    //Creo la riga
                    TableRow nuovaRiga = new TableRow(getApplicationContext());

                    //Creo la TextView
                    TextView nuovaCategoria = new TextView(getApplicationContext());
                    nuovaCategoria.setText(categoriaSelezioanta);
                    nuovaCategoria.setTextColor(getColor(R.color.nero));
                    nuovaCategoria.setTextSize(20);

                    //Aggiungo la TextView alla riga
                    nuovaRiga.addView(nuovaCategoria);

                    //Aggiungo la riga alla tabella
                    tabellaCategorie.addView(nuovaRiga);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.erroreListaSezioniVuota), Toast.LENGTH_LONG).show();
                }

            }
        });

        btnInviaStruttura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcoloTempiCategorie();
                /*
                Questo metodo analizza la lista Sezioni e ricava il tempo di caso peggiore per ogni
                sezione.
                 */
                if(controlli()){
                    int secondiTempoStimato = 0;
                    for(itemStrutturaEsame i: Sezioni){
                        int secondiSezione;
                        /*
                        Il tempo massimo della sezione viene inizializzato con quello della
                        sua prima categoria.
                         */
                        secondiSezione = Categoria_Tempo.get(i.getCategorie().get(0));
                        for (String c: i.getCategorie()){
                            if(Categoria_Tempo.get(c) > secondiSezione){
                                secondiSezione = Categoria_Tempo.get(c);
                            }
                        }
                        //Alla fine del ciclo ho trovato il tempo massimo della sezione
                        secondiTempoStimato  = secondiTempoStimato + secondiSezione;
                    }
                    /*
                    Alla fine del ciclo ho trovato la durata in secondi dell'esame e posso passarla
                    all'activity di valutazione tramite l'intent.
                     */
                    Intent intent = new Intent(StrutturaEsame.this, Valutazione.class);
                    Bundle b = new Bundle();
                    b.putString("stringaDurataEsame", editDurataEsame.getText().toString().trim());
                    b.putInt("secondiDurataEsame", secondiEsame(editDurataEsame.getText().toString().trim()));
                    b.putInt("secondiTempoStimato", secondiTempoStimato);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });

    }

    private void prelievoNomiCategorie(){
        fh = new fileHandler(getApplicationContext());

        //Prelevo i nomi delle categorie da inserire nello spinner
        materia = fh.read(criteriRicercaFile);
        categorie = new ArrayList<>(realm.where(Materia.class).equalTo("Nome", materia).findFirst().getCategorie());

        //Prealloco la lista dei nomi delle categorie
        nomiCategorie = new ArrayList<>(Collections.nCopies(categorie.size(), null));

        for (Categoria c: categorie){
            nomiCategorie.set(categorie.indexOf(c), c.getNome());
        }
    }

    private int secondiEsercizio(String strTempoEsercizio){
            int secondiTempoEsercizio = 0;
            /*
            I tempi stimati sono nella forma mm:ss:cc quindi devo prma scomporre la stringa in
            minuti, secondi e centesimi di secondo, moltiplicarli per appositi coefficienti e sommarli.

            I centesimi di secondo vengono arrotondati, quindi diventano o 1 secondo o 0 secondi.
             */
            StringTokenizer st = new StringTokenizer(strTempoEsercizio, ":");

            //Minuti
            secondiTempoEsercizio = secondiTempoEsercizio + Integer.parseInt(st.nextToken())*60;

            //Secondi
            secondiTempoEsercizio = secondiTempoEsercizio + Integer.parseInt(st.nextToken());

            //Centesimi di secondo
            secondiTempoEsercizio = secondiTempoEsercizio + Math.round(Integer.parseInt(st.nextToken())/100);

            return secondiTempoEsercizio;
    }

    private int secondiEsame(String strTempoEsame){
        int secondiDurataEsame = 0;
        /*
            La durata d'esame è nella forma hh:mm
        */
        StringTokenizer st = new StringTokenizer(strTempoEsame, ":");

        //Ore
        secondiDurataEsame = secondiDurataEsame + Integer.parseInt(st.nextToken())*3600;

        //Minuti
        secondiDurataEsame = secondiDurataEsame + Integer.parseInt(st.nextToken())*60;

        return secondiDurataEsame;
    }

    private void calcoloTempiCategorie(){
        for (itemStrutturaEsame i: Sezioni){
            for( String c: i.getCategorie()){
                if(!Categoria_Tempo.containsKey(c)){
                    /*
                    Se il nome della categoria non è contenuto devo calcolarne il tempo
                    di caso peggiore ed inserirlo nella mappa.
                    */
                    Categoria objCategoria = realm.where(Categoria.class).equalTo("Nome", c).findFirst();
                    List<Esercizio> objEsercizi = objCategoria.getEsercizi().where().sort("dataSvolgimento", Sort.DESCENDING).limit(5).findAll();
                    //Il valore iniziale del tempo di caso peggiore della categoria è quello del primo esercizio
                    int secondiCategoria = secondiEsercizio(objEsercizi.get(0).getTempoSvolgimento());
                    for(Esercizio e: objEsercizi){
                        int tempoEsercizio = secondiEsercizio(e.getTempoSvolgimento());
                        if(tempoEsercizio > secondiCategoria){
                            secondiCategoria = tempoEsercizio;
                        }
                    }
                    /*
                    Alla fine del ciclo ho trovato l'esercizio con il tempo massimo e posso inserlo
                    nella mappa.
                     */
                    Categoria_Tempo.put(c, secondiCategoria);
                }
            }
        }
    }

    private boolean controlli(){
        /*
        Questo metodo verifica che sia stata inserita almeno una sezione e che nell'ultima sezione
        sia presente almeno una categoria.
        E' sufficiente verificare solo l'ultima sezione perchè non si possono aggiungere sezioni
        oltre la prima se non si inseriscono categorie.
         */
        if(editDurataEsame.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), getString(R.string.inserireDurataEsame), Toast.LENGTH_LONG).show();
            return false;
        }else{
            if(Sezioni.isEmpty()){
                Toast.makeText(getApplicationContext(), getString(R.string.erroreListaSezioniVuota), Toast.LENGTH_LONG).show();
                return false;
            } else {
                if(Sezioni.get(Sezioni.size()-1).getCategorie().isEmpty()){
                    Toast.makeText(getApplicationContext(), getString(R.string.erroreSezioneVuota), Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    return true;
                }
            }

        }
    }


}
