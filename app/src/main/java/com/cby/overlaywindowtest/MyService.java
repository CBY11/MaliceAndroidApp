package com.cby.overlaywindowtest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MyService extends Service {
    public int cnt = 10;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Handler handler = new Handler();
        cnt = 10;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = new TransparentDialog(MyService.this, cnt);
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                Log.i("cby", "show dialog");
                dialog.show();
                if(cnt > 0) {
                    handler.postDelayed(this, 1000);
                    cnt--;
                }
            }
        }, 1000);
        return super.onStartCommand(intent, flags, startId);
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