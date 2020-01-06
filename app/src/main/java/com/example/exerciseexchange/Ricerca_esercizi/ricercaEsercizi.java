package com.example.exerciseexchange.Ricerca_esercizi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exerciseexchange.R;
import com.example.exerciseexchange.Utilità.fileHandler;
import com.example.exerciseexchange.model.Categoria;
import com.example.exerciseexchange.model.Esercizio;
import com.example.exerciseexchange.model.Libro;
import com.example.exerciseexchange.model.Materia;

import java.util.ArrayList;

import io.realm.Realm;

import static com.example.exerciseexchange.Utilità.MyApplication.criteriRicercaFile;

public class ricercaEsercizi extends AppCompatActivity {

    private RadioGroup radioMenu;
    private RadioButton radioScelta;
    private String radioText;
    private EditText editMateria, editLibro, edit_Capitolo, editNumero, edit_Categoria;
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

        getSupportActionBar().setTitle(R.string.strExerciseResearch);
        getSupportActionBar().setSubtitle(R.string.modalitàRicerca);

        radioMenu = findViewById(R.id.radioMenu);

        editMateria = findViewById(R.id.editMateria);
        editLibro = findViewById(R.id.editLibro);
        edit_Capitolo = findViewById(R.id.edit_Capitolo);
        editNumero = findViewById(R.id.editNumero);
        edit_Categoria = findViewById(R.id.edit_Categoria);

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
            edit_Categoria.setText("");
            txtCategoria.setVisibility(View.GONE);
            edit_Categoria.setVisibility(View.GONE);

            /*
            Ora rendo visibili gli elementi dell'interfaccia della ricerca dettagliata
             */
            txtLibro.setVisibility(View.VISIBLE);
            editLibro.setVisibility(View.VISIBLE);

            txtCapitolo.setVisibility(View.VISIBLE);
            edit_Capitolo.setVisibility(View.VISIBLE);

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
            edit_Capitolo.setText("");
            editNumero.setText("");

            txtLibro.setVisibility(View.GONE);
            editLibro.setVisibility(View.GONE);

            txtCapitolo.setVisibility(View.GONE);
            edit_Capitolo.setVisibility(View.GONE);

            txtNumero.setVisibility(View.GONE);
            editNumero.setVisibility(View.GONE);

            /*
            Ora devo inserire la categoria
             */
            txtCategoria.setVisibility(View.VISIBLE);
            edit_Categoria.setVisibility(View.VISIBLE);

            //Imposto la modalità
            mode = false;
        }
    }

    private void gotoVisualizzazione() {
        Intent intent = new Intent(this, Visualizzazione.class);
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
            capitolo = edit_Capitolo.getText().toString().trim();
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
            categoria = edit_Categoria.getText().toString().trim();
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
