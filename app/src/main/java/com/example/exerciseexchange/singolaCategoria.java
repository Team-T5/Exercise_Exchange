package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exerciseexchange.model.Categoria;
import com.example.exerciseexchange.model.Esercizio;
import com.example.exerciseexchange.model.Materia;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class singolaCategoria extends AppCompatActivity {

    private EditText editMateria, editCategoria;
    private Button btnInvia;

    private String materia, categoria;

    private Realm realm;
    private RealmList<Esercizio> esercizi;
    private Esercizio[] arrayEsercizi;
    //private Materia objMateria;
    //private Categoria objCategoria;
    private List<String> listNomiCategorie, listTimestamp, listTempi;
    private List<Integer> listNumeroTentativi;

    private String[] arrayTimeStamp, arrayTempi;
    private int[] arrayNumeroTentativi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singola_categoria);

        editMateria = findViewById(R.id.editMateria);
        editCategoria = findViewById(R.id.editCategoria);
        btnInvia = findViewById(R.id.btnInvia);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        btnInvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materia = editMateria.getText().toString().trim();
                categoria = editCategoria.getText().toString().trim();

                //Eseguo la query per prelevare gli esercizi
                if(runQuery()){
                    //A scopo di test stampo un toast contenente la lista degli esercizi prelevati
                    Toast.makeText(getApplicationContext(),esercizi.toString(), Toast.LENGTH_LONG).show();

                    /*
                    Purtroppo non si può passare una lista di oggetti come extra, quindi devo
                    prelevare i dati necessari e convertire le liste in array
                     */

                    /*
                    Il seguente metodo lo tengo per precauzione ma in futuro verrà canellato
                     */
//                    for (Esercizio e: esercizi){
//                        listTimestamp.add(e.getTimestamp());
//                        listNumeroTentativi.add(e.getNumTentativi());
//                        listTempi.add(e.getTempoSvolgimento());
//                    }

                    /*
                    Questo metodo dovrebbe funzionare
                     */
//                    arrayEsercizi = (Esercizio[]) esercizi.toArray();
//
//                    arrayTimeStamp = new String[esercizi.size()-1];
//                    arrayNumeroTentativi = new int[esercizi.size()-1];
//                    arrayTempi = new String[esercizi.size()-1];
//
//                    for(int i = 0; i <= esercizi.size()-1; i++){
//                        arrayTimeStamp[i] = arrayEsercizi[i].getTimestamp();
//                        arrayTempi[i] = arrayEsercizi[i].getTempoSvolgimento();
//                        arrayNumeroTentativi[i] = arrayEsercizi[i].getNumTentativi();
//                    }

//                    //Creo un bundle per contenere i valori da passare
//                    Bundle b = new Bundle();
//                    b.putStringArray("arrayTimeStamp", arrayTimeStamp);
//                    b.putStringArray("arrayTempi", arrayTempi);
//                    b.putIntArray("arrayNumeroTentativi", arrayNumeroTentativi);
//
//                    //Se la query è andata a buon fine passo i dati all'attività che stampa i grafici
//                    Intent intent = new Intent(this, Grafici.class);
//                    intent.putExtras(b);
//                    startActivity(intent);

                }

            }
        });
    }

    private boolean runQuery(){
        Materia objMateria = realm.where(Materia.class).equalTo("Nome", materia).findFirst();
        if(objMateria == null ){
            Toast.makeText(getApplicationContext(),getString(R.string.subjectNotFound) , Toast.LENGTH_LONG).show();
            return false;
        } else{
            //Verifico che la categoria inserita sia presente tra quelle correlate alla materia
            Categoria objCategoria = realm.where(Categoria.class).equalTo("Nome", categoria).findFirst();
            if(!objMateria.getCategorie().contains(objCategoria)){
                Toast.makeText(getApplicationContext(),getString(R.string.categoryNotFound) , Toast.LENGTH_LONG).show();
                return false;
            } else{
                /*
                La categoria inserita è contenuta nella lista delle categorie della materia.
                Verifico che a questa categoria siano correlati degli esercizi
                 */
                esercizi = objCategoria.getEsercizi();
                if(esercizi == null || esercizi.size() == 0){
                    Toast.makeText(getApplicationContext(),getString(R.string.eserciziEmptyList) , Toast.LENGTH_LONG).show();
                    return false;
                } else{
                    //La query è andata a buon fine
                    return true;
                }
            }
        }
    }
}
