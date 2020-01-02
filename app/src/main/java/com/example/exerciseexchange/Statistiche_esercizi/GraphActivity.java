package com.example.exerciseexchange.Statistiche_esercizi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.exerciseexchange.R;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.exerciseexchange.Utilità.fileHandler;
import com.example.exerciseexchange.model.Categoria;
import com.example.exerciseexchange.model.Esercizio;
import com.example.exerciseexchange.model.Materia;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.example.exerciseexchange.Utilità.MyApplication.credentialsFile;
import static com.example.exerciseexchange.Utilità.MyApplication.criteriRicercaFile;

public class GraphActivity extends AppCompatActivity {

    private int Npunti = 5;
    private GraphView GraficoTempi, GraficoTentativi;


    private DataPoint[] dpGraficoTempi = new DataPoint[Npunti];
    private DataPoint[] dpGraficoTentativi = new DataPoint[Npunti];

    private Realm realm;

    private fileHandler fh;

    private String username, materia, categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        GraficoTempi = findViewById(R.id.graph);
        GraficoTentativi = findViewById(R.id.graph2);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        fh = new fileHandler(getApplicationContext());

        //Utilizzo il fileHandler per recuperare lo username e i criteri di ricerca
        recuperaUsername();
        recuperaCriteri();

        /*
        Imposto il titolo e il sottotitolo della toolbar
         */
        getSupportActionBar().setTitle(materia + " - " + categoria);
        getSupportActionBar().setSubtitle(getString(R.string.sottotitoloGraphActivity));

        RealmResults<Esercizio> Esercizi = realm.where(Categoria.class).equalTo("Nome", categoria).findFirst().getEsercizi().where().equalTo("caricatoDa", username).sort("dataSvolgimento", Sort.ASCENDING).limit(Npunti).findAll();

        double minutiImpiegati = 0;

        for (Esercizio e : Esercizi) {
                minutiImpiegati = tempoInMinuti(e.getTempoSvolgimento());
            dpGraficoTempi[Esercizi.indexOf(e)] = new DataPoint(Esercizi.indexOf(e) +1, minutiImpiegati);
            dpGraficoTentativi[Esercizi.indexOf(e)] = new DataPoint(Esercizi.indexOf(e) + 1, e.getNumTentativi());
        }
        stampaGrafici();
    }

    private void stampaGrafici(){
        LineGraphSeries<DataPoint> SerieTempi = new LineGraphSeries<>(dpGraficoTempi);
        LineGraphSeries<DataPoint> SerieTentativi = new LineGraphSeries<>(dpGraficoTentativi);

        GraficoTempi.setTitle(getString(R.string.titoloGraficoTempi));
        GraficoTempi.setTitleTextSize(60);
        GraficoTempi.getViewport().setMinY(0);
        GraficoTempi.getViewport().setMaxY(30);
        GraficoTempi.getViewport().setYAxisBoundsManual(true);
        GraficoTempi.addSeries(SerieTempi);

        GraficoTentativi.setTitle(getString(R.string.titoloGraficotentativi));
        GraficoTentativi.setTitleTextSize(60);
        GraficoTentativi.getViewport().setMinY(0);
        GraficoTentativi.getViewport().setMaxY(10);
        GraficoTentativi.getViewport().setYAxisBoundsManual(true);
        GraficoTentativi.addSeries(SerieTentativi);
    }

    private void recuperaUsername(){
        /*
        Dato che il file Credentials.txt può contenere o solo lo username oppure username e password
        devo distinguere i due casi verificando la presenza della @.
         */
        String credentials = fh.read(credentialsFile);
        int atPosition = credentials.indexOf('@');
        if(atPosition != -1){
            //The file has username@password
            username = credentials.substring(0, atPosition);
        } else{
            //The file only has the username
            username = credentials;
        }
    }

    private void recuperaCriteri(){
        String criteri = fh.read(criteriRicercaFile);
        StringTokenizer st = new StringTokenizer(criteri, "@");

        //Il file contiene materia@categoria
        materia = st.nextToken();
        categoria = st.nextToken();
    }

    private double tempoInMinuti(String tempoString){
        /*
        La stringa del tempo di svolgimento è nel formato mm:ss:SS
         */
        double minuti = 0;
        StringTokenizer st = new StringTokenizer(tempoString, ":");

        //Minuti
        minuti = minuti + Integer.parseInt(st.nextToken());

        //Secondi
        minuti = minuti + Double.parseDouble(st.nextToken())/60;

        //Centesimi di secondo
        minuti  = minuti + Double.parseDouble(st.nextToken())/(100*60);

        //Arrotondo alla seconda cifra decimale
        minuti = Math.round(minuti *100)/100;

        return minuti;
    }
}
