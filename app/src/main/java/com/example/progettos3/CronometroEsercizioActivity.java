package com.example.progettos3;

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

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class CronometroEsercizioActivity extends AppCompatActivity {
    Button start, stop, azzera, piu, meno, prosegui;
    TextView decimi, txttentativi;
    Chronometer simpleChronometer;
    Integer tentativi;
    TimerTask myTimerTask;
    Timer timer;
    final Handler handler = new Handler();
    Long app1;
    String mills;
    String StatoCronometro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro_esercizio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StatoCronometro = "0"; //cronometro fermo


        myTimerTask = new TimerTask() {
            @Override
            public void run() {
                // post a runnable to the handler
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        simpleChronometer  = (Chronometer) findViewById(R.id.Cronometro);
                        if (StatoCronometro.equals("1")) {
                            long elapsedMillis = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
                            app1 = (elapsedMillis / 1000) * 1000;
                            app1 = elapsedMillis - app1;
                            if (Long.toString(app1).length() < 2) {
                                mills = ":0" + Long.toString(app1).substring(0, 1);
                            }
                            else {
                                mills = ":"+ Long.toString(app1).substring(0, 2);
                            }

                            decimi = (TextView) findViewById(R.id.txtCentesimi);
                            decimi.setText(mills);
                        }
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(myTimerTask, 0,1);


        //getSupportActionBar().setTitle("Home");
        getSupportActionBar().setSubtitle("Progetto S3");
        tentativi = 1;
        txttentativi = (TextView) findViewById(R.id.txtTentativi);
        txttentativi.setText(Integer.toString(tentativi));


        simpleChronometer  = (Chronometer) findViewById(R.id.Cronometro); // initiate a chronometer
        start = (Button) findViewById(R.id.btnStart);
        stop = (Button) findViewById(R.id.btnStop);
        azzera = (Button) findViewById(R.id.btnAzzera);
        piu = (Button) findViewById(R.id.btnPiu);
        meno = (Button) findViewById(R.id.btnMeno);
        prosegui = (Button) findViewById(R.id.btnProsegui);

        prosegui.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(CronometroEsercizioActivity.this, InserimentoEsercizioActivity.class);
                startActivity(intent);
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
