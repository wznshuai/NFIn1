package com.wzn.libaray.utils

import android.content.Context
import android.content.Intent
import android.provider.Settings

/**
 * Created by luona on 2017/4/14.
 */
class IntentUtil {
    companion object {
        fun gotoSystemSetting(context: Context? = null): Intent {
            return Intent(Settings.ACTION_APPLICATION_SETTINGS)
        }

        fun getGoHistoryActivityAndClearTopIntent(context: Context, goClass: Class<*>, intent: Intent? = null): Intent {
            var intent = intent
            if (null == intent)
                intent = Intent(context, goClass)

            if (null == intent.component)
                intent.setClass(context, goClass)

            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }

        /**
         * 必须Activity的windowIsTranslucent设置为false 但是这样的话Activity背景就不透明 手势回退会看到黑屏
         * @param context
         * *
         * @param goClass
         * *
         * @param intent
         * *
         * @return
         */
        fun getGoHistoryActivityIntent(context: Context, goClass: Class<*>, intent: Intent? = null): Intent {
            var intent = intent
            if (null == intent)
                intent = Intent(context, goClass)

            if (null == intent.component)
                intent.setClass(context, goClass)

            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            return intent
        }

    }
}