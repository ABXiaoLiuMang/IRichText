package com.zzhoujay.xhtml;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    int i = 1;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = (TextView) findViewById(R.id.text);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                test(i % 6);
            }
        });

        test(i % 6);
    }


    private void test(int index) {
        String name = "TestHtml.html";
        switch (index) {
            case 0:
                name = "TestHtml.html";
                break;
            case 1:
                name = "TestHtml2.html";
                break;
            case 2:
                name = "TestHtml3.html";
                break;
            case 3:
                name = "TestHtml4.html";
                break;
            case 4:
                name = "TestHtml5.html";
                break;
            case 5:
                name = "TestHtml6.html";
                break;
        }

        String text = "";
        try {
            InputStream in = getAssets().open(name);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            text = new String(buffer);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        HtmlView.parseHtml(text).into(t);
    }

}
