package com.wzn.libaray.sample

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v4.os.EnvironmentCompat
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.madai.annotation.ReleaseUtils
import com.madai.util.network.DownloadUtil
import com.wzn.libaray.utils.Logger
import com.wzn.libaray.utils.toast.ToastHelper

import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        val md5 = MD5Util.getFileMD5String(File(Environment.getExternalStorageDirectory().path + "/TengNiu/p2p/temp/patch.temp"))
        ToastHelper.init(this)
        ToastHelper.getInstance().showToast(md5)
        val file = File(Environment.getExternalStorageDirectory(), "test.apk")
        DownloadUtil.Builder()
                .fileUrl("")
                .file(File("/sdcard/test.apk"))
                .build()
                .downLoadFile()
                .subscribe {
                    Logger.d("ccc", "地址 ${Environment.getExternalStorageDirectory().absolutePath} \n 下载进度$it")
                }
    }

    protected fun initViews() {
        findViewById(R.id.go_manual).setOnClickListener { startActivity(Intent(this@MainActivity, ManualAddTitleBarActivity::class.java)) }

        findViewById(R.id.go_test_lightStatusbar).setOnClickListener { startActivity(Intent(this@MainActivity, TestLightStatusbarActivity::class.java)) }
    }

    override fun finish() {
        super.finish()
        ReleaseUtils.doRelease()
    }
}
