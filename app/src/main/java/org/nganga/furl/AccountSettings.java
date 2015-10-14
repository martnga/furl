package org.nganga.furl;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.digits.sdk.android.Digits;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.twitter.sdk.android.Twitter;

import org.nganga.furl.activity.Login;


public class AccountSettings extends Activity {

    private ViewPager viewPager;
    private Button logOutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setUpViews();
        logOutBtn = (Button) findViewById(R.id.logOutBtn);
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


    public void deleteSession(){

        Digits.getSessionManager().clearActiveSession();
        ParseObject po = new ParseObject("USERS");
        po.put("installed", false);
        po.saveEventually(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    final Intent signout = new Intent(getApplicationContext(),
                            Login.class);
                    startActivity(signout);
                } else {

                }

            }
        });
    }


}
