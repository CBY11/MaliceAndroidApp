package com.cby.functest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.cby.functest.databinding.ActivityMainBinding;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'functest' library on application startup.
    static {
        System.loadLibrary("functest");
    }

    private ActivityMainBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        numberTextView = binding.sampleText;
        handler = new Handler();
        startCounting();

        /* native 方法测试
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI(this.getFilesDir().getAbsolutePath()));
        */

        /* 通知权限测试
        Button button = binding.button; //显示通知按钮
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent =
                        PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                String channelId = createNotificationChannel("my_channel_ID",
                       "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH );
                NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this, channelId)
                        .setContentTitle("通知")
                        .setContentText("收到一条消息")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(false)
                        .setOngoing(true);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(100, notification.build());
            }
        });
         */

        /**/
        Button button = binding.button2;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberTextView = binding.sampleText;
                handler = new Handler();
                startCounting();
//                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                PendingIntent pendingIntent =
//                        PendingIntent.getActivity(MainActivity.this, 0,
//                                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                String channelId = createNotificationChannel("my_channel_ID",
//                        "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH );
//                NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this, channelId)
//                        .setContentTitle("通知")
//                        .setContentText("收到一条消息")
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setCategory(NotificationCompat.CATEGORY_CALL)
//                        .setFullScreenIntent(pendingIntent, true)
//                        .setAutoCancel(false)
//                        .setOngoing(true);
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
//                notificationManager.notify(100, notification.build());
            }
        });


    }

    private TextView numberTextView;
    private int count = 0;
    private Handler handler;
    private Runnable runnable;
    PowerManager.WakeLock wakeLock = null;




    /*
    private void startCounting() {
        runnable = new Runnable() {
            @Override
            public void run() {
                count++;
                numberTextView.setText(String.valueOf(count));
                createNotification(count);
                Log.i("cby tag", "i am alive!" + count);
                handler.postDelayed(this, 3000); // 1秒后再次执行
            }
        };
        handler.postDelayed(runnable, 3000); // 1秒后第一次执行
    }

    private void createNotification(int cnt){
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(MainActivity.this, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String channelId = createNotificationChannel("my_channel_ID" + cnt,
                "my_channel_NAME" + cnt, NotificationManager.IMPORTANCE_HIGH );
        NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this, channelId)
                .setContentTitle("通知")
                .setContentText("收到一条消息")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setFullScreenIntent(pendingIntent, true)
                .setAutoCancel(false)
                .setOngoing(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
        notificationManager.notify(100, notification.build());
    }

    private String createNotificationChannel(String channelID, String channelNAME, int level) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
            manager.createNotificationChannel(channel);
            return channelID;
        } else {
            return null;
        }
    }*/




    /* wakeLock Test*/
    //获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
    private void acquireWakeLock() {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "cby:PostLocationService");
            if (null != wakeLock) {
                wakeLock.acquire();
            }
        }
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
    private void startCounting() {
        runnable = new Runnable() {
            @Override
            public void run() {
                count++;
                numberTextView.setText(String.valueOf(count));
                Log.i("cby tag", "i am alive!" + count);
                releaseWakeLock2();
                handler.postDelayed(this, 300); // 1秒后再次执行
                acquireWakeLock2();
            }
        };
        handler.postDelayed(runnable, 3000); // 1秒后第一次执行
    }






    /**
     * A native method that is implemented by the 'functest' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI(String filePath);
}