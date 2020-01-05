package com.example.exerciseexchange.Statistiche_esercizi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exerciseexchange.R;
import com.example.exerciseexchange.Utilità.fileHandler;
import com.example.exerciseexchange.model.Categoria;
import com.example.exerciseexchange.model.Materia;

import java.util.List;

import io.realm.Realm;

import static com.example.exerciseexchange.Utilità.MyApplication.criteriRicercaFile;

public class selezioneMateria extends AppCompatActivity {

    private Realm realm;

    private EditText editMateria;
    private Button btnConferma;

    private String materia;

    private fileHandler fh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selezione_materia);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        fh = new fileHandler(getApplicationContext());

        editMateria = findViewById(R.id.editMateria);
        btnConferma = findViewById(R.id.btnConferma);

        btnConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(controlli()){
                    /*
                    Inserisco la materia da cercare nel file criteriRicerca.txt e passo alla
                    struttra d'esame
                     */
                    fh.write(criteriRicercaFile, materia);
                    Intent intent = new Intent(selezioneMateria.this, StrutturaEsame2.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean controlli() {
        materia = editMateria.getText().toString().trim();
        //Verifico che la materia scelta esista
        Materia objMateria = realm.where(Materia.class).equalTo("Nome", materia).findFirst();
        if (objMateria == null) {
            Toast.makeText(getApplicationContext(), getString(R.string.subjectNotFound), Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}
