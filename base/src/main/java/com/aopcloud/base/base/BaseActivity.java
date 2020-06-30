package com.aopcloud.base.base;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.aopcloud.base.BuildConfig;
import com.aopcloud.base.R;
import com.aopcloud.base.common.AppManager;
import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.base.common.ICommonViewUi;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.presenter.ICommonRequestPresenter;
import com.aopcloud.base.presenter.impl.CommonRequestPresenterImpl;
import com.aopcloud.base.util.KeyboardUtil;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.widget.LoadingDialog;
import com.aopcloud.base.widget.TipsDialog;
import com.gyf.immersionbar.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import vip.devkit.common.layout.BaseQuickLayoutManager;
import vip.devkit.common.layout.ViewType;

/**
 * @PackageName : com.aopcloud.base.base
 * @File : BaseActivity.java
 * @Date : 2019/12/26 11:57
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseQuickLayoutManager.OnInflateListener, ICommonViewUi {
    private long lastBackKeyDownTick = 0;
    public static final long MAX_DOUBLE_BACK_DURATION = 1500;
    protected boolean isResumed;
    public boolean isAlive = false;

    private Bundle savedInstanceState;
    private int layoutResId = -1;
    protected BaseQuickLayoutManager mLayoutManager;
    protected LoadingDialog mLoadingDialog;
    protected ImmersionBar mImmersionBar;
    protected Unbinder unbinder;
    protected ICommonRequestPresenter iCommonRequestPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        this.savedInstanceState = savedInstanceState;
        initAttributes();
        setContentView(layoutResId);
        unbinder = ButterKnife.bind(this);
        initBase();
        initData();
        initView();
        setListener();
    }

    private void initAttributes() {
        Layout layout = getClass().getAnnotation(Layout.class);
        if (null != layout && layout.value() != -1) {
            layoutResId = layout.value();
        }
        if (setLayoutId() != -1) {
            layoutResId = setLayoutId();
        }
        if (layoutResId == -1) {
            Logcat.i(ResourceUtil.getString(R.string.base_exception_layout_null));
        }
    }


    protected void init() {


    }

    @LayoutRes
    protected int setLayoutId() {
        return layoutResId;
    }

    /**
     * @return set root/content layout . default root layout
     */
    protected View setContentLayout() {
        return null;
    }

    /**
     *
     */
    protected abstract void initView();

    private void initBase() {
        AppManager.getAppManager().addActivity(this);
        initBaseLayout();
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        if (isBindEventBusHere()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
        initICommonViewUi();
    }

    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.barColor(getBarColor());
        mImmersionBar.fitsSystemWindows(true);
        mImmersionBar.statusBarDarkFont(true);//解决部分手机默认白色状态栏
        mImmersionBar.flymeOSStatusBarFontColor("#ffffff");
        mImmersionBar.navigationBarColor("#111111"); //导航栏颜色，不写默认黑色
        mImmersionBar.navigationBarEnable(true);
        mImmersionBar.autoNavigationBarDarkModeEnable(true, 0.2f);
        mImmersionBar.keyboardEnable(true)
                .navigationBarWithKitkatEnable(false)
                .init();
    }

    protected String getBarColor(){
        return "#FF108CF7";
    }

    private void showTips() {
        Date date = new Date();
//        date.setTime(BuildConfig.DEBUG_DURATION);
        long s = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String msg = null;
        boolean exit = false;
        if (s < date.getTime()) {
            msg = ResourceUtil.getString(R.string.base_debug_version_duration, "" + dateFormat.format(date));
        } else {
            exit = true;
            msg = ResourceUtil.getString(R.string.base_debug_version_duration_expire);
        }
        final boolean finalExit = exit;
        TipsDialog tipsDialog = new TipsDialog.Builder(this)
                .setMsg(msg)
                .setShowCancel(false)
                .setCancelable(false)
                .setOnTipsClickListener(new TipsDialog.OnTipsClickListener() {
                    @Override
                    public void onSure(Dialog dialog) {
                        if (finalExit) {
                            dialog.dismiss();
                            AppManager.getAppManager().appExit(BaseActivity.this);
                        } else {
                            dialog.dismiss();
                        }
                    }
                }).build();
        tipsDialog.show();
    }

    protected ICommonRequestPresenter initICommonViewUi() {
        return iCommonRequestPresenter = new CommonRequestPresenterImpl(this, this);
    }

    protected void initData() {
    }

    protected void setListener() {

    }


    /**
     * @return
     */
    protected boolean isBindEventBusHere() {
        return true;
    }

    /**
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }


    /**
     * @return RootView  FrameLayout
     */
    protected FrameLayout getRootView() {
        View re = this.findViewById(android.R.id.content);
        if (re != null && re instanceof FrameLayout) {
            return (FrameLayout) re;
        }
        ViewGroup viewGroup = (ViewGroup) this.getWindow().getDecorView();
        re = viewGroup.getChildAt(viewGroup.getChildCount() - 1);
        if (re != null && re instanceof FrameLayout) {
            return (FrameLayout) re;
        } else {
            re = new FrameLayout(this);
            this.getActionBar().getHeight();
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT);
            viewGroup.addView(re, lp);
            return (FrameLayout) re;
        }
    }


    /**
     * @param currentScreen
     * @param lastScreen
     */
    protected void onScreenChange(int currentScreen, int lastScreen) {

    }


    /**
     * EventBus
     *
     * @param postResult
     */
    public void onEvent(BaseEvent postResult) {
        String type = postResult.getType();
        if (TextUtils.isEmpty(type)) {
            if (type.equals(BaseEvent.EVENT_LOGOUT)) {

            }
        }
    }


    /**
     * startActivity
     *
     * @param clazz
     */
    protected void gotoActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }


    /**
     * startActivity  putExtras（Bundle）
     *
     * @param clazz
     * @param extras
     */
    protected void gotoActivity(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivity(intent);
    }


    /**
     * startActivity
     *
     * @param clazz
     * @param requestCode
     */
    protected void gotoActivity(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }


    /**
     * startActivity  putExtras（Bundle）
     *
     * @param clazz       shape_progress_drawable
     * @param extras
     * @param requestCode
     */
    protected void gotoActivity(Class<?> clazz, Bundle extras, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivityForResult(intent, requestCode);
    }


    /**
     * @return set android app font
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        long currentTick = System.currentTimeMillis();
        if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
            lastBackKeyDownTick = currentTick;
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResumed = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResumed = false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
        }
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
        AppManager.getAppManager().removeActivity(this);

    }

    @Override
    public void finish() {
        if (KeyboardUtil.isSoftInputShow(this)) {
            KeyboardUtil.closeKeyboard(this);
        }
        super.finish();
    }


    /**
     * @param tips default loading...
     */
    public void showPopXupLoading(String tips) {
        if (isFinishing()) {
            return;
        }
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.setTips(tips);
        mLoadingDialog.show();
    }


    public void dismissPopupLoading() {
        if (mLoadingDialog != null && !this.isFinishing()) {
            mLoadingDialog.dismiss();
        }
    }


    protected void initBaseLayout() {
        if (setContentLayout() == null) {
            mLayoutManager = BaseQuickLayoutManager.wrap(this);
        } else {
            mLayoutManager = BaseQuickLayoutManager.wrap(setContentLayout());
        }
        mLayoutManager.setOnInflateListener(this);
        mLayoutManager.setNetError(R.layout.base_layout_error);
        mLayoutManager.setError(R.layout.base_layout_error);
        mLayoutManager.setEmpty(R.layout.base_layout_empty);
        mLayoutManager.setLoading(R.layout.base_layout_loading);
    }

    /**
     * @param viewType {@link ViewType}
     * @param view
     */
    @Override
    public void onInflate(int viewType, View view) {

        if (view.getId() == R.id.tv_base_retry) {
            initView();
            initData();
        }

    }


    protected void showLoading() {
        if (mLayoutManager != null) {
            mLayoutManager.showLoading();
        }
    }

    protected void showError(String error) {
        if (mLayoutManager != null) {
            mLayoutManager.showError();
        }
    }

    protected void showNetError() {
        if (mLayoutManager != null) {
            mLayoutManager.showNetError();
        }
    }


    protected void showEmpty() {
        if (mLayoutManager != null) {
            mLayoutManager.showEmpty();
        }
    }

    protected void showContent() {
        if (mLayoutManager != null) {
            mLayoutManager.showContent();
        }
    }

    @Override
    public void toRequest(int eventTag) {

    }

    @Override
    public void getRequestData(int eventTag, String result) {

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {

    }
}
