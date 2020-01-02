package com.example.exerciseexchange.Inserimento_esercizi;

import androidx.core.content.FileProvider;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;


import com.example.exerciseexchange.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListFileActivity extends ListActivity {
    private String path;
    private String codice_esercizio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_file);
        // Use the current directory as title

        this.codice_esercizio = getIntent().getStringExtra("codice_esercizio");
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/" + this.codice_esercizio + "/");
        this.path = storageDir.getPath();
        /*if (getIntent().hasExtra("path")) {
            path = getIntent().getStringExtra("path");
        }*/
        setTitle(path);

        // Read all files sorted into the values-array
        List values = new ArrayList();
        File dir = new File(path);
        if (!dir.canRead()) {
            setTitle(getTitle() + " (inaccessible)");
        }
        String[] list = dir.list();
        if (list != null) {
            for (String file : list) {
                if (!file.startsWith(".")) {
                    values.add(file);
                }
            }
        }
        Collections.sort(values);

        // Put the data into the list
        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, values);
        AdapterFileList adapter = new AdapterFileList(this, list);
        adapter.foldername = path;
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        File percorsoFile = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/"+ this.codice_esercizio + "/");
        //percorsoFile = this.getExternalFilesDir(this.path);

        String Percorso = percorsoFile.getAbsolutePath();
        //String Percorso = this.path;
        //Percorso = Percorso + "/" + "es1";
        String filename = Percorso + "/" + (String) getListAdapter().getItem(position);
        File f = new File(filename);
        //Uri photoUri =  Uri.fromFile(f);
        Uri photoUri = FileProvider.getUriForFile(this, "com.example.progettos3.fileprovider", f);
        /*ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(this).addStream(photoUri);
        Intent chooserIntent = intentBuilder.createChooserIntent();
        startActivity(chooserIntent);*/





        //Intent intent = new Intent();
        //intent.setAction(Intent.ACTION_VIEW);
        //intent.setDataAndType(photoUri, "image/*");
        ////intent.setDataAndType(filename, "image/*");
        //startActivity(intent);


        Intent intent = new Intent(v.getContext(), ImageViewActivity.class);
        intent.putExtra("path", filename);
        startActivity(intent);


        /*
        if (path.endsWith(File.separator)) {
            filename = path + filename;
        } else {
            filename = path + File.separator + filename;
        }
        if (new File(filename).isDirectory()) {
            Intent intent = new Intent(this, ListFileActivity.class);
            intent.putExtra("path", filename);
            startActivity(intent);
        } else {
            Toast.makeText(this, filename + " is not a directory", Toast.LENGTH_LONG).show();
        }*/
    }
}
