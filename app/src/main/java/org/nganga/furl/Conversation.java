package org.nganga.furl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;

/**
 * Created by mansa on 10/14/15.
 */
public class Conversation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);

        setUpViews();
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Crashlytics.log("Conversations: getting back to FriendsList");
            final Intent intent = new Intent(getApplicationContext(), Friends.class);
            startActivity(intent);

    }

}
