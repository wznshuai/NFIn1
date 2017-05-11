package com.wzn.libaray.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wzn.libaray.R;
import com.wzn.libaray.utils.StatusBarUtil;
import com.wzn.libaray.utils.ViewUtil;
import com.wzn.libaray.utils.device.DeviceInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.wzn.libaray.utils.CommonUtils.dp2px;


/**
 * Created by Wind_Fantasy on 16/3/16.
 */
public class TitleBar extends LinearLayout {

    private final String TAG = "TitleBar";

    private View mLineView, mLeftView;
    private RelativeLayout mTitleView;
    private TextView mCenterTxt;
    private RadioGroup mRadioGroup;
    private List<RadioData> mRadioDatas;

    private ArrayList<Integer> mRightChildIds, mLeftChildIds;

    private int mTitleColor = Color.WHITE;
    private boolean isComputeStatusBarHeight;


    private int mTitleTxtColor = Color.BLACK;



    public TitleBar(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }


    public TitleBar setTitleTxtColor(int titleTxtColor) {
        this.mTitleTxtColor = titleTxtColor;
        return this;
    }

    public TitleBar setIsComputeStatusBarHeight(boolean isComputeStatusBarHeight) {
        this.isComputeStatusBarHeight = isComputeStatusBarHeight;
        invalidate();
        setPadding(0, isComputeStatusBarHeight ? StatusBarUtil.getStatusBarHeight(getContext()) : 0, 0, 0);
        return this;
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

    public TitleBar addCustomView(int layoutId) {
        View view = LayoutInflater.from(getContext()).inflate(layoutId, this, false);
        resetTitleBar();
        removeAllViews();
        addView(view);
        return this;
    }

    private float computeHeight() {
        float suggestHeight = DeviceInfo.getScreenSize(getContext()).height * 0.07f;
        Rect rect = new Rect();
        makeCenterTxt().getPaint().getTextBounds("哈", 0, 1, rect);
        int minMarginTop = dp2px(getContext(), 8);
        return Math.max(suggestHeight, rect.height() + 2 * minMarginTop);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        int dividerLineVisibility = View.VISIBLE;
        if(null != attrs){
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, defStyleRes);
            dividerLineVisibility = a.getInt(R.styleable.TitleBar_dividing_line_visibility, 0);
        }
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setBackgroundColor(mTitleColor);
        setClickable(true);
        if(getId() == -1)
            setId(ViewUtil.generateViewId());

        //初始化title栏按钮、标题所在layout
        mTitleView = new RelativeLayout(getContext());
        LayoutParams mTitleParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) computeHeight());
        mTitleParams.weight = 1;
        addView(mTitleView, mTitleParams);

        //初始化标题栏下方分割线
        mLineView = new View(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                dp2px(getContext(), 0.5f));
        layoutParams.gravity = Gravity.CENTER;
        mLineView.setLayoutParams(layoutParams);
        mLineView.setBackgroundColor(Color.parseColor("#b2b2b2"));
        mLineView.setVisibility(0 == dividerLineVisibility ? View.VISIBLE : 1 == dividerLineVisibility ? View.INVISIBLE : View.GONE);
        addView(mLineView);
    }


    public TitleBar setBackgroundByColor(@ColorInt int color) {
        setBackgroundColor(color);
        mTitleColor = color;
        return this;
    }

    public TitleBar setLineViewVisibility(int visibility){
        mLineView.setVisibility(visibility);
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
        imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageButton;
    }

    private TextView makeTitleTxt() {
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        TextView titleView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.title_button, this, false);
        titleView.setSingleLine();
        titleView.setEllipsize(TextUtils.TruncateAt.END);
        titleView.setTextColor(Color.BLACK);
        titleView.setMaxLines(1);
        titleView.setEllipsize(TextUtils.TruncateAt.END);
        titleView.setGravity(Gravity.CENTER);
        titleView.setLayoutParams(layoutParams);
        return titleView;
    }

    private TextView makeCenterTxt() {
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        TextView titleView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.title_button, this, false);
        titleView.setTextSize(16);
        titleView.setGravity(Gravity.CENTER);
        titleView.setTextColor(mTitleTxtColor);
        titleView.setLayoutParams(layoutParams);
        titleView.setCompoundDrawablePadding(dp2px(getContext(), 2));
        return titleView;
    }


    private AppCompatRadioButton makeRadioButton() {
        RadioGroup.LayoutParams layoutParams =
                new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT);
        AppCompatRadioButton radioButton = new AppCompatRadioButton(getContext());
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    public TextView getRightTxtView() {
        TextView mRightTxt = makeTitleTxt();
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
            layoutParams.rightMargin = getCommonPaddingLeftRight();
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
        return dp2px(getContext(), 8);
    }


    public int getCommonPaddingTopBottom() {
        return dp2px(getContext(), 1);
    }

    public AppCompatImageView getLeftImgView() {

        AppCompatImageView mLeftImg = makeTitleButton();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLeftImg.getLayoutParams();
        int size = getLeftChildIds().size();
        int id = ViewUtil.generateViewId();
        mLeftImg.setId(id);
        if (size > 0) {
            int lastId = getLeftChildIds().get(size - 1);
            layoutParams.leftMargin = getCommonPaddingLeftRight();
            layoutParams.addRule(RelativeLayout.RIGHT_OF, lastId);
        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        getLeftChildIds().add(id);
        mTitleView.addView(mLeftImg, layoutParams);

        return mLeftImg;
    }

    public TextView getCenterTitleView() {
        if (null != mRadioGroup)
            mTitleView.removeView(mRadioGroup);
        if (null == mCenterTxt) {//如果为null 则还没加入到TITLE栏
            mCenterTxt = makeCenterTxt();
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mCenterTxt.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mCenterTxt.setClickable(false);
            mCenterTxt.setGravity(Gravity.CENTER);
            mCenterTxt.setCompoundDrawablePadding(dp2px(getContext(), 3));
            mTitleView.addView(mCenterTxt, layoutParams);
        }

        return mCenterTxt;
    }

    public RadioGroup getCenterRadioGroup(final List<RadioData> radioDatas, final OnTitleRadioCheckedListener onTitleRadioCheckedListener) {
        if (null != mCenterTxt)
            mTitleView.removeView(mCenterTxt);
        if (null == mRadioGroup && null != radioDatas) {
            this.mRadioDatas = radioDatas;
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mRadioGroup = new RadioGroup(getContext());
            mRadioGroup.setOrientation(HORIZONTAL);
            int size = radioDatas.size();
            int horPading = dp2px(getContext(), 25);
            int verPading = dp2px(getContext(), 10);
            for (int i = 0; i < size; i++) {
                final RadioData radioData = radioDatas.get(i);
                RadioButton radioButton = makeRadioButton();
                radioButton.setId(ViewUtil.generateViewId());
                radioButton.setText(radioData.title);
                radioButton.setChecked(radioData.isChecked);
                radioButton.setTag(i);
                if (i == 0)
                    ViewCompat.setBackground(radioButton,
                            ContextCompat.getDrawable(getContext(), R.drawable.title_radio_bg_left));
                else if(i == size - 1)
                    ViewCompat.setBackground(radioButton,
                            ContextCompat.getDrawable(getContext(), R.drawable.title_radio_bg_right));
                else
                    ViewCompat.setBackground(radioButton,
                            ContextCompat.getDrawable(getContext(), R.drawable.title_radio_bg_center));
                radioButton.setButtonDrawable(null);
                radioButton.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.title_radio_text_color));
                radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            int index = (Integer) buttonView.getTag();
                            RadioData radioData1 = radioDatas.get(index);
                            if (null != onTitleRadioCheckedListener)
                                onTitleRadioCheckedListener.onRadioChecked(buttonView, radioData1);
                        }
                    }
                });
                mRadioGroup.addView(radioButton);
            }
            mTitleView.addView(mRadioGroup, layoutParams);
        }
        return mRadioGroup;
    }

    public TextView getLeftTxtView() {

        TextView mLeftTxt = makeTitleTxt();
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
            ;
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

    public TitleBar removeAllRightButtons(){
        if(null != mRightChildIds){
            Iterator<Integer> iterator = mRightChildIds.iterator();
            while (iterator.hasNext()){
                mTitleView.removeView(mTitleView.findViewById(iterator.next()));
                iterator.remove();
            }
        }
        return this;
    }

    public TitleBar removeAllLeftButtons(){
        if(null != mLeftChildIds){
            Iterator<Integer> iterator = mLeftChildIds.iterator();
            while (iterator.hasNext()){
                mTitleView.removeView(mTitleView.findViewById(iterator.next()));
                iterator.remove();
            }
        }
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

    public static class RadioData {
        public String title;
        public String callback;
        public boolean isChecked;
    }

    public interface OnTitleRadioCheckedListener {
        void onRadioChecked(CompoundButton radioButton, RadioData radioData);
    }

}
