package com.example.exerciseexchange.Ricerca_esercizi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.exerciseexchange.R;
import com.example.exerciseexchange.model.Esercizio;

import java.util.List;

import io.realm.Realm;

public class Slideshow extends AppCompatActivity {
    List<String> fotoURLs;

    Realm realm;

    ViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        /*
        Prelevo l'ID dell'esercizio tramite l'intent e prelevo le sue foto tramite una query
         */
        int ID = getIntent().getIntExtra("ID", 0);
        fotoURLs = realm.where(Esercizio.class).equalTo("ID", ID).findFirst().getFotografie();

        view_pager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, fotoURLs);
        view_pager.setAdapter(adapter);
    }
}
