package ru.ID20.app.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import ru.ID20.app.R;
import ru.ID20.app.tools.TypefaceCache;

public class CustomFontButton extends AppCompatButton {

    public CustomFontButton(Context context) {
        super(context);
    }

    public CustomFontButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomFontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = null;
        try {
            a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomFontView);
            String customFont = a.getString(R.styleable.CustomFontView_typeface);
            if (customFont != null) {
                setCustomFont(ctx, "fonts/" + customFont);
            }
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf;
        try {
            tf = TypefaceCache.getInstance().getTypeface(asset, ctx);
        } catch (Exception e) {
            return false;
        }
        setTypeface(tf);
        return true;
    }
}
