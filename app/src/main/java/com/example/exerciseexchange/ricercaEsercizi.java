package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exerciseexchange.model.Categoria;
import com.example.exerciseexchange.model.Esercizio;
import com.example.exerciseexchange.model.Libro;
import com.example.exerciseexchange.model.Materia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.example.exerciseexchange.MyApplication.criteriRicercaFile;

public class ricercaEsercizi extends AppCompatActivity {

    private RadioGroup radioMenu;
    private RadioButton radioScelta;
    private String radioText;
    private EditText editMateria, editLibro, editCapitolo, editNumero, editCategoria;
    private TextView txtLibro, txtCapitolo, txtNumero, txtCategoria;
    private Button btnRicerca;

    private String materia, libro, capitolo, numero, categoria;
    private boolean mode = true; //true per la ricerca dettagliata, false per la ricerca generica

    private Realm realm;

    private ArrayList<Esercizio> objEsercizi;
    private Materia objMateria;
    private Categoria objCategoria;
    private Libro objLibro;

    private fileHandler fh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca_esercizi);

        radioMenu = findViewById(R.id.radioMenu);

        editMateria = findViewById(R.id.editMateria);
        editLibro = findViewById(R.id.editLibro);
        editCapitolo = findViewById(R.id.editCapitolo);
        editNumero = findViewById(R.id.editNumero);
        editCategoria = findViewById(R.id.editCategoria);

        txtLibro = findViewById(R.id.txtLibro);
        txtCapitolo = findViewById(R.id.txtCapitolo);
        txtNumero = findViewById(R.id.txtNumero);
        txtCategoria = findViewById(R.id.txtCategoria);

        btnRicerca = findViewById(R.id.btnRicerca);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        fh = new fileHandler(getApplicationContext());

        btnRicerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (runQuery()) {
                    gotoVisualizzazione();
                }
            }
        });
    }

    public void checkButton(View v) {
        int radioId = radioMenu.getCheckedRadioButtonId();

        radioScelta = findViewById(radioId);
        radioText = radioScelta.getText().toString();

        /*
        La selezione di un radioButton rende visibili alcuni elementi dell'interfaccia e ne rende
        invisibili altri.
         */

        if (radioText == getString(R.string.ricercaDettagliata)) {
            /*
            Devo prima svuotare la categoria e rimuoverla dall'interfaccia per fare spazio.
             */
            editCategoria.setText("");
            txtCategoria.setVisibility(View.GONE);
            editCategoria.setVisibility(View.GONE);

            /*
            Ora rendo visibili gli elementi dell'interfaccia della ricerca dettagliata
             */
            txtLibro.setVisibility(View.VISIBLE);
            editLibro.setVisibility(View.VISIBLE);

            txtCapitolo.setVisibility(View.VISIBLE);
            editCapitolo.setVisibility(View.VISIBLE);

            txtNumero.setVisibility(View.VISIBLE);
            editNumero.setVisibility(View.VISIBLE);

            //Imposto la modalità
            mode = true;
        } else {
            /*
            Devo prima svuotare le caselle di testo: libro, capitolo e numero, poi rimuoverle
            dall'interfaccia per fare spazio alla categoria.
             */
            editLibro.setText("");
            editCapitolo.setText("");
            editNumero.setText("");

            txtLibro.setVisibility(View.GONE);
            editLibro.setVisibility(View.GONE);

            txtCapitolo.setVisibility(View.GONE);
            editCapitolo.setVisibility(View.GONE);

            txtNumero.setVisibility(View.GONE);
            editNumero.setVisibility(View.GONE);

            /*
            Ora devo inserire la categoria
             */
            txtCategoria.setVisibility(View.VISIBLE);
            editCategoria.setVisibility(View.VISIBLE);

            //Imposto la modalità
            mode = false;
        }
    }

    private void gotoVisualizzazione() {
//        Bundle b = new Bundle();
//        b.putSerializable("Esercizi", objEsercizi);
        /*
        Verificare che la serializzazione funzioni, e se funziona utilizzarla anche nella classe
        singolaCategoria
         */
        Intent intent = new Intent(this, Visualizzazione.class);
//        intent.putExtras(b);
        startActivity(intent);
    }

    private boolean runQuery() {
        /*
        Questa funzione scrive i criteri di ricerca in un file che verrà letto nella classe: Visualizzazione
        La ricerca dettagliata ha i parametri separati dalla @, quella generica ha i parametri
        separati da |.
         */
        materia = editMateria.getText().toString().trim();
        objMateria = realm.where(Materia.class).equalTo("Nome", materia).findFirst();
        //Devo agire in modo diverso a seconda del tipo di ricerca
        if (mode) {
            //Ricerca dettagliata
            libro = editLibro.getText().toString().trim();
            capitolo = editCapitolo.getText().toString().trim();
            numero = editNumero.getText().toString().trim();

            if (objMateria == null) {
                Toast.makeText(getApplicationContext(), getString(R.string.subjectNotFound), Toast.LENGTH_LONG).show();
                return false;
            } else {
                //Verifico che il libro inserito sia presente tra quelli correlati alla materia
                objLibro = realm.where(Libro.class).equalTo("Nome", libro).findFirst();
                if (!objMateria.getLibri().contains(objLibro)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.bookNotFound), Toast.LENGTH_LONG).show();
                    return false;
                } else {
                /*
                Il libro inserito è contenuto nella lista dei libri della materia.
                Verifico che a questo libro siano correlati degli esercizi con i criteri cercati.
                 */
//                    RealmResults<Esercizio> exc = objLibro.getEsercizi().where().equalTo("Capitolo", capitolo).equalTo("codiceIdentificativo", numero).findAll();
//                    objEsercizi.addAll(exc);
                    objEsercizi = new ArrayList<>(objLibro.getEsercizi().where().equalTo("Capitolo", capitolo).equalTo("codiceIdentificativo", numero).findAll());
                    if (objEsercizi.isEmpty()) {
                        Toast.makeText(getApplicationContext(), getString(R.string.eserciziEmptyList), Toast.LENGTH_LONG).show();
                        return false;
                    } else {
                        //La ricerca ha prodotto dei risultati
                        fh.write(criteriRicercaFile, libro + "@" + capitolo + "@" + numero);
                        return true;
                    }
                }
            }
        } else {
            //Ricerca generica
            categoria = editCategoria.getText().toString().trim();
            if(objMateria == null ){
                Toast.makeText(getApplicationContext(),getString(R.string.subjectNotFound) , Toast.LENGTH_LONG).show();
                return false;
            } else{
                //Verifico che la categoria inserita sia presente tra quelle correlate alla materia
                objCategoria = realm.where(Categoria.class).equalTo("Nome", categoria).findFirst();
                if(!objMateria.getCategorie().contains(objCategoria)){
                    Toast.makeText(getApplicationContext(),getString(R.string.categoryNotFound) , Toast.LENGTH_LONG).show();
                    return false;
                } else{
                /*
                La categoria inserita è contenuta nella lista delle categorie della materia.
                Verifico che a questa categoria siano correlati degli esercizi.
                 */
                    objEsercizi = new ArrayList<>(objCategoria.getEsercizi());
                    if(objCategoria.getEsercizi().isEmpty()){
                        Toast.makeText(getApplicationContext(),getString(R.string.eserciziEmptyList) , Toast.LENGTH_LONG).show();
                        return false;
                    } else{
                        //La ricerca è andata a buon fine
                        fh.write(criteriRicercaFile, categoria);
                        return true;
                    }
                }
            }
        }
    }
}
