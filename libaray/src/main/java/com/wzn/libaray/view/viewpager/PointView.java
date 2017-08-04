package com.wzn.libaray.view.viewpager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wzn.libaray.R;
import com.wzn.libaray.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wind_Fantasy on 16/9/18.
 */
public class PointView extends LinearLayout {

    private List<ImageView> mImageViews;
    private ViewPager mPager;
    private LoopViewPager mLoopViewPager;
    private Drawable mSelectedDrawable, mUnSelectedDrawable;

    public PointView(Context context) {
        this(context, null);
    }

    public PointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PointView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PointView, defStyleAttr, defStyleRes);
        if (null != a) {
            int mSelectedColor = a.getColor(R.styleable.PointView_point_selected_color, Color.WHITE);
            int mUnSelectedColor = a.getColor(R.styleable.PointView_point_unselected_color, Color.TRANSPARENT);
            mSelectedDrawable = ContextCompat.getDrawable(context, R.drawable.shape_oval_indicator_hover);
            mUnSelectedDrawable = ContextCompat.getDrawable(context, R.drawable.shape_oval_indicator);

            mSelectedDrawable.setColorFilter(mSelectedColor, PorterDuff.Mode.SRC_ATOP);
            mUnSelectedDrawable.setColorFilter(mUnSelectedColor, PorterDuff.Mode.SRC_ATOP);
            a.recycle();
        }
    }




    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        getDrops();
    }

    public void setViewPager(ViewPager viewPager) {
        this.mPager = viewPager;
        if (null != mPager)
            init();
    }

    public void setViewPager(LoopViewPager viewPager) {
        this.mLoopViewPager = viewPager;
        if (null != mLoopViewPager)
            init();
    }

    private int getViewPagerChildCount() {
        if (null != mPager) {
            return mPager.getAdapter().getCount();
        } else if (null != mLoopViewPager) {
            return mLoopViewPager.getAdapter().getCount();
        }
        return 0;
    }

    private void getDrops() {
        mImageViews = new ArrayList<>();
        ImageView imageView;
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        // 动态生成Banner与标记点
        removeAllViews();
        int count = getViewPagerChildCount();
        for (int i = 0; i < count; i++) {
            imageView = new ImageView(getContext());
            imageView.setBackgroundResource(R.drawable.shape_oval_indicator);
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            addView(imageView, layoutParams);
            mImageViews.add(imageView);
        }


        draw_Point(0);

        if (null != mPager) {
            mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    position = (mImageViews.size() + position % mImageViews.size()) % mImageViews.size();
                    draw_Point(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else if (null != mLoopViewPager) {
            mLoopViewPager.addOnPageChangeListener(new LoopViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    position = (mImageViews.size() + position % mImageViews.size()) % mImageViews.size();
                    draw_Point(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    // 更换标记点
    private void draw_Point(int index) {
        for (int i = 0; i < mImageViews.size(); i++) {
            if (i == index) {
                ViewUtil.setBackound(mImageViews.get(i), mSelectedDrawable);
            } else {
                ViewUtil.setBackound(mImageViews.get(i), mUnSelectedDrawable);
            }
        }
    }

}
