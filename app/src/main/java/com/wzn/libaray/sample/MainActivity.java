package com.wzn.libaray.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wzn.libaray.sample.base.BaseTitleBarActivity;
import com.wzn.libaray.utils.StatusBarUtil;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
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
}
