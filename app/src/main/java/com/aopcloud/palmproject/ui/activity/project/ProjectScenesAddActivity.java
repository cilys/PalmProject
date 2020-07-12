package com.aopcloud.palmproject.ui.activity.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.ImageItem;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.camera.SelectSceneActivity;
import com.aopcloud.palmproject.ui.adapter.file.FileListAdapter;
import com.aopcloud.palmproject.utils.JsonUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project
 * @File : ProjectScenesAddActivity.java
 * @Date : 2020/5/15 14:09
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_project_scene_add)
public class ProjectScenesAddActivity extends BaseActivity implements FileListAdapter.OnItemChildClickListener,
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
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.iv_type)
    ImageView mIvType;
    @BindView(R.id.et_tag)
    EditText mEtTag;
    @BindView(R.id.iv_tag)
    ImageView mIvTag;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;

    private String type = "进度";
    private String tags = "";
    private String task_id;
    private String project_id;
    private String attach;

    private FileListAdapter mFileListAdapter;
    private List<MediaEntity> mMediaEntities = new ArrayList<>();
    private MediaEntity mAddMediaEntity;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            task_id = bundle.getString("task_id", "");
            project_id = bundle.getString("project_id", "");
        }
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("提交影像");
        mAddMediaEntity = new MediaEntity();
        mMediaEntities.add(mAddMediaEntity);
        mFileListAdapter = new FileListAdapter(R.layout.item_project_scenes_file, mMediaEntities);
        mFileListAdapter.setEdit(true);
        mFileListAdapter.setOnItemClickListener(this);
        mFileListAdapter.setOnItemChildClickListener(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_white))
                .width(1)
                .build();
        mRvList.addItemDecoration(itemDecoration);
        mRvList.setLayoutManager(new GridLayoutManager(this, 3));
        mRvList.setAdapter(mFileListAdapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (position == mMediaEntities.size() - 1) {
//            List list = new ArrayList();
//            list.addAll(mMediaEntities);
//            list.remove(mMediaEntities.size() - 1);
//            Phoenix.with()
//                    .theme(PhoenixOption.THEME_DEFAULT)// 主题
//                    .fileType(MimeType.ofAll())//显示的文件类型图片、视频、图片和视频
//                    .maxPickNumber(10)// 最大选择数量
//                    .minPickNumber(1)// 最小选择数量
//                    .spanCount(4)// 每行显示个数
//                    .enablePreview(true)// 是否开启预览
//                    .enableCamera(true)// 是否开启拍照
//                    .enableAnimation(true)// 选择界面图片点击效果
//                    .enableCompress(true)// 是否开启压缩
//                    .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
//                    .compressVideoFilterSize(2018)//多少kb以下的视频不压缩
//                    .thumbnailHeight(160)// 选择界面图片高度
//                    .thumbnailWidth(160)// 选择界面图片宽度
//                    .enableClickSound(false)// 是否开启点击声音
//                    .pickedMediaList(list)// 已选图片数据
//                    .videoFilterTime(1000000)//显示多少秒以内的视频
//                    .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
//                    //如果是在Activity里使用就传Activity，如果是在Fragment里使用就传Fragment
//                    .start(ProjectScenesAddActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 2);
            gotoActivity(SelectSceneActivity.class, 0);
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

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_type, R.id.iv_type, R.id.iv_tag, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_type:
            case R.id.iv_type:
                showType();
                break;
            case R.id.iv_tag:
                break;
            case R.id.tv_submit:
                tags = mEtTag.getText().toString().trim();
                List<MediaEntity> list = new ArrayList();
                list.addAll(mMediaEntities);
                if (list.contains(mAddMediaEntity)) {
                    list.remove(mAddMediaEntity);
                }
                showPopXupLoading("提交中");
                uploadFile(list);
                break;
        }
    }

    private OptionsPickerView pvOptions;


    public void showType() {
        List<String> list = new ArrayList();
        list.add("材料");
        list.add("机械");
        list.add("质量");
        list.add("进度");
        list.add("安全");
        list.add("其他");
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                type = list.get(options1);
                mTvType.setText(type);
            }
        })
                .setCyclic(false, true, true)
                .setContentTextSize(14)
                .setTextColorCenter(ResourceUtil.getColor(R.color.theme_color))
                .setLineSpacingMultiplier(3)
                .build();
        pvOptions.setNPicker(list, null, null);
        pvOptions.show();
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("project_id", "" + project_id);//项目名称
        if (eventTag == ApiConstants.EventTags.scenes_add) {
            map.put("task_id", "" + task_id);
            map.put("type", "" + type);
            if (!TextUtils.isEmpty(tags)) {
                map.put("tags", "" + tags);
            }
            map.put("attach", "" + attach);
            Log.i(TAG, "------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.scenes_add, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Log.i(TAG, "------------" + eventTag + "/" + result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.scenes_add) {
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
        Log.i(TAG, "------------" + eventTag + "/" + msg);
        ToastUtil.showToast("网络错误，请重试");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            List<MediaEntity> result = Phoenix.result(data);
            Log.i(TAG, "------------" + JSON.toJSONString(result));
            mMediaEntities.clear();
            mMediaEntities.addAll(result);
            mMediaEntities.add(mAddMediaEntity);
            mFileListAdapter.notifyDataSetChanged();
        } else if (requestCode == 0 && resultCode == 101) {
            Bundle bundle = data.getExtras();
            if (data==null||bundle==null){
                return;
            }
            List<ImageItem> beanList = (List<ImageItem>) bundle.getSerializable("list");
            List<MediaEntity> result = new ArrayList<>();
            for (int i = 0; i < beanList.size(); i++) {
                MediaEntity entity = new MediaEntity();
                entity.setLocalPath(beanList.get(i).path);
                entity.setCompressPath(beanList.get(i).path);
                result.add(entity);
            }
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
                    attach = !TextUtils.isEmpty(entity.getCompressPath()) ? entity.getCompressPath() : entity.getLocalPath();
                } else {
                    attach = attach + "," + entity.getLocalPath();
                }
                result.remove(entity);
                uploadFile(result);
            } else {
                Log.i(TAG, "------------" + getPictureSuffix(entity.getLocalPath()));
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
            Log.i(TAG, "-------" + attach);
            toRequest(ApiConstants.EventTags.scenes_add);
        }
    }

    private String getPictureSuffix(String path) {
        if (path.contains(".")) {
            return "live_" + System.currentTimeMillis() + path.substring(path.lastIndexOf("."), path.length());
        }
        return "live_" + System.currentTimeMillis() + ".jpg";
    }
}
