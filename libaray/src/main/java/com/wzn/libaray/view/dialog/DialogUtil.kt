package com.wzn.libaray.view.dialog

import android.app.Activity
import android.view.View
import com.afollestad.materialdialogs.GravityEnum
import com.afollestad.materialdialogs.MaterialDialog

/**
 * Created by Rona on 2017/4/14.
 */
object DialogUtil {
    fun showCommonDialog(activity: Activity,
                         alertModel: AlertModel,
                         onAlertDialogClickListener: AlertModel.OnAlertDialogClickListener?): MaterialDialog {
        val build = MaterialDialog.Builder(activity)

        alertModel.mTitle?.let {
            build.title(it)
                    .titleGravity(GravityEnum.CENTER)
        }

        alertModel.mMessage?.let {
            build.content(it)
                    .contentGravity(GravityEnum.START)
        }

        alertModel.mPositiveButtonText?.let {
            build.positiveText(alertModel.mPositiveButtonText)
                    .onPositive { dialog, which ->
                        dialog.dismiss()
                        onAlertDialogClickListener?.onPositiveClick()
                    }
        }

        alertModel.mNegativeButtonText?.let {
            build.negativeText(alertModel.mNegativeButtonText)
                    .onNegative { dialog, which ->
                        dialog.dismiss()
                        onAlertDialogClickListener?.onNegativeClick()
                    }
        }

        return build.cancelable(false).show()
    }

    fun showCustomDialog(activity: Activity,
                         alertModel: AlertModel,
                         customeView: View,
                         onAlertDialogClickListener: AlertModel.OnAlertDialogClickListener?): MaterialDialog {
        val build = MaterialDialog.Builder(activity)

        alertModel.mTitle?.let {
            build.title(it)
        }

        customeView.let {
            build.customView(customeView, false)
        }

        alertModel.mPositiveButtonText.let {
            build.positiveText(alertModel.mPositiveButtonText)
                    .onPositive { dialog, which ->
                        dialog.dismiss()
                        onAlertDialogClickListener?.onPositiveClick()
                    }
        }

        alertModel.mNegativeButtonText.let {
            build.negativeText(alertModel.mNegativeButtonText)
                    .onNegative { dialog, which ->
                        dialog.dismiss()
                        onAlertDialogClickListener?.onNegativeClick()
                    }
        }

        return build.cancelable(false).show()
    }
}
