package com.wzn.libaray.utils.toast;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.wzn.libaray.utils.CommonUtils;


/**
 * Created by luona on 16/7/21.
 */
public class CustomToast {

    private static Handler mHandler = new Handler();

    private WindowManager mWindowManager;

    private long mDurationMillis;

    private View mView;

    private WindowManager.LayoutParams mParams;

    private Context mContext;

    public static CustomToast makeText(Context context, String text, long duration) {
        return new CustomToast(context)
                .setText(text)
                .setDuration(duration)
                .setGravity(Gravity.BOTTOM, 0, CommonUtils.dp2px(context,60));
    }

    /**
     * 参照Toast源码TN()写
     *
     * @param context
     */
    private CustomToast(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.windowAnimations = android.R.style.Animation_Toast;
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParams.setTitle("Toast");
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        // 默认显示在底部
        mParams.gravity = Gravity.BOTTOM;
    }

    /**
     * Set the location at which the notification should appear on the screen.
     *
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public CustomToast setGravity(int gravity, int xOffset, int yOffset) {
        // We can resolve the Gravity here by using the Locale for getting
        // the layout direction
        final int finalGravity;
        if (Build.VERSION.SDK_INT >= 17) {
            final Configuration config = mView.getContext().getResources().getConfiguration();
            finalGravity = Gravity.getAbsoluteGravity(gravity, config.getLayoutDirection());
        } else {
            finalGravity = gravity;
        }
        mParams.gravity = finalGravity;
        if ((finalGravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
            mParams.horizontalWeight = 1.0f;
        }
        if ((finalGravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
            mParams.verticalWeight = 1.0f;
        }
        mParams.y = yOffset;
        mParams.x = xOffset;
        return this;
    }

    public CustomToast setDuration(long durationMillis) {
        if (durationMillis < 0) {
            mDurationMillis = 0;
        }
        if (durationMillis == Toast.LENGTH_SHORT) {
            mDurationMillis = 2000;
        } else if (durationMillis == Toast.LENGTH_LONG) {
            mDurationMillis = 3500;
        } else {
            mDurationMillis = durationMillis;
        }
        return this;
    }

    /**
     * 不能和{@link #setText(String)}一起使用，要么{@link #setView(View)} 要么{@link #setView(View)}
     *
     * @param view 传入view
     * @return 自身对象
     */
    public CustomToast setView(View view) {
        mView = view;
        return this;
    }

    public CustomToast setMargin(float horizontalMargin, float verticalMargin) {
        mParams.horizontalMargin = horizontalMargin;
        mParams.verticalMargin = verticalMargin;
        return this;
    }

    /**
     * 不能和{@link #setView(View)}一起使用，要么{@link #setView(View)} 要么{@link #setView(View)}
     *
     * @param text 字符串
     * @return 自身对象
     */
    public CustomToast setText(String text) {
        // 模拟Toast的布局文件 com.android.internal.R.layout.transient_notification
        // 虽然可以手动用java写，但是不同厂商系统，这个布局的设置好像是不同的，因此我们自己获取原生Toast的view进行配置
        if (mView == null) {
            mView = Toast.makeText(mContext, text, Toast.LENGTH_SHORT).getView();
            setView(mView);
        }
        TextView tv = (TextView) mView.findViewById(android.R.id.message);
        tv.setText(text);
        return this;
    }

    public void show() {
        mHandler.post(mShow);
        mHandler.postDelayed(mHide, mDurationMillis);
    }

    public void cancel() {
        mHandler.post(mHide);
    }

    private void handleShow() {
        if (mView != null) {
            if (mView.getParent() != null) {
                mWindowManager.removeViewImmediate(mView);
            }
            mWindowManager.addView(mView, mParams);
        }
    }

    private void handleHide() {
        if (mView != null) {
            if (mView.getParent() != null) {
                mWindowManager.removeViewImmediate(mView);
            }
            mView = null;
        }
    }

    private final Runnable mShow = new Runnable() {
        @Override
        public void run() {
            handleShow();
        }
    };

    private final Runnable mHide = new Runnable() {
        @Override
        public void run() {
            handleHide();
        }
    };

}
