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
import com.example.exerciseexchange.model.Esercizio;
import com.example.exerciseexchange.model.Materia;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

import static com.example.exerciseexchange.Utilità.MyApplication.criteriRicercaFile;

public class singolaCategoria extends AppCompatActivity {

    private EditText editMateria, editCategoria;
    private Button btnInvia;

    private String materia, categoria;

    private Realm realm;
    private RealmList<Esercizio> esercizi;

    private fileHandler fh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singola_categoria);

        editMateria = findViewById(R.id.editMateria);
        editCategoria = findViewById(R.id.edit_Categoria);
        btnInvia = findViewById(R.id.btnRicerca);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        fh = new fileHandler(getApplicationContext());

        btnInvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materia = editMateria.getText().toString().trim();
                categoria = editCategoria.getText().toString().trim();
                //Eseguo la query per prelevare gli esercizi
                if(runQuery(materia, categoria)){
                    //Inserisco i criteri di ricerca in un file
                    fh.write(criteriRicercaFile, materia + "@" + categoria);
                    Intent intent = new Intent(singolaCategoria.this, GraphActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private boolean runQuery(String mat, String cat){
        //Verifico che i campi siano inseriti
        if(mat.isEmpty()){
            Toast.makeText(getApplicationContext(),getString(R.string.erroreMateriaVuota) , Toast.LENGTH_LONG).show();
            return false;
        }
        if(cat.isEmpty()){
            Toast.makeText(getApplicationContext(),getString(R.string.erroreCategoriaVuota) , Toast.LENGTH_LONG).show();
            return false;
        }

        //Verifico che la ricerca produca dei risultati
        Materia objMateria = realm.where(Materia.class).equalTo("Nome", mat).findFirst();
        if(objMateria == null ){
            Toast.makeText(getApplicationContext(),getString(R.string.subjectNotFound) , Toast.LENGTH_LONG).show();
            return false;
        } else{
            //Verifico che la categoria inserita sia presente tra quelle correlate alla materia
            Categoria objCategoria = realm.where(Categoria.class).equalTo("Nome", cat).findFirst();
            if(!objMateria.getCategorie().contains(objCategoria)){
                Toast.makeText(getApplicationContext(),getString(R.string.categoryNotFound) , Toast.LENGTH_LONG).show();
                return false;
            } else{
                /*
                La categoria inserita è contenuta nella lista delle categorie della materia.
                Verifico che a questa categoria siano correlati degli esercizi.
                 */
                esercizi = objCategoria.getEsercizi(); //Modificare in base a quanto fatto nella classe ricercaEsercizi
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
