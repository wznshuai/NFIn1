package com.wzn.libaray.sample;

import android.os.Bundle;

import com.wzn.libaray.sample.base.BaseTitleBarActivity;
import com.wzn.libaray.utils.StatusBarUtil;


public class MainActivity extends BaseTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initTitleBar() {
        getTitleBar().setIsComputeStatusBarHeight(true);
        StatusBarUtil.setTranslucent(this);
        setTitleMine("测试测试");
    }
}
