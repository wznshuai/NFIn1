package com.wzn.libaray.activity;

import android.animation.ValueAnimator;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.wzn.libaray.view.SwipeDismissLayout;

/**
 * Created by Wind_Fantasy on 15/3/31.
 */
public abstract class BaseSecondActivity extends BaseTitleBarActivity {


    private SwipeDismissLayout swipeDismissLayout;

    @Override
    public void setContentView(int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, null, false));
    }

    @Override
    public void setContentView(View view) {
        swipeDismissLayout = new SwipeDismissLayout(this);
        View contentView = makeFinalContentView(view);
        ViewCompat.setFitsSystemWindows(contentView, false);
        swipeDismissLayout.addView(contentView);
        ViewCompat.setFitsSystemWindows(swipeDismissLayout, true);
        getDelegate().setContentView(swipeDismissLayout);
        swipeDismissLayout.setOnDismissedListener(new SwipeDismissLayout.OnDismissedListener() {
            @Override
            public void onDismissed(SwipeDismissLayout layout) {
                finish();
                overridePendingTransition(0, android.support.v7.appcompat.R.anim.abc_shrink_fade_out_from_bottom);
            }
        });

        swipeDismissLayout.setOnSwipeProgressChangedListener(new SwipeDismissLayout.OnSwipeProgressChangedListener() {
            @Override
            public void onSwipeProgressChanged(SwipeDismissLayout layout, float progress, float translate) {
                ViewCompat.setTranslationX(layout, translate);
            }

            @Override
            public void onSwipeCancelled(SwipeDismissLayout layout) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(ViewCompat.getTranslationX(layout), 0);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        ViewCompat.setTranslationX(swipeDismissLayout, value);
                    }
                });
                valueAnimator.start();
            }
        });
    }
}
