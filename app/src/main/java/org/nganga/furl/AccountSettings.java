package org.nganga.furl;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.digits.sdk.android.Digits;
import com.parse.ParseUser;

import org.nganga.furl.activity.InitialActivity;


public class AccountSettings extends Activity {

    private Button logOutBtn;
    private Button saveSettingsBtn;
    public static ParseUser user;
    private TextInputLayout mUsernameLayout;
    private TextInputLayout mStatusLayout;
    private EditText mUsernameText;
    private EditText mStatusText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setUpViews();

        mStatusLayout = (TextInputLayout) findViewById(R.id.status_text_layout);
        mStatusText = (EditText) findViewById(R.id.status_text);
        mUsernameLayout = (TextInputLayout) findViewById(R.id.user_name_text_layout);
        mUsernameText = (EditText) findViewById(R.id.user_name_text);

        logOutBtn = (Button) findViewById(R.id.logOutBtn);
        saveSettingsBtn = (Button) findViewById(R.id.saveSettingsBtn);

        saveSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserSettings();
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSession();
            }
        });

    }

    private void setUpViews() {
        setUpBack();

    }


    private void setUpBack() {
        // go back if clicked
        final ImageView backButton = (ImageView) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setUpCount() {
        // go back if clicked
        final ImageView backButton = (ImageView) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void saveUserSettings(){

        boolean isUsernameEmpty = userNameEmpty();
        boolean isStatusEmpty = statusInputEmpty();

        if (isStatusEmpty && isUsernameEmpty){

            Context context = getApplicationContext();
            CharSequence text = "All The Fields!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else if (isStatusEmpty && !isUsernameEmpty) {

            mStatusLayout.setError("Whats On Your Mind");
            mUsernameLayout.setError(null);
        }
        else if (!isStatusEmpty && isUsernameEmpty) {

            mUsernameLayout.setError("You Used To Have A Name");
            mStatusLayout.setError(null);
        }
        else {

            user.put("username", mUsernameText.getText().toString());
            user.put("status", mStatusText.getText().toString());
            user.saveEventually();
            final Intent intent = new Intent(AccountSettings.this,
                    FurlMain.class);
            startActivity(intent);
        }



    }

    private boolean userNameEmpty(){

        return mUsernameText.getText() == null || mUsernameText.getText().toString() == null || mUsernameText.getText().toString().isEmpty();
    }


    private boolean statusInputEmpty(){

        return mStatusText.getText() == null || mStatusText.getText().toString() == null || mStatusText.getText().toString().isEmpty();
    }

    public void deleteSession(){

        Digits.getSessionManager().clearActiveSession();
        user.put("installed", false);
        user.saveEventually();

        final Intent intent = new Intent(AccountSettings.this,
                InitialActivity.class);
        startActivity(intent);

    }


}
