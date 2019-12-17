package com.example.progettos3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import com.google.gson.Gson;

import com.google.gson.annotations.SerializedName;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //WebView wv = (WebView) findViewById(R.id.wvMain);


        //WebSettings ws = wv.getSettings();
        //ws.setJavaScriptEnabled(true);
        //wv.loadUrl("file:///android_asset/test.html");
        /*wv.addJavascriptInterface(new Object()
        {
            @JavascriptInterface
            public void performClick(String webmsg)
            {
                ClassObj object = Gson.fromJson(webmsg, ClassObj.class);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Hai Scritto " + webmsg,
                        Toast.LENGTH_SHORT);

                toast.show();

            }
        }, "ok");*/
    }
}

class ClassObj {
    @SerializedName("webmsg")
    private final String webmsg = "";

}
