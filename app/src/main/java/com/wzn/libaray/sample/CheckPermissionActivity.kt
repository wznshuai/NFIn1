package com.wzn.libaray.sample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.wzn.libaray.utils.IntentUtil
import com.wzn.libaray.utils.Logger
import com.wzn.libaray.utils.permission.PERMISSION_LAUCH
import com.wzn.libaray.utils.permission.PermissionUtil
import com.wzn.libaray.view.dialog.AlertModel

class CheckPermissionActivity : AppCompatActivity() {
    val mResult: TextView by lazy {
        findViewById(R.id.tv_result) as TextView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_permission)

        mResult.setOnClickListener{
            checkLauchPermission()
        }
    }

    fun checkLauchPermission() {
        PermissionUtil.checkPermmission(this,
                AlertModel(getString(R.string.common_request_permission_title),
                        getString(R.string.common_request_permission_cancel),
                        getString(R.string.common_request_permission_ok),
                        getString(R.string.lauch_request_permission_message)),
                object : PermissionUtil.OnPermissionResultListener {
                    override fun onPermissionGranted() {
                        mResult.text = "已授权"
                    }

                    override fun onPermissionGrantCancel() {
                        mResult.text="取消授权"
//                        finish()
//                        System.exit(2)
                    }

                    override fun onPermissionGrantSet() {
                        startActivityForResult(IntentUtil.gotoSystemSetting(this@CheckPermissionActivity), PERMISSION_LAUCH)
                    }
                }
                , android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PERMISSION_LAUCH -> {
                checkLauchPermission()
            }
        }
    }
}
