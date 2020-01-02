package com.example.exerciseexchange.Statistiche_esercizi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exerciseexchange.R;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StrutturaEsameActivity extends AppCompatActivity {
//    private Button btnAggiungi;
//    private Button btnAggiungiCategoria;
//    private Button btnInvia;
//    private Spinner drpSezioni;
//    private Spinner drpCategorie;
//    private ArrayList<String> Sezioni;
//    private Integer NumeroSezioni;
//    ArrayList<String> Categorie;
//    public ArrayList<StrutturaEsame> StrutturazioneEsame;
//    public Integer DurataEsame = 0;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_struttura_esame);
//        Sezioni = new ArrayList<String>();
//        Categorie = new ArrayList<String>();
//        Sezioni.add("Seleziona una sezione....");
//        this.NumeroSezioni = 0;
//        StrutturazioneEsame = new ArrayList<StrutturaEsame>();
//
//
//
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                R.layout.drp_layout, Sezioni);
//
//        adapter.setDropDownViewResource(R.layout.drp_layout);
//        drpSezioni = findViewById(R.id.drpSezioneDaAggiungere);
//        drpSezioni.setAdapter(adapter);
//        drpSezioni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i==0) {
//                    RecyclerView recyclerView = findViewById(R.id.lstCategorie);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(StrutturaEsameActivity.this));
//                    Categorie.clear();
//                    CategorieRecyclerViewAdapter adapter = new CategorieRecyclerViewAdapter(StrutturaEsameActivity.this, Categorie);
//                    recyclerView.setAdapter(adapter);
//                    return;
//                }
//                ArrayList<String> CategorieSelezionate = new ArrayList<String>();
//                int indx;
//                RecyclerView recyclerView = findViewById(R.id.lstCategorie);
//                recyclerView.setLayoutManager(new LinearLayoutManager(StrutturaEsameActivity.this));
//                Categorie.clear();
//                for (indx = 0; indx < StrutturazioneEsame.get(i - 1).ElencoCategorie.size(); indx++) {
//                    Categorie.add(StrutturazioneEsame.get(i - 1).ElencoCategorie.get(indx));
//                }
//                CategorieRecyclerViewAdapter adapter = new CategorieRecyclerViewAdapter(StrutturaEsameActivity.this, Categorie);
//                recyclerView.setAdapter(adapter);
//            }
//
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                return;
//            }
//        });
//
//
//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
//                R.array.categorie, R.layout.drp_layout);
//
//        adapter2.setDropDownViewResource(R.layout.drp_layout);
//        drpCategorie = findViewById(R.id.drpCategorieDaAggiungere);
//        drpCategorie.setAdapter(adapter2);
//
//        btnInvia = (Button)findViewById(R.id.btnInvia);
//        btnInvia.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                TextView txtDurataEsame = findViewById(R.id.txtDurataEsame);
//                StrutturaEsameActivity.this.DurataEsame = Integer.parseInt(txtDurataEsame.getText().toString());
//                /*TODO*/
//                /*Al termine tutti i dati (sezioni/categorie) sono contenuti nella proprietà
//                StrutturazioneEsame ( è un ArrayList) e nella proprietà DurataEsame. Qui aggiungere
//                il codice che esegue la query sui dati sulla base dei dati presenti nelle
//                proprietà StrutturazioneEsame e DurataEsame
//                */
//
//            }
//        });
//
//        btnAggiungi = (Button)findViewById(R.id.btnAggiungiSezione);
//        btnAggiungi.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                StrutturaEsameActivity.this.NumeroSezioni = StrutturaEsameActivity.this.NumeroSezioni + 1;
//                StrutturaEsameActivity.this.Sezioni.add("Sezione " + StrutturaEsameActivity.this.NumeroSezioni.toString());
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(StrutturaEsameActivity.this,
//                        R.layout.drp_layout, Sezioni);
//
//                adapter.setDropDownViewResource(R.layout.drp_layout);
//                drpSezioni = findViewById(R.id.drpSezioneDaAggiungere);
//                drpSezioni.setAdapter(adapter);
//                StrutturaEsame se = new StrutturaEsame();
//                se.NomeSezione = "Sezione " + StrutturaEsameActivity.this.NumeroSezioni.toString();
//                se.ElencoCategorie = new ArrayList<String>();
//                StrutturazioneEsame.add(se);
//            }
//        });
//
//        btnAggiungiCategoria = (Button)findViewById(R.id.btnAggiungiCategoria);
//        btnAggiungiCategoria.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                RecyclerView recyclerView = findViewById(R.id.lstCategorie);
//                recyclerView.setLayoutManager(new LinearLayoutManager(StrutturaEsameActivity.this));
//                Spinner drpCategorie = findViewById(R.id.drpCategorieDaAggiungere);
//                Spinner drpSezione = findViewById(R.id.drpSezioneDaAggiungere);
//                if (drpSezione.getSelectedItemPosition() == 0) {
//                    return;
//                }
//                if (drpCategorie.getSelectedItemPosition() == 0) {
//                    return;
//                }
//
//                String CategoriaDaControllare = drpCategorie.getSelectedItem().toString();
//                String EsisteCategoria = "N";
//                for (int i=0;i<StrutturazioneEsame.get(drpSezione.getSelectedItemPosition() - 1).ElencoCategorie.size();i++) {
//                    if (StrutturazioneEsame.get(drpSezione.getSelectedItemPosition() - 1).ElencoCategorie.get(i) == CategoriaDaControllare) {
//                        EsisteCategoria = "S";
//                    }
//                }
//                if (EsisteCategoria=="S") {
//                    return;
//                }
//
//                Categorie.add(drpCategorie.getSelectedItem().toString());
//                CategorieRecyclerViewAdapter adapter = new CategorieRecyclerViewAdapter(StrutturaEsameActivity.this, Categorie);
//
//                recyclerView.setAdapter(adapter);
//
//                StrutturazioneEsame.get(drpSezione.getSelectedItemPosition() - 1).ElencoCategorie.add(drpCategorie.getSelectedItem().toString());
//            }
//
//        });
//    }
}
