package com.aopcloud.palmproject.ui.activity.task.list;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.adapter.feagment.AppFragmentPagerAdapter;
import com.aopcloud.palmproject.ui.fragment.home.HomeTaskFragment;
import com.cily.utils.base.StrUtils;
import com.cilys.app.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.ac_task_list)
public class TaskListAc extends BaseActivity {
    private List<Fragment> fgs;
    private TextView tv_title;

    @Override
    protected void initView() {
        String type = getIntent().getStringExtra("type");
        String state = getIntent().getStringExtra("state");
        if (StrUtils.isEmpty(type)) {
            type = "2";
        }
        tv_title = (TextView)findViewById(R.id.tv_header_title);
        if ("3".equals(type)){
            tv_title.setText("我参与的∨");
        } else if ("1".equals(type)){
            tv_title.setText("我发起的∨");
        } else {
            tv_title.setText("派给我的∨");
        }

        NoScrollViewPager noVp = (NoScrollViewPager)findViewById(R.id.noVp);
        String[] states = {
                HomeTaskFragment.STATE_all,
                HomeTaskFragment.STATE_progress,
                HomeTaskFragment.STATE_expect,
                HomeTaskFragment.STATE_complete,
                HomeTaskFragment.STATE_no_start,
                HomeTaskFragment.STATE_cancel,
                HomeTaskFragment.STATE_pause,
                HomeTaskFragment.STATE_operation
        };

        int defaultItem = 0;


        fgs = new ArrayList<>();
        for (int i = 0; i < states.length; i++) {
            TaskListFg fg = new TaskListFg();
            Bundle bundle = new Bundle();
            bundle.putString("state", states[i]);
            bundle.putString("type", type);
            fg.setArguments(bundle);
            fgs.add(fg);

            if (states[i].equals(state)){
                defaultItem = i;
            }
        }

        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(tv_title);
            }
        });

        final HorizontalScrollView hsv = (HorizontalScrollView)findViewById(R.id.hsv);

        final RadioButton rbt_all = (RadioButton)findViewById(R.id.rbt_all);
        final RadioButton rbt_progress = (RadioButton)findViewById(R.id.rbt_progress);
        final RadioButton rbt_expect = (RadioButton)findViewById(R.id.rbt_expect);
        final RadioButton rbt_complete = (RadioButton)findViewById(R.id.rbt_complete);
        final RadioButton rbt_no_start = (RadioButton)findViewById(R.id.rbt_no_start);
        final RadioButton rbt_cancel = (RadioButton)findViewById(R.id.rbt_cancel);
        final RadioButton rbt_pause = (RadioButton)findViewById(R.id.rbt_pause);
        final RadioButton rbt_operation = (RadioButton)findViewById(R.id.rbt_operation);

        AppFragmentPagerAdapter adapter = new AppFragmentPagerAdapter(getSupportFragmentManager(), fgs, null);
        noVp.setAdapter(adapter);
        noVp.setCurrentItem(defaultItem);
        if (defaultItem == 1) {
            rbt_progress.setChecked(true);
        } else if (defaultItem == 2){
            rbt_expect.setChecked(true);
        } else if (defaultItem == 3){
            rbt_complete.setChecked(true);
        } else if (defaultItem == 4){
            rbt_no_start.setChecked(true);
        } else if (defaultItem == 5){
            rbt_cancel.setChecked(true);
        } else if (defaultItem == 6){
            rbt_pause.setChecked(true);
        } else if (defaultItem == 7){
            rbt_operation.setChecked(true);
        } else {
            rbt_all.setChecked(true);
        }
        if (defaultItem == 5){
            hsv.smoothScrollTo(3000, 0);
        } else if (defaultItem == 2){
            hsv.smoothScrollTo(-50, 0);
        }

        noVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 1) {
                    rbt_progress.setChecked(true);
                } else if (i == 2){
                    rbt_expect.setChecked(true);
                } else if (i == 3){
                    rbt_complete.setChecked(true);
                } else if (i == 4){
                    rbt_no_start.setChecked(true);
                } else if (i == 5){
                    rbt_cancel.setChecked(true);
                } else if (i == 6){
                    rbt_pause.setChecked(true);
                } else if (i == 7){
                    rbt_operation.setChecked(true);
                } else {
                    rbt_all.setChecked(true);
                }
                if (i == 5){
                    hsv.smoothScrollTo(3000, 0);
                } else if (i == 2){
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
                } else if (i == R.id.rbt_expect){
                    noVp.setCurrentItem(2);
                } else if (i == R.id.rbt_complete){
                    noVp.setCurrentItem(3);
                } else if (i == R.id.rbt_no_start){
                    noVp.setCurrentItem(4);
                } else if (i == R.id.rbt_cancel){
                    noVp.setCurrentItem(5);
                } else if (i == R.id.rbt_pause){
                    noVp.setCurrentItem(6);
                } else if (i == R.id.rbt_operation){
                    noVp.setCurrentItem(7);
                } else {
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

    private void showToast(CharSequence str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
