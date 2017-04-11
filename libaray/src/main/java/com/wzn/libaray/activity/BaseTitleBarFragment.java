package com.wzn.libaray.activity;

import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wzn.libaray.R;
import com.wzn.libaray.utils.StatusBarUtil;
import com.wzn.libaray.view.TitleBar;


/**
 * Created by Wind_Fantasy on 16/4/8.
 */
public abstract class BaseTitleBarFragment extends BaseFragment {


    protected TitleBar titleBar;
    protected View mContentView;

    public void setTitleBar(TitleBar titleBar) {
        this.titleBar = titleBar;
    }

    @Override
    public void onResume() {
        super.onResume();
        initTitleBar();
    }

    protected View makeFinalContentView(View contentView) {
        mContentView = contentView;
        if (null == contentView.getBackground()) {
            contentView.setBackgroundResource(R.color.common_bg);
        }
        RelativeLayout relativeLayout = new RelativeLayout(getActivity());

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(layoutParams);

        titleBar = new TitleBar(getActivity());

        RelativeLayout.LayoutParams contentViewLayoutParams = new RelativeLayout.LayoutParams(contentView.getLayoutParams());
        contentViewLayoutParams.addRule(RelativeLayout.BELOW, getTitleBar().getId());
        relativeLayout.addView(contentView, contentViewLayoutParams);

        relativeLayout.addView(titleBar);


        return relativeLayout;
    }

    protected void setStatusBarTransparent() {
        StatusBarUtil.setTranslucent(getActivity());
        RelativeLayout.LayoutParams titleBarLayoutparams = (RelativeLayout.LayoutParams)getTitleBar().getLayoutParams();
        titleBarLayoutparams.topMargin = StatusBarUtil.getStatusBarHeight(getContext());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mContentView.getLayoutParams();
        if (layoutParams != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                layoutParams.removeRule(RelativeLayout.BELOW);
            } else {
                layoutParams.addRule(RelativeLayout.BELOW, 0);
            }
        }
    }

    protected void resetStatusBarTransparent() {
        StatusBarUtil.resetSatusBar(getActivity());
        RelativeLayout.LayoutParams titleBarLayoutparams = (RelativeLayout.LayoutParams)getTitleBar().getLayoutParams();
        titleBarLayoutparams.topMargin -= StatusBarUtil.getStatusBarHeight(getContext());
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mContentView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.addRule(RelativeLayout.BELOW,getTitleBar().getId());
        }
    }

    protected void initTitleBar() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        if (count > 0) {
//            setLeftTextViewDrawableLeft(R.drawable.left_arrow)
//                    .setLeftTextView("", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            onBackPressed();
//                        }
//                    });
        }

    }


    public TitleBar getTitleBar() {
        return titleBar;
    }

    public BaseTitleBarFragment resetTitleBar() {
        getTitleBar().resetTitleBar();
        return this;
    }

    public BaseTitleBarFragment setTitlebarColorResources(@ColorRes int id) {
        getTitleBar().setBackgroundResource(id);
        getTitleBar().removeView(getTitleBar().getDivideLine());
        return this;
    }

    public BaseTitleBarFragment setTitlebarColor(@ColorInt int color) {
        getTitleBar().setBackgroundByColor(color);
        getTitleBar().removeView(getTitleBar().getDivideLine());
        return this;
    }

    public BaseTitleBarFragment setTitle(int titleId) {
        getTitleBar().getCenterTitleView().setText(titleId);
        return this;
    }

    public BaseTitleBarFragment setTitle(CharSequence title) {
        getTitleBar().getCenterTitleView().setText(title);
        return this;
    }

    public BaseTitleBarFragment setTitleColors(int id) {
        getTitleBar().getCenterTitleView().setTextColor(id);
        return this;
    }


    public BaseTitleBarFragment setLeftTextView(int resId, View.OnClickListener onClickListener) {
        return setLeftTextView(getString(resId), onClickListener);
    }

    public BaseTitleBarFragment setLeftTextView(String str) {
        return setLeftTextView(str, null);
    }

    public BaseTitleBarFragment setLeftTextView(String str, View.OnClickListener onClickListener) {
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


    public BaseTitleBarFragment setRightTextView(int resId) {
        return setRightTextView(getString(resId));
    }

    public BaseTitleBarFragment setRightTextView(int resId, View.OnClickListener onClickListener) {
        return setRightTextView(getString(resId), onClickListener);
    }


    public BaseTitleBarFragment setRightTextView(CharSequence str) {
        return setRightTextView(str, null);
    }

    public BaseTitleBarFragment setRightTextView(CharSequence str, View.OnClickListener onClickListener) {
        return setRightTextView(-1, str, onClickListener);
    }

    public BaseTitleBarFragment setRightTextView(@ColorInt int textColor, @StringRes int resStr, View.OnClickListener onClickListener) {
        return setRightTextView(textColor, getContext().getText(resStr), onClickListener);
    }

    public BaseTitleBarFragment setRightTextView(@ColorInt int textColor, CharSequence str, View.OnClickListener onClickListener) {
        TextView textView = getRightNewTextView();
        textView.setText(str);
        textView.setOnClickListener(onClickListener);
        textView.setVisibility(View.VISIBLE);
        if (textColor != -1)
            textView.setTextColor(textColor);
        return this;
    }

    public BaseTitleBarFragment setRightImage(int resId) {
        return setRightImage(resId, null);
    }

    public BaseTitleBarFragment setRightImage(int resId, View.OnClickListener onClickListener) {
        ImageView imageView = getRightNewImageView();
        imageView.setImageResource(resId);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(onClickListener);
        return this;
    }
}
