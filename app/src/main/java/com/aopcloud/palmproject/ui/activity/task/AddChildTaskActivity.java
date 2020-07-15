package com.aopcloud.palmproject.ui.activity.task;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.map.SelectLocationActivity;
import com.aopcloud.palmproject.ui.activity.project.ProjectTaskDetailActivity;
import com.aopcloud.palmproject.ui.activity.team.bean.TeamMemberBean;
import com.aopcloud.palmproject.ui.adapter.file.FileListAdapter;
import com.aopcloud.palmproject.utils.JsonUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task
 * @File : AddTaskActivity.java
 * @Date : 2020/5/8 2020/5/8
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_task_add_child)
public class AddChildTaskActivity extends BaseAc implements FileListAdapter.OnItemChildClickListener,
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
    @BindView(R.id.tv_parent_name)
    TextView mTvParentName;
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
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.spinner_level)
    Spinner mSpinnerLevel;
    @BindView(R.id.tv_leader)
    TextView mTvLeader;
    @BindView(R.id.iv_leader_more)
    ImageView mIvLeaderMore;
    @BindView(R.id.tv_user)
    TextView mTvUser;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;


    private String parent_task_name;
    private String parent_task_id;


    private String start_date="";
    private String end_date="";
    private String name="";
    private String work_value="";
    private String work_unit="";
    private String work_des="";
    private String attach="";
    private String address="";
    private String longitue="";
    private String latitude="";
    private String scope="";
    private String level="";
    private String leader_id="";
    private String memberids="";


    private FileListAdapter mFileListAdapter;
    private List<MediaEntity> mMediaEntities = new ArrayList<>();
    private MediaEntity mAddMediaEntity;

    private String team_id;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            parent_task_name = bundle.getString("task_name", "");
            parent_task_id = bundle.getString("task_id", "");
            team_id = bundle.getString("team_id");
        }


    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("新建任务");

        if (!TextUtils.isEmpty(parent_task_name)) {
            mTvParentName.setText(parent_task_name);
        }


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
                    .start(AddChildTaskActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 2);
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
            Log.w(TAG, "-1--" + JSON.toJSONString(entities));
            Log.d(TAG, "--2-" + JSON.toJSONString(mMediaEntities));
            mFileListAdapter.notifyDataSetChanged();
        }


    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_parent_name, R.id.tv_start_time, R.id.tv_end_time, R.id.tv_address, R.id.tv_leader, R.id.tv_user, R.id.tv_submit})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_parent_name:
                if (TextUtils.isEmpty(parent_task_id)) {
                    gotoActivity(AddChildTaskActivity.class, 10);
                }
                break;
            case R.id.tv_start_time:
                showTimePicker(1);
                break;
            case R.id.tv_end_time:
                showTimePicker(2);
                break;
            case R.id.tv_address:
                gotoActivity(SelectLocationActivity.class, 1);
                break;
            case R.id.tv_leader:
                bundle = new Bundle();
                bundle.putString("team_id", "" + team_id);
                bundle.putString("title", "负责人选择");
                gotoActivity(SelectTaskLeaderActivity.class, bundle, 3);
                break;
            case R.id.tv_user:
                bundle = new Bundle();
                bundle.putString("team_id", "" + team_id);
                bundle.putString("title", "参与人选择");
                gotoActivity(SelectTaskLeaderActivity.class, bundle, 4);
                break;
            case R.id.tv_submit:
                name = mEtName.getText().toString();
                work_value = mEtCount.getText().toString();
                work_unit = mEtUnit.getText().toString();
                work_des = mEtContent.getText().toString();
                level = (String) mSpinnerLevel.getSelectedItem();
                checkParams();
                break;
        }
    }

    private void checkParams() {
        if (TextUtils.isEmpty(parent_task_id)) {
            ToastUtil.showToast("请选择父级项目");
            return;
        }
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
        if (TextUtils.isEmpty(address)) {
            ToastUtil.showToast("请选择地址");
            return;
        }

        if (TextUtils.isEmpty(work_des)) {
            ToastUtil.showToast("请输入工单描述");
            return;
        }
        if (TextUtils.isEmpty(leader_id)) {
            ToastUtil.showToast("请选择负责人");
            return;
        }
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
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.task_addsub) {
            map.put("name", "" + name);
            map.put("pid", "" + parent_task_id);
            map.put("attach", "" + attach);
            map.put("work_value", "" + work_value);
            map.put("work_unit", "" + work_unit);
            map.put("work_des", "" + work_des);
            map.put("address", "" + address);
            map.put("longitue", "" + longitue);
            map.put("longitude", "" + latitude);
            map.put("latitude", "" + latitude);
            map.put("scope", "" + scope);
            map.put("leader_id", "" + leader_id);
            map.put("start_date", "" + start_date);
            map.put("end_date", "" + end_date);
            map.put("memberids", "" + memberids);
            map.put("level", "" + level);


            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.task_addsub, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.task_addsub) {
                String task_id = JsonUtil.parserField(bean.getData(), "task_id");
                showSuccess(task_id);

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


    public void showTimePicker(int type) {
        TimePickerView pickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (type == 1) {
                    mTvStartTime.setText(dateFormat.format(date));
                    start_date = dateFormat.format(date);
                } else if (type == 2) {
                    mTvEndTime.setText(dateFormat.format(date));
                    end_date = dateFormat.format(date);
                }
            }
        })
                .setLineSpacingMultiplier(2)
                .build();
        pickerView.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            List<MediaEntity> result = Phoenix.result(data);
            mMediaEntities.clear();
            mMediaEntities.addAll(result);
            mMediaEntities.add(mAddMediaEntity);
            mFileListAdapter.notifyDataSetChanged();
        }

        if (data == null) {
            return;
        }
        if (requestCode == 1) {
            Bundle bundle = data.getExtras();
            longitue = bundle.getString("longitude");
            latitude = bundle.getString("latitude");
            address = bundle.getString("address");
            scope = bundle.getString("range");
            mTvAddress.setText("" + address);
        } else if (requestCode == 3) {
            Bundle bundle = data.getExtras();
            String json = bundle.getString("json");
            List<TeamMemberBean> list = JSON.parseArray(json, TeamMemberBean.class);
            mTvLeader.setText(""+list.get(0).getUser_name());
            leader_id=list.get(0).getUser_id()+"";
        } else if (requestCode == 4) {
            Bundle bundle = data.getExtras();
            String json = bundle.getString("json");
            List<TeamMemberBean> list = JSON.parseArray(json, TeamMemberBean.class);
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i <list.size() ; i++) {
                stringBuffer.append(list.get(i).getUser_name()).append(i==list.size()-1?"":",");
                ids.append(list.get(i).getUser_id()+"").append(i==list.size()-1?"":",");
            }
            memberids=ids.toString();
            mTvUser.setText(stringBuffer.toString());
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
                                Log.w(TAG, "add live video exception :" + e + "/");
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
            toRequest(ApiConstants.EventTags.task_addsub);
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
                bundle.putString("project_id", "");
                bundle.putString("task_id", "" + task_id);
                gotoActivity(ProjectTaskDetailActivity.class, bundle);
                setResult(0);
                finish();
            }
        });
    }
}

