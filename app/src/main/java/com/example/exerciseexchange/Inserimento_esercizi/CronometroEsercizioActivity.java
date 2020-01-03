package com.example.exerciseexchange.Inserimento_esercizi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;


import com.example.exerciseexchange.R;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class CronometroEsercizioActivity extends AppCompatActivity {
    private Button start, stop, azzera, piu, meno, prosegui;
    private TextView decimi, txttentativi;
    private Chronometer simpleChronometer;
    private Integer tentativi;
    private TimerTask myTimerTask;
    private Timer timer;
    private final Handler handler = new Handler();
    private Long app1;
    private String mills;
    private String StatoCronometro;
    private long elapsedMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro_esercizio);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.titoloInserimento));
        getSupportActionBar().setSubtitle(getString(R.string.tempiETentativi));

        txttentativi = findViewById(R.id.txtTentativi);
        simpleChronometer  = findViewById(R.id.Cronometro); // initiate a chronometer
        start = findViewById(R.id.btnStart);
        stop = findViewById(R.id.btnStop);
        azzera = findViewById(R.id.btnAzzera);
        piu = findViewById(R.id.btnPiu);
        meno = findViewById(R.id.btnMeno);
        prosegui = findViewById(R.id.btnProsegui);
        simpleChronometer  = findViewById(R.id.Cronometro);
        decimi = findViewById(R.id.txtCentesimi);

        StatoCronometro = "0"; //cronometro fermo
        myTimerTask = new TimerTask() {
            @Override
            public void run() {
                // post a runnable to the handler
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (StatoCronometro.equals("1")) {
                            elapsedMillis = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
                            app1 = (elapsedMillis / 1000) * 1000;
                            app1 = elapsedMillis - app1;
                            if (Long.toString(app1).length() < 2) {
                                mills = ":0" + Long.toString(app1).substring(0, 1);
                            }
                            else {
                                mills = ":"+ Long.toString(app1).substring(0, 2);
                            }
                            decimi.setText(mills);
                        }
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(myTimerTask, 0,1);

        tentativi = 1;
        txttentativi.setText(Integer.toString(tentativi));

        prosegui.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Mi assicuro che il timer sia stato attivato
                if (elapsedMillis != 0) {
                    Intent intent = new Intent(CronometroEsercizioActivity.this, InserimentoEsercizioActivity.class);
                /*/
                Costruisco la stringa del tempo impiegato partendo dai millisecondi misurati dal
                cronometro.
                La stringa contenente il tempo impiegato è composta da minuti:secondi:centesimi di secondo
                */
                    String tempoImpiegato = new SimpleDateFormat("mm:ss:SS").format(elapsedMillis);
                    intent.putExtra("tempoImpiegato", tempoImpiegato);
                    intent.putExtra("numeroTentativi", tentativi);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), getString(R.string.utilizzareTimer),
                            Toast.LENGTH_LONG).show();
                }
            }
        });



        simpleChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

            }
        });
        meno.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                tentativi = tentativi - 1;
                if (tentativi <= 1) {
                    tentativi = 1;
                }

                txttentativi = (TextView) findViewById(R.id.txtTentativi);
                txttentativi.setText(Integer.toString(tentativi));
            }
        });

        piu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                tentativi = tentativi + 1;
                txttentativi = (TextView) findViewById(R.id.txtTentativi);
                txttentativi.setText(Integer.toString(tentativi));
            }
        });
        //simpleChronometer.start(); // start a chronometer
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                simpleChronometer.setBase(SystemClock.elapsedRealtime());
                simpleChronometer.start();
                decimi = (TextView) findViewById(R.id.txtCentesimi);
                decimi.setText(":00");
                StatoCronometro = "1";
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                simpleChronometer.stop();
                /*long elapsedMillis = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
                decimi = (TextView) findViewById(R.id.txtCentesimi);
                decimi.setText(":"+Long.toString(elapsedMillis).substring(1,2));*/
                StatoCronometro = "0";
            }
        });

        azzera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                simpleChronometer.setBase(SystemClock.elapsedRealtime());
                //simpleChronometer.start();
                decimi = (TextView) findViewById(R.id.txtCentesimi);
                decimi.setText(":00");
                StatoCronometro = "0";
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