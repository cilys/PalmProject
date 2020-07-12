package com.aopcloud.palmproject.ui.activity.project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.common.BaseEvent;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.MassageEvent;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.enterprise.SwitchEnterpriseActivity;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseListBean;
import com.aopcloud.palmproject.ui.activity.map.SelectLocationActivity;
import com.aopcloud.palmproject.ui.activity.project.list.SelectProjectAc;
import com.aopcloud.palmproject.ui.adapter.file.FileListAdapter;
import com.aopcloud.palmproject.utils.JsonUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cily.utils.base.StrUtils;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project
 * @File : ProjectTaskAddActivity.java
 * @Date : 2020/5/7 2020/5/7
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：创建工单
 */
@Layout(R.layout.activity_project_task_add)
public class ProjectTaskAddActivity extends BaseActivity implements FileListAdapter.OnItemChildClickListener,
        FileListAdapter.OnItemClickListener {

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
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.iv_name_more)
    ImageView mIvNameMore;
    @BindView(R.id.et_count)
    EditText mEtCount;
    @BindView(R.id.et_unit)
    EditText mEtUnit;
    @BindView(R.id.iv_unit_more)
    ImageView mIvUnitMore;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.rv_list_img)
    RecyclerView mRvListImg;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.tv_address)
    TextView mTvAddress;

    @BindView(R.id.ll_company)
    LinearLayout ll_company;    //公司
    @BindView(R.id.tv_company)
    TextView tv_company;
    @BindView(R.id.ll_project_name)
    LinearLayout ll_project_name;   //项目
    @BindView(R.id.tv_project_name)
    TextView tv_project_name;


    private String project_id;
    private String pid = "0";
    private String name;
    private String work_value;
    private String work_unit;
    private String work_des;
    private String attach = "";
    private String assignAddress;
    private String assignRange;
    private String assignLongitude;
    private String assignLatitude;


    private FileListAdapter mFileListAdapter;
    private List<MediaEntity> mMediaEntities = new ArrayList<>();
    private MediaEntity mAddMediaEntity;

    private String project_name;
    private String company_id;
    private String company_name;
    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            company_id = LoginUserUtil.getCurrentEnterpriseNo(this);

            project_id = bundle.getString("project_id");
            project_name = bundle.getString("project_name");

            company_id = bundle.getString("company_id");
            company_name = bundle.getString("company_name");
        }
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("创建工单");

        if (!StrUtils.isEmpty(company_name)) {
            tv_company.setText(company_name);
        }
        if (!StrUtils.isEmpty(company_id)){
            toRequest(ApiConstants.EventTags.company_my);
        }

        if (!StrUtils.isEmpty(project_name)) {
            tv_project_name.setText(project_name);
        }
        ll_project_name.setEnabled(StrUtils.isEmpty(project_id));

        mAddMediaEntity = new MediaEntity();
        mMediaEntities.add(mAddMediaEntity);
        mFileListAdapter = new FileListAdapter(R.layout.item_default_file, mMediaEntities);
        mFileListAdapter.setEdit(true);
        mFileListAdapter.setOnItemClickListener(this);
        mFileListAdapter.setOnItemChildClickListener(this);
        mRvListImg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvListImg.setAdapter(mFileListAdapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (position == mMediaEntities.size() - 1) {
            List list = new ArrayList();
            list.addAll(mMediaEntities);
            list.remove(mMediaEntities.size() - 1);
            Phoenix.with()
                    .theme(PhoenixOption.THEME_DEFAULT)// 主题
                    .fileType(MimeType.ofAll())//显示的文件类型图片、视频、图片和视频
                    .maxPickNumber(9)// 最大选择数量
                    .minPickNumber(1)// 最小选择数量
                    .spanCount(4)// 每行显示个数
                    .enablePreview(true)// 是否开启预览
                    .enableCamera(true)// 是否开启拍照
                    .enableAnimation(true)// 选择界面图片点击效果
                    .enableCompress(true)// 是否开启压缩
                    .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                    .compressVideoFilterSize(2018)//多少kb以下的视频不压缩
                    .thumbnailHeight(160)// 选择界面图片高度
                    .thumbnailWidth(160)// 选择界面图片宽度
                    .enableClickSound(false)// 是否开启点击声音
                    .pickedMediaList(list)// 已选图片数据
                    .videoFilterTime(500)//显示多少秒以内的视频
                    .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                    //如果是在Activity里使用就传Activity，如果是在Fragment里使用就传Fragment
                    .start(ProjectTaskAddActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 2);
        } else {

        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.iv_del) {
            List<MediaEntity> entities = new ArrayList<>();
            entities.addAll(mMediaEntities);
            entities.remove(position);
            mMediaEntities.clear();
            mMediaEntities.addAll(entities);
            Logcat.i("-1--" + JSON.toJSONString(entities));
            Logcat.d("-2-" + JSON.toJSONString(mMediaEntities));
            mFileListAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.iv_name_more, R.id.iv_unit_more,R.id.tv_address,
            R.id.tv_submit, R.id.ll_company, R.id.ll_project_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.iv_name_more:
                break;
            case R.id.iv_unit_more:
                break;
            case R.id.tv_address:
                gotoActivity(SelectLocationActivity.class,1);
                break;
            case R.id.tv_submit:
                name = mEtName.getText().toString();
                work_value = mEtCount.getText().toString();
                work_unit = mEtUnit.getText().toString();
                work_des = mEtContent.getText().toString();
                checkParams();
                break;

            case R.id.ll_company:
                //获取公司列表
                if (StrUtils.isEmpty(company_id)){
                    gotoActivity(SwitchEnterpriseActivity.class, 0);
                } else {
                    toRequest(ApiConstants.EventTags.company_my);
                }
                break;

            case R.id.ll_project_name:
                //获取企业下的项目
                if (StrUtils.isEmpty(company_id)){
                    ToastUtil.showToast("请先选择企业");
                }else {
                    Bundle b = new Bundle();
                    b.putString("selected_project_id", project_id);
                    b.putString("company_id", company_id);
                    gotoActivity(SelectProjectAc.class, b, 101);
                }
                break;
        }
    }

    private void checkParams() {
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast("请输入工单名称");
            return;
        }
        if (TextUtils.isEmpty(work_value)) {
            ToastUtil.showToast("请输入数量");
            return;
        }
        if (TextUtils.isEmpty(work_unit)) {
            ToastUtil.showToast("请输入单位");
            return;
        }
