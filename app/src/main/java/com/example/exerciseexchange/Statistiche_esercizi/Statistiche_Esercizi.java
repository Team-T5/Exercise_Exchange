package com.example.exerciseexchange.Statistiche_esercizi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.exerciseexchange.R;

public class Statistiche_Esercizi extends AppCompatActivity {

    Button btnSingolaCategoria, btnStrutturaEsame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiche__esercizi);

        getSupportActionBar().setTitle(R.string.strExerciseStats);
        getSupportActionBar().setSubtitle(R.string.selezioneModalità);

        btnSingolaCategoria = findViewById(R.id.btnSingolaCategoria);
        btnStrutturaEsame = findViewById(R.id.btnStrutturaEsame);

        btnSingolaCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSingolaCategoria();
            }
        });

        btnStrutturaEsame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoStrutturaEsame();
            }
        });

    }

    private void gotoSingolaCategoria(){
        Intent intent = new Intent(this, singolaCategoria.class);
        startActivity(intent);
    }

    private void gotoStrutturaEsame(){
        Intent intent = new Intent(this, selezioneMateria.class);
        startActivity(intent);
    }
}
