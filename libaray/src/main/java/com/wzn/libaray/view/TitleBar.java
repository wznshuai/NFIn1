package com.wzn.libaray.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wzn.libaray.utils.CommonUtils;
import com.wzn.libaray.utils.StatusBarUtil;
import com.wzn.libaray.utils.ViewUtil;
import com.wzn.libaray.utils.device.DeviceInfo;

import java.util.ArrayList;


/**
 * Created by Wind_Fantasy on 16/3/16.
 */
public class TitleBar extends LinearLayout {

    private final String TAG = "TitleBar";

    private View mLineView, mLeftView;
    private RelativeLayout mTitleView;
    private AppCompatTextView mCenterTxt;

    private ArrayList<Integer> mRightChildIds, mLeftChildIds;
    private boolean isComputeStatusBarHeight;

    private int mTitleColor = Color.parseColor("#f07b3b");

    public TitleBar(Context context) {
        super(context);
        init();
    }


    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public void setIsComputeStatusBarHeight(boolean isComputeStatusBarHeight) {
        this.isComputeStatusBarHeight = isComputeStatusBarHeight;
        invalidate();
        setPadding(0, isComputeStatusBarHeight ? StatusBarUtil.getStatusBarHeight(getContext()) : 0, 0, 0);
    }

    public ArrayList<Integer> getLeftChildIds() {
        if (null == mLeftChildIds)
            mLeftChildIds = new ArrayList<>();
        return mLeftChildIds;
    }

    public ArrayList<Integer> getRightChildIds() {
        if (null == mRightChildIds)
            mRightChildIds = new ArrayList<>();
        return mRightChildIds;
    }

    public void addCustomView(int layoutId) {
        View view = LayoutInflater.from(getContext()).inflate(layoutId, this, false);
        resetTitleBar();
        removeAllViews();
        addView(view);
    }

