package ru.ID20.app.ui.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.ImageView;

import ru.ID20.app.R;
import ru.ID20.app.tools.Tools;

/**
 * Created  by  s.shevchenko  on  15.07.2015.
 */
public class MyActionView extends FrameLayout {

    private ImageView imgItem;
    private CustomFontTextView tvItemsCount;

    public MyActionView(Context context) {
        super(context);
    }

    public MyActionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imgItem = (ImageView) findViewById(R.id.img_ab_button_logo);
        tvItemsCount = (CustomFontTextView) findViewById(R.id.tv_new_items_count);

        int actionBarSize = Tools.getActionBarHeight(getContext());
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)tvItemsCount.getLayoutParams();
        params.height = (int)(actionBarSize * 0.3);
        tvItemsCount.setLayoutParams(params);
        tvItemsCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(actionBarSize * 0.2));
    }

    public void setImage(Drawable buttonImage){
        imgItem.setBackgroundDrawable(buttonImage);
    }

    public void showNotification(int newItemsCount){
        tvItemsCount.setText(String.valueOf(newItemsCount));
    }
}
