package com.aopcloud.palmproject.ui.activity.task.list;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.conf.TaskStatus;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.adapter.feagment.AppFragmentPagerAdapter;
import com.aopcloud.palmproject.ui.fragment.home.HomeTaskFragment;
import com.aopcloud.palmproject.ui.fragment.project.DashboardFragment;
import com.cily.utils.base.StrUtils;
import com.cilys.app.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.ac_task_list)
public class TaskListAc extends BaseAc {
    private List<Fragment> fgs;
    private TextView tv_title;
    private RadioButton[] rbts;
    private String type, state;

    @Override
    protected void initView() {
        type = getIntent().getStringExtra("type");
        state = getIntent().getStringExtra("state");
        if (StrUtils.isEmpty(type)) {
            type = "2";
        }
        tv_title = (TextView)findViewById(R.id.tv_header_title);
        TextView tv_title_left = (TextView)findViewById(R.id.tv_header_left);
        TextView tv_header_right = (TextView)findViewById(R.id.tv_header_right);
        tv_header_right.setText("重要情况");
        findViewById(R.id.ll_header_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if ("3".equals(type)){
            tv_title.setText("我参与的 ∨");
        } else if ("1".equals(type)){
            tv_title.setText("我发起的 ∨");
        } else if("0".equals(type)){
            tv_title.setText("全部 ∨");
        } else {
            tv_title.setText("派给我的 ∨");
        }
        if (StrUtils.isEmpty(state)){
            state = HomeTaskFragment.STATE_UNDO;
        }

        final RadioButton rbt_all = (RadioButton)findViewById(R.id.rbt_all);
        final RadioButton rbt_progress = (RadioButton)findViewById(R.id.rbt_progress);
        final RadioButton rbt_complete = (RadioButton)findViewById(R.id.rbt_complete);
        final RadioButton rbt_no_start = (RadioButton)findViewById(R.id.rbt_no_start);
        final RadioButton rbt_cancel = (RadioButton)findViewById(R.id.rbt_cancel);
        final RadioButton rbt_pause = (RadioButton)findViewById(R.id.rbt_pause);

        rbts = new RadioButton[]{rbt_all, rbt_progress, rbt_complete, rbt_no_start, rbt_cancel, rbt_pause};

        NoScrollViewPager noVp = (NoScrollViewPager)findViewById(R.id.noVp);
        String[] states = null;

        states = new String[6];
        states[0] = TaskStatus.STATE_all;
        states[1] = TaskStatus.STATE_no_start;
        states[2] = TaskStatus.STATE_progress;
        states[3] = TaskStatus.STATE_expect;
//        states[4] = HomeTaskFragment.STATE_complete;
        states[4] = "待验收";
        states[5] = TaskStatus.STATE_pause;
        int defSelectItem = 0;
        if (HomeTaskFragment.STATE_DONE.equals(state)){
            tv_title_left.setText("待验收");
            defSelectItem = 4;
        } else if (HomeTaskFragment.STATE_DOING.equals(state)){
            tv_title_left.setText("进行中");
            defSelectItem = 2;
        } else if (HomeTaskFragment.STATE_OUT_OF_TIME.equals(state)
                || HomeTaskFragment.STATE_ALL.equals(state)){
            if (HomeTaskFragment.STATE_ALL.equals(state)){
                tv_title_left.setText("全部");
                defSelectItem = 0;
            }else {
                tv_title_left.setText("已逾期");
                defSelectItem = 3;
            }
        } else {
            tv_title_left.setText("未开始");
            defSelectItem = 1;
        }
        //去掉左上角文字
        tv_title_left.setText("");

        fgs = new ArrayList<>();
        for (int i = 0; i < states.length; i++) {
            TaskListFg fg = new TaskListFg();
            Bundle bundle = new Bundle();
            if ("待验收".equals(states[i])){
                bundle.putString("state", TaskStatus.STATE_complete);
            }else {
                bundle.putString("state", states[i]);
            }
//            if (i == 0) {
//                bundle.putString("state", DashboardFragment.STATUS_ALL);
//            } else if (i == 1){
//                bundle.putString("state", DashboardFragment.STATUS_UN_START);
//            } else if (i == 2){
//                bundle.putString("state", DashboardFragment.STATUS_IN_PROCESS);
//            } else if (i == 3){
//                bundle.putString("state", DashboardFragment.STATUS_OUT_TIME);
//            } else if (i == 4){
//                bundle.putString("state", DashboardFragment.STATUS_COMPLETE);
//            } else if (i == 5){
//                bundle.putString("state", DashboardFragment.STATUS_PAUSE);
//            }
            bundle.putString("type", type);
            bundle.putString("STATE_BIG", state);   //大类，进行中、已完成、未完成、已逾期
            fg.setArguments(bundle);
            fgs.add(fg);

            rbts[i].setText(states[i]);
        }

        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showMenu(tv_title);
                showMenuDialog();
            }
        });

        final HorizontalScrollView hsv = (HorizontalScrollView)findViewById(R.id.hsv);

        AppFragmentPagerAdapter adapter = new AppFragmentPagerAdapter(getSupportFragmentManager(), fgs, null);
        noVp.setAdapter(adapter);
        noVp.setCurrentItem(defSelectItem);
        rbts[defSelectItem].setChecked(true);
        final int defItem = defSelectItem;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (defItem >= 3){
                    hsv.smoothScrollTo(3000, 0);
                } else if (defItem <= 2){
                    hsv.smoothScrollTo(-50, 0);
                }
            }
        }, 100);


        noVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                rbts[i].setChecked(true);

                if (i >= 3){
                    hsv.smoothScrollTo(3000, 0);
                } else if (i <= 2){
                    hsv.smoothScrollTo(-50, 0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        RadioGroup rg = (RadioGroup)findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbt_progress){
                    noVp.setCurrentItem(1);
                }  else if (i == R.id.rbt_complete){
                    noVp.setCurrentItem(2);
                } else if (i == R.id.rbt_no_start){
                    noVp.setCurrentItem(3);
                } else if (i == R.id.rbt_cancel){
                    noVp.setCurrentItem(4);
                } else if (i == R.id.rbt_pause){
                    noVp.setCurrentItem(5);
                }  else {
                    noVp.setCurrentItem(0);
                }
            }
        });
    }


    private void showMenu(View view){
        View popView = LayoutInflater.from(this).inflate(R.layout.pop_task_type, null);

        PopupWindow pop = new PopupWindow(popView, 300, 300);
        pop.setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));

        TextView  tv_2 = (TextView)popView.findViewById(R.id.tv_2);
            tv_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv_title.setText(tv_2.getText().toString() + "∨");
                    changeFragmentType("2");
                    pop.dismiss();
                }
            });

        TextView tv_3 = (TextView)popView.findViewById(R.id.tv_3);
        tv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_title.setText(tv_3.getText().toString() + "∨");
                changeFragmentType("3");
                pop.dismiss();
            }
        });

        TextView    tv_1 = (TextView)popView.findViewById(R.id.tv_1);
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_title.setText(tv_1.getText().toString() + "∨");
                changeFragmentType("1");
                pop.dismiss();
            }
        });
        pop.setOutsideTouchable(true);

        pop.showAsDropDown(view, 270, 0);
    }

    private void changeFragmentType(String type){
        if (fgs != null){
            for (Fragment fg : fgs){
                if (fg instanceof TaskListFg){
                    ((TaskListFg) fg).onChange(type);
                }
            }
        }
    }

    public void showMenuDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.dialog_project_task_type_title);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        dialog.show();

        TextView title = dialog.findViewById(R.id.tv_title);
        title.setVisibility(View.GONE);

        TextView tv_type_all = (TextView)dialog.findViewById(R.id.tv_type_all);
        tv_type_all.setText("全部");
        tv_type_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                tv_title.setText("全部 ∨");
                changeFragmentType("0");
            }
        });

        TextView tv_copy = (TextView) dialog.findViewById(R.id.tv_copy);
        tv_copy.setText("派给我的");
        tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                if ("2".equals(type)){
//                    return;
//                }

                tv_title.setText("派给我的 ∨");
                changeFragmentType("2");

//                Bundle b = new Bundle();
//                b.putString("type", "2");
//                b.putString("state", state);
//                gotoActivity(TaskListAc.class, b);
//                finish();
            }
        });
        TextView tv_edit = dialog.findViewById(R.id.tv_edit);
        tv_edit.setText("我参与的");
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                if ("3".equals(type)){
//                    return;
//                }

                tv_title.setText("我参与的 ∨");
                changeFragmentType("3");
//
//                dialog.dismiss();
//
//                Bundle b = new Bundle();
//                b.putString("type", "3");
//                b.putString("state", state);
//                gotoActivity(TaskListAc.class, b);
//                finish();
            }
        });

        TextView tv_del = dialog.findViewById(R.id.tv_del);
        tv_del.setText("我发起的");
        tv_del.setTextColor(0xFF41484D);
        tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                if ("1".equals(type)){
//                    return;
//                }

                tv_title.setText("我发起的 ∨");
                changeFragmentType("1");

//
//                Bundle b = new Bundle();
//                b.putString("type", "1");
//                b.putString("state", state);
//                gotoActivity(TaskListAc.class, b);
//                finish();
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}