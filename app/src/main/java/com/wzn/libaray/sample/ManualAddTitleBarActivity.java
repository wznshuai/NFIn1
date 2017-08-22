package com.wzn.libaray.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shudu.hypermoney.base.BaseActivity;
import com.wzn.libaray.sample.base.BaseTitleBarActivity;
import com.wzn.libaray.utils.StatusBarUtil;
import com.wzn.libaray.utils.toast.ToastHelper;
import com.wzn.libaray.view.TitleBar;


public class ManualAddTitleBarActivity extends BaseActivity {
    private TitleBar titleBar;
    boolean isShowStatusbar = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add_titlebar);
    }

    @Override
    protected void findViews() {
        super.findViews();
        titleBar = myFindViewById(R.id.titlebar);
    }

    @Override
    protected void initViews() {
        super.initViews();
        StatusBarUtil.setTranslucent(this);
        titleBar.getCenterTitleView().setText("手动的添加");
        titleBar.setIsComputeStatusBarHeight(true);
        TextView textView = titleBar.getLeftTxtView();
        textView.setText("啊");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowStatusbar = !isShowStatusbar;
                titleBar.setIsComputeStatusBarHeight(isShowStatusbar);
            }
        });
        titleBar.getLeftImgView().setBackgroundResource(R.mipmap.ic_launcher);
        titleBar.getCenterImageView().setImageResource(R.mipmap.ic_launcher);
    }
}
