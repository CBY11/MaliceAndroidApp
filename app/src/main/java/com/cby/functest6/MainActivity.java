package com.cby.functest6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int count;
    TransparentDialog dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        // 设置窗口位置在左上角
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.width = 1;
        params.height = 0;
        window.setAttributes(params);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                count++;
                if (dialog != null) {
                    dialog.dismiss();
                }
                dialog = new TransparentDialog(MainActivity.this, count);
                // 显示 Dialog
                dialog.show();
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }
}

class TransparentDialog extends Dialog {
    public TransparentDialog(@NonNull Context context, int count) {
        super(context);
        setContentView(R.layout.dialog_layout);
        // 设置窗口背景透明
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置透明背景黑暗程度为0，达到完全透明的效果
        getWindow().setDimAmount(0f);
        //设置点击弹窗周围弹窗不取消，达到类似霸屏的效果
        setCancelable(false);
        String str = (String) ((TextView) findViewById(R.id.textView2)).getText();
        str = "count is : " + count + "\n" + str;
        ((TextView) findViewById(R.id.textView2)).setText(str);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}