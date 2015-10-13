package org.nganga.furl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.nganga.furl.R;
import org.nganga.furl.SessionRecorder;

public class Login extends Activity {

    private TwitterLoginButton twitterButton;
    private DigitsAuthButton phoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpViews();
    }

    private void setUpViews() {
        setUpSkip();
        setUpTwitterButton();
        setUpDigitsButton();
    }

    private void setUpTwitterButton() {
        twitterButton = (TwitterLoginButton) findViewById(R.id.twitter_button);
        twitterButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                SessionRecorder.recordSessionActive("Login: twitter account active", result.data);
                Answers.getInstance().logEvent("login:twitter:success");
                startThemeChooser();
            }

            @Override
            public void failure(TwitterException exception) {
                Answers.getInstance().logEvent("login:twitter:failure");
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.toast_twitter_signin_fail),
                        Toast.LENGTH_SHORT).show();
                Crashlytics.logException(exception);
            }
        });
    }

    private void setUpDigitsButton() {
        phoneButton = (DigitsAuthButton) findViewById(R.id.phone_button);
        phoneButton.setAuthTheme(R.style.AppTheme);
        phoneButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession digitsSession, String phoneNumber) {
                SessionRecorder.recordSessionActive("Login: digits account active", digitsSession);
                Answers.getInstance().logEvent("login:digits:success");
                startThemeChooser();
            }

            @Override
            public void failure(DigitsException e) {
                Answers.getInstance().logEvent("login:digits:failure");
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.toast_twitter_digits_fail),
                        Toast.LENGTH_SHORT).show();
                Crashlytics.logException(e);
            }
        });
    }

    private void setUpSkip() {
        TextView skipButton;
        skipButton = (TextView) findViewById(R.id.skip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crashlytics.log("Login: skipped login");
                Answers.getInstance().logEvent("skipped login");
                startThemeChooser();
                overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterButton.onActivityResult(requestCode, resultCode, data);
    }

    private void startThemeChooser() {
        final Intent themeChooserIntent = new Intent(LoginActivity.this,
                ThemeChooserActivity.class);
        startActivity(themeChooserIntent);
    }
}
