package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;

import com.example.exerciseexchange.model.Categoria;
import com.example.exerciseexchange.model.Esercizio;
import com.example.exerciseexchange.model.Libro;
import com.example.exerciseexchange.model.Voto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import io.realm.Realm;

import static com.example.exerciseexchange.MyApplication.criteriRicercaFile;

public class Visualizzazione extends AppCompatActivity {

    private ArrayList<Esercizio> Esercizi;
    /*
    Dato che da un certo punto in poi i risultati non vengono visualizzati
    tilizzo un array per contenere i primi 20 risultati.
     */
    int max_length;
    private ArrayList<esercizio_item> esercizio_items;

    private RecyclerView rw;
    private listAdapter adapter;
    private RecyclerView.LayoutManager lm;

    private fileHandler fh;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizzazione);

        fh = new fileHandler(getApplicationContext());

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        prelievoEsercizi();
        //Scarto gli esercizi senza foto
        scarto();
        //Prealloco l' ArrayList che conterrà gli elementi della lista
        esercizio_items = new ArrayList<>(Collections.nCopies(max_length, null));

        creazioneElementi();
        //Sovrascrivo la lista di esercizi con la stessa lista ordinata
        esercizio_items = new ArrayList<>(esercizio_items.stream().sorted(Comparator.comparing(esercizio_item::getVotoMedio).reversed()).collect(Collectors.toList()));

        rw = findViewById(R.id.listaEsercizi);
        rw.setHasFixedSize(true);
        lm = new LinearLayoutManager(this);
        adapter = new listAdapter(esercizio_items);

        rw.setLayoutManager(lm);
        rw.setAdapter(adapter);

        adapter.setOnItemClickListener(new listAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position){
                Intent intent = new Intent(getApplicationContext(), Slideshow.class);
                intent.putExtra("ID",Esercizi.get(position).getID());
                startActivity(intent);
                }
        });
    }

    private void prelievoEsercizi(){
        /*
        Leggo il file contenente i criteri di ricerca e distinguo i due casi in base alla presenza
        della @.
        Poi in ciascuno di questi casi utilizzo una query per prelevare gli esercizi.
         */
        String criteri = fh.read(criteriRicercaFile);
        int atPosition = criteri.indexOf('@');
        if(atPosition != -1){
            //Ricerca dettagliata
            StringTokenizer st = new StringTokenizer(criteri, "@");
            //Il file contiene libro@capitolo@numero
            String libro = st.nextToken();
            String capitolo = st.nextToken();
            String numero = st.nextToken();

            //Eseguo la query
            Esercizi = new ArrayList<>(realm.where(Libro.class).equalTo("Nome", libro).findFirst().getEsercizi().where().equalTo("Capitolo", capitolo).equalTo("codiceIdentificativo", numero).findAll());
        } else {
            /*
            Ricerca generica
            In questo caso il file è composto solo dalla categoria, quindi categoria = criteri
             */
            Esercizi = new ArrayList<>(realm.where(Categoria.class).equalTo("Nome", criteri).findFirst().getEsercizi().where().findAll());
        }
    }

    private void creazioneElementi(){
        //Riempio la lista esercizio_items
        for(Esercizio e: Esercizi){
            //Calcolo il voto medio dell'esercizio
            double votoMedio = 0;
            if(!e.getVoti().isEmpty()) {
                for (Voto v : e.getVoti()) {
                    votoMedio = votoMedio + v.getValutazione();
                }
                votoMedio = votoMedio / e.getVoti().size();
            }
            esercizio_items.set(Esercizi.indexOf(e), new esercizio_item(e.getID(), e.getFotografie().get(0), e.getCaricatoDa(), votoMedio));
        }
    }

    private void scarto(){
        /*
        Funzione che prima rimuove gli oggetti senza foto dalla lista
         */
        Iterator<Esercizio> iterator = Esercizi.iterator();
        while (iterator.hasNext()) {
            Esercizio e = iterator.next();
            //Se un esercizio non ha immagini lo scarto
            if(e.getFotografie().isEmpty()){
                iterator.remove();
            }
        }
        //Dopo aver scartato gli oggetti inutili prendo la lunghezza della lista
        max_length = Esercizi.size();
    }
}
