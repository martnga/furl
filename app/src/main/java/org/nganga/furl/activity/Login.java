package org.nganga.furl.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.nganga.furl.AccountSettings;
import org.nganga.furl.FurlMain;
import org.nganga.furl.R;
import org.nganga.furl.SessionRecorder;

import java.util.Random;

public class Login extends Activity {

   // private TwitterLoginButton twitterButton;
    private DigitsAuthButton phoneButton;
    private CheckBox termsCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpViews();
    }

    private void setUpViews() {
        setUpTermsCheck();
    }

    private void setUpTermsCheck(){

        termsCheck =(CheckBox)findViewById(R.id.terms_check);
        if (termsCheck.isChecked())  {

            setUpDigitsButton();
        }
        else {

            Toast.makeText(getApplicationContext(), "Agree To Terms And Conditions To Proceed.", Toast.LENGTH_SHORT).show();
        }
    }
    /*private void setUpTwitterButton() {
        twitterButton = (TwitterLoginButton) findViewById(R.id.twitter_button);
        twitterButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                SessionRecorder.recordSessionActive("Login: twitter account active", result.data);
                Answers.getInstance().logCustom(new CustomEvent("login:twitter:success"));
                startApp();
            }

            @Override
            public void failure(TwitterException exception) {
                Answers.getInstance().logCustom(new CustomEvent("login:twitter:failure"));
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.toast_twitter_signin_fail),
                        Toast.LENGTH_SHORT).show();
                Crashlytics.logException(exception);
            }
        });
    }*/

    private void setUpDigitsButton() {
        phoneButton = (DigitsAuthButton) findViewById(R.id.phone_button);
        phoneButton.setAuthTheme(R.style.AppTheme);
        phoneButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession digitsSession, String phoneNumber) {
                Random rand = new Random();

                int n = rand.nextInt(1 * 1000000000) + 1;

                SessionRecorder.recordSessionActive("Login: digits account active", digitsSession);
                Answers.getInstance().logCustom(new CustomEvent("login:digits:success"));
                final ParseUser pu = new ParseUser();
                pu.put("phoneNumber", phoneNumber);
                pu.setUsername("Furl" + n);
                pu.setPassword("12345");
                pu.put("installed", true);
                pu.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            startApp();
                            finish();
                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                            builder.setMessage(e.getMessage());
                            builder.setTitle("Sorry Mate");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //to close the dialog
                                    deleteSession();
                                    dialogInterface.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                            e.printStackTrace();
                        }
                    }
                });

            }

            @Override
            public void failure(DigitsException e) {
                Answers.getInstance().logCustom(new CustomEvent("login:digits:failure"));
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.toast_twitter_digits_fail),
                        Toast.LENGTH_SHORT).show();
                Crashlytics.logException(e);
            }
        });
    }

    /*private void setUpSkip() {
        TextView skipButton;
        skipButton = (TextView) findViewById(R.id.skip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crashlytics.log("Login: skipped login");
                Answers.getInstance().logCustom(new CustomEvent("skipped login"));
                startApp();
                overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
            }
        });
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterButton.onActivityResult(requestCode, resultCode, data);
    }*/

    private void startApp() {

    final Intent intent = new Intent(Login.this,
                AccountSettings.class);
        startActivity(intent);
    }

    public void deleteSession(){

        Digits.getSessionManager().clearActiveSession();

        final Intent intent = new Intent(Login.this,
                InitialActivity.class);
        startActivity(intent);

    }

}
