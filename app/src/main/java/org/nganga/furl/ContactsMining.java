package org.nganga.furl;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;


/**
 * Created by mansa on 10/8/15.
 */
public class ContactsMining extends ListActivity  {

    ListView contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        Cursor contacts = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        startManagingCursor(contacts);
            String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone._ID};
            int[] to = {R.id.phone_name, R.id.phone_number};
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, R.layout.contact_row, contacts,from,to);
        contactList = (ListView)findViewById(R.id.contact_list);
        contactList.setAdapter(listAdapter);



        setUpViews();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);


        Cursor cursor = ((SimpleCursorAdapter)l.getAdapter()).getCursor();
        cursor.moveToPosition(position);

        String phone_number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));

        Intent bucket = new Intent(this, Map.class);

        Bundle b = new Bundle();
        b.putString("phonenumber", phone_number);
        bucket.putExtras(b);
        startActivity(bucket);

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



