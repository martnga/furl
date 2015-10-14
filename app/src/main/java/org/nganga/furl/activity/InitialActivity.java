
package org.nganga.furl.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import com.digits.sdk.android.Digits;

import org.nganga.furl.FurlMain;
import org.nganga.furl.SessionRecorder;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Session;


public class InitialActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Session activeSession = SessionRecorder.recordInitialSessionState(
                Twitter.getSessionManager().getActiveSession(),
                Digits.getSessionManager().getActiveSession()
        );

        if (activeSession != null) {
            startApp();
        } else {
            startLoginActivity();
        }
    }

    private void startApp() {
        startActivity(new Intent(this, FurlMain.class));
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, Login.class));
    }
}
