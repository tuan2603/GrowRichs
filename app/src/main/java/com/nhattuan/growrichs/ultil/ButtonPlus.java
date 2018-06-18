package com.nhattuan.growrichs.ultil;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class ButtonPlus extends android.support.v7.widget.AppCompatButton {

    public ButtonPlus(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public ButtonPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public ButtonPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/oswald_bold.ttf", context);
        setTypeface(customFont);
    }
}