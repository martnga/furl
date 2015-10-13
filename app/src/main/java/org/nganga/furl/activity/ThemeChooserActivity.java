

package org.nganga.furl.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.EventAttributes;
import com.twitter.sdk.android.Twitter;

import org.nganga.furl.App;
import org.nganga.furl.R;
import org.nganga.furl.model.Theme;
import org.nganga.furl.view.ThemeAdapter;

public class ThemeChooserActivity extends Activity {
    public static final String IS_NEW_POEM = "ThemeChooser.IS_NEW_POEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_chooser);
        setUpViews();
    }

    private void setUpViews() {

        setUpThemes();
    }



    private void setUpThemes() {
        final ListView view = (ListView) findViewById(R.id.theme_list);
        view.setAdapter(new ThemeAdapter(this, Theme.values()));
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Theme theme = Theme.values()[position];
                Crashlytics.log("ThemeChooser: clicked on Theme: " + theme.getDisplayName());
                Crashlytics.setString(App.CRASHLYTICS_KEY_THEME, theme.getDisplayName());
                Answers.getInstance().logEvent("clicked build poem", new EventAttributes().put("theme", theme.getDisplayName()));
                final Intent intent = new Intent(getBaseContext(), Furl.class);
                intent.putExtra(Furl.KEY_THEME, theme);
                startActivity(intent);
            }
        });
    }
}
