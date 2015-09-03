package ru.ID20.app.ui.widgets;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.ID20.app.R;

/**
 * Created  by  s.shevchenko  on  08.07.2015.
 */
public class MaterialDialog extends DialogFragment {

    public static Builder newBuilder(MaterialDialog dialog) {
        return dialog.new Builder();
    }

    private static String messageText;
    private static View.OnClickListener positiveButtonClickListener;
    private static View.OnClickListener negativeButtonClickListener;

    private CustomFontButton btnPositive, btnNegative;
    private CustomFontTextView tvMessage;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE,0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_material, container);
        btnPositive = (CustomFontButton)view.findViewById(R.id.btn_dialog_positive);
        btnNegative = (CustomFontButton) view.findViewById(R.id.btn_dialog_negative);
        if (negativeButtonClickListener != null){
            btnNegative.setVisibility(View.VISIBLE);
            btnNegative.setOnClickListener(negativeButtonClickListener);
        }
        btnPositive.setOnClickListener(positiveButtonClickListener);

        tvMessage = (CustomFontTextView)view.findViewById(R.id.tv_dialog_message);
        if (messageText != null){
            tvMessage.setText(messageText);
        }
        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        messageText = null;
        positiveButtonClickListener = null;
        negativeButtonClickListener = null;
    }

    public class Builder{

        private Builder(){

        }

        public Builder setMessageText(String message) {
            MaterialDialog.messageText = message;
            return this;
        }

        public Builder setPositiveButtonClickListener(View.OnClickListener listener){
            MaterialDialog.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButtonClickListener(View.OnClickListener listener) {
            MaterialDialog.negativeButtonClickListener = listener;
            return this;
        }

        public MaterialDialog build(){
            return MaterialDialog.this;
        }
    }
}
