package com.wzn.libaray.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Wind_Fantasy on 15/5/6.
 */
public class ViewUtil {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    @SuppressLint("NewApi")
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue))
                    return result;
            }
        } else
            return View.generateViewId();
    }

    public static void removeOnGlobalLayoutListener(View view,
                                                    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            view.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        else
            view.getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
    }

    public static int getColor(Context context, int id) {
        return ContextCompat.getColor(context, id);
    }

    public static Drawable getDrawable(Context context, int id) {
        return ContextCompat.getDrawable(context, id);
    }

    public static DisplayMetrics getScreenMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        window.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static void setBackound(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static void setTouchDelegate(final View view, final int expandTouchWidth) {
        final View parentView = (View) view.getParent();
        view.post(new Runnable() {
            @Override
            public void run() {
                final Rect rect = new Rect();
                view.getHitRect(rect);
                rect.top -= expandTouchWidth;
                rect.bottom += expandTouchWidth;
                rect.left -= expandTouchWidth;
                rect.right += expandTouchWidth;
                TouchDelegate touchDelegate = new TouchDelegate(rect, view);
                parentView.setTouchDelegate(touchDelegate);
            }
        });
    }

}
