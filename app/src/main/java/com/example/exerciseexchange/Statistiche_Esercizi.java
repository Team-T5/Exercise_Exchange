package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Statistiche_Esercizi extends AppCompatActivity {

    Button btnSingolaCategoria, btnStrutturaEsame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiche__esercizi);

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
        /*
        Inserire l'intent
         */
    }

    private void gotoStrutturaEsame(){
        /*
        Inserire l'intent
         */
    }
}
