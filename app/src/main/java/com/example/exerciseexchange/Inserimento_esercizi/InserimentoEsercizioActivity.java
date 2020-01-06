package com.example.exerciseexchange.Inserimento_esercizi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import org.apache.commons.net.ftp.FTPClient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.exerciseexchange.BuildConfig;
import com.example.exerciseexchange.Homepage;
import com.example.exerciseexchange.R;
import com.example.exerciseexchange.Utilità.fileHandler;
import com.example.exerciseexchange.model.Categoria;
import com.example.exerciseexchange.model.Esercizio;
import com.example.exerciseexchange.model.Libro;
import com.example.exerciseexchange.model.Materia;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

import static com.example.exerciseexchange.Utilità.MyApplication.credentialsFile;

public class InserimentoEsercizioActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private Uri photoURI;
    private String currentPhotoPath, CodiceEsercizio, capitolo, PercorsoFoto, categoria, libro, materia;
    private ImageButton btnCamera, btnListaImmagini;
    private Button btnConferma;
    private EditText edit_CodiceEsercizio, edit_Capitolo, edit_Categoria, edit_Libro, editMateria;

    private List<String> Fotografie; //Le stringhe sono gli URL delle foto sul sito

    private Realm realm;

    private fileHandler fh;

    private Categoria objCategoria;
    private Libro objLibro;

    private String subDirectory; //Stringa che conterrà la cartella in cui inserie l'immagine

    private FTPClient client = new FTPClient();//Serve per il caricamento delle immagini su Altervista

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserimento_esercizio);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.titoloInserimento));
        getSupportActionBar().setSubtitle(getString(R.string.informazioniEsercizio));

        //Serve per utilizzare la connessione FTP
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        subDirectory = null;

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
        editMateria = findViewById(R.id.editMateria);

        btnConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificaCampiInseriti()){
//                /*
//                Il caricamento delle foto sul server è opzionale, infatti se l'utente non scatta
//                delle fotografie la lista dei file sarà vuota e il metodo caricamentoFTP non verrà
//                chiamato. Il metodo che carica l'oggetto in realm, invece, viene sempre utilizzato.
//                 */
                    Upload();
                }
                }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificaCampiInseriti()) {
                    // TODO Auto-generated method stub
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        File photoFile = null;
                        try {
//                            photoFile = createImageFile(CodiceEsercizio);
                            photoFile = createImageFile(subDirectory);
                            //photoFile.delete();
                        } catch (IOException ex) {
                            Toast.makeText(InserimentoEsercizioActivity.this, ex.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
//                            photoURI = FileProvider.getUriForFile(v.getContext(),
//                                    "${applicationId}.provider",
//                                    photoFile);
                            photoURI = FileProvider.getUriForFile(v.getContext(),
                                    BuildConfig.APPLICATION_ID + ".fileprovider",
                                    photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            //startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                }
            }
        });

        btnListaImmagini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificaCampiInseriti()) {
                    // TODO Auto-generated method stub

//                     Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                     Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
//                             + "/files/Pictures/");
//                      File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//                     intent.setDataAndType(Uri.parse("file://" + storageDir.getPath()+"/"), "*/*");
//                     startActivity(Intent.createChooser(intent, "Open folder"));

                    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/" + subDirectory + "/");
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                    String imageFileName = "JPEG_" + timeStamp + "_";

                    try {
                        File image = File.createTempFile(
                                imageFileName,  /* prefix */
                                ".jpg",         /* suffix */
                                storageDir      /* subDirectory */
                        );

                        PercorsoFoto = image.getParent();
                        image.delete();
                    } catch (IOException ioex) {
                        PercorsoFoto = PercorsoFoto;
                    }

                    // Save a file: path for use with ACTION_VIEW intents

                    Intent iListaFile = new Intent(v.getContext(), ListFileActivity.class);
                    iListaFile.putExtra("path", PercorsoFoto);
                    iListaFile.putExtra("subDirectory", subDirectory);
                    startActivity(iListaFile);
                }
            }
        });
    }

    private boolean verificaCampiInseriti() {
        CodiceEsercizio = edit_CodiceEsercizio.getText().toString();
        capitolo = edit_Capitolo.getText().toString();
        categoria = edit_Categoria.getText().toString();
        libro = edit_Libro.getText().toString();
        materia = editMateria.getText().toString().trim();

        if (materia.isEmpty()) {
            Toast.makeText(InserimentoEsercizioActivity.this, getString(R.string.erroreMateriaVuota),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (libro.isEmpty()) {
            Toast.makeText(InserimentoEsercizioActivity.this, getString(R.string.erroreLibroVuoto),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (categoria.isEmpty()) {
            Toast.makeText(InserimentoEsercizioActivity.this, getString(R.string.erroreCategoriaVuota),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (CodiceEsercizio.isEmpty()) {
            Toast.makeText(InserimentoEsercizioActivity.this, getString(R.string.erroreCodiceEsercizioVuoto),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (capitolo.isEmpty()) {
            Toast.makeText(InserimentoEsercizioActivity.this, getString(R.string.erroreCapitoloVuoto),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        /*
        Tutti i campi sono stati inseriti, quindi posso verificare che l'inserimento sia coerente
         */

        Materia objMateria = realm.where(Materia.class).equalTo("Nome", materia).findFirst();
        if (objMateria == null) {
            Toast.makeText(InserimentoEsercizioActivity.this, getString(R.string.subjectNotFound),
                    Toast.LENGTH_LONG).show();
            return false;
        } else {
            objCategoria = realm.where(Categoria.class).equalTo("Nome", categoria).findFirst();
            if (!objMateria.getCategorie().contains(objCategoria)) {
                Toast.makeText(InserimentoEsercizioActivity.this, getString(R.string.subjectNotFound),
                        Toast.LENGTH_LONG).show();
                return false;
            } else {
                objLibro = realm.where(Libro.class).equalTo("Nome", libro).findFirst();
                if (!objMateria.getLibri().contains(objLibro)) {
                    Toast.makeText(InserimentoEsercizioActivity.this, getString(R.string.bookNotFound),
                            Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    /*
                    L'inserimento è coerente.
                    A questo punto imposto la subDirectory di destinazione degli esercizi utilizzando
                    un timestamp limitato ai secondi. Inoltre dato che questa funzione viene
                    eseguita ogni volta che si preme un pulsante se non istanziassi la variabile
                    diractory solo se questa è nulla inserirei le immagini di volta in volta in una
                    subDirectory diversa.
                     */
                    if(subDirectory == null){
                        subDirectory = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                    }
                    return true;
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
    private File createImageFile(String subdirectory) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/" + subdirectory + "/");
        if (!storageDir.exists()){
            storageDir.mkdirs();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* subDirectory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void Upload(){
        //Creo il nuovo esercizio
        Esercizio e = new Esercizio();

        //Prelevo lo username dell'utente
        String username = prelevaUsername();
        String tempoImpiegato = getIntent().getStringExtra("tempoImpiegato");
        int numeroTentativi = getIntent().getIntExtra("numeroTentativi", 1);

        //L'ID del nuovo esercizio è dato dal ID massimo nella tabella + 1
        long ID = (long) realm.where(Esercizio.class).max("ID") + 1;

        e.setID(ID);
        e.setCodiceIdentificativo(CodiceEsercizio);
        e.setCapitolo(capitolo);
        e.setNumTentativi(numeroTentativi);
        e.setTempoSvolgimento(tempoImpiegato);
        e.setDataSvolgimento(new Date());

        /*
        Devo accedere alla cartella contenente le foto e per ogni foto devo inserire il codice
        univoco (nome) in realm e devo caricarla su Altervista
         */
        File currentImage;
        FTPUploader uploader = null;
        //Connessione FTP
        try {
            uploader = new FTPUploader(getString(R.string.hostname), getString(R.string.usernameFTP), getString(R.string.passwordFTP));

            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/" + subDirectory + "/");
            String[] list = storageDir.list();
            for (int i = 0; i < list.length; i++){
                currentImage = new File(storageDir + "/" + list[i]);
                uploader.uploadFile(currentImage.getAbsolutePath(), currentImage.getName(), "");
                e.addFotografia(getString(R.string.baseURL) +currentImage.getName());
            }
        } catch (Exception error){
            Log.e("Errore di connessione", error.getMessage());
        } finally{
            try{
                if(uploader != null){
                    uploader.disconnect();
                }
            } catch (Exception eccezione){
                Log.e("Errore di chiusura", eccezione.getMessage());
            }
        }
        e.setCaricatoDa(username);


        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(e);
                /*
                Ora devo aggiungere l'esercizio alle liste degli esercizi correlati alla categoria
                e al libro inserito
                 */
                realm.where(Libro.class).equalTo("Nome", libro).findFirst().addEsercizio(e);
                realm.where(Categoria.class).equalTo("Nome", categoria).findFirst().addEsercizio(e);
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

    private String prelevaUsername(){
        /*
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
        return username;
    }
}

