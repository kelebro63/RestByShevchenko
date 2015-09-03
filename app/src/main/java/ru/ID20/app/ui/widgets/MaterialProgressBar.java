package ru.ID20.app.ui.widgets;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import ru.ID20.app.R;

/**
 * Created  by  s.shevchenko  on  08.07.2015.
 */
public class MaterialProgressBar extends ProgressDialog {

    public MaterialProgressBar(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onCreate(savedInstanceState);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.progress_dialog);
    }
}


