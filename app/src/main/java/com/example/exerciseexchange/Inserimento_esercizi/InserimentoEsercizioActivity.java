package com.example.exerciseexchange.Inserimento_esercizi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.exerciseexchange.Homepage;
import com.example.exerciseexchange.R;
import com.example.exerciseexchange.Utilità.fileHandler;
import com.example.exerciseexchange.model.Esercizio;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmList;

import static com.example.exerciseexchange.Utilità.MyApplication.credentialsFile;

public class InserimentoEsercizioActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private Uri photoURI;
    private String currentPhotoPath, CodiceEsercizio, capitolo, PercorsoFoto, categoria, libro;
    private ImageButton btnCamera, btnListaImmagini;
    private Button btnConferma;
    private EditText edit_CodiceEsercizio, edit_Capitolo, edit_Categoria, edit_Libro;

    private List<String> Fotografie; //Le stringhe sono gli URL delle foto sul sito

    private Realm realm;

    private fileHandler fh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserimento_esercizio);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Home");
        getSupportActionBar().setSubtitle("Progetto S3");

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        fh = new fileHandler(getApplicationContext());

        btnConferma = findViewById(R.id.btnConferma);
        btnCamera = findViewById(R.id.btnCamera);
        edit_CodiceEsercizio = findViewById(R.id.edit_CodiceEsercizio);
        btnListaImmagini = findViewById(R.id.btnListaImmagini);
        edit_Capitolo = findViewById(R.id.edit_Capitolo);
        edit_Categoria =findViewById(R.id.edit_Categoria);
        edit_Libro = findViewById(R.id.edit_Libro);

        btnConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificaCampiInseriti()){
                /*
                Inserire qui l'upload delle immagini su hosting + timestamp.

                Fotografie.add(hosting + timestamp);

                Utilizzare un ciclo ed inserire la generazione del timestamp al suo interno
                così da avere un timestamp diverso per ogni fotografia
                 */
                //String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSSS").format(new Date());

                    caricamentoOggettoRealm();

                }
//                finish();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(CodiceEsercizio);
                        //photoFile.delete();
                    } catch (IOException ex) {
                        Toast.makeText(InserimentoEsercizioActivity.this, ex.getMessage(),
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(v.getContext(),
                                "com.example.exerciseexchange.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        //startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });

        btnListaImmagini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                //         + "/files/Pictures/");
                //  File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                // intent.setDataAndType(Uri.parse("file://" + storageDir.getPath()+"/"), "*/*");
                // startActivity(Intent.createChooser(intent, "Open folder"));*/
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/" + edit_CodiceEsercizio.getText() + "/");
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";

                try {
                    File image = File.createTempFile(
                            imageFileName,  /* prefix */
                            ".jpg",         /* suffix */
                            storageDir      /* directory */
                    );

                    PercorsoFoto = image.getParent();
                    image.delete();
                } catch (IOException ioex) {
                    PercorsoFoto = PercorsoFoto;
                }

                // Save a file: path for use with ACTION_VIEW intents

                Intent iListaFile = new Intent(v.getContext(), ListFileActivity.class);
                iListaFile.putExtra("path", PercorsoFoto);
                iListaFile.putExtra("codice_esercizio", edit_CodiceEsercizio.getText().toString());
                startActivity(iListaFile);
            }
        });
    }

    private boolean verificaCampiInseriti() {
        CodiceEsercizio = edit_CodiceEsercizio.getText().toString();
        capitolo = edit_Capitolo.getText().toString();
        categoria = edit_Categoria.getText().toString();
        libro = edit_Libro.getText().toString();

        if (CodiceEsercizio.isEmpty()) {
            Toast.makeText(InserimentoEsercizioActivity.this, getString(R.string.erroreCodiceEsercizioVuoto),
                    Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (capitolo.isEmpty()) {
                Toast.makeText(InserimentoEsercizioActivity.this, getString(R.string.erroreCapitoloVuoto),
                        Toast.LENGTH_LONG).show();
                return false;
            } else {
                if (categoria.isEmpty()) {
                    Toast.makeText(InserimentoEsercizioActivity.this, getString(R.string.erroreCategoriaVuota),
                            Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    if (libro.isEmpty()) {
                        Toast.makeText(InserimentoEsercizioActivity.this, getString(R.string.erroreLibroVuoto),
                                Toast.LENGTH_LONG).show();
                        return false;
                    } else {
                        //Tutti i campi sono stati inseriti
                        return true;
                    }
                }
            }
        }
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    private File createImageFile(String codEsercizio) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/" + codEsercizio + "/");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void caricamentoOggettoRealm(){
        //Creo il nuovo esercizio
        Esercizio e = new Esercizio();

        //Prelevo lo username dell'utente
                    /*
                    Metto lo username nella casella di testo in alto a sinistra.
                    Dato che il file Credentials.txt può contenere o solo lo username oppure username e password
                    devo distinguere i due casi verificando la presenza della @.
                     */
        String credentials = fh.read(credentialsFile);
        int atPosition = credentials.indexOf('@');
        String username;
        if(atPosition != -1){
            //The file has username@password
            username = credentials.substring(0, atPosition);
        } else{
            //The file only has the username
            username = credentials;
        }

        String tempoImpiegato = getIntent().getStringExtra("tempoImpiegato");
        int numeroTentativi = getIntent().getIntExtra("numeroTentativi", 1);

        RealmList<String> immagini = new RealmList<>();
        immagini.addAll(Fotografie);

        //Queste righe di codice lasciale stare per ora, poi le toglierò se necessario
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//                    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//                    String dataSvolgimento = sdf.format(new Date());

        //L'ID del nuovo esercizio è dato dal ID massimo nella tabella + 1
        int ID = (int) realm.where(Esercizio.class).max("ID") + 1;

        e.setID(ID);
        e.setCodiceIdentificativo(CodiceEsercizio);
        e.setCapitolo(capitolo);
        e.setNumTentativi(numeroTentativi);
        e.setTempoSvolgimento(tempoImpiegato);
        e.setDataSvolgimento(new Date());
        e.setFotografie(immagini);
        e.setCaricatoDa(username);


        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(e);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Toast.makeText(getApplicationContext(), getString(R.string.inserimentoEffettuato), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(InserimentoEsercizioActivity.this, Homepage.class);
                startActivity(intent);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Toast.makeText(getApplicationContext(), getString(R.string.inserimentoFallito), Toast.LENGTH_LONG).show();
            }
        });
    }

}

