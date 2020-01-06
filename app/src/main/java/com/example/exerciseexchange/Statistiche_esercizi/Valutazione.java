package com.example.exerciseexchange.Statistiche_esercizi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.exerciseexchange.R;

import java.util.StringTokenizer;

public class Valutazione extends AppCompatActivity {

    private TextView editableDurataEsame, editableTempoStimato, editableVerdetto, editableDidascalia;
    private double rapportoTempi;
    private int secondiDurataEsame = 0, secondiTempoStimato = 0, oreTempoStimato = 0, minutiTempoStimato = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valutazione);

        editableDurataEsame = findViewById(R.id.editableDurataEsame);
        editableTempoStimato = findViewById(R.id.editableTempoStimato);
        editableVerdetto = findViewById(R.id.editableVerdetto);
        editableDidascalia = findViewById(R.id.editableDidascalia);

        getSupportActionBar().setTitle(R.string.strutturaEsame);
        getSupportActionBar().setSubtitle(R.string.strValutazione);

        /*
        Prendo la durata dell'esame e il tempo di svolgimento stimato in secondi dall'intent poi ne
        calcolo il rapporto
         */

        Bundle b = getIntent().getExtras();
            //Il trasferimento dei dati tramite l'intent potrebbe dare problemi
        if(b != null) {
            secondiDurataEsame = b.getInt("secondiDurataEsame");
            secondiTempoStimato = b.getInt("secondiTempoStimato");

            editableDurataEsame.setText(b.getString("stringaDurataEsame"));

            oreTempoStimato = secondiTempoStimato/3600;
            minutiTempoStimato = (secondiTempoStimato - oreTempoStimato*3600)/60;

            String stringTempoStimato = secondiTempoStimato/3600 + ":" + (secondiTempoStimato - oreTempoStimato*3600)/60;
            editableTempoStimato.setText(stringTempoStimato);


            rapportoTempi = (float) secondiTempoStimato/secondiDurataEsame;

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
