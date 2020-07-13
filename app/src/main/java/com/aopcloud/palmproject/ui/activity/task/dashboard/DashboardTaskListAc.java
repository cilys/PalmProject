package com.aopcloud.palmproject.ui.activity.task.dashboard;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.palmproject.R;

/**
 * 项目详情、看板、任务分类列表
 */
@Layout(R.layout.ac_dashboard_task_list)
public class DashboardTaskListAc extends BaseActivity {
    private SwipeRefreshLayout srl;

    @Override
    protected void initView() {
        String state = getIntent().getStringExtra("state");

        TextView tv_title = (TextView)findViewById(R.id.tv_header_title);
        tv_title.setText(state == null ? "" : state);

        findViewById(R.id.ll_header_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        srl = (SwipeRefreshLayout)findViewById(R.id.srl);
        RecyclerView rv = findViewById(R.id.rv);

    }
}
