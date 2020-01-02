package com.example.exerciseexchange.Utilità;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

/*
Questa classe è stata fatta per rendere facile e veloce la gestione dei file nel resto del codice
 */
public class fileHandler{

    protected Context context = null;

    //Costruttore
    public fileHandler(Context context){
        this.context = context.getApplicationContext();
    }

    public String read(String file){

        FileInputStream fis = null;
        String line = null;
        try{
            fis = context.openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            //Le credenziali sono nella prima riga del file
            line = br.readLine();
        } catch(Exception e){
            Log.e("File error: ", e.getMessage());
        } finally{
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    public void write(String file, String line){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(file, MODE_PRIVATE);
            /*
               Anche se il file fosse stato già scritto questa funzione ne sovrascrive il contenuto.
               Utilizzo la @ per separare username e password, quindi entrambi non devono averla
               al loro interno.
            */
            fos.write(line.getBytes());
        } catch (Exception e) {
            Log.e("File error: ", e.getMessage());
        }
        finally {
            try {
                fos.close();
            } catch(IOException e ) {
                Log.e("File closing error: ", e.getMessage());
            }
        }
    }

    public void emptyFile(String file) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(file, MODE_PRIVATE);

            /*
            Svuoto il file scrivendoci null
             */
            fos.write(("").getBytes());
        } catch (Exception e) {
            Log.e("File error: ", e.getMessage());
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                Log.e("File closing error: ", e.getMessage());
            }
        }
    }

}
