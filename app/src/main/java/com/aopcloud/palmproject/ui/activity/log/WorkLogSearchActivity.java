package com.aopcloud.palmproject.ui.activity.log;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.KeyboardUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.log.bean.WorkLogListBean;
import com.aopcloud.palmproject.ui.adapter.log.WorkLogAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.log
 * @File : WorkLogSearchActivity.java
 * @Date : 2020/4/22 11:29
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_work_log_search)
public class WorkLogSearchActivity extends BaseActivity implements TextView.OnEditorActionListener {
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.ll_search_menu)
    LinearLayout mLlSearchMenu;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.rb_day)
    RadioButton mRbDay;
    @BindView(R.id.rb_week)
    RadioButton mRbWeek;
    @BindView(R.id.rb_month)
    RadioButton mRbMonth;
    @BindView(R.id.tv_project)
    TextView mTvProject;
    @BindView(R.id.tv_worker)
    TextView mTvWorker;
    @BindView(R.id.tv_tag)
    TextView mTvTag;
    @BindView(R.id.iv_mic)
    ImageView mIvMic;
    private WorkLogAdapter mLogAdapter;
    private List<WorkLogListBean> mListBean = new ArrayList();

    private String type = "";
    private int pageIndex = 1;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.reset()
                .fitsSystemWindows(true)
                .barColor("#ffffff")
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initView() {
        mEtSearch.setOnEditorActionListener(this);
        mLogAdapter = new WorkLogAdapter(R.layout.item_work_log, mListBean);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mLogAdapter);
        mLogAdapter.setEmptyView(R.layout.base_layout_empty,mRvList);
        mLogAdapter.isUseEmpty(true);
    }


    private void setViewData(List<WorkLogListBean> beanList) {

        if (pageIndex == 1) {
            mListBean.clear();
        }
        mListBean.addAll(beanList);
        mLogAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.i(TAG, "搜索内容：" + v.getText().toString());
        String keyWord = v.getText().toString();
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtil.showToast("请输入搜索内容");
        } else {
            KeyboardUtil.closeKeyboard(this);
//            mListBean.add(logListBean);
            mLogAdapter.notifyDataSetChanged();
            mLlSearchMenu.setVisibility(View.GONE);
            mRvList.setVisibility(View.VISIBLE);
            toRequest(ApiConstants.EventTags.reportjob_all);
        }
        return true;
    }


    @OnClick({R.id.tv_cancel, R.id.rb_day, R.id.rb_week, R.id.rb_month})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.rb_day:
                type = "";
                break;
            case R.id.rb_week:
                type = "";
                break;
            case R.id.rb_month:
                type = "";
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        if (eventTag == ApiConstants.EventTags.reportjob_all) {
            map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
            map.put("role", "1");
            map.put("page", "" + pageIndex);
            map.put("page_size", "20");
            map.put("type", "" + type);//(可选)类型；1：日报，2：周报，3：月报，不填则代表获取全部

            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.reportjob_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.reportjob_all) {
                List<WorkLogListBean> beanList = JSON.parseArray(bean.getData(), WorkLogListBean.class);
                setViewData(beanList);

            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
    }
}
