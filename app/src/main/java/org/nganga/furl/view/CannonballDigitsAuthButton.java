
package org.nganga.furl.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.digits.sdk.android.DigitsAuthButton;

import io.fabric.samples.cannonball.App;
import io.fabric.samples.cannonball.R;

public class CannonballDigitsAuthButton extends DigitsAuthButton {
    public CannonballDigitsAuthButton(Context context) {
        super(context);
        init();
    }

    public CannonballDigitsAuthButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    public CannonballDigitsAuthButton(Context context, AttributeSet attrs, int defStyle) {
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
        setTypeface(App.getInstance().getTypeface());
    }
}
