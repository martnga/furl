package org.nganga.unifurl;

import android.app.Application;

import com.parse.Parse;
import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;


/**
 * Created by nganga on 9/24/15.
 */
public class MyApp extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "0L3nSKwwSjc36Hw5qjwOds4fV";
    private static final String TWITTER_SECRET = "0MJsGXhuRBGe3T6kMBlBUfyu5e5FgiISfnZNOS9h5UxkGRgAgw";




    private static MyApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());

// Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "Fsq5BW0P93coLN0x8jKy4NTWksDfGsVRfofK5Z4W", "Y3GenvClzLdq4WrLgegpxrUjjx2Fv3r2bMHbCMgr");
    }




}
