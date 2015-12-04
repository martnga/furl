package org.nganga.furl.communicationInfrastructure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;

import org.nganga.furl.R;
import org.nganga.furl.layoutCustomisation.CustomActivity;
import org.nganga.furl.view.AvenirTextView;

/**
 * Created by mansa on 10/14/15.
 */
public class Chat extends CustomActivity {

   /* UsersName               */

    private AvenirTextView mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

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
        Crashlytics.log("Conversations: getting back to FurlMain");
        finish();
            final Intent intent = new Intent(getApplicationContext(), FurlMain.class);
            startActivity(intent);

    }


    /* (non-Javadoc)
 * @see android.support.v4.app.FragmentActivity#onResume()
 */
    @Override
    protected void onResume()
    {
        super.onResume();

    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onPause()
     */
    @Override
    protected void onPause()
    {
        super.onPause();

    }

    /* (non-Javadoc)
     * @see com.socialshare.custom.CustomFragment#onClick(android.view.View)
     */
    @Override
    public void onClick(View v)
    {
        super.onClick(v);
       /* if (v.getId() == R.id.btnSend)
        {
           // sendMessage();
        }*/

    }

}
