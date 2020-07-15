package com.aopcloud.palmproject.ui.activity.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.camera.PreviewActivity;
import com.aopcloud.palmproject.ui.activity.project.bean.ProjectTaskDetailBean;
import com.aopcloud.palmproject.ui.activity.task.bean.TaskTrendsBean;
import com.aopcloud.palmproject.ui.adapter.file.FileListAdapter;
import com.aopcloud.palmproject.ui.adapter.file.PreviewAdapter;
import com.aopcloud.palmproject.ui.adapter.project.ProjectScenesAdapter;
import com.aopcloud.palmproject.ui.adapter.project.ProjectScenesChildAdapter;
import com.aopcloud.palmproject.utils.JsonUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cily.utils.base.StrUtils;
import com.cily.utils.base.time.TimeUtils;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.xw.repo.BubbleSeekBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.Serializable;
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
    @BindView(R.id.ll_number_progress)
    LinearLayout mRlNumberProgress;
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

    private ProjectScenesChildAdapter imgAdapter;
    private List<String> imgDatas;

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

            //强制改为true
            child = true;
        }
    }
    @Override
    protected void initView() {
        mTvHeaderTitle.setText("进度反馈");
        mTvTaskName.setText(""+task_name);

        if (child){
            mProgressBar.setVisibility(View.VISIBLE);
//            mRlNumberProgress.setVisibility(View.GONE);
            mRlNumberProgress.setVisibility(View.VISIBLE);
        }else {
            mProgressBar.setVisibility(View.GONE);
            mRlNumberProgress.setVisibility(View.VISIBLE);
            mTvCountUnit.setText(""+task_work_unit);
        }

        mProgressBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                if (progress < initProgress){
                    bubbleSeekBar.setProgress(initProgress);
                }
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });

        mAddMediaEntity = new MediaEntity();
        mMediaEntities.add(mAddMediaEntity);
        mFileListAdapter = new FileListAdapter(R.layout.item_default_file, mMediaEntities);
        mFileListAdapter.setEdit(true);
        mFileListAdapter.setOnItemClickListener(this);
        mFileListAdapter.setOnItemChildClickListener(this);
        mRvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvList.setAdapter(mFileListAdapter);

        toRequest(ApiConstants.EventTags.trends_all);
//        toRequest(ApiConstants.EventTags.task_get);

        imgDatas = new ArrayList<>();
        imgAdapter = new ProjectScenesChildAdapter(R.layout.item_project_scenes_img, imgDatas);
        mRvListImg.setLayoutManager(new GridLayoutManager(this, 3));
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .height(4)
                .build();
        mRvListImg.addItemDecoration(itemDecoration);
        mRvListImg.setAdapter(imgAdapter);
        imgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArrayList<PreviewAdapter.PreviewBean> list = new ArrayList();

                for (String url : imgDatas) {
                    list.add(new PreviewAdapter.PreviewBean(url));
                }


                Bundle bundle = new Bundle();
                bundle.putSerializable("PreviewBean", (Serializable) list);
                gotoActivity(PreviewActivity.class, bundle);
            }
        });
    }

    private int initProgress;

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
            Log.w(TAG, "-1--" + JSON.toJSONString(entities));
            Log.d(TAG, "--2-" + JSON.toJSONString(mMediaEntities));
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
                mEtNumber.setSelection(mEtNumber.getText().toString().length());
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
//        uploadFile(list);
        it_for_upload = list.iterator();
        uploadFile();
    }

    private Iterator<MediaEntity> it_for_upload;
    private void uploadFile() {
        if (it_for_upload != null && it_for_upload.hasNext()){
            MediaEntity entity = it_for_upload.next();
            if (!TextUtils.isEmpty(entity.getMediaName()) && entity.getMediaName().equals("img_add_network")) {
                if (TextUtils.isEmpty(attach)) {
                    attach = entity.getLocalPath();
                } else {
                    attach = attach + "," + entity.getLocalPath();
                }
                it_for_upload.remove();
                uploadFile();
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
                                Log.i(TAG, "add Serices Course  response :" + response);
                                ResultBean bean = JSON.parseObject(response, ResultBean.class);
                                if (bean != null && bean.getCode() == 0) {
                                    if (TextUtils.isEmpty(attach)) {
                                        attach = JsonUtil.parserField(bean.getData(), "path");
                                    } else {
                                        attach = attach + "," + JsonUtil.parserField(bean.getData(), "path");
                                    }
                                    it_for_upload.remove();
                                    uploadFile();
                                } else {
                                    ToastUtil.showToast(bean != null ? bean.getMsg() : "上传失败，请重试");
                                }
                            }
                        });
            }
        } else {
            toRequest(ApiConstants.EventTags.task_feedback);
        }
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
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.task_feedback, map);
        } else if (eventTag == ApiConstants.EventTags.task_get) {
            //进度反馈详细列表
//            map.put("type", "0");
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.task_get, map);
        } else if (eventTag == ApiConstants.EventTags.trends_all){
            map.put("type", "0");

            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.trends_all, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.task_feedback) {
                setResult(0);
                finish();
            } else if (eventTag == ApiConstants.EventTags.trends_all) {
                List<TaskTrendsBean> beanList = JSON.parseArray(bean.getData(), TaskTrendsBean.class);
                setViewData(beanList);
            } else if (eventTag == ApiConstants.EventTags.task_get) {
                ProjectTaskDetailBean b = JSON.parseObject(bean.getData(), ProjectTaskDetailBean.class);
                setData(b == null ? null : b.getTrends());
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }
    }

    private void setData(List<ProjectTaskDetailBean.TrendsBean> datas){
        if (datas != null && datas.size() > 0) {
            ProjectTaskDetailBean.TrendsBean bean = null;
            for (ProjectTaskDetailBean.TrendsBean b : datas) {
                if (b.getType() == 0){
                    if (bean == null) {
                        bean = b;
                    }else {
                        if (bean.getMake_time() < b.getMake_time()) {
                            bean = b;
                        }
                    }
                }
            }
            if (bean != null) {
                mTvLastTime.setText("上次反馈：" + TimeUtils.milToStr(bean.getMake_time(), null));

            }
        }
    }

    private void setViewData(List<TaskTrendsBean> beanList){
        if (beanList != null && beanList.size() > 0) {
            String extra = beanList.get(0).getExtra();
            TaskTrendsBean.ExtraBean bean = JSON.parseObject(extra, TaskTrendsBean.ExtraBean.class);
            if (bean != null){
                mTvLastTime.setText("上次反馈：" + TimeUtils.milToStr(beanList.get(0).getMake_time() * 1000, null));
                mProgressBar.setProgress(bean.getProgress_after());
                initProgress = bean.getProgress_after();
//                mProgressBar.getMin()
                mEtNumber.setText(bean.getWork_value_done_after() + "");
                mEtNumber.setSelection(mEtNumber.getText().toString().length());

                if (!TextUtils.isEmpty( beanList.get(0).getAttach())) {
                    String[] url =  beanList.get(0).getAttach().split(",");
                    for (int i = 0; i < url.length; i++) {
                        if (!StrUtils.isEmpty(url[i]) && !url[i].equals("null")) {
                            imgDatas.add(BuildConfig.BASE_URL + url[i]);
                        }
                    }
                }
                if (imgAdapter != null) {
                    imgAdapter.notifyDataSetChanged();
                }
            }
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
                                Log.i(TAG, "add Serices Course  response :" + response);
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