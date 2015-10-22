package org.nganga.furl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.nganga.furl.custom.CustomActivity;
import org.nganga.furl.models.Conversation;
import org.nganga.furl.utils.Const;
import org.nganga.furl.view.AvenirTextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

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
        if (v.getId() == R.id.btnSend)
        {
           // sendMessage();
        }

    }

}
