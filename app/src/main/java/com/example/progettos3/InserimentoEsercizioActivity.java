package com.example.progettos3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InserimentoEsercizioActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    Uri photoURI;
    String currentPhotoPath;
    ImageButton btnCamera, btnListaImmagini;
    Button btnConferma;
    TextView txtCodiceEsercizio;
    String PercorsoFoto;
    String CodiceEsercizio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserimento_esercizio);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Home");
        getSupportActionBar().setSubtitle("Progetto S3");
        btnConferma = (Button)findViewById(R.id.btnConferma);
        btnConferma.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnCamera = (ImageButton)findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txtCodiceEsercizio = (TextView)findViewById(R.id.txtCodiceEsercizio);
                if (txtCodiceEsercizio.getText().length()==0) {
                    Toast.makeText(InserimentoEsercizioActivity.this, "Indicare un codice esercizio",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                // TODO Auto-generated method stub
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(txtCodiceEsercizio.getText().toString());
                        //photoFile.delete();
                    } catch (IOException ex) {
                        Toast.makeText(InserimentoEsercizioActivity.this, ex.getMessage(),
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(v.getContext(),
                                "com.example.progettos3.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        //startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                    }
                }
            }
        });
        btnListaImmagini = (ImageButton)findViewById(R.id.btnListaImmagini);
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
                txtCodiceEsercizio = (TextView)findViewById(R.id.txtCodiceEsercizio);
                if (txtCodiceEsercizio.getText().length()==0) {
                    Toast.makeText(InserimentoEsercizioActivity.this, "Indicare un codice esercizio",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/" + txtCodiceEsercizio.getText() + "/");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
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

                Intent iListaFile = new Intent(v.getContext(),ListFileActivity.class);
                iListaFile.putExtra("path", PercorsoFoto);
                iListaFile.putExtra("codice_esercizio", txtCodiceEsercizio.getText().toString());
                startActivity(iListaFile);
            }
        });
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
    private File createImageFile(String CodiceEsercizio) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/" + CodiceEsercizio + "/");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
