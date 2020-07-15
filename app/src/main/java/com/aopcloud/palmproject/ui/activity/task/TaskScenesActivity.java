package com.aopcloud.palmproject.ui.activity.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.KeyboardUtil;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectSceneBean;
import com.aopcloud.palmproject.ui.activity.task.bean.TaskScenesBean;
import com.aopcloud.palmproject.ui.activity.task.bean.TaskTrendsBean;
import com.aopcloud.palmproject.ui.adapter.project.ProjectScenesAdapter;
import com.aopcloud.palmproject.ui.adapter.task.TaskScenesAdapter;
import com.aopcloud.palmproject.ui.adapter.task.TaskTrendsAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.HorizontalDividerItemDecoration;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task
 * @File : TaskScenesActivity.java
 * @Date : 2020/5/21 2020/5/21
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_task_scenes)
public class TaskScenesActivity extends BaseAc implements TextView.OnEditorActionListener {
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
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.spinner_type)
    Spinner mSpinnerType;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    private TaskScenesAdapter mAdapter;
    private List<TaskScenesBean> mBeanList = new ArrayList<>();
    private List<TaskScenesBean> mAllBeanList = new ArrayList<>();
    private String task_id;

    private int pageIndex = 1;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            task_id = bundle.getString("task_id");
        }
        toRequest(ApiConstants.EventTags.scenes_all);
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("影像");

        mEtSearch.setOnEditorActionListener(this);

        mAdapter = new TaskScenesAdapter(R.layout.item_project_scenes, mBeanList);
        mRvList.setBackgroundResource(R.color.theme_background_f5);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);

    }

    @Override
    protected void setListener() {
        super.setListener();
        mSpinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = (String) mSpinnerType.getSelectedItem();
                setFilter(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setViewData(List<TaskScenesBean> beanList) {
        mAllBeanList.clear();
        mBeanList.clear();
        mAllBeanList.addAll(beanList);
        mBeanList.addAll(beanList);
        mAdapter.notifyDataSetChanged();
    }


    private void setFilter(String type) {
        Log.i(TAG, "---------" + type + "/" + 1);
        List<TaskScenesBean> beans = new ArrayList<>();
        if (!TextUtils.isEmpty(type) && type.contains("全部")) {
            beans.addAll(mAllBeanList);
        } else {
            for (int i = 0; i < mAllBeanList.size(); i++) {
                if (mAllBeanList.get(i).getTags().contains(type)) {
                    beans.add(mAllBeanList.get(i));
                }
            }
        }
        mBeanList.clear();
        mBeanList.addAll(beans);
        mAdapter.notifyDataSetChanged();
    }
    private void setUserFilter(String name) {
        Log.d(TAG, "---------" + name + "/" + 1);
        List<TaskScenesBean> beans = new ArrayList<>();
        if (!TextUtils.isEmpty(name) && name.contains("全部")) {
            beans.addAll(mAllBeanList);
        } else {
            for (int i = 0; i < mAllBeanList.size(); i++) {
                if (mAllBeanList.get(i).getUser_name().contains(name)) {
                    beans.add(mAllBeanList.get(i));
                }
            }
        }
        mBeanList.clear();
        mBeanList.addAll(beans);
        mAdapter.notifyDataSetChanged();
    }

    private void searchList(String k) {
        mBeanList.clear();
        for (int i = 0; i < mAllBeanList.size(); i++) {
            if (mAllBeanList.get(i).getTask_name().contains(k)
                    || mAllBeanList.get(i).getUser_name().contains(k)
                    || mAllBeanList.get(i).getTags().contains(k)) {
                mBeanList.add(mAllBeanList.get(i));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_header_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_header_title:
                showUser();
                break;
        }
    }

    private void showUser() {
        List<String> list = new ArrayList();
        list.add("全部");
        for (int i = 0; i < mBeanList.size(); i++) {
            if (!list.contains(mBeanList.get(i).getUser_name())){
                list.add(mBeanList.get(i).getUser_name());
            }
        }
        OptionsPickerView pickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mTvHeaderTitle.setText(""+list.get(options1));

                setUserFilter(list.get(options1));

            }
        })
                .build();

        pickerView.setPicker(list);
        pickerView.show();

    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("task_id", "" + task_id);//项目名称
        map.put("page", "" + pageIndex);
        map.put("page_size", "20");
        if (eventTag == ApiConstants.EventTags.scenes_all) {
            Log.i(TAG, "------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.scenes_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Log.i(TAG, "------------" + eventTag + "/" + result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.scenes_all) {
                List<TaskScenesBean> beanList = JSON.parseArray(bean.getData(), TaskScenesBean.class);
                setViewData(beanList);
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
        Log.i(TAG, "------------" + eventTag + "/" + msg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        toRequest(ApiConstants.EventTags.scenes_all);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        String keyWord = v.getText().toString();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtil.showToast("请输入搜索内容");
        } else {
            KeyboardUtil.closeKeyboard(mEtSearch, this);
            mEtSearch.clearFocus();
            searchList(keyWord);
        }
        return true;
    }
}
