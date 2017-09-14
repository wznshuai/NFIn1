package com.wzn.libaray.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.madai.annotation.ReleaseUtils;
import com.wzn.libaray.utils.toast.ToastHelper;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        String md5 = MD5Util.INSTANCE.getFileMD5String(new File(Environment.getExternalStorageDirectory().getPath() + "/TengNiu/p2p/temp/patch.temp"));
        ToastHelper.init(this);
        ToastHelper.getInstance().showToast(md5);
    }

    protected void initViews() {
        findViewById(R.id.go_manual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ManualAddTitleBarActivity.class));
            }
        });

        findViewById(R.id.go_test_lightStatusbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestLightStatusbarActivity.class));
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        ReleaseUtils.doRelease();
    }
}
