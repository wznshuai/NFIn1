package com.wzn.libaray.view.dialog;

/**
 * Created by Rona on 2017/4/14.
 */
public class AlertModel {
    public String mTitle;
    public String mNegativeButtonText;
    public String mPositiveButtonText;
    public String mMessage;

    public AlertModel(String title, String negativeButtonText, String positiveButtonText, String message) {
        this.mTitle = title;
        this.mNegativeButtonText = negativeButtonText;
        this.mPositiveButtonText = positiveButtonText;
        this.mMessage = message;
    }

    public interface OnAlertDialogClickListener {
        void onPositiveClick();
        void onNegativeClick();
    }


}