    private float computeHeight() {
        float suggestHeight = DeviceInfo.getScreenSize(getContext()).height * 0.07f;
        Rect rect = new Rect();
        makeCenterTxt().getPaint().getTextBounds("哈", 0, 1, rect);
        int minMarginTop = CommonUtils.dp2px(getContext(), 8);
        return Math.max(suggestHeight, rect.height() + 2 * minMarginTop);
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setBackgroundColor(mTitleColor);
        setClickable(true);
        setId(ViewUtil.generateViewId());
        //初始化title栏按钮、标题所在layout
        mTitleView = new RelativeLayout(getContext());
        LayoutParams mTitleParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) computeHeight());
        mTitleParams.weight = 1;
        addView(mTitleView, mTitleParams);

        //初始化标题栏下方分割线
        mLineView = new View(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                CommonUtils.dp2px(getContext(), 0.5f));
        layoutParams.gravity = Gravity.CENTER;
        mLineView.setLayoutParams(layoutParams);
        mLineView.setBackgroundColor(Color.parseColor("#b2b2b2"));
        addView(mLineView);
    }


    public TitleBar setBackgroundByColor(@ColorInt int color) {
        setBackgroundColor(color);
        mTitleColor = color;
        return this;
    }

    public int getTitleColor() {
        return mTitleColor;
    }

    private int getImageViewWidthAndHeight() {
        return (int) computeHeight();
    }

    private AppCompatImageView makeTitleButton() {
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(getImageViewWidthAndHeight(),
                        getImageViewWidthAndHeight());
        AppCompatImageView imageButton = new AppCompatImageView(getContext());
        imageButton.setLayoutParams(layoutParams);
        imageButton.setBackgroundColor(Color.TRANSPARENT);
        imageButton.setPadding(getCommonPaddingLeftRight(), getCommonPaddingTopBottom(),
                getCommonPaddingLeftRight(), getCommonPaddingTopBottom());
        imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return imageButton;
    }

    private AppCompatTextView makeTitleTxt() {
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        AppCompatTextView titleView = new AppCompatTextView(getContext());
        titleView.setTextColor(Color.BLACK);
        titleView.setMaxLines(1);
        titleView.setEllipsize(TextUtils.TruncateAt.END);
        titleView.setGravity(Gravity.CENTER);
        titleView.setLayoutParams(layoutParams);
        return titleView;
    }

    private AppCompatTextView makeCenterTxt() {
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        AppCompatTextView titleView = new AppCompatTextView(getContext());
        titleView.setGravity(Gravity.CENTER);
        titleView.setTextColor(Color.WHITE);
        titleView.setLayoutParams(layoutParams);
        return titleView;
    }

    public AppCompatTextView getRightTxtView() {
        AppCompatTextView mRightTxt = makeTitleTxt();
        mRightTxt.setPadding(getCommonPaddingLeftRight(), 0, getCommonPaddingLeftRight(), 0);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRightTxt.getLayoutParams();
        int id = ViewUtil.generateViewId();
        mRightTxt.setId(id);
        int size = getRightChildIds().size();
        if (size > 0) {
            int lastId = getRightChildIds().get(size - 1);
            layoutParams.addRule(RelativeLayout.LEFT_OF, lastId);
        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        getRightChildIds().add(id);
        addView(mRightTxt, layoutParams);

        return mRightTxt;
    }

    private void addView(View view, RelativeLayout.LayoutParams layoutParams) {
        if (null != mTitleView)
            mTitleView.addView(view, layoutParams);
    }

    public AppCompatImageView getRightImgView() {

        AppCompatImageView mRightImg = makeTitleButton();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRightImg.getLayoutParams();
        int size = getRightChildIds().size();
        int id = ViewUtil.generateViewId();
        mRightImg.setId(id);
        if (size > 0) {
            int lastId = getRightChildIds().get(size - 1);
            layoutParams.addRule(RelativeLayout.LEFT_OF, lastId);
        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        getRightChildIds().add(id);
        addView(mRightImg, layoutParams);

        return mRightImg;
    }

    public int getCommonPaddingLeftRight() {
        return CommonUtils.dp2px(getContext(), 8);
    }

    public int getCommonPaddingTopBottom() {
        return CommonUtils.dp2px(getContext(), 3);
    }

    public AppCompatImageView getLeftImgView() {

        AppCompatImageView mLeftImg = makeTitleButton();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLeftImg.getLayoutParams();
        int size = getLeftChildIds().size();
        int id = ViewUtil.generateViewId();
        mLeftImg.setId(id);
        if (size > 0) {
            int lastId = getLeftChildIds().get(size - 1);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, lastId);
        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        getLeftChildIds().add(id);
        mTitleView.addView(mLeftImg, layoutParams);

        return mLeftImg;
    }

    public AppCompatTextView getCenterTitleView() {

        if (null == mCenterTxt) {//如果为null 则还没加入到TITLE栏
            mCenterTxt = makeCenterTxt();
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mCenterTxt.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mCenterTxt.setClickable(false);
            mCenterTxt.setGravity(Gravity.CENTER);
            mCenterTxt.setCompoundDrawablePadding(CommonUtils.dp2px(getContext(), 3));
            mTitleView.addView(mCenterTxt, layoutParams);
        }

        return mCenterTxt;
    }


    public AppCompatTextView getLeftTxtView() {

        AppCompatTextView mLeftTxt = makeTitleTxt();
        mLeftTxt.setPadding(getCommonPaddingLeftRight(), 0, getCommonPaddingLeftRight(), 0);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLeftTxt.getLayoutParams();
        int size = getLeftChildIds().size();
        int id = ViewUtil.generateViewId();
        mLeftTxt.setId(id);
        if (size > 0) {
            int lastId = getLeftChildIds().get(size - 1);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, lastId);
        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        getLeftChildIds().add(id);
        mTitleView.addView(mLeftTxt, layoutParams);

        return mLeftTxt;
    }


    public View getLeftView(int layoutId) {

        if (null == mLeftView) {
            mLeftView = LayoutInflater.from(getContext()).inflate(layoutId, this, false);

            if (ViewCompat.isPaddingRelative(mLeftView)) {
                ViewCompat.setPaddingRelative(mLeftView,
                        ViewCompat.getPaddingStart(mLeftView) == 0 ? getCommonPaddingLeftRight() : ViewCompat.getPaddingStart(mLeftView),
                        0,
                        ViewCompat.getPaddingEnd(mLeftView) == 0 ? getCommonPaddingLeftRight() : ViewCompat.getPaddingEnd(mLeftView),
                        0
                );
            } else {
                mLeftView.setPadding(mLeftView.getLeft() == 0 ? getCommonPaddingLeftRight() : mLeftView.getLeft(),
                        0,
                        mLeftView.getRight() == 0 ? getCommonPaddingLeftRight() : mLeftView.getPaddingRight(),
                        0
                );
            }

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mLeftView.getLayoutParams());
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            mTitleView.addView(mLeftView, layoutParams);
        }

        return mLeftView;
    }

    public View getDivideLine() {
        return mLineView;
    }

    public View getTitleView() {
        return mTitleView;
    }

    public TitleBar resetTitleBar() {
        mTitleView.removeAllViews();
        mLeftView = null;
        mCenterTxt = null;
        setVisibility(VISIBLE);
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        float maxSize = computeHeight();
        if (isComputeStatusBarHeight) {
            maxSize += StatusBarUtil.getStatusBarHeight(getContext());
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                /*(int) Math.min(height, maxSize)*/ (int) maxSize);
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

}
