package org.nganga.furl.communicationInfrastructure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.nganga.furl.R;

import java.util.ArrayList;
import java.util.List;


public class Strangers extends Activity{

    /** The Chat list. */
    private ArrayList<ParseUser> uList;

    /** The user. */
    public static ParseUser user = ParseUser.getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.strangers);

        setUpViews();
        loadUserList();
    }

    private void setUpViews() {
        setUpBack();
        setUpRequests();

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

    private void setUpRequests() {
        // go back if clicked
        final ImageView backButton = (ImageView) findViewById(R.id.requests_icon);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Requests.class);
                startActivity(intent);

            }
        });
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
    private void loadUserList()
    {
        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_loading));
        ParseGeoPoint userLocation = (ParseGeoPoint) user.get("location");
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", user.getUsername());
        query.whereNear("location", userLocation);
        query.setLimit(25);
        query.findInBackground(new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> li, ParseException e) {
                dia.dismiss();
                if (li != null) {
                    if (li.size() == 0)
                        Toast.makeText(Strangers.this,
                                R.string.msg_no_user_found,
                                Toast.LENGTH_SHORT).show();

                    uList = new ArrayList<ParseUser>(li);
                    ListView list = (ListView) findViewById(R.id.friends_list);
                    list.setAdapter(new UserAdapter());
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0,
                                                View arg1, int pos, long arg3) {
                                    /*startActivity(new Intent(Strangers.this,
                                            Chat.class).putExtra(
                                            Const.EXTRA_DATA, uList.get(pos)
                                                    .getUsername()));*/
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Strangers.this);
                    builder.setMessage(e.getMessage());
                    builder.setTitle("Sorry Mate");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //to close the dialog
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
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
                    R.drawable.user, 0, R.drawable.checked_user, 0);

            return v;
        }

    }


}

