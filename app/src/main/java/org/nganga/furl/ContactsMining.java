package org.nganga.furl;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by mansa on 10/8/15.
 */
public class ContactsMining extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

       /* Cursor contacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (contacts.moveToNext()){
            int contactsIndex = contacts.getColumnIndex(ContactsContract.PhoneLookup.NUMBER);
            String numbers = contacts.getString(contactsIndex);
            Log.d("Numbers", numbers);
        }*/

        setUpViews();
    }

    private void setUpViews() {
        setUpCount();
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
}



