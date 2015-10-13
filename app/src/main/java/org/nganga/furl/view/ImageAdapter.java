
package org.nganga.furl.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import org.nganga.furl.R;
import org.nganga.furl.model.Theme;

public final class ImageAdapter extends PagerAdapter {
    private final WeakReference<Context> context;
    private final Theme theme;

    public ImageAdapter(Context ctx, Theme theme) {
        this.theme = theme;
        this.context = new WeakReference<Context>(ctx);

    }

    @Override
    public int getCount() {
        return theme.getImageList().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        if (context.get() != null) {
            final int drawableId = theme.getImageList().get(position + 1);
            final ImageView view = new ImageView(context.get());
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            view.setContentDescription(context.get().getResources().getString(R.string
                    .content_desc_poempic));
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setAdjustViewBounds(true);
            view.setTag(drawableId);
            container.addView(view, 0);
            view.post(new Runnable() {
                @Override
                public void run() {
                    ImageLoader.getImageLoader().load(drawableId, view);
                }
            });
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
        object = null;
    }
}
