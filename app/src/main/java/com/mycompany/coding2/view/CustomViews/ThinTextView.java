package com.mycompany.coding2.view.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class ThinTextView extends AppCompatTextView {
    public ThinTextView(Context context) {
        super(context);
        setFont();
    }

    public ThinTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public ThinTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        setTypeface(Typeface.createFromAsset
                (getContext().getAssets(), "fonts/RobotoSlab-Thin.ttf"));
    }
}
