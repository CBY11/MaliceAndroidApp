package com.cby.forktest_new;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.cby.forktest_new.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'forktest_new' library on application startup.
    static {
        System.loadLibrary("forktest_new");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI(this.getFilesDir().getAbsolutePath()));
    }

    /**
     * A native method that is implemented by the 'forktest_new' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI(String filepath);
}