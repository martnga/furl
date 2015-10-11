package org.nganga.sesame;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;


/**
 * Created by nganga on 9/24/15.
 */
public class MyApp extends Application {

    private static MyApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

// Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "Fsq5BW0P93coLN0x8jKy4NTWksDfGsVRfofK5Z4W", "Y3GenvClzLdq4WrLgegpxrUjjx2Fv3r2bMHbCMgr");
    }




}
