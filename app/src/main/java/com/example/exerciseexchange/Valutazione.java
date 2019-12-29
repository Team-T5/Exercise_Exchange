package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.StringTokenizer;

public class Valutazione extends AppCompatActivity {

    private TextView editableDurataEsame, editableTempoStimato, editableVerdetto, editableDidascalia;
    private double rapportoTempi;
    private int secondiDurataEsame = 0, secondiTempoStimato = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valutazione);

        editableDurataEsame = findViewById(R.id.editableDurataEsame);
        editableTempoStimato = findViewById(R.id.editableTempoStimato);
        editableVerdetto = findViewById(R.id.editableVerdetto);
        editableDidascalia = findViewById(R.id.editableDidascalia);

        /*
        Prendo la durata dell'esame e il tempo di svolgimento stimato in secondi dall'intent poi ne
        calcolo il rapporto
         */

        Bundle b = getIntent().getExtras();
            //Il trasferimento dei dati tramite l'intent potrebbe dare problemi
        if(b != null) {
            secondiDurataEsame = b.getInt("secondiDurataEsame");
            secondiTempoStimato = b.getInt("secondiTempoStimato");


//            /*
//            Trasferire il blocco di istruzioni contenente la conversione dei tempi nella classe
//            della struttura d'esame
//            */
//
//            /*
//            I tempi stimati sono nella forma mm:ss:cc quindi devo prma scomporre la stringa in
//            minuti, secondi e centesimi di secondo, moltiplicarli per appositi coefficienti e sommarli.
//
//            I centesimi di secondo vengono arrotondati, quindi diventano o 1 secondo o 0 secondi.
//             */
//            StringTokenizer st = new StringTokenizer(strTempoStimato, ":");
//
//            //Minuti
//            secondiTempoStimato = secondiTempoStimato + Integer.parseInt(st.nextToken())*60;
//
//            //Secondi
//            secondiTempoStimato = secondiTempoStimato + Integer.parseInt(st.nextToken());
//
//            //Centesimi di secondo
//            secondiTempoStimato = secondiTempoStimato + Math.round(Integer.parseInt(st.nextToken())/100);
//
//            /*
//            La durata d'esame è nella forma hh:mm
//             */
//            st = new StringTokenizer(strDurataEsame, ":");
//
//            //Ore
//            secondiDurataEsame = secondiDurataEsame + Integer.parseInt(st.nextToken())*3600;
//
//            //Minuti
//            secondiDurataEsame = secondiDurataEsame + Integer.parseInt(st.nextToken())*60;

            rapportoTempi = secondiTempoStimato/secondiDurataEsame;

            //Classificazione del rapporto dei tempi
            if(rapportoTempi > 1){
                editableVerdetto.setTextColor(getResources().getColor(R.color.pessimoRosso));
                editableVerdetto.setText(getString(R.string.strVerdettoPessimo));

                editableDidascalia.setTextColor(getResources().getColor(R.color.pessimoRosso));
                editableDidascalia.setText(getString(R.string.strDidascaliaPessimo));
            } else{
                if(rapportoTempi > 0.9 && rapportoTempi <= 1){
                    editableVerdetto.setTextColor(getResources().getColor(R.color.discretoGiallo));
                    editableVerdetto.setText(getString(R.string.strVerdettoDiscreto));

                    editableDidascalia.setTextColor(getResources().getColor(R.color.discretoGiallo));
                    editableDidascalia.setText(getString(R.string.strDidascaliaDiscreto));
                } else {
                    if(rapportoTempi > 0.8 && rapportoTempi <= 0.9){
                        editableVerdetto.setTextColor(getResources().getColor(R.color.buonoVerde));
                        editableVerdetto.setText(getString(R.string.strVerdettoBuono));

                        editableDidascalia.setTextColor(getResources().getColor(R.color.buonoVerde));
                        editableDidascalia.setText(getString(R.string.strDidascaliaBuono));
                    } else {
                        editableVerdetto.setTextColor(getResources().getColor(R.color.ottimoVerde));
                        editableVerdetto.setText(getString(R.string.strVerdettoOttimo));

                        editableDidascalia.setTextColor(getResources().getColor(R.color.ottimoVerde));
                        editableDidascalia.setText(getString(R.string.strDidascaliaOttimo));
                    }
                }
            }

        } else{
            //Se non posso prelevare i dati devo segnalare l'errore
            editableVerdetto.setTextColor(getResources().getColor(R.color.erroreRosso));
            editableVerdetto.setText(getString(R.string.strVerdettoErrore));

            editableDidascalia.setTextColor(getResources().getColor(R.color.erroreRosso));
            editableDidascalia.setText(getString(R.string.strDidascaliaErrore));
        }
    }
}
