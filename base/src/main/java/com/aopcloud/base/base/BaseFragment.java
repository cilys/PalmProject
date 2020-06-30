package com.aopcloud.base.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.base.common.ICommonViewUi;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.presenter.ICommonRequestPresenter;
import com.aopcloud.base.presenter.impl.CommonRequestPresenterImpl;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import vip.devkit.common.layout.BaseQuickLayoutManager;


/**
 * 作者 by K
 * 时间：on 2017/8/31 16:31
 * 邮箱 by  vip@devkit.vip
 * <p/>
 * 类用途：
 * 最后修改：
 */
public abstract class BaseFragment extends Fragment implements ICommonViewUi {

    public BaseActivity mActivity;
    protected View mRootView;
    private Unbinder unbinder;
    protected boolean isVisible;
    protected boolean isFirstVisible = true;
    private boolean isCreateView = false;
    protected ImmersionBar mImmersionBar;
    protected BaseQuickLayoutManager mBaseLayoutManager;
    protected ICommonRequestPresenter iCommonRequestPresenter;


    private boolean isViewCreated; // 界面是否已创建完成
    private boolean isVisibleToUser; // 是否对用户可见
    private boolean isDataLoaded; // 数据是否已请求, isNeedReload()返回false的时起作用
    private boolean isHidden = true; // 记录当前fragment的是否隐藏


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mActivity = (BaseActivity) context;
        } else {
            mActivity = (BaseActivity) context;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        this.isCreateView = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.isCreateView = true;
        initICommonViewUi();
        initBaseLayout();
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        if (isBindEventBusHere()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
        initData();
        initView(mRootView);
        setListener();
        checkNetState(mActivity);
        isViewCreated = true;

//        lazyLoad(getUserVisibleHint());
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.barColor("#FF108CF7");
        mImmersionBar.fitsSystemWindows(true);
        mImmersionBar.statusBarDarkFont(true);//解决部分手机默认白色状态栏
        mImmersionBar.flymeOSStatusBarFontColor("#ffffff");
        mImmersionBar.keyboardEnable(true)
                .navigationBarWithKitkatEnable(false)
                .init();
    }

    protected ICommonRequestPresenter initICommonViewUi() {
        return iCommonRequestPresenter = new CommonRequestPresenterImpl(mActivity, this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logcat.i("Fragment Visible:" + this.getClass().getSimpleName() + "/" + hidden);
        isHidden = hidden;
        if (!hidden) {
        }
        lazyLoad(!hidden);
        if (!hidden && isImmersionBarEnabled() && mImmersionBar != null) {
            initImmersionBar();
        }
    }

    /**
     * 使用ViewPager嵌套fragment时，切换ViewPager回调该方法
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;

        if (isVisibleToUser && isImmersionBarEnabled() && mImmersionBar != null) {
            initImmersionBar();
        }
        lazyLoad(getUserVisibleHint());
    }


    /**
     * ViewPager场景下，判断父fragment是否可见
     *
     * @return
     */
    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return fragment == null || (fragment instanceof BaseFragment && ((BaseFragment) fragment).isVisibleToUser);
    }

    /**
     * ViewPager场景下，当前fragment可见，如果其子fragment也可见，则尝试让子fragment加载请求
     */
    private void dispatchParentVisibleState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof BaseFragment && ((BaseFragment) child).isVisibleToUser) {
                ((BaseFragment) child).initData();
            }
        }
    }


    private void lazyLoad(boolean isVisibleToUser) {
        if (!isCreateView) {
            return;
        }
        if (isVisibleToUser) {
            if (isFirstVisible) {
                onFirstVisible();
                isFirstVisible = false;
            } else {
                isVisible = true;
                onVisible();
            }
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */

    protected void onVisible() {
    }

    /**
     * 第一次可见
     */
    protected void onFirstVisible() {

    }


    /**
     * 不可见
     */
    protected void onInvisible() {

    }


    /**
     * Sets layout id.
     *
     * @return the layout id
     */
    protected abstract int setLayoutId();

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 是否绑定eventBus
     */
    protected boolean isBindEventBusHere() {
        return true;
    }

    /**
     * 是否 使用多状态view
     */
    protected boolean isBaseLayoutEnabled() {
        return true;
    }


    /**
     * View 的根布局，默认是整个界面，如果需要变换可以重写此方法
     */
    @Deprecated
    public View getStateViewRoot() {
        return mRootView;
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * view与数据绑定
     */
    protected void initView(View view) {

    }

    /**
     * 初始化多状态布局View
     */
    protected View setBaseLayout() {
        return null;
    }


    /**
     * 设置监听
     */
    protected void setListener() {

    }

    /**
     * 找到activity的控件
     *
     * @param <T> the type parameter
     * @param id  the id
     * @return the t
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findActivityViewById(@IdRes int id) {
        return (T) mActivity.findViewById(id);
    }
    /**
     * startActivity  putExtras（Bundle）
     *
     * @param clazz
     */
    protected void gotoActivity(Class<?> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        startActivity(intent);
    }

    /**
     * startActivity  putExtras（Bundle）
     *
     * @param clazz
     * @param extras
     */
    protected void gotoActivity(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(extras);
        startActivity(intent);
    }

    /**
     * startActivity  putExtras（Bundle）
     *
     * @param clazz
     * @param extras
     */
    protected void gotoActivity(Class<?> clazz, int m, Bundle extras) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(extras);
        startActivityForResult(intent, m);
    }
    /**
     * startActivity  putExtras（Bundle）
     *
     * @param clazz
     * @param extras
     */
    protected void gotoActivity(Class<?> clazz,  Bundle extras,int m) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(extras);
        startActivityForResult(intent, m);
    }
    /**
     * startActivity  putExtras（Bundle）
     *
     * @param clazz
     */
    protected void gotoActivity(Class<?> clazz, int m) {
        Intent intent = new Intent(mActivity, clazz);
        startActivityForResult(intent, m);
    }

    /**
     * 重启当前Activity
     */
    private void reStartActivity() {
        Intent intent = mActivity.getIntent();
        mActivity.finish();
        startActivity(intent);
    }

    /**
     * @param mContext
     */
    protected void checkNetState(Context mContext) {
        if (mBaseLayoutManager != null) {
        }
    }

    /**
     * 初始化多状态布局View
     */
    protected void initBaseLayout() {
        if (!isBaseLayoutEnabled()) {
            return;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy(this);
        }
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
    }
    /**
     * EventBus
     */
    public void onEvent(BaseEvent postResult) {
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