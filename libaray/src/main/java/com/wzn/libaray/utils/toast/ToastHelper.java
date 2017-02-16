package com.wzn.libaray.utils.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by speakJ on 15/3/30.
 */
public class ToastHelper {
    private static ToastHelper mToastHelper;
    private Context mContext;
    private CustomToast mToast;

    public static void init(Context context) {
        mToastHelper = new ToastHelper(context);
    }

    public static ToastHelper getInstance() {
        if (null == mToastHelper)
            throw new NullPointerException("请先调用init方法初始化");
        else
            return mToastHelper;
    }

    private ToastHelper() {
    }

    private ToastHelper(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * 可被覆盖的toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        if (mContext == null)
            return;
        if (mToast == null) {
            mToast = CustomToast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public void showToast(int resId) {
        showToast(mContext.getString(resId));
    }

    public static void release() {
        if (null != mToastHelper)
            mToastHelper.mContext = null;
        mToastHelper = null;
    }
}
