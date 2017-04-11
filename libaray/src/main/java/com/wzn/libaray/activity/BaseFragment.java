package com.wzn.libaray.activity;
/**
 * Created by Wind_Fantasy on 15/3/26.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.trello.rxlifecycle.components.support.RxFragment;
import com.wzn.libaray.utils.Logger;

import java.util.HashSet;


public abstract class BaseFragment extends RxFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    public String TAG = getClass().getSimpleName();

    public HashSet<String> REQUEST_TAG_ARRAY;

    public Context mCon;
    public String title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.d(TAG, TAG + "  onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d(TAG, TAG + "  onActivityCreated");
        mCon = getActivity().getApplicationContext();
        REQUEST_TAG_ARRAY = new HashSet<>();
        addTagInRequestArray(TAG);
        initData(savedInstanceState);
        findViews();
        initViews();
    }


    @Override
    public void onResume() {
        super.onResume();
        Logger.d(TAG, TAG + "  onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d(TAG, TAG + "  onPause");
    }

    @Override
    public void onDestroyView() {
        cleanDataOnHide();
        super.onDestroyView();
        Logger.d(TAG, TAG + "  onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG, TAG + "  onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d(TAG, TAG + "  onDetach");
    }

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    protected abstract void findViews();

    protected abstract void initViews();


    public void addTagInRequestArray(String tag) {
        REQUEST_TAG_ARRAY.add(tag);
    }

    protected void cleanDataOnHide() {
    }


    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public <T extends View> T findParentActViewById(int id) {
        return (T) getActivity().findViewById(id);
    }


    public void back() {
        getFragmentManager().popBackStack(getClass().getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
//		getFragmentManager().beginTransaction().setCustomAnimations(R.anim.act_anim_in2, R.anim.act_anim_out2).remove(BaseFragment.this).commit();
    }

    protected <T extends View> T findViewById(int id) {
        if (getView() != null)
            return (T) getView().findViewById(id);
        else
            return (T) findParentActViewById(id);
    }

    protected void getRequestFailAlertDialog(DialogInterface.OnClickListener positiveClick) {
        new AlertDialog.Builder(getActivity()).setMessage("请求失败")
                .setPositiveButton("重新请求", positiveClick).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog = null;
            }
        }).create().show();
    }


    protected boolean checkActivity(Activity activity) {
        return null != activity && activity instanceof BaseActivity;
    }

    /**
     * 是否可以继续执行
     *
     * @return
     */
    public boolean canGoon() {
        return !(!isAdded() || isRemoving() || isDetached());
    }

    // variable to track event time
    private long mLastClickTime = 0;

    //View.OnClickListener.onClick method defination

    @Override
    final public void onClick(View v) {
        // Preventing multiple clicks, using threshold of 1 second
        if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();

        onViewClick(v);
    }

    @Override
    final public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Preventing multiple clicks, using threshold of 1 second
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();

        onItemViewClick(parent, view, position, id);
    }

    public void onRightTextViewClick() {
    }

    public void onItemViewClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void onViewClick(View v) {

    }

    public <T> T findFragmentByTag(String tag) {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(tag);
        if (null == fragment)
            fragment = getFragmentManager().findFragmentByTag(tag);
        if (null == fragment)
            fragment = getChildFragmentManager().findFragmentByTag(tag);
        return (T) fragment;
    }


    public boolean onBackPressed() {
        boolean flag = false;
        FragmentManager fragmentManager = getChildFragmentManager();
        if (null != fragmentManager) {
            int stackCount = fragmentManager.getBackStackEntryCount();
            if (stackCount != 0) {
                Fragment fragment = fragmentManager.findFragmentByTag(fragmentManager.getBackStackEntryAt(stackCount - 1).getName());
                if (null != fragment && fragment instanceof BaseFragment) {
                    if (!((BaseFragment) fragment).onBackPressed()) {
                        fragmentManager.popBackStack();
                    }
                } else {
                    fragmentManager.popBackStack();
                }
                flag = true;
            }
        }
        return flag;
    }


    public String getPackageName(){
        return getContext().getPackageName();
    }
}

