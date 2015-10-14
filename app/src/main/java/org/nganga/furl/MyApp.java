package org.nganga.furl;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;



/**
 * Created by nganga on 9/24/15.
 */
public class MyApp extends Application {



    public final static String CRASHLYTICS_KEY_SESSION_ACTIVATED = "session_activated";
    public final static String CRASHLYTICS_KEY_CRASHES = "are_crashes_enabled";

    private static MyApp singleton;
    private TwitterAuthConfig authConfig;
    private Typeface avenirFont;



    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "0L3nSKwwSjc36Hw5qjwOds4fV";
    private static final String TWITTER_SECRET = "0MJsGXhuRBGe3T6kMBlBUfyu5e5FgiISfnZNOS9h5UxkGRgAgw";




    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Digits(), new Crashlytics(), new Twitter(authConfig));

        Crashlytics.setBool(CRASHLYTICS_KEY_CRASHES, areCrashesEnabled());

// Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "Fsq5BW0P93coLN0x8jKy4NTWksDfGsVRfofK5Z4W", "Y3GenvClzLdq4WrLgegpxrUjjx2Fv3r2bMHbCMgr");
    }


    public static MyApp getInstance() {
        return singleton;
    }

    public static Context getAppContext() {
        return singleton.getApplicationContext();
    }


   private void extractAvenir() {
        avenirFont = Typeface.createFromAsset(getAppContext().getAssets(), "fonts/OpenSans-Light.ttf");
    }

    public Typeface getTypeface() {
        if (avenirFont == null) {
            extractAvenir();
        }
        return avenirFont;
    }

    public boolean areCrashesEnabled() {
        SharedPreferences preferences;
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return preferences.getBoolean("are_crashes_enabled", false);
    }

    public void setCrashesStatus(boolean status) {
        SharedPreferences preferences;
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("are_crashes_enabled", status);
        editor.apply();
    }




}
