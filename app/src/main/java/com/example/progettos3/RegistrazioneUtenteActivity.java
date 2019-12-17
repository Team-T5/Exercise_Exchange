package com.example.progettos3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.progettos3.data.model.Utente;

import io.realm.Realm;

public class RegistrazioneUtenteActivity extends AppCompatActivity {
    private TextView txtUsername;
    private TextView txtPassword;
    private TextView txtNomeCompleto;
    private Button btnConferma;
    Realm realm = Realm.getDefaultInstance();
    //Utente utente = realm.createObject(Utente.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione_utente);

        txtUsername = (TextView)findViewById(R.id.txtNomeUtenteRegistrazione);
        txtPassword = (TextView)findViewById(R.id.txtPasswordRegistrazione);
        txtNomeCompleto = (TextView)findViewById(R.id.txtNomeCompletoRegistrazione);
        btnConferma = (Button)findViewById(R.id.btnConferma);

        btnConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                final Utente utente = realm.createObject(Utente.class,1);
                utente.setUserName(txtUsername.getText().toString());
                utente.setPassword(txtPassword.getText().toString());
                utente.setFullName(txtNomeCompleto.getText().toString());
                realm.commitTransaction();
                finish();
            }
        });


    }
}
