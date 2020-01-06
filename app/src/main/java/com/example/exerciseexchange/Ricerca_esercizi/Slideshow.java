package com.example.exerciseexchange.Ricerca_esercizi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.exerciseexchange.R;
import com.example.exerciseexchange.model.Esercizio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.realm.Realm;

public class Slideshow extends AppCompatActivity {
    List<String> fotoURLs;

    Realm realm;

    ViewPager view_pager;
    FloatingActionButton fabValutazione;

    long ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        getSupportActionBar().setTitle(R.string.strExerciseResearch);
        getSupportActionBar().setSubtitle(R.string.slideshow);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        view_pager = findViewById(R.id.view_pager);
        fabValutazione = findViewById(R.id.fabValutazione);
        /*
        Prelevo l'ID dell'esercizio tramite l'intent e prelevo le sue foto tramite una query
         */
        ID = getIntent().getLongExtra("ID", 0);
        fotoURLs = realm.where(Esercizio.class).equalTo("ID", ID).findFirst().getFotografie();

        ViewPagerAdapter adapter = new ViewPagerAdapter(this, fotoURLs);
        view_pager.setAdapter(adapter);

        fabValutazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoVotaEsercizio();
            }
        });
    }

    private void gotoVotaEsercizio(){
        Intent intent = new Intent(this, votaEsercizio.class);
        intent.putExtra("ID", ID);
        startActivity(intent);
    }
}
