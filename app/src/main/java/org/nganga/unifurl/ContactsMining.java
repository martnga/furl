package org.nganga.unifurl;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by mansa on 10/8/15.
 */
public class ContactsMining extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Cursor contacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (contacts.moveToNext()){
            int contactsIndex = contacts.getColumnIndex(ContactsContract.PhoneLookup.NUMBER);
            String numbers = contacts.getString(contactsIndex);
            Log.d("Numbers", numbers);
        }
    }
}



