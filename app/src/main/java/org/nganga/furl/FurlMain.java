package org.nganga.furl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.parse.ParseUser;


public class FurlMain extends AppCompatActivity {


    public static ParseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.furl);
        setUpViews();
    }

    private void setUpViews() {
        FetchContacts();
        setFriends();
        setAccount();
    }

    private void FetchContacts() {
        final ImageView icon = (ImageView) findViewById(R.id.cannonball_logo);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crashlytics.log("FurlMain: clicked Contacts button");
                Answers.getInstance().logCustom(new CustomEvent("clicked contacts"));
                final Intent intent = new Intent(getApplicationContext(), ContactsMining.class);
                startActivity(intent);
            }
        });
    }

    private void setFriends() {
        final ImageView popular = (ImageView) findViewById(R.id.popular);
        popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crashlytics.log("FurlMain: clicked friends button");
                Answers.getInstance().logCustom(new CustomEvent("clicked friends"));
                Intent intent = new Intent(getApplicationContext(), Friends.class);
                startActivity(intent);
            }
        });
    }

    private void setAccount() {
        final ImageView history = (ImageView) findViewById(R.id.history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crashlytics.log("FurlMain: clicked settings button");
                Answers.getInstance().logCustom(new CustomEvent("clicked AccountSettings"));
                final Intent intent = new Intent(getApplicationContext(),
                        AccountSettings.class);
                startActivity(intent);
            }
        });
    }


}
