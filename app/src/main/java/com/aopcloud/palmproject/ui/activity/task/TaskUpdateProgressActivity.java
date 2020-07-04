package com.aopcloud.palmproject.ui.activity.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.project.UpdateProjectProgressActivity;
import com.aopcloud.palmproject.ui.adapter.file.FileListAdapter;
import com.aopcloud.palmproject.utils.JsonUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.xw.repo.BubbleSeekBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.task
 * @File : TaskUpdateProgressActivity.java
 * @Date : 2020/5/23 2020/5/23
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_task_update_progress)
public class TaskUpdateProgressActivity extends BaseActivity implements FileListAdapter.OnItemChildClickListener,
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
    @BindView(R.id.tv_last_time)
    TextView mTvLastTime;
    @BindView(R.id.tv_task_name)
    TextView mTvTaskName;
    @BindView(R.id.progress_bar)
    BubbleSeekBar mProgressBar;
    @BindView(R.id.et_number)
    EditText mEtNumber;
    @BindView(R.id.tv_count_unit)
    TextView mTvCountUnit;
    @BindView(R.id.tv_complete_all)
    TextView mTvCompleteAll;
    @BindView(R.id.rl_number_progress)
    RelativeLayout mRlNumberProgress;
    @BindView(R.id.et_msg)
    EditText mEtMsg;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.rv_list_img)
    RecyclerView mRvListImg;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

    private String task_id;
    private String task_name;
    private String task_work_unit;
    private String task_work_value;
    private boolean child;


    private int progress;
    private String message;
    private String work_value;
    private String attach;
    private FileListAdapter mFileListAdapter;

    private List<MediaEntity> mMediaEntities = new ArrayList<>();
    private MediaEntity mAddMediaEntity;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            task_id = bundle.getString("task_id");
            task_name = bundle.getString("task_name");
            task_work_unit = bundle.getString("work_unit");
            task_work_value = bundle.getString("work_value");
            child = bundle.getBoolean("child",false);
        }
    }
    @Override
    protected void initView() {
        mTvHeaderTitle.setText("进度反馈");
        mTvTaskName.setText(""+task_name);

        if (child){
            mProgressBar.setVisibility(View.VISIBLE);
            mRlNumberProgress.setVisibility(View.GONE);
        }else {
            mProgressBar.setVisibility(View.GONE);
            mRlNumberProgress.setVisibility(View.VISIBLE);
            mTvCountUnit.setText(""+task_work_unit);
        }

        mAddMediaEntity = new MediaEntity();
        mMediaEntities.add(mAddMediaEntity);
        mFileListAdapter = new FileListAdapter(R.layout.item_default_file, mMediaEntities);
        mFileListAdapter.setEdit(true);
        mFileListAdapter.setOnItemClickListener(this);
        mFileListAdapter.setOnItemChildClickListener(this);
        mRvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvList.setAdapter(mFileListAdapter);

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
                    .maxPickNumber(10)// 最大选择数量
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
                    .start(TaskUpdateProgressActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 2);
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
            Logcat.w("-1--" + JSON.toJSONString(entities));
            Logcat.d("--2-" + JSON.toJSONString(mMediaEntities));
            mFileListAdapter.notifyDataSetChanged();
        }


    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right,R.id.tv_complete_all,R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_complete_all:
                mEtNumber.setText(""+task_work_value);
                break;
            case R.id.tv_submit:
                message = mEtMsg.getText().toString();
                work_value = mEtNumber.getText().toString();
                progress = mProgressBar.getProgress();
                checkParams();
                break;
        }
    }

    private void checkParams() {
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
        map.put("task_id", "" + task_id);//项目名称
        if (eventTag == ApiConstants.EventTags.task_feedback) {
            map.put("attach", "" + attach);
            map.put("message", "" + message);
            map.put("progress", "" + progress);
            if (TextUtils.isEmpty(work_value)){
                map.put("progress", "" + progress);
            }else {
                map.put("work_value", "" + work_value);
            }
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.task_feedback, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.task_feedback) {
                setResult(0);
                finish();
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }
    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
        Logcat.i("------------" + eventTag + "/" + msg);
        ToastUtil.showToast("网络错误");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            List<MediaEntity> result = Phoenix.result(data);
            Logcat.i("------------" + JSON.toJSONString(result));
            mMediaEntities.clear();
            mMediaEntities.addAll(result);
            mMediaEntities.add(mAddMediaEntity);
            mFileListAdapter.notifyDataSetChanged();
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
                Logcat.i("------------" + getPictureSuffix(entity.getLocalPath()));
                OkHttpUtils.post().url(ApiConstants.file_upload)
                        .addParams("token", "" + LoginUserUtil.getToken(this))
                        .addFile("file", getPictureSuffix(entity.getLocalPath()), new File(entity.getLocalPath()))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                dismissPopupLoading();
                                Logcat.w("add live video exception :" + e + "/");
                                ToastUtil.showToast("文件上传失败，请重试");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                dismissPopupLoading();
                                Logcat.i("add Serices Course  response :" + response);
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
            Logcat.i("-------" + attach);
            toRequest(ApiConstants.EventTags.task_feedback);
        }
    }

    private String getPictureSuffix(String path) {
        if (path.contains(".")) {
            return "live_" + System.currentTimeMillis() + path.substring(path.lastIndexOf("."), path.length());
        }
        return "live_" + System.currentTimeMillis() + ".jpg";
    }
}
