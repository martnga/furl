package org.nganga.furl;


import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


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

       final String phone_number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("phoneNumber", phone_number);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    Intent bucket = new Intent(ContactsMining.this, Map.class);

                    Bundle b = new Bundle();
                    b.putString("phonenumber", phone_number);
                    bucket.putExtras(b);
                    startActivity(bucket);
                } else {
                    // Something went wrong.

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContactsMining.this);
                    builder.setMessage("Invite " + phone_number + " to Furl." );
                    builder.setTitle(R.string.send_invitation);
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            //to close the dialog
                            dialogInterface.dismiss();
                        }
                    });


                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //to close the dialog
                            dialogInterface.dismiss();
                        }
                    });


                    AlertDialog  dialog = builder.create();
                    dialog.show();
                }
            }
        });



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



