package com.example.exerciseexchange;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

/*
This class was made to make it easy and quick to handle the files in the rest of the code.
Whenever files are either red or written the code refers to this class
 */
public class fileHandler{

    protected Context context = null;

    //Constructor
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

            //The credentials are in the first line of the file
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
               Even if the file was already written the content gets deleted if I write into it.
               I use the @ to separate the username and the password therefore
               neither the username nor the password can have the @ into them
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
            I empty the file by writing null into it
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