//        if (TextUtils.isEmpty(assignAddress)) {
//            ToastUtil.showToast("请选择地址");
//            return;
//        }

//        if (TextUtils.isEmpty(work_des)) {
//            ToastUtil.showToast("请输入工单描述");
//            return;
//        }
        attach = "";
        List<MediaEntity> list = new ArrayList();
        list.addAll(mMediaEntities);
        if (list.contains(mAddMediaEntity)) {
            list.remove(mAddMediaEntity);
        }
        uploadFile(list);
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", company_id);
        map.put("project_id", "" + project_id);
        if (eventTag == ApiConstants.EventTags.task_add) {
            if (!StrUtils.isEmpty(name)) {
                map.put("name", name);
            }
            if (!StrUtils.isEmpty(pid)) {
                map.put("pid", pid);
            }
            if (!StrUtils.isEmpty(attach)) {
                map.put("attach", attach);
            }
            if (!StrUtils.isEmpty(work_value)) {
                map.put("work_value", work_value);
            }
            if (!StrUtils.isEmpty(work_unit)) {
                map.put("work_unit", work_unit);
            }
            if (!StrUtils.isEmpty(work_des)) {
                map.put("work_des", work_des);
            }
            if (!StrUtils.isEmpty(assignAddress)) {
                map.put("address", assignAddress);
            }
            if (!StrUtils.isEmpty(assignLongitude)) {
                map.put("longitue", assignLongitude);
            }
            if (!StrUtils.isEmpty(assignLatitude)) {
                map.put("latitude", assignLatitude);
            }
            if (!StrUtils.isEmpty(assignRange)) {
                map.put("scope", assignRange);
            }

            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.task_add, map);
        } else if (eventTag == ApiConstants.EventTags.company_my) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_my, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.task_add) {
                String task_id = JsonUtil.parserField(bean.getData(), "task_id");
                showSuccess(task_id);

            } else if (eventTag == ApiConstants.EventTags.company_my) {
                List<EnterpriseListBean> beanList = JSON.parseArray(bean.getData(), EnterpriseListBean.class);
                if (beanList != null && beanList.size() > 0){
                    for (EnterpriseListBean b : beanList){
                        String n = b.getCode();
                        if (!StrUtils.isEmpty(n)){
                            if (n.equals(company_id)) {
                                tv_company.setText(b.getName());
                            }
                        }
                    }
                }
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }
    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
        ToastUtil.showToast("网络错误");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            List<MediaEntity> result = Phoenix.result(data);
            mMediaEntities.clear();
            mMediaEntities.addAll(result);
            mMediaEntities.add(mAddMediaEntity);
            mFileListAdapter.notifyDataSetChanged();
        }
        if (requestCode == 101){
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    ToastUtil.showToast("未选择项目");
                } else {
                    project_id = data.getStringExtra("selected_project_id");
                    project_name = data.getStringExtra("selected_project_name");
                    if (!StrUtils.isEmpty(project_name)){
                        tv_project_name.setText(project_name);
                    }else {
                        tv_project_name.setText("");
                    }
                    if (StrUtils.isEmpty(project_id)){
                        ToastUtil.showToast("未选择项目");
                    }
                }
            }
        }

        if (data == null) {
            return;
        }
        if (requestCode == 1) {
            Bundle bundle = data.getExtras();
            assignLongitude = bundle.getString("longitude");
            assignLatitude = bundle.getString("latitude");
            assignAddress = bundle.getString("address");
            assignRange = bundle.getString("range");

            mTvAddress.setText("" + assignAddress);
        }


    }

    private void uploadFile(List<MediaEntity> result) {
        if (result.size() > 0) {
            MediaEntity entity = result.get(result.size() - 1);
            if (!TextUtils.isEmpty(entity.getMediaName()) && entity.getMediaName().equals("img_add_network")) {
                if (TextUtils.isEmpty(attach)) {
                    attach = entity.getLocalPath();
                } else {
                    attach = attach + "," + entity.getLocalPath();
                }
                result.remove(entity);
                uploadFile(result);
            } else {
                OkHttpUtils.post().url(ApiConstants.file_upload)
                        .addParams("token", "" + LoginUserUtil.getToken(this))
                        .addFile("file", getPictureSuffix(entity.getLocalPath()), new File(entity.getLocalPath()))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                dismissPopupLoading();
                                ToastUtil.showToast("文件上传失败，请重试");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                dismissPopupLoading();
                                ResultBean bean = JSON.parseObject(response, ResultBean.class);
                                if (bean != null && bean.getCode() == 0) {
                                    if (TextUtils.isEmpty(attach)) {
                                        attach = JsonUtil.parserField(bean.getData(), "path");
                                    } else {
                                        attach = attach + "," + JsonUtil.parserField(bean.getData(), "path");
                                    }
                                    result.remove(result.size() - 1);
                                    uploadFile(result);
                                } else {
                                    ToastUtil.showToast(bean != null ? bean.getMsg() : "上传失败，请重试");
                                }
                            }
                        });
            }
        } else {
            toRequest(ApiConstants.EventTags.task_add);
        }
    }

    private String getPictureSuffix(String path) {
        if (path.contains(".")) {
            return "live_" + System.currentTimeMillis() + path.substring(path.lastIndexOf("."), path.length());
        }
        return "live_" + System.currentTimeMillis() + ".jpg";
    }

    public void showSuccess(String task_id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_project_success);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_dialog_success);
        lp.width = ViewUtil.dp2px(this, 250);
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("project_id", "" + project_id);
                bundle.putString("task_id", "" + task_id);
                gotoActivity(ProjectTaskDetailActivity.class, bundle);
                setResult(0);
                finish();
            }
        });
    }

    @Override
    public void onEvent(BaseEvent postResult) {
        super.onEvent(postResult);
        if (postResult.type.equals(MassageEvent.SWITCH_COMPANY)) {
            company_name = LoginUserUtil.getCurrentEnterpriseBean(this).getName();
            tv_company.setText("" + company_name);
            company_id = LoginUserUtil.getCurrentEnterpriseNo(this);
        }
    }
}