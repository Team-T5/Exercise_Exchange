package com.example.exerciseexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class ImageViewActivity extends AppCompatActivity {
    public String FileName;
    private ImageView imgFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        this.FileName = getIntent().getStringExtra("path");
        Bitmap myBitmap = BitmapFactory.decodeFile(FileName);
        //Drawable d = new BitmapDrawable(getResources(), myBitmap);
        imgFoto = (ImageView)findViewById(R.id.imgImmagine);
        imgFoto.setImageBitmap(myBitmap);



    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

