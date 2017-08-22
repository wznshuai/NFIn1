package com.wzn.libaray.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shudu.hypermoney.base.BaseActivity;
import com.wzn.libaray.sample.base.BaseTitleBarActivity;
import com.wzn.libaray.utils.StatusBarUtil;
import com.wzn.libaray.view.TitleBar;


public class TestLightStatusbarActivity extends BaseTitleBarActivity {
    boolean isLightSatusBar = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_light_statusbar);
    }

    @Override
    protected void findViews() {
        super.findViews();
    }

    @Override
    protected void initViews() {
        super.initViews();
        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusBarUtil.setStatusBarisLight(TestLightStatusbarActivity.this, isLightSatusBar);
                isLightSatusBar = !isLightSatusBar;
            }
        });
    }

    @Override
    public void initTitleBar() {
        StatusBarUtil.setColor(this, Color.WHITE);
        setTitleMine("adahjsd");
    }
}
