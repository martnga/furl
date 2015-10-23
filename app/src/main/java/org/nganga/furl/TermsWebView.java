package org.nganga.furl;


        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.ImageView;

/**
 * Created by nganga on 9/17/15.
 */
public class TermsWebView extends Activity {

    String url = "https://opensource.org/licenses/MIT";

    protected void onCreate(Bundle savedInstanceState) {

        setUpBack();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_web_view);

        WebView browser = (WebView) findViewById(R.id.webview);
        browser.loadUrl(url);

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

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            url = "https://opensource.org/licenses/MIT";
            view.loadUrl(url);
            return true;
        }
    }


}
