
package org.nganga.furl.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.digits.sdk.android.DigitsAuthButton;

import org.nganga.furl.MyApp;
import org.nganga.furl.R;

public class FurlDigitsAuthButton extends DigitsAuthButton {
    public FurlDigitsAuthButton(Context context) {
        super(context);
        init();
    }

    public FurlDigitsAuthButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    public FurlDigitsAuthButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private void init() {
        if (isInEditMode()){
            return;
        }
        final Drawable phone = getResources().getDrawable(R.drawable.ic_signin_phone);
        phone.setColorFilter(getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
        setCompoundDrawablesWithIntrinsicBounds(phone, null, null, null);
        setBackgroundResource(R.drawable.digits_button);
        setTextSize(20);
        setTextColor(getResources().getColor(R.color.green));
        setTypeface(MyApp.getInstance().getTypeface());
    }
}
