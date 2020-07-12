package com.aopcloud.palmproject.ui.activity.task;

import android.view.View;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.palmproject.R;

@Layout(R.layout.ac_arrange_task)
public class ArrangeTaskAc extends BaseActivity {

    @Override
    protected void initView() {
        String company_id = getIntent().getStringExtra("company_id");
        String company_name = getIntent().getStringExtra("company_name");
        String project_id = getIntent().getStringExtra("project_id");
        String project_name = getIntent().getStringExtra("project_name");
        String task_id = getIntent().getStringExtra("task_id");
        String task_name = getIntent().getStringExtra("task_name");
        String work_count = getIntent().getStringExtra("work_count");
        String work_unit = getIntent().getStringExtra("work_unit");




        findViewById(R.id.ll_header_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView tv_title = (TextView)findViewById(R.id.tv_header_title);
        tv_title.setText("派单");

        TextView tv_company = (TextView)findViewById(R.id.tv_company);
        tv_company.setText(company_name);

        TextView tv_project_name = (TextView)findViewById(R.id.tv_project_name);
        tv_project_name.setText(project_name);

        TextView tv_task_name = (TextView)findViewById(R.id.tv_task_name);
        tv_task_name.setText(task_name);

        TextView tv_work = (TextView)findViewById(R.id.tv_work);
        tv_work.setText(work_count + work_unit);

        TextView tv_class_name = (TextView)findViewById(R.id.tv_class_name);

        TextView tv_start_date = (TextView)findViewById(R.id.tv_start_date);
        TextView tv_end_date = (TextView)findViewById(R.id.tv_end_date);

        findViewById(R.id.ll_class_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findViewById(R.id.ll_start_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findViewById(R.id.ll_end_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
