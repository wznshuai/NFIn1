package com.wzn.libaray.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.constraint.ConstraintLayout;
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

    private View mLineView, mStatusbarView;
    private ConstraintLayout mTitleView;
    private TextView mCenterTxt;
    private ImageView mCenterImgView;
    private RadioGroup mRadioGroup;
    private List<RadioData> mRadioDatas;

    private ArrayList<Integer> mRightChildIds, mLeftChildIds;

    private int mTitleColor = Color.WHITE;


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
        if(StatusBarUtil.isSupport()){
            if (isComputeStatusBarHeight) {
                if (null == mStatusbarView) {
                    makeStatusbarView();
                    addView(mStatusbarView, 0);
                }
            } else {
                if (null != mStatusbarView) {
                    removeView(mStatusbarView);
                    mStatusbarView = null;
                }
            }
        }
        return this;
    }

    private void makeStatusbarView() {
        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        StatusBarUtil.getStatusBarHeight(getContext()));
        mStatusbarView = new View(getContext());
        mStatusbarView.setLayoutParams(layoutParams);
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
        float mSuggestHeight = computeHeight();
        if (null != attrs) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, defStyleRes);
            dividerLineVisibility = a.getInt(R.styleable.TitleBar_dividing_line_visibility, 0);
            mSuggestHeight = a.getDimension(R.styleable.TitleBar_EXACTLY_Height, mSuggestHeight);
            a.recycle();
        }
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setBackgroundColor(mTitleColor);
        setClickable(true);
        if (getId() == -1)
            setId(ViewUtil.generateViewId());

        //初始化title栏按钮、标题所在layout
        mTitleView = new ConstraintLayout(getContext());
        LayoutParams mTitleParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) mSuggestHeight);
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

    public TitleBar setLineViewVisibility(int visibility) {
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
        ConstraintLayout.LayoutParams layoutParams =
                new ConstraintLayout.LayoutParams(0,
                        0);
        layoutParams.dimensionRatio = "w,1:1";
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        AppCompatImageView imageButton = new AppCompatImageView(getContext());
        imageButton.setLayoutParams(layoutParams);
        imageButton.setBackgroundColor(Color.TRANSPARENT);
        imageButton.setPadding(getCommonPaddingLeftRight(), getCommonPaddingTopBottom(),
                getCommonPaddingLeftRight(), getCommonPaddingTopBottom());
        imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageButton;
    }

    private AppCompatImageView makeCenterImgView() {
        ConstraintLayout.LayoutParams layoutParams =
                new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        0);
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        AppCompatImageView imageButton = new AppCompatImageView(getContext());
        imageButton.setLayoutParams(layoutParams);
        imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageButton;
    }

    private TextView makeTitleTxt() {
        ConstraintLayout.LayoutParams layoutParams =
                new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        0);
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
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
        ConstraintLayout.LayoutParams layoutParams =
                new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        0);
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        TextView titleView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.title_button, this, false);
        titleView.setTextSize(16);
        titleView.setGravity(Gravity.CENTER);
        titleView.setTextColor(mTitleTxtColor);
        titleView.setLayoutParams(layoutParams);
        titleView.setCompoundDrawablePadding(dp2px(getContext(), 2));
        return titleView;
    }


    private AppCompatRadioButton makeRadioButton() {
        ConstraintLayout.LayoutParams layoutParams =
                new ConstraintLayout.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        AppCompatRadioButton radioButton = new AppCompatRadioButton(getContext());
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    public TextView getRightTxtView() {
        TextView mRightTxt = makeTitleTxt();
        mRightTxt.setPadding(getCommonPaddingLeftRight(), 0, getCommonPaddingLeftRight(), 0);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mRightTxt.getLayoutParams();
        int id = ViewUtil.generateViewId();
        mRightTxt.setId(id);
        int size = getRightChildIds().size();
        if (size > 0) {
            int lastId = getRightChildIds().get(size - 1);
            layoutParams.rightToLeft = lastId;
        } else {
            layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        }
        getRightChildIds().add(id);
        addView(mRightTxt, layoutParams);

        return mRightTxt;
    }

    private void addView(View view, ConstraintLayout.LayoutParams layoutParams) {
        if (null != mTitleView)
            mTitleView.addView(view, layoutParams);
    }


    public AppCompatImageView getRightImgView() {

        AppCompatImageView mRightImg = makeTitleButton();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mRightImg.getLayoutParams();
        int size = getRightChildIds().size();
        int id = ViewUtil.generateViewId();
        mRightImg.setId(id);
        if (size > 0) {
            int lastId = getRightChildIds().get(size - 1);
            layoutParams.rightMargin = getCommonPaddingLeftRight();
            layoutParams.rightToLeft = lastId;
        } else {
            layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        }
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
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mLeftImg.getLayoutParams();
        int size = getLeftChildIds().size();
        int id = ViewUtil.generateViewId();
        mLeftImg.setId(id);
        if (size > 0) {
            int lastId = getLeftChildIds().get(size - 1);
            layoutParams.leftMargin = getCommonPaddingLeftRight();
            layoutParams.leftToRight = lastId;
        } else {
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        }
        getLeftChildIds().add(id);
        mTitleView.addView(mLeftImg, layoutParams);

        return mLeftImg;
    }


    /**
     * 返回一个新的并添加到titlebar
     * @return
     */
    public TextView getCenterTitleView() {
        if (null != mRadioGroup)
            mTitleView.removeView(mRadioGroup);
        if(null != mCenterImgView)
            mTitleView.removeView(mCenterImgView);
        if (null == mCenterTxt) {//如果为null 则还没加入到TITLE栏
            mCenterTxt = makeCenterTxt();
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mCenterTxt.getLayoutParams();
            mCenterTxt.setClickable(false);
            mCenterTxt.setGravity(Gravity.CENTER);
            mCenterTxt.setCompoundDrawablePadding(dp2px(getContext(), 3));
            mTitleView.addView(mCenterTxt, layoutParams);
        }

        return mCenterTxt;
    }

    /**
     * 如果已添加则返回，没添加返回null
     * @return
     */
    public TextView getAddedCenterTitleView(){
        return mCenterTxt;
    }

    /**
     * 如果已添加则返回，没添加返回null
     * @return
     */
    public ImageView getAddedCenterImageView(){
        return mCenterImgView;
    }

    /**
     * 返回一个新的并添加到titlebar
     * @return
     */
    public ImageView getCenterImageView() {
        if (null != mRadioGroup)
            mTitleView.removeView(mRadioGroup);
        if(null != mCenterTxt)
            mTitleView.removeView(mCenterTxt);
        if (null == mCenterImgView) {//如果为null 则还没加入到TITLE栏
            mCenterImgView = makeCenterImgView();
            mTitleView.addView(mCenterImgView, mCenterImgView.getLayoutParams());
        }
        return mCenterImgView;
    }

    /**
     * 如果已添加则返回，没添加返回null
     * @return
     */
    public RadioGroup getAddedtCenterRadioGroup() {
        return mRadioGroup;
    }

    /**
     * 返回一个新的RadioGroup 并添加到titlebar
     * @param radioDatas
     * @param onTitleRadioCheckedListener
     * @return
     */
    public RadioGroup getCenterRadioGroup(final List<RadioData> radioDatas, final OnTitleRadioCheckedListener onTitleRadioCheckedListener) {
        if (null != mCenterTxt)
            mTitleView.removeView(mCenterTxt);
        if(null != mCenterImgView)
            mTitleView.removeView(mCenterImgView);
        if (null == mRadioGroup && null != radioDatas) {
            this.mRadioDatas = radioDatas;
            ConstraintLayout.LayoutParams layoutParams =
                    new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
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
                else if (i == size - 1)
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
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mLeftTxt.getLayoutParams();
        int size = getLeftChildIds().size();
        int id = ViewUtil.generateViewId();
        mLeftTxt.setId(id);
        if (size > 0) {
            int lastId = getLeftChildIds().get(size - 1);
            layoutParams.leftToRight = lastId;
        } else {
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        }
        getLeftChildIds().add(id);
        mTitleView.addView(mLeftTxt, layoutParams);

        return mLeftTxt;
    }


    public View getLeftView(int layoutId) {

        View mLeftView = LayoutInflater.from(getContext()).inflate(layoutId, this, false);

        int id = mLeftView.getId();
        if (id == -1) {
            id = ViewUtil.generateViewId();
            mLeftView.setId(id);
        }
        int size = getLeftChildIds().size();

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(mLeftView.getLayoutParams());
        if (size > 0) {
            int lastId = getLeftChildIds().get(size - 1);
            layoutParams.leftToRight = lastId;
        } else {
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        }
        getLeftChildIds().add(id);
        mTitleView.addView(mLeftView, layoutParams);

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
        mCenterTxt = null;
        setVisibility(VISIBLE);
        return this;
    }

    public TitleBar removeAllRightButtons() {
        if (null != mRightChildIds) {
            Iterator<Integer> iterator = mRightChildIds.iterator();
            while (iterator.hasNext()) {
                mTitleView.removeView(mTitleView.findViewById(iterator.next()));
                iterator.remove();
            }
        }
        return this;
    }

    public TitleBar removeAllLeftButtons() {
        if (null != mLeftChildIds) {
            Iterator<Integer> iterator = mLeftChildIds.iterator();
            while (iterator.hasNext()) {
                mTitleView.removeView(mTitleView.findViewById(iterator.next()));
                iterator.remove();
            }
        }
        return this;
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
//        mSuggestHeight = computeHeight();
//
//        if (heightModel == MeasureSpec.EXACTLY) {
//            mSuggestHeight = height;
//        }
//
//        if (isComputeStatusBarHeight) {
//            mSuggestHeight += StatusBarUtil.getStatusBarHeight(getContext());
//        }
//        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
//    }

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
