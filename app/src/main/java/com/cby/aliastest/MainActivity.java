package com.cby.aliastest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.getPackageManager().setComponentEnabledSetting(
                        new ComponentName(MainActivity.this, "com.cby.aliastest.MainActivity"),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP );
                MainActivity.this.getPackageManager().setComponentEnabledSetting(
                        new ComponentName(MainActivity.this, "com.cby.aliastest.alias1"),
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP );
            }
        });


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.getPackageManager().setComponentEnabledSetting(
                        new ComponentName(MainActivity.this, "com.cby.aliastest.alias1"),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP );
                MainActivity.this.getPackageManager().setComponentEnabledSetting(
                        new ComponentName(MainActivity.this, "com.cby.aliastest.MainActivity"),
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP );
            }
        });
    }
}