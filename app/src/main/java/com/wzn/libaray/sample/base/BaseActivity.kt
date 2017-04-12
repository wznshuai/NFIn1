package com.shudu.hypermoney.base

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.wzn.libaray.utils.Logger
import com.wzn.libaray.utils.StatusBarUtil
import rx.Observable
import rx.android.schedulers.AndroidSchedulers


/**
 * Created by Wind_Fantasy on 15/3/26.
 */
abstract class BaseActivity : RxAppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener {

    val TAG = javaClass.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(TAG, TAG + " onCreate")
        initData(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Logger.d(TAG, TAG + "  onResume")
    }

    protected fun onResume(onlyCallSuper: Boolean) {
        if (onlyCallSuper)
            super.onResume()
        else
            onResume()
    }

    override fun onPause() {
        super.onPause()
        Logger.d(TAG, TAG + "  onPause")
    }

    protected fun onPause(onlyCallSuper: Boolean) {
        if (onlyCallSuper)
            super.onPause()
        else
            onPause()
    }

    protected fun onStop(onlyCallSuper: Boolean) {
        if (onlyCallSuper)
            super.onStop()
        else
            onStop()
    }

    override fun onStop() {
        super.onStop()
        Logger.d(TAG, TAG + "  onStop")
    }

    override fun onDestroy() {
        cleanData()
        super.onDestroy()
        Logger.d(TAG, TAG + "  onDestroy")
    }

    protected fun onDestroy(onlyCallSuper: Boolean) {
        if (onlyCallSuper)
            super.onDestroy()
        else
            onDestroy()
    }


    protected open fun initData(savedInstanceState: Bundle?) {}

    protected open fun findViews() {}

    protected open fun initViews() {}


    private var mLoadingDialog: ProgressDialog? = null

    fun <T> loadingManager(): Observable.Transformer<T, T> {
        return Observable.Transformer<T, T> { observable ->
            observable.doOnSubscribe { showLoading() }.subscribeOn(AndroidSchedulers.mainThread())
                    .doOnUnsubscribe { hideLoading() }.doOnCompleted { hideLoading() }.doOnError { hideLoading() }
        }
    }


    protected fun showLoading() {
        if (null == mLoadingDialog) {
            mLoadingDialog = ProgressDialog(this)
            mLoadingDialog!!.show()
        }
    }

    protected fun hideLoading() {
        if (null != mLoadingDialog) {
            mLoadingDialog!!.dismiss()
            mLoadingDialog = null
        }
    }

    /**
     * @see StatusBarUtil.setColor
     */
    @TargetApi(19)
    @Deprecated("")
    fun setStatusBarTintColorResource(@ColorRes colorId: Int) {
        setStatusBarTintColor(ContextCompat.getColor(this, colorId))
    }

    /**
     * @see StatusBarUtil.setColor
     */
    @TargetApi(19)
    @Deprecated("")
    fun setStatusBarTintColor(@ColorInt color: Int) {
        StatusBarUtil.setColor(this, color)
    }

    private fun resetStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setDefaultColor(this)
        }
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        resetStatusBar()
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        resetStatusBar()
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        super.setContentView(view, params)
        resetStatusBar()
    }


    override fun onContentChanged() {
        super.onContentChanged()
        findViews()
        initViews()
    }


    override fun onPostResume() {
        super.onPostResume()
        Logger.d(TAG, TAG + "  onPostResume")
    }

    protected fun onPostResume(onlyCallSuper: Boolean) {
        if (onlyCallSuper)
            super.onPostResume()
        else
            onPostResume()
    }

    protected fun <T : View> myFindViewById(@IdRes id: Int): T {
        return findViewById(id) as T
    }


    /**
     * 在onDestroy中被回调
     */
    open fun cleanData() {

    }

    protected fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun showToast(resId: Int) {
        Toast.makeText(applicationContext, resId, Toast.LENGTH_SHORT).show()
    }


    /**
     * 是否可以继续执行

     * @return
     */
    fun canGoon(): Boolean {
        return !isFinishing
    }

    // variable to track event time
    private var mLastClickTime: Long = 0

    //View.OnClickListener.onClick method defination

    override fun onClick(v: View) {
        // Preventing multiple clicks, using threshold of 1 second
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }

        mLastClickTime = SystemClock.elapsedRealtime()

        onViewClick(v)
    }

    open fun onViewClick(v: View) {

    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        // Preventing multiple clicks, using threshold of 1 second
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }

        mLastClickTime = SystemClock.elapsedRealtime()

        onItemViewClick(parent, view, position, id)
    }

    fun onItemViewClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {}

}
