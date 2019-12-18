package com.example.exerciseexchange;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.w3c.dom.Text;

public class AdapterFileList extends ArrayAdapter<String> {
    private final Context context;
    private final String[] files;
    public String[] Immagini;
    public ImageView imgFoto;
    public TextView txtNomeFile;
    public String FileName;
    public String foldername;

    public AdapterFileList(Context context, String[] values) {
        super(context, R.layout.riga_filelist, values);
        this.context = context;
        this.files = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.riga_filelist, parent, false);
        imgFoto = (ImageView) rowView.findViewById(R.id.imgFoto);
        txtNomeFile = (TextView) rowView.findViewById(R.id.fotoDescription);
        File path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        //FileName = path.getPath();
        FileName = this.foldername;

        //Log.d("Files", "Path: " + path);
        //File directory = new File(String.valueOf(path));
        //File[] files = directory.listFiles();
        //Log.d("Files", "Size: "+ files.length);
        // for (int i = 0; i < files.length; i++)
        // {
        //     Log.d("Files", "FileName:" + files[i].getName());
        // }



        Bitmap myBitmap = BitmapFactory.decodeFile(FileName + "/" + files[position]);
        //Drawable d = new BitmapDrawable(getResources(), myBitmap);
        imgFoto.setImageBitmap(myBitmap);
        txtNomeFile.setText(files[position]);




        return rowView;
    }
}
