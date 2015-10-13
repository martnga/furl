package org.nganga.furl;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Environment;
import android.preference.PreferenceManager;

import java.io.File;

i
/**
 * Created by nganga on 9/24/15.
 */
public class MyApp extends Application {


    public final static String CRASHLYTICS_KEY_THEME = "theme";
    public final static String CRASHLYTICS_KEY_SESSION_ACTIVATED = "session_activated";
    public final static String CRASHLYTICS_KEY_SEARCH_COUNT = "last_twitter_search_result_count";
    public final static String CRASHLYTICS_KEY_COUNTDOWN = "countdown_timer_remaining_sec";
    public final static String CRASHLYTICS_KEY_WORDBANK_COUNT = "word_bank_count_loaded";
    public final static String CRASHLYTICS_KEY_POEM_TEXT = "saving_poem_text";
    public final static String CRASHLYTICS_KEY_POEM_IMAGE = "saving_poem_image";
    public final static String CRASHLYTICS_KEY_CRASHES = "are_crashes_enabled";

    private static MyApp singleton;
    private TwitterAuthConfig authConfig;
    private Typeface avenirFont;

    public static MyApp getInstance() {
        return singleton;
    }

    return false;
    }

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "0L3nSKwwSjc36Hw5qjwOds4fV";
    private static final String TWITTER_SECRET = "0MJsGXhuRBGe3T6kMBlBUfyu5e5FgiISfnZNOS9h5UxkGRgAgw";




    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits(), new Crashlytics());

        Crashlytics.setBool(CRASHLYTICS_KEY_CRASHES, areCrashesEnabled());

// Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "Fsq5BW0P93coLN0x8jKy4NTWksDfGsVRfofK5Z4W", "Y3GenvClzLdq4WrLgegpxrUjjx2Fv3r2bMHbCMgr");
    }



   private void extractAvenir() {
        avenirFont = Typeface.createFromAsset(getAssets(), "fonts/Avenir.ttc");
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
