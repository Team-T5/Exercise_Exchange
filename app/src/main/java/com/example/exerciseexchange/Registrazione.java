package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registrazione extends AppCompatActivity {

    EditText editUsername, editPassword, editConfermaPassword;
    Button btnRegistrati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        //Istanzio i riferimenti agli elementi
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editConfermaPassword = findViewById(R.id.editConfermaPassword);
        btnRegistrati = findViewById(R.id.btnRegistrati);

        btnRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrati();
            }
        });

    }

    private void Registrati(){

        //Prelevo i dati dall'interfaccia
        String strUsername, strPassword, strConfermaPassword;

        strUsername = editUsername.getText().toString().trim();
        strPassword = editPassword.getText().toString();
        strConfermaPassword = editConfermaPassword.toString();

        //Mi assicuro che tutti i dati siano stati inseriti
        if(strUsername.isEmpty()){
            String toastMessage = getString(R.string.insertUsername);
            Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
            toast.show();
        } else{
            if(strPassword.isEmpty()){
                String toastMessage = getString(R.string.insertPassword);
                Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                if(strConfermaPassword.isEmpty()){
                    String toastMessage = getString(R.string.insertConfirmPassword);
                    Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    //Tutti i campi sono inseriti e procedo con l'elaborazione

                    //I campi relativi all password e alla sua conferma devono essere uguali
                    if(!strPassword.equals(strConfermaPassword)){
                        String toastMessage = getString(R.string.differentPasswordInputs);
                        Toast toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
                        toast.show();
                    }
                    //Le password coincidono, quindi posso fare la query per l'iserimento

                    /*
                    Tenere a mente che l'Username può essere già presente nel database, quindi
                    bisogna prevedere un messaggio di errore apposito
                     */

                    /*
                    Inserire la query qui
                     */
                }
            }
        }

    }
}
