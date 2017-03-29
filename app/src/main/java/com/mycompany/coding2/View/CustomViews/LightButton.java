package com.mycompany.coding2.View.CustomViews;


import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


public class LightButton extends AppCompatButton {
    public LightButton(Context context) {
        super(context);
        setFont();
    }

    public LightButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public LightButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont() {
        setTypeface(Typeface.createFromAsset
                (getContext().getAssets(), "fonts/RobotoSlab-Light.ttf"));
    }
}
