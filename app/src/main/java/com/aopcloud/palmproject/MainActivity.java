package com.aopcloud.palmproject;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.UserBean;
import com.aopcloud.palmproject.common.Constants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.camera.PreviewActivity;
import com.aopcloud.palmproject.ui.activity.enterprise.SwitchEnterpriseActivity;
import com.aopcloud.palmproject.ui.activity.log.AddWorkLogActivity;
import com.aopcloud.palmproject.ui.activity.mine.AccountInfoActivity;
import com.aopcloud.palmproject.ui.activity.mine.LoginActivity;
import com.aopcloud.palmproject.ui.activity.mine.MyInfoCardActivity;
import com.aopcloud.palmproject.ui.activity.mine.QrCodeActivity;
import com.aopcloud.palmproject.ui.activity.project.AddProjectActivity;
import com.aopcloud.palmproject.ui.activity.project.ProjectTaskAddActivity;
import com.aopcloud.palmproject.ui.activity.setting.SettingActivity;
import com.aopcloud.palmproject.ui.activity.task.AddTaskActivity;
import com.aopcloud.palmproject.ui.activity.task.ArrangeTaskAc;
import com.aopcloud.palmproject.ui.activity.web.WebActivity;
import com.aopcloud.palmproject.ui.adapter.file.PreviewAdapter;
import com.aopcloud.palmproject.ui.fragment.FindFragment;
import com.aopcloud.palmproject.ui.fragment.HomeFragment;
import com.aopcloud.palmproject.ui.fragment.NewsFragment;
import com.aopcloud.palmproject.ui.fragment.ProjectFragment;
import com.aopcloud.palmproject.utils.AppVersionUtils;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.CircleImageView;
import com.aopcloud.palmproject.view.TipsDialog;
import com.cily.utils.base.StrUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 *
 */
