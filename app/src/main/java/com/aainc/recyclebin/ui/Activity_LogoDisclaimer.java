package com.aainc.recyclebin.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;

import com.aainc.recyclebin.R;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

// Logo screen before moving into actual app, This is the entry point into app
public class Activity_LogoDisclaimer extends Activity {

    ImageView logodisclaimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logodisclaimer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        logodisclaimer = (ImageView) findViewById(R.id.imageview_logodisclaimer);

        requestConsentThenProceed();
    }

    private void requestConsentThenProceed() {
        ConsentRequestParameters params = new ConsentRequestParameters.Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();

        ConsentInformation consentInformation = UserMessagingPlatform.getConsentInformation(this);
        consentInformation.requestConsentInfoUpdate(this, params,
                () -> UserMessagingPlatform.loadAndShowConsentFormIfRequired(this, formError -> {
                    // Consent gathered (or not required) — proceed to app
                    launchMainActivity();
                }),
                requestConsentError -> {
                    // Could not determine consent status — proceed anyway (non-EEA users)
                    launchMainActivity();
                });
    }

    private void launchMainActivity() {
        Intent i = new Intent(Activity_LogoDisclaimer.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
