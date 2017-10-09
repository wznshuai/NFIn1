package com.wzn.libaray.utils.permission

import android.app.Activity
import com.tbruyelle.rxpermissions.RxPermissions
import com.wzn.libaray.view.dialog.AlertModel
import com.wzn.libaray.view.dialog.DialogUtil
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by Rona on 2017/4/14.
 */

object PermissionUtil {
    interface OnPermissionResultListener {
        //授予权限
        fun onPermissionGranted()

        //授予取消权限
        fun onPermissionGrantCancel()

        //授予设置权限
        fun onPermissionGrantSet()
    }

    fun checkPermmission(activity: Activity,
                         alertModle: AlertModel,
                         onPermissionResultListener: OnPermissionResultListener?,
                         vararg permission: String) {
        val rxPermissions = RxPermissions(activity)
        rxPermissions
                .request(*permission)
                .subscribe { granted ->
                    if (granted) {
                        onPermissionResultListener?.onPermissionGranted()
                    } else {
                        DialogUtil.showCommonDialog(activity, alertModle, object : AlertModel.OnAlertDialogClickListener {
                            override fun onPositiveClick() {
                                onPermissionResultListener?.onPermissionGrantSet()
                            }

                            override fun onNegativeClick() {
                                onPermissionResultListener?.onPermissionGrantCancel()
                            }
                        })
                    }
                }
    }

    fun checkPermmission(activity: Activity,
                         onPermissionResultListener: OnPermissionResultListener?,
                         vararg permission: String) {
        val rxPermissions = RxPermissions(activity)
        rxPermissions
                .request(*permission)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ granted ->
                    if (granted) {
                        onPermissionResultListener?.onPermissionGranted()
                    } else {
                        onPermissionResultListener?.onPermissionGrantCancel()
                    }
                }, {
                    it.printStackTrace()
                })
    }

}
