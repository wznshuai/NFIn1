package com.wzn.libaray.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzn.libaray.utils.Logger;
import com.wzn.libaray.utils.StatusBarUtil;


/**
 * Created by Wind_Fantasy on 15/3/26.
 */
public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    public final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, TAG + " onCreate");
        initData(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d(TAG, TAG + "  onResume");
    }

    protected void onResume(boolean onlyCallSuper) {
        if (onlyCallSuper)
            super.onResume();
        else
            onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d(TAG, TAG + "  onPause");
    }

    protected void onPause(boolean onlyCallSuper) {
        if (onlyCallSuper)
            super.onPause();
        else
            onPause();
    }

    protected void onStop(boolean onlyCallSuper) {
        if (onlyCallSuper)
            super.onStop();
        else
            onStop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(TAG, TAG + "  onStop");
    }

    @Override
    protected void onDestroy() {
        cleanData();
        super.onDestroy();
        Logger.d(TAG, TAG + "  onDestroy");
    }

    protected void onDestroy(boolean onlyCallSuper) {
        if (onlyCallSuper)
            super.onDestroy();
        else
            onDestroy();
    }


    protected void initData(@Nullable Bundle savedInstanceState) {
    }

    protected void findViews() {
    }

    protected void initViews() {
    }




    /**
     * @see StatusBarUtil#setColor(Activity, int)
     * @deprecated
     */
    @TargetApi(19)
    public void setStatusBarTintColorResource(@ColorRes int colorId) {
        setStatusBarTintColor(ContextCompat.getColor(this, colorId));
    }

    /**
     * @see StatusBarUtil#setColor(Activity, int)
     * @deprecated
     */
    @TargetApi(19)
    public void setStatusBarTintColor(@ColorInt int color) {
        StatusBarUtil.setColor(this, color);
    }

    private void resetStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setDefaultColor(this);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        resetStatusBar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        resetStatusBar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        resetStatusBar();
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        findViews();
        initViews();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        Logger.d(TAG, TAG + "  onPostResume");
    }

    protected void onPostResume(boolean onlyCallSuper) {
        if (onlyCallSuper)
            super.onPostResume();
        else
            onPostResume();
    }

    protected <T extends View> T myFindViewById(@IdRes int id) {
        return (T) findViewById(id);
    }


    /**
     * 在onDestroy中被回调
     */
    public void cleanData() {

    }

    protected void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int resId) {
        Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
    }


    /**
     * 是否可以继续执行
     *
     * @return
     */
    public boolean canGoon() {
        return !isFinishing();
    }

    // variable to track event time
    private long mLastClickTime = 0;

    //View.OnClickListener.onClick method defination

    @Override
    final public void onClick(View v) {
        // Preventing multiple clicks, using threshold of 1 second
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();

        onViewClick(v);
    }

    public void onViewClick(View v) {

    }

    @Override
    final public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Preventing multiple clicks, using threshold of 1 second
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();

        onItemViewClick(parent, view, position, id);
    }

    public void onItemViewClick(AdapterView<?> parent, View view, int position, long id) {
    }

}
