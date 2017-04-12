package com.wzn.libaray.sample.base;

import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shudu.hypermoney.base.BaseActivity;
import com.wzn.libaray.utils.Logger;
import com.wzn.libaray.view.TitleBar;


/**
 * Created by Wind_Fantasy on 15/4/2.
 */
public abstract class BaseTitleBarActivity extends BaseActivity {

    protected TitleBar titleBar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(makeFinalContentView(layoutResID));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(makeFinalContentView(view));
    }

    protected View makeFinalContentView(int layoutResID) {
        return makeFinalContentView(getLayoutInflater().inflate(layoutResID, null, false));
    }

    protected View makeFinalContentView(View contentView) {
        if(null == contentView.getBackground()){
            contentView.setBackgroundResource(com.wzn.libaray.R.color.common_bg);
        }
        LinearLayout linearLayout = new LinearLayout(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            linearLayout.setFitsSystemWindows(true);
        }
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        titleBar = new TitleBar(this);
        linearLayout.addView(titleBar);

        LinearLayout.LayoutParams contentViewLayoutParams = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        if (null == contentViewLayoutParams)
            contentViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.addView(contentView, contentViewLayoutParams);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);

        return linearLayout;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Logger.d(getTAG(), "onAttachFragment");
    }

    final public TitleBar getTitleBar() {
        return titleBar;
    }


    public BaseTitleBarActivity resetTitleBar() {
        getTitleBar().resetTitleBar();
        return this;
    }

    public BaseTitleBarActivity setTitlebarColorResources(@ColorRes int id) {
        getTitleBar().setBackgroundResource(id);
        getTitleBar().removeView(getTitleBar().getDivideLine());
        return this;
    }

    public BaseTitleBarActivity setTitlebarColor(@ColorInt int color) {
        getTitleBar().setBackgroundByColor(color);
        getTitleBar().removeView(getTitleBar().getDivideLine());
        return this;
    }


    public BaseTitleBarActivity setTitleColors(int id) {
        getTitleBar().getCenterTitleView().setTextColor(id);
        return this;
    }


    public BaseTitleBarActivity setLeftTextView(int resId, View.OnClickListener onClickListener) {
        return setLeftTextView(getString(resId), onClickListener);
    }

    public BaseTitleBarActivity setLeftTextView(String str) {
        return setLeftTextView(str, null);
    }

    public BaseTitleBarActivity setLeftTextView(String str, View.OnClickListener onClickListener) {
        TextView textView = getLeftNewTextView();
        textView.setText(str);
        textView.setOnClickListener(onClickListener);
        textView.setVisibility(View.VISIBLE);
        return this;
    }

    public <T extends View> T getLeftView() {
        return getLeftView(0);
    }

    public <T extends View> T getLeftView(int index) {
        try {
            return (T)getTitleBar().findViewById(getTitleBar().getLeftChildIds().get(index));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回一个全新的view,并按顺数add到titlebar左侧
     *
     * @return
     */

    public TextView getLeftNewTextView() {
        return getTitleBar().getLeftTxtView();
    }

    /**
     * 返回一个全新的view,并按顺数add到titlebar左侧
     *
     * @return
     */
    public ImageView getLeftNewImageView() {
        return getTitleBar().getLeftImgView();
    }


    /**
     * 返回一个全新的view,并按顺数add到titlebar右侧
     *
     * @return
     */
    public TextView getRightNewTextView() {
        return getTitleBar().getRightTxtView();
    }

    /**
     * 返回一个全新的view,并按顺数add到titlebar右侧
     *
     * @return
     */
    public ImageView getRightNewImageView() {
        return getTitleBar().getRightImgView();
    }

    public <T extends View> T getRightView() {
        return getRightView(0);
    }

    public <T extends View> T getRightView(int index) {
        try {
            return (T)getTitleBar().findViewById(getTitleBar().getRightChildIds().get(index));
        } catch (Exception e) {
            return null;
        }
    }


    public BaseTitleBarActivity setRightTextView(int resId) {
        return setRightTextView(getString(resId));
    }

    public BaseTitleBarActivity setRightTextView(int resId, View.OnClickListener onClickListener) {
        return setRightTextView(getString(resId), onClickListener);
    }


    public BaseTitleBarActivity setRightTextView(CharSequence str) {
        return setRightTextView(str, null);
    }

    public BaseTitleBarActivity setRightTextView(CharSequence str, View.OnClickListener onClickListener) {
        return setRightTextView(-1, str, onClickListener);
    }

    public BaseTitleBarActivity setRightTextView(@ColorInt int textColor, @StringRes int resStr, View.OnClickListener onClickListener) {
        return setRightTextView(textColor, getText(resStr), onClickListener);
    }

    public BaseTitleBarActivity setRightTextView(@ColorInt int textColor, CharSequence str, View.OnClickListener onClickListener) {
        TextView textView = getRightNewTextView();
        textView.setText(str);
        textView.setOnClickListener(onClickListener);
        textView.setVisibility(View.VISIBLE);
        if (textColor != -1)
            textView.setTextColor(textColor);
        return this;
    }

    public BaseTitleBarActivity setRightImage(int resId) {
        return setRightImage(resId, null);
    }

    public BaseTitleBarActivity setRightImage(int resId, View.OnClickListener onClickListener) {
        ImageView imageView = getRightNewImageView();
        imageView.setImageResource(resId);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(onClickListener);
        return this;
    }

    public BaseTitleBarActivity setTitleMine(CharSequence title) {
        getTitleBar().getCenterTitleView().setText(title);
        return this;
    }

    public BaseTitleBarActivity setTitleMine(int titleId) {
        getTitleBar().getCenterTitleView().setText(titleId);
        return this;
    }

    public View getLeftTitileBarViewStub(int layoutId) {
        return getTitleBar().getLeftView(layoutId);
    }

    public abstract void initTitleBar();

    @Override
    protected void onResume() {
        super.onResume();
        initTitleBar();
    }

}
