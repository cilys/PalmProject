package com.aopcloud.palmproject.ui.activity.task;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.adapter.feagment.AppFragmentPagerAdapter;
import com.aopcloud.palmproject.ui.fragment.task.TaskFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task
 * @File : TaskManageActivity.java
 * @Date : 2020/5/7 2020/5/7
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_task_manage)
public class TaskManageActivity extends BaseAc {
    @BindView(R.id.ll_header_back)
    LinearLayout mLlHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mTvHeaderTitle;
    @BindView(R.id.tv_header_right)
    TextView mTvHeaderRight;
    @BindView(R.id.ll_header_right)
    LinearLayout mLlHeaderRight;
    @BindView(R.id.ll_common_layout)
    LinearLayout mLlCommonLayout;
    @BindView(R.id.tab_view)
    SlidingTabLayout mTabView;
    @BindView(R.id.page_view)
    ViewPager mPageView;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;


    private List<Fragment> mFragments;
    private List<String> mTabs;

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("任务列表");
        mTvHeaderRight.setText("");


        mFragments = new ArrayList<>();
        mTabs = new ArrayList<>();
        mTabs.add("我发起的");
        mTabs.add("派给我的");
        mTabs.add("我参与的");
        mFragments.add(TaskFragment.getInstance("1"));
        mFragments.add(TaskFragment.getInstance("2"));
        mFragments.add(TaskFragment.getInstance("3"));
        mPageView.setAdapter(new AppFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTabs));
        mPageView.setCurrentItem(0);
        mTabView.setViewPager(mPageView);
        mPageView.setOffscreenPageLimit(mFragments.size());
    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.iv_add:
                showAddMenu();
                break;
        }
    }

    public void showAddMenu() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_task_add_menu);
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
        calendar.setFirstDayOfWeek(7);
        mTvDate.setText((calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH));
        mTvWeek.setText(getWeek(calendar));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.ll_task_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("敬请期待");
                //  gotoActivity(AddTaskActivity.class);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.ll_task_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("敬请期待");
                // gotoActivity(AddTaskActivity.class);
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
}
