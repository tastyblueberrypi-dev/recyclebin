package com.aainc.recyclebin.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;

import com.aainc.recyclebin.R;
import com.aainc.recyclebin.util.Util;

// Logo screen before moving into actual app, This is the entry point into app
public class Activity_LogoDisclaimer extends Activity {

    private static int timeout = 1000;
    ImageView logodisclaimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logodisclaimer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        logodisclaimer = (ImageView) findViewById(R.id.imageview_logodisclaimer);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(Activity_LogoDisclaimer.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, timeout);

    }
}
