
package org.nganga.furl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.nganga.furl.MyApp;
import org.nganga.furl.R;

public class FurlTwitterLoginButton extends TwitterLoginButton {
    public FurlTwitterLoginButton(Context context) {
        super(context);
        init();
    }

    public FurlTwitterLoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FurlTwitterLoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (isInEditMode()){
            return;
        }
        setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable
                .ic_signin_twitter), null, null, null);
        setBackgroundResource(R.drawable.sign_up_button);
        setTextSize(20);
        setPadding(30, 0, 10, 0);
        setTextColor(getResources().getColor(R.color.tw__blue_default));
        setTypeface(MyApp.getInstance().getTypeface());
    }
}
