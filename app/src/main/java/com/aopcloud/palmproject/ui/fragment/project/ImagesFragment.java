package com.aopcloud.palmproject.ui.fragment.project;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.base.BaseFragment;
import com.aopcloud.base.util.KeyboardUtil;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.camera.PreviewActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectSceneBean;
import com.aopcloud.palmproject.ui.adapter.file.PreviewAdapter;
import com.aopcloud.palmproject.ui.adapter.project.ProjectScenesAdapter;
import com.aopcloud.palmproject.ui.adapter.project.SpinnerItemAdapter;
import com.aopcloud.palmproject.utils.ListUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.HorizontalDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.project
 * @File : DashboardFragment.java
 * @Date : 2020/5/2 2020/5/2
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class ImagesFragment extends BaseFragment implements TextView.OnEditorActionListener {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.spinner_child)
    Spinner mSpinnerChild;
    @BindView(R.id.spinner_type)
    Spinner mSpinnerType;
    @BindView(R.id.et_search)
    EditText mEtSearch;

    private ProjectScenesAdapter mAdapter;
    private List<ProjectSceneBean.ScenesBean> mBeanList = new ArrayList();
    private List<ProjectSceneBean.ScenesBean> mAllBeanList = new ArrayList();

    private SpinnerItemAdapter mTaskAdapter;


    public static ImagesFragment getInstance(String project_id) {
        ImagesFragment fragment = new ImagesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("project_id", "" + project_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String project_id;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            project_id = bundle.getString("project_id");
        }
        toRequest(ApiConstants.EventTags.scenes_all);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_project_images;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mAdapter = new ProjectScenesAdapter(R.layout.item_project_scenes, mBeanList);
        HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(mActivity)
                .margin(0)
                .size(ViewUtil.dp2px(mActivity, 1))
                .color(ResourceUtil.getColor(R.color.transparent))
                .build();
        mRvList.setBackgroundResource(R.color.theme_background_f5);
        mRvList.addItemDecoration(decoration);
        mRvList.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvList.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_common_empty, mRvList);
        mAdapter.isUseEmpty(true);
        mEtSearch.setOnEditorActionListener(this::onEditorAction);


        mTaskAdapter = new SpinnerItemAdapter(mActivity, R.layout.item_spinner_team, mTaskList);
        mSpinnerChild.setAdapter(mTaskAdapter);

    }

    private List<SpinnerItemAdapter.SpinnerItemBean> mTaskList = new ArrayList();

    private void setViewData(List<ProjectSceneBean> beanList) {

        mTaskList.clear();
        mTaskList.add(new SpinnerItemAdapter.SpinnerItemBean(-1, "全部工单"));

        List<ProjectSceneBean.ScenesBean> beans = new ArrayList<>();
        for (int i = 0; i <beanList.size() ; i++) {
            if (!ListUtil.isEmpty(beanList.get(i).getScenes())){
                mTaskList.add(new SpinnerItemAdapter.SpinnerItemBean(Integer.valueOf(beanList.get(i).getTask_id()), beanList.get(i).getScenes().get(0).getTask_name()));
                beans.addAll(beanList.get(i).getScenes());
            }
        }

        mBeanList.clear();
        mAllBeanList.clear();
        mAllBeanList.addAll(beans);
        mBeanList.addAll(mAllBeanList);
        mAdapter.notifyDataSetChanged();

        mTaskAdapter.notifyDataSetChanged();
    }

    private void setFilter( String type, int id) {
        Log.d(TAG, "---------" + type +  "/" + id);

        List<ProjectSceneBean.ScenesBean> beans = new ArrayList<>();

        if (!TextUtils.isEmpty(type)&&type.contains("全部")||TextUtils.isEmpty(type)&&id==-1){
            beans.addAll(mAllBeanList);
        }else {
            for (int i = 0; i <mAllBeanList.size() ; i++) {
                if (TextUtils.isEmpty(type)){
                    if (mAllBeanList.get(i).getTask_id()==id){
                        beans.add(mAllBeanList.get(i));
                    }
                }else {
                    if (mAllBeanList.get(i).getTags().contains(type)){
                        beans.add(mAllBeanList.get(i));
                    }
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

    @Override
    protected void setListener() {
        super.setListener();

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<PreviewAdapter.PreviewBean> list = new ArrayList();
                if (!TextUtils.isEmpty(mBeanList.get(position).getAttach())) {
                    String[] url = mBeanList.get(position).getAttach().split(",");
                    for (int i = 0; i < url.length; i++) {
                        list.add(new PreviewAdapter.PreviewBean(BuildConfig.BASE_URL + url[i]));
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("PreviewBean", (Serializable) list);
                gotoActivity(PreviewActivity.class, bundle);

            }
        });
        mAdapter.setOnItemFileClickListener(new ProjectScenesAdapter.OnItemFileClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<PreviewAdapter.PreviewBean> list = new ArrayList();
                if (!TextUtils.isEmpty(mBeanList.get(position).getAttach())) {
                    String[] url = mBeanList.get(position).getAttach().split(",");
                    for (int i = 0; i < url.length; i++) {
                        list.add(new PreviewAdapter.PreviewBean(BuildConfig.BASE_URL + url[i]));
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("PreviewBean", (Serializable) list);
                gotoActivity(PreviewActivity.class, bundle);
            }
        });

        mSpinnerChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItemAdapter.SpinnerItemBean itemBean = (SpinnerItemAdapter.SpinnerItemBean) mSpinnerChild.getSelectedItem();
                setFilter(null,itemBean.getId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = (String) mSpinnerType.getSelectedItem();
                setFilter(type,-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(mActivity));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(mActivity));
        map.put("project_id", "" + project_id);//项目名称
        if (eventTag == ApiConstants.EventTags.scenes_all) {
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.scenes_all, map);
        } else if (eventTag == ApiConstants.EventTags.project_tasks) {
            iCommonRequestPresenter.requestPost(eventTag, mActivity, ApiConstants.project_tasks, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.scenes_all) {
                List<ProjectSceneBean> beanList = JSON.parseArray(bean.getData(), ProjectSceneBean.class);
                setViewData(beanList);
            } else if (eventTag == ApiConstants.EventTags.project_tasks) {
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
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
            KeyboardUtil.closeKeyboard(mEtSearch, mActivity);
            mEtSearch.clearFocus();
            searchList(keyWord);
        }
        return true;
    }
}