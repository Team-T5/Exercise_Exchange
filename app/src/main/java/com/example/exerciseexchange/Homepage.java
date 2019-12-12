package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Homepage extends AppCompatActivity {

    Button btnRicercaEsercizi, btnInserimentoEsercizi, btnStatisticheEsercizi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        btnRicercaEsercizi = findViewById(R.id.btnRicercaEsercizi);
        btnInserimentoEsercizi = findViewById(R.id.btnInserimentoEsercizi);
        btnStatisticheEsercizi = findViewById(R.id.btnStatisticheEsercizi);

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
    }

    private void gotoRicercaEsercizi(){
        /*
        Inserire qui l'intent
         */
    }

    private void gotoInserimentoEsercizi(){
        /*
        Inserire qui l'intent
         */
    }

    private void gotoStatisticheEsercizi(){
        /*
        Inserire qui l'intent
         */
    }

    private void Logout(View view){
        /*
        Rimuovere le credenziali dalla sessione e tornare alla schermata di login
         */
    }
}
