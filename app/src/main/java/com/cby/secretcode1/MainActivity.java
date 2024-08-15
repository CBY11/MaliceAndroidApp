package com.cby.secretcode1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                String callNumber = "*%#06%#";
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);   //android.intent.action.DIAL
                System.out.println(Uri.parse("tel:" + callNumber));
                intent.setData(Uri.parse("tel:" + callNumber));
                startActivity(intent);

                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }
}