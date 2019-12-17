package com.example.progettos3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RicercaEsercizi extends AppCompatActivity  {
    private Spinner spinner;
    private static final String[] paths = {"item 1", "item 2", "item 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca_esercizi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Home");
        getSupportActionBar().setSubtitle("Progetto S3");

        /*Spinner staticSpinner = (Spinner) findViewById(R.id.drpMateria);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.materie,
                        android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(staticAdapter);*/

        Spinner dynamicSpinner = (Spinner) findViewById(R.id.drpMateria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.materie, R.layout.drp_layout);

        adapter.setDropDownViewResource(R.layout.drp_layout);
        dynamicSpinner.setAdapter(adapter);

        Spinner dynamicSpinner2 = (Spinner) findViewById(R.id.drpLibro);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.libri, R.layout.drp_layout);

        adapter.setDropDownViewResource(R.layout.drp_layout);
        dynamicSpinner2.setAdapter(adapter);

        Spinner dynamicSpinner3 = (Spinner) findViewById(R.id.drpCapitolo);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.capitoli, R.layout.drp_layout);

        adapter.setDropDownViewResource(R.layout.drp_layout);
        dynamicSpinner3.setAdapter(adapter);



        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        dynamicSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
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

}