@Layout(R.layout.activity_main)
public class MainActivity extends BaseAc {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tv_switch_enterprise)
    TextView mTvSwitchEnterprise;
    @BindView(R.id.iv_img)
    CircleImageView mIvImg;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_real_name)
    TextView mTvRealName;
    @BindView(R.id.iv_qr_code)
    ImageView mIvQrCode;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.tv_mine_info)
    TextView mTvMineInfo;
    @BindView(R.id.rl_mine_card)
    RelativeLayout mRlMineCard;
    @BindView(R.id.rl_feedback)
    RelativeLayout mRlFeedback;
    @BindView(R.id.rl_setting)
    RelativeLayout mRlSetting;
    @BindView(R.id.container_frameLayout)
    FrameLayout mFrameContentLayout;
    @BindView(R.id.tab_view)
    RadioGroup mTabView;
    @BindView(R.id.rb_home)
    RadioButton mRbHome;
    @BindView(R.id.rb_news)
    RadioButton mRbNews;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.rb_project)
    RadioButton mRbProject;
    @BindView(R.id.rb_find)
    RadioButton mRbFind;

    private HomeFragment mHomeFragment;
    private NewsFragment mNewsFragment;
    private ProjectFragment mProjectFragment;
    private FindFragment mFindFragment;


    @Override
    protected void init() {
        super.init();
        // 定位初始化
        List<String> list = new ArrayList<>();
        list.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        list.add(Manifest.permission.ACCESS_FINE_LOCATION);
        XXPermissions.with(this).permission(list).request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                if (isAll) {
                } else {
                    ToastUtil.showToast("请先开启权限");
                }
            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {
                ToastUtil.showToast("请开启定位权限");
            }
        });
    }

    @Override
    protected void initView() {
        mRbHome.performClick();
        selectedFragment(0);
        for (int i = 0; i < mTabView.getChildCount(); i++) {
            if (i != 2) {
                RadioButton button = (RadioButton) mTabView.getChildAt(i);
                Drawable[] drawables = button.getCompoundDrawables();
                drawables[1].setBounds(0, 0, 50, 50);
                button.setCompoundDrawables(null, drawables[1], null, null);
            }
        }

        if (LoginUserUtil.isLogin(this)) {
            toRequest(ApiConstants.EventTags.user_info);
        } else {
            setLoginUser(null);
        }

        toRequest(ApiConstants.EventTags.check_app_version);
    }

    private void setLoginUser(UserBean userBean) {
        if (userBean != null) {
            AppImageLoader.loadCircleImage(this, BuildConfig.BASE_URL + userBean.getAvatar(), mIvImg);
            mTvName.setText("" + userBean.getNickname());
            mTvRealName.setText(userBean.getStatus() == 1 ? "已实名" : "未实名");
            mTvRealName.setBackgroundResource(userBean.getStatus() == 1 ? R.drawable.shape_real_name_s : R.drawable.shape_real_name_n);

            LoginUserUtil.setLoginUserBean(this, userBean);
            mTvState.setVisibility(View.VISIBLE);
            mTvRealName.setVisibility(View.VISIBLE);

            if (StrUtils.isEmpty(userBean.getName())){
                //姓名为空，则提示用户补充信息
                showToast("请补充用户姓名等信息");
                gotoActivity(AccountInfoActivity.class);
            }

        } else {
            mTvName.setText("未登录");
            mTvState.setVisibility(View.GONE);
            mTvRealName.setVisibility(View.GONE);
        }
    }

    public void selectedFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch (position) {
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    mHomeFragment.setDrawerLayout(mDrawerLayout);
                    transaction.add(R.id.container_frameLayout, mHomeFragment, "home");
                } else {
                    transaction.show(mHomeFragment);
                }
                break;
            case 1:
                if (mNewsFragment == null) {
                    mNewsFragment = new NewsFragment();
                    transaction.add(R.id.container_frameLayout, mNewsFragment, "news");
                } else {
                    transaction.show(mNewsFragment);
                }
                break;
            case 2:
                if (mProjectFragment == null) {
                    mProjectFragment = new ProjectFragment();
                    transaction.add(R.id.container_frameLayout, mProjectFragment, "project");
                } else {
                    transaction.show(mProjectFragment);
                }
                break;
            case 3:
                if (mFindFragment == null) {
                    mFindFragment = new FindFragment();
                    transaction.add(R.id.container_frameLayout, mFindFragment, "project");
                } else {
                    transaction.show(mFindFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /***
     * 隐藏fragment
     * @param transaction
     */
    public void hideFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mNewsFragment != null) {
            transaction.hide(mNewsFragment);
        }
        if (mProjectFragment != null) {
            transaction.hide(mProjectFragment);
        }
        if (mFindFragment != null) {
            transaction.hide(mFindFragment);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(false);
    }

    /**
     * @param keyCode
     * @param event
     * @return 作后台处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//如果返回键按下
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.drawer_layout, R.id.tv_switch_enterprise, R.id.iv_img, R.id.tv_name, R.id.tv_real_name, R.id.iv_qr_code,
            R.id.tv_state, R.id.tv_mine_info, R.id.rl_mine_card, R.id.rl_feedback, R.id.rl_setting,
            R.id.rb_home, R.id.rb_news, R.id.iv_add, R.id.rb_project, R.id.rb_find})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case 1:
                mDrawerLayout.openDrawer(Gravity.START);
                mDrawerLayout.openDrawer(Gravity.END);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.tv_switch_enterprise:
                if (!LoginUserUtil.isLogin(this)) {
                    ToastUtil.showToast("请先登录");
                    gotoActivity(LoginActivity.class, 0);
                } else {
                    gotoActivity(SwitchEnterpriseActivity.class);
                }
                break;
            case R.id.iv_img:
                List<PreviewAdapter.PreviewBean> previewBeans = new ArrayList();
                previewBeans.add(new PreviewAdapter.PreviewBean(BuildConfig.BASE_URL + LoginUserUtil.getLoginUserBean(this).getAvatar()));
                Bundle bundle = new Bundle();
                bundle.putSerializable("PreviewBean", (Serializable) previewBeans);
                gotoActivity(PreviewActivity.class, bundle);
                break;
            case R.id.tv_name:
            case R.id.tv_real_name:
            case R.id.tv_mine_info:
                if (!LoginUserUtil.isLogin(this)) {
                    ToastUtil.showToast("请先登录");
                    gotoActivity(LoginActivity.class, 0);
                } else {
                    gotoActivity(AccountInfoActivity.class, 0);
                }
                break;
            case R.id.iv_qr_code:
                if (!LoginUserUtil.isLogin(this)) {
                    ToastUtil.showToast("请先登录");
                    gotoActivity(LoginActivity.class, 0);
                } else {
                    gotoActivity(QrCodeActivity.class);
                }
                break;
            case R.id.tv_state:
                if (!LoginUserUtil.isLogin(this)) {
                    ToastUtil.showToast("请先登录");
                    gotoActivity(LoginActivity.class, 0);
                } else {
                }
                break;
            case R.id.rl_mine_card:
                if (!LoginUserUtil.isLogin(this)) {
                    ToastUtil.showToast("请先登录");
                    gotoActivity(LoginActivity.class, 0);
                } else {
                    gotoActivity(MyInfoCardActivity.class);
                }
                break;
            case R.id.rl_feedback:
                bundle = new Bundle();
                bundle.putString("title", "" + "意见反馈");
                bundle.putString("url", "" + Constants.URL_FEEDBACK);
                gotoActivity(WebActivity.class, bundle, 0);
                break;
            case R.id.rl_setting:
                gotoActivity(SettingActivity.class);
                break;
            case R.id.rb_home:
                selectedFragment(0);
                break;
            case R.id.rb_news:
                selectedFragment(1);
                break;
            case R.id.iv_add:
                showAddMenu();
                break;
            case R.id.rb_project:
                selectedFragment(2);
                break;
            case R.id.rb_find:
                selectedFragment(3);
//                List<String> list = new ArrayList<>();
//                list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                list.add(Manifest.permission.CAMERA);
//                list.add(Manifest.permission.RECORD_AUDIO);
//                XXPermissions.with(this).permission(list).request(new OnPermission() {
//                    @Override
//                    public void hasPermission(List<String> granted, boolean isAll) {
//                        if (isAll) {
//                            gotoActivity(PictureOrVideoActivity.class);
//                        } else {
//                            ToastUtil.showToast("请先开启权限");
//                        }
//                    }
//
//                    @Override
//                    public void noPermission(List<String> denied, boolean quick) {
//                    }
//                });
                break;
        }
    }


    public void showAddMenu() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_main_add_menu);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setBackgroundDrawableResource(R.color.theme_white);
        lp.width = ViewUtil.getScreenWidth(this);
        dialog.getWindow().setAttributes(lp);
        dialog.setCancelable(false);
        dialog.show();
        ImageView imageView = dialog.findViewById(R.id.iv_cancel);
        TextView mTvName = dialog.findViewById(R.id.tv_user_name);
        TextView mTvDate = dialog.findViewById(R.id.tv_date);
        TextView mTvWeek = dialog.findViewById(R.id.tv_week);
        TextView mTvDayConut = dialog.findViewById(R.id.tv_count);
        TextView mTvTodo = dialog.findViewById(R.id.tv_todo_count);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_nav_add_menu);
        animation.setInterpolator(new LinearInterpolator());
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
        Calendar calendar = Calendar.getInstance();
//        calendar.setFirstDayOfWeek(7);
        calendar.setFirstDayOfWeek(Calendar.SATURDAY);
        mTvDate.setText((calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH));
        mTvWeek.setText(getWeek(calendar));

        mTvName.setText("Hi，" + LoginUserUtil.getLoginUserBean(this).getNickname());
        mTvTodo.setText("0");

        long startTime = LoginUserUtil.getLoginUserBean(this).getMake_time() * 1000;
        long endTime = System.currentTimeMillis();
        long betweenDays = 0;
        betweenDays = ((endTime - startTime) / (1000 * 60 * 60 * 24));
        mTvDayConut.setText("" + betweenDays);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.ll_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.ll_add_project).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(AddProjectActivity.class);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.ll_add_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(AddWorkLogActivity.class);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.ll_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("company_id", getCompanyId());
                gotoActivity(ArrangeTaskAc.class, bundle);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.ll_add_project_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("company_id", LoginUserUtil.getCurrentEnterpriseNo(MainActivity.this));
                gotoActivity(ProjectTaskAddActivity.class, bundle);
                dialog.dismiss();
            }
        });
    }

    private String getWeek(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        switch (dayOfWeek) {
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
        }
        return "星期天";
    }


    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        if (eventTag == ApiConstants.EventTags.user_info) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.user_info, map);
        } else if (eventTag == ApiConstants.EventTags.check_app_version) {
            iCommonRequestPresenter.request(eventTag, this, ApiConstants.check_app_version, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        if (eventTag == ApiConstants.EventTags.check_app_version) {
            try {
                JSONObject json = new JSONObject(result);
                String version = json.getString("version");
                if (BuildConfig.VERSION_NAME.equals(version)){

                } else {
                    if (!AppVersionUtils.needUpdate(version)){
                        return;
                    }

                    TipsDialog.wrap(this)
                            .setTitle("版本更新")
                            .setShowCancel(true)
                            .setMsg("发现新版本：" + version)
                            .setMsgColor(ResourceUtil.getColor("#FFE51C23"))
                            .setOnActionClickListener(new TipsDialog.onActionClickListener() {
                                @Override
                                public void onSure(Dialog dialog) {
                                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Conf.URL_APP_VERSION));
                                    startActivity(i);
                                    System.exit(0);
                                }
                            }).show();
                }
                String url = json.getString("url");
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.user_info) {
                UserBean userBean = JSON.parseObject(bean.getData(), UserBean.class);
                setLoginUser(userBean);
            }
        } else {
            showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
            if (bean.getCode() == 1 || bean.getCode() == 22) {
                EventBus.getDefault().post(new BaseEvent(BaseEvent.EVENT_LOGOUT));
            }
        }
    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            toRequest(ApiConstants.EventTags.user_info);
        }
    }

    @Override
    public void onEvent(BaseEvent postResult) {
        super.onEvent(postResult);
        if (postResult.type.equals(BaseEvent.EVENT_LOGIN)) {
            toRequest(ApiConstants.EventTags.user_info);
        }
        if (postResult.type.equals(BaseEvent.EVENT_LOGOUT)) {
            setLoginUser(null);
            LoginUserUtil.exitLogin(this);
            TipsDialog.wrap(this).setMsg("登录失效，是否重新登录？").setTitle("登录提示")
                    .setShowCancel(false).setOnActionClickListener(new TipsDialog.onActionClickListener() {
                @Override
                public void onSure(Dialog dialog) {
                    gotoActivity(LoginActivity.class, 0);
                    dialog.dismiss();
                }
            }).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        getManageAll();
    }
}