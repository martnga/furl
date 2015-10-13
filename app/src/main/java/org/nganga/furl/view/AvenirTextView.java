
package org.nganga.furl.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import org.nganga.furl.MyApp;

public class AvenirTextView extends TextView {

    public AvenirTextView(Context context) {
        super(context);
        init();
    }

    public AvenirTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AvenirTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        if (isInEditMode()){
            return;
        }
        setTypeface(MyApp.getInstance().getTypeface());
    }
}
