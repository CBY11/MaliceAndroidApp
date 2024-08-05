package com.cby.wakelocktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public PowerManager.WakeLock wakeLock = null;
    public Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        releaseWakeLock2();
                        handler.postDelayed(this, 1000); // 1秒后再次执行
                        acquireWakeLock2();
                    }
                }, 1000);
            }
        });
    }

    private void releaseWakeLock2() {
        if (null != wakeLock) {
            wakeLock.release();
        }
    }
    private void acquireWakeLock2() {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "cby:PostLocationService");
            if (null != wakeLock) {
                wakeLock.acquire(1000);
            }
        } else
            wakeLock.acquire(1000);
    }
}