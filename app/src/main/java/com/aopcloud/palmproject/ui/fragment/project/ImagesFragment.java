package com.aopcloud.palmproject.ui.fragment.project;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
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
import com.aopcloud.palmproject.dialog.BottomListMulDialog;
import com.aopcloud.palmproject.dialog.bean.MulBean;
import com.aopcloud.palmproject.ui.activity.camera.PreviewActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectSceneBean;
import com.aopcloud.palmproject.ui.adapter.file.PreviewAdapter;
import com.aopcloud.palmproject.ui.adapter.project.ProjectScenesAdapter;
import com.aopcloud.palmproject.ui.adapter.project.SpinnerItemAdapter;
import com.aopcloud.palmproject.utils.ListUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.HorizontalDividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cily.utils.base.StrUtils;

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
    @BindView(R.id.tv_child)
    TextView tv_child;

    @BindView(R.id.et_search)
    EditText mEtSearch;

    @BindView(R.id.tv_type)
    TextView tv_type;

    private ProjectScenesAdapter mAdapter;
    private List<ProjectSceneBean.ScenesBean> mBeanList = new ArrayList();
    private List<ProjectSceneBean.ScenesBean> mAllBeanList = new ArrayList();


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

        tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTypeDialog();
            }
        });
        tv_type.setText("全部");
        Drawable df = getResources().getDrawable(R.mipmap.icon_sort_down);
        df.setBounds(0, 0, 46, 26);
        tv_type.setCompoundDrawables(null, null, df, null);

        tv_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChildDialog();
            }
        });
        tv_child.setText("全部工单");
        tv_child.setCompoundDrawables(null, null, df, null);
    }
    private List<MulBean> datas_type = new ArrayList<>();
    private void showTypeDialog(){
        if (datas_type == null) {
            datas_type = new ArrayList<>();
        }
        if (datas_type.size() < 1) {
            MulBean m0 = new MulBean();
            m0.setId("0");
            m0.setName("全部");
            m0.setSelected(true);
            datas_type.add(m0);

            MulBean m1 = new MulBean();
            m1.setId("1");
            m1.setName("进度");
            datas_type.add(m1);

            MulBean m2 = new MulBean();
            m2.setId("2");
            m2.setName("质量");
            datas_type.add(m2);

            MulBean m3 = new MulBean();
            m3.setId("3");
            m3.setName("安全");
            datas_type.add(m3);

            MulBean m4 = new MulBean();
            m4.setId("4");
            m4.setName("签到");
            datas_type.add(m4);

            MulBean m5 = new MulBean();
            m5.setId("5");
            m5.setName("签退");
            datas_type.add(m5);

            MulBean m6 = new MulBean();
            m6.setId("6");
            m6.setName("材料");
            datas_type.add(m6);

            MulBean m7 = new MulBean();
            m7.setId("7");
            m7.setName("机械");
            datas_type.add(m7);

            MulBean m8 = new MulBean();
            m8.setId("8");
            m8.setName("其他");
            datas_type.add(m8);
        }
        for (MulBean b : datas_type){
            if (tv_type.getText().toString().trim().equals(b.getName())) {
                b.setSelected(true);
            }
        }
        BottomListMulDialog dialog = new BottomListMulDialog(getActivity(), false, datas_type);
        dialog.setOnClickListener(new BottomListMulDialog.OnClickListener() {
            @Override
            public void onCommit(View view) {
                String type = "";
                for (MulBean b : datas_type) {
                    if (b.isSelected()){
                        type = b.getName();
                        break;
                    }
                }
                if (!StrUtils.isEmpty(type)) {
                    tv_type.setText(type);
                    setFilter(type, -1);
                }
            }

            @Override
            public void onCancel(View view) {

            }

            @Override
            public void onItemClick(View view, int position, boolean selected) {
                if (selected) {
                    tv_type.setText(datas_type.get(position).getName());
                    setFilter(datas_type.get(position).getName(), -1);
                }
            }
        });
    }

    private BottomListMulDialog dialog_child;
    private void showChildDialog(){
        if (datas_child == null) {
            datas_child = new ArrayList<>();
        }
        if (datas_child.size() < 1) {
            MulBean mulBean = new MulBean();
            mulBean.setId("-1");
            mulBean.setName("全部工单");
            datas_child.add(mulBean);
        }
        for (MulBean b : datas_child) {
            if (tv_child.getText().toString().trim().equals(b.getName())) {
                b.setSelected(true);
            }
        }

        dialog_child = new BottomListMulDialog(getActivity(), false, datas_child);
        dialog_child.setOnClickListener(new BottomListMulDialog.OnClickListener() {
            @Override
            public void onCommit(View view) {
                String id = "0";
                String name = "";
                for (MulBean b : datas_type) {
                    if (b.isSelected()){
                        id = b.getId();
                        name = b.getName();
                        break;
                    }
                }
                if (!StrUtils.isEmpty(name)) {
                    tv_child.setText(name);
                    setFilter(null, Integer.valueOf(id));
                }
            }

            @Override
            public void onCancel(View view) {

            }

            @Override
            public void onItemClick(View view, int position, boolean selected) {
                if (selected) {
                    tv_child.setText(datas_child.get(position).getName());
                    setFilter(null, Integer.valueOf(datas_child.get(position).getId()));
                }
            }
        });
    }
    private List<MulBean> datas_child = new ArrayList<>();
    private void setViewData(List<ProjectSceneBean> beanList) {
        if (datas_child == null) {
            datas_child = new ArrayList<>();
        }
        datas_child.clear();
        MulBean mulBean = new MulBean();
        mulBean.setId("-1");
        mulBean.setName("全部工单");
        datas_child.add(mulBean);

        if (beanList != null) {
            List<ProjectSceneBean.ScenesBean> beans = new ArrayList<>();

            for (int i = 0; i < beanList.size(); i++) {
                if (!ListUtil.isEmpty(beanList.get(i).getScenes())) {
                    MulBean m = new MulBean();
                    m.setId(String.valueOf(beanList.get(i).getTask_id()));
                    m.setName(beanList.get(i).getScenes().get(0).getTask_name());

                    datas_child.add(m);

                    beans.addAll(beanList.get(i).getScenes());
                }
            }

            mBeanList.clear();
            mAllBeanList.clear();
            mAllBeanList.addAll(beans);
            mBeanList.addAll(mAllBeanList);
            mAdapter.notifyDataSetChanged();

            if (dialog_child != null) {
                dialog_child.notifyDateChanged();
            }
        }
    }

    private void setFilter(String type, int id) {
        List<ProjectSceneBean.ScenesBean> beans = new ArrayList<>();

        if (!TextUtils.isEmpty(type) && type.contains("全部") || TextUtils.isEmpty(type) && id == -1) {
            beans.addAll(mAllBeanList);
        } else {
            for (int i = 0; i < mAllBeanList.size(); i++) {
                if (TextUtils.isEmpty(type)) {
                    if (mAllBeanList.get(i).getTask_id() == id) {
                        beans.add(mAllBeanList.get(i));
                    }
                } else {
//                    if (mAllBeanList.get(i).getTags().contains(type)) {
                    if (mAllBeanList.get(i).getType().contains(type)) {
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