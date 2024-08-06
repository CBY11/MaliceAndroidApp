package com.cby.overlaywindowtest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!MainActivity.this.isOverlayPermission(MainActivity.this)) {
                    MainActivity.this.jumpToPermission();
                } else {
                    startService(new Intent(MainActivity.this, MyService.class));
                }

            }
        });
    }

    // 判断是否有悬浮窗权限
    public boolean isOverlayPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        } else {
            try {
                Class clazz = Settings.class;
                Method method = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                return (Boolean) method.invoke(null, context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // 跳转系统设置-悬浮窗页面
    public void jumpToPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + MainActivity.this.getPackageName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

