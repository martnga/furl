package org.nganga.furl;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


 /*This holds all the users furls*/

public class FurlMain extends AppCompatActivity {



    final ParseUser currentUser = ParseUser.getCurrentUser();

    GPSTracker gps;

    /** The Chat list. */
    private ArrayList<ParseUser> uList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.furl);
        setUpViews();
        setUpLocation();
        loadUserList();
    }

    private void setUpViews() {
        FetchContacts();
        setStrangers();
        setAccountSettings();
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

    private void setStrangers() {
        final ImageView popular = (ImageView) findViewById(R.id.popular);
        popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crashlytics.log("FurlMain: clicked strangers button");
                Answers.getInstance().logCustom(new CustomEvent("clicked strangers"));
                Intent intent = new Intent(getApplicationContext(), Strangers.class);
                startActivity(intent);
            }
        });
    }

    private void setAccountSettings() {
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




    public void setUpLocation() {

        gps = new GPSTracker(FurlMain.this);

        if(gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            ParseGeoPoint userLocation = new ParseGeoPoint(latitude, longitude);

            currentUser.put("location", userLocation);
            currentUser.saveInBackground();
        } else {
            gps.showSettingsAlert();
        }
    }






    /* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
    @Override
    protected void onResume()
    {
        super.onResume();
        loadUserList();

    }


    /**
     * Load list of users.
     */
    private void loadUserList() {
        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_loading));
        ParseGeoPoint userLocation = (ParseGeoPoint) currentUser.get("location");
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", currentUser.getUsername());
        query.whereNear("location", userLocation);
        query.setLimit(25);
        query.findInBackground(new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> li, ParseException e) {
                dia.dismiss();
                if (li != null) {
                    if (li.size() == 0)
                        Toast.makeText(getApplicationContext(),
                                R.string.msg_no_user_found,
                                Toast.LENGTH_SHORT).show();

                    uList = new ArrayList<ParseUser>(li);
                    ListView list = (ListView) findViewById(R.id.furls_list);
                    list.setAdapter(new UserAdapter());
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0,
                                                View arg1, int pos, long arg3) {

                            // This is starts specific chat
                            /*startActivity(new Intent(UserList.this,
                                    Chat.class).putExtra(
                                    Const.EXTRA_DATA, uList.get(pos)
                                            .getUsername()));*/
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FurlMain.this);
                    builder.setMessage(e.getMessage());
                    builder.setTitle("Sorry Mate");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //to close the dialog
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog  dialog = builder.create();
                    dialog.show();
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * The Class UserAdapter is the adapter class for User ListView. This
     * adapter shows the user name and it's only online status for each item.
     */
    private class UserAdapter extends BaseAdapter
    {

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount()
        {
            return uList.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public ParseUser getItem(int arg0)
        {
            return uList.get(arg0);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int arg0)
        {
            return arg0;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int pos, View v, ViewGroup arg2)
        {
            if (v == null)
                v = getLayoutInflater().inflate(R.layout.chat_item, null);

            ParseUser c = getItem(pos);
            TextView lbl = (TextView) v;
            lbl.setText(c.getUsername());
            lbl.setCompoundDrawablesWithIntrinsicBounds(
                   R.drawable.furl_32, 0,  c.getBoolean("online") ? R.drawable.far
                            : R.drawable.close, 0);

            return v;
        }

    }


}
