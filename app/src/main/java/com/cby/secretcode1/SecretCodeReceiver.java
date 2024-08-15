package com.cby.secretcode1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SecretCodeReceiver extends BroadcastReceiver {

    public static final String SECRET_CODE_ACTION = "android.provider.Telephony.SECRET_CODE";
    public static final String SECRET_CODE = "123";

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case SECRET_CODE_ACTION:
                Uri uri = intent.getData();
                if (uri != null) {
                    String host = uri.getSchemeSpecificPart().substring(2);
                    if (SECRET_CODE.equals(host)) {
                        //启动应用
                        Intent i = new Intent(context, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
                break;
        }

    }
}