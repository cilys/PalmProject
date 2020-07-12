package com.aopcloud.palmproject.ui.activity.log;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.adapter.file.FileListAdapter;
import com.aopcloud.palmproject.utils.JsonUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @PackageName : com.aopcloud.palmproject.ui.fragment.log
 * @File : AddWorkLogActivity.java
 * @Date : 2020/4/22 2020/4/22
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_work_log_add)
public class AddWorkLogActivity extends BaseActivity implements FileListAdapter.OnItemChildClickListener,
        FileListAdapter.OnItemClickListener{
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
    @BindView(R.id.rb_day)
    RadioButton mRbDay;
    @BindView(R.id.rb_week)
    RadioButton mRbWeek;
    @BindView(R.id.rb_month)
    RadioButton mRbMonth;
    @BindView(R.id.ll_day_date)
    LinearLayout mLlDayDate;
    @BindView(R.id.tv_day)
    TextView mTvDay;
    @BindView(R.id.tv_week_start)
    TextView mTvWeekStart;
    @BindView(R.id.tv_week_end)
    TextView mTvWeekEnd;
    @BindView(R.id.ll_week_date)
    LinearLayout mLlWeekDate;
    @BindView(R.id.tv_month)
    TextView mTvMonth;
    @BindView(R.id.ll_month_date)
    LinearLayout mLlMonthDate;
    @BindView(R.id.tv_complete_count)
    TextView mTvCompleteCount;
    @BindView(R.id.tv_expire_count)
    TextView mTvExpireCount;
    @BindView(R.id.tv_progress_count)
    TextView mTvProgressCount;
    @BindView(R.id.et_summary)
    EditText mEtSummary;
    @BindView(R.id.tv_summary_count)
    TextView mTvSummaryCount;
    @BindView(R.id.et_plan)
    EditText mEtPlan;
    @BindView(R.id.tv_plan_count)
    TextView mTvPlanCount;
    @BindView(R.id.tv_img_count)
    TextView mTvImgCount;
    @BindView(R.id.tv_notify)
    TextView mTvNotify;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.et_finished)
    EditText mEtFinished;
    @BindView(R.id.et_overtime)
    EditText mEtOvertime;
    @BindView(R.id.et_doing)
    EditText mEtDoing;
    @BindView(R.id.rv_list_img)
    RecyclerView mRvListImg;

    private String type;
    private String tasks_finished;
    private String tasks_overtime;
    private String tasks_doing;
    private String summary;
    private String plan;
    private String attach;


    private FileListAdapter mFileListAdapter;

    private List<MediaEntity> mMediaEntities = new ArrayList<>();

    private  MediaEntity mAddMediaEntity;
    @Override
    protected void initView() {
        mTvHeaderTitle.setText("新建汇报");
        mRbDay.performClick();
        mAddMediaEntity = new MediaEntity();
        mMediaEntities.add(mAddMediaEntity);
        mFileListAdapter = new FileListAdapter(R.layout.item_default_file, mMediaEntities);
        mFileListAdapter.setEdit(true);
        mFileListAdapter.setOnItemClickListener(this);
        mFileListAdapter.setOnItemChildClickListener(this);
        mRvListImg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvListImg.setAdapter(mFileListAdapter);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        mTvDay.setText(""+dateFormat.format(calendar.getTime()));
        SimpleDateFormat month = new SimpleDateFormat("yyyy年MM月");
        mTvMonth.setText(""+month.format(calendar.getTime()));



        mTvWeekStart.setText(""+dateFormat.format(getCurrentWeekDateBegin(calendar .getTime())));
        mTvWeekEnd.setText(""+dateFormat.format(getCurrentWeekDateEnd(calendar .getTime())));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (position==mMediaEntities.size()-1) {
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
                    .start(AddWorkLogActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 2);
        } else {

        }

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.iv_del) {
            mMediaEntities.remove(position);
            mFileListAdapter.notifyDataSetChanged();
        }


    }
    @Override
    protected void setListener() {
        super.setListener();
        mEtSummary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null != s && s.length() > 0) {
                    mTvSummaryCount.setText(s.length() + "/3000");
                }else {
                    mTvSummaryCount.setText("0/3000");
                }
            }
        });
        mEtPlan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null != s && s.length() > 0) {
                    mTvPlanCount.setText(s.length() + "/3000");
                }else {
                    mTvPlanCount.setText( "0/3000");
                }
            }
        });
    }

    @OnClick({R.id.ll_header_back, R.id.rb_day, R.id.rb_week, R.id.rb_month, R.id.tv_notify, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.rb_day:
                mLlDayDate.setVisibility(View.VISIBLE);
                mLlWeekDate.setVisibility(View.GONE);
                mLlMonthDate.setVisibility(View.GONE);
                mTvCompleteCount.setText("当日完成任务");
                mTvExpireCount.setText("当日过期任务");
                mTvProgressCount.setText("当日进行中任务");
                type = "1";
                break;
            case R.id.rb_week:
                mLlDayDate.setVisibility(View.GONE);
                mLlWeekDate.setVisibility(View.VISIBLE);
                mLlMonthDate.setVisibility(View.GONE);
                mTvCompleteCount.setText("本周完成任务");
                mTvExpireCount.setText("本周过期任务");
                mTvProgressCount.setText("本周进行中任务");
                type = "2";
                break;
            case R.id.rb_month:
                mLlDayDate.setVisibility(View.GONE);
                mLlWeekDate.setVisibility(View.GONE);
                mLlMonthDate.setVisibility(View.VISIBLE);
                mTvCompleteCount.setText("本月完成任务");
                mTvExpireCount.setText("本月过期任务");
                mTvProgressCount.setText("本月进行中任务");
                type = "3";
                break;
            case R.id.tv_notify:
                break;
            case R.id.tv_submit:
                tasks_finished = mEtFinished.getText().toString();
                tasks_overtime = mEtOvertime.getText().toString();
                tasks_doing = mEtDoing.getText().toString();
                plan = mEtPlan.getText().toString();
                summary = mEtSummary.getText().toString();
                checkParams();
                break;
        }
    }

    private void checkParams() {
        if (TextUtils.isEmpty(tasks_finished)) {
            ToastUtil.showToast("请输入已完成任务数量");
            return;
        }
        if (TextUtils.isEmpty(tasks_overtime)) {
            ToastUtil.showToast("请输入已未完成任务数量");
            return;
        }
        if (TextUtils.isEmpty(tasks_doing)) {
            ToastUtil.showToast("请输入进行中任务数量");
            return;
        }
        if (TextUtils.isEmpty(summary)) {
            ToastUtil.showToast("请输入工作总结");
            return;
        }
        if (TextUtils.isEmpty(plan)) {
            ToastUtil.showToast("请输入工作计划");
            return;
        }

        showPopXupLoading("提交中");
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
        if (eventTag == ApiConstants.EventTags.reportjob_add) {
            map.put("type", "" + type);
            map.put("tasks_finished", "" + tasks_finished);
            map.put("tasks_overtime", "" + tasks_overtime);
            map.put("tasks_doing", "" + tasks_doing);
            map.put("summary", "" + summary);
            map.put("plan", "" + plan);
            map.put("attach", "" + attach);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.reportjob_add, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.reportjob_add) {
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
            toRequest(ApiConstants.EventTags.reportjob_add);
        }
    }
    private String getPictureSuffix(String path) {
        if (path.contains(".")) {
            return "live_" + System.currentTimeMillis() + path.substring(path.lastIndexOf("."), path.length());
        }
        return "live_" + System.currentTimeMillis() + ".jpg";
    }

    /**
     * pass
     * 返回当前周的开始时间
     * @return Date
     */
    public static Date getCurrentWeekDateBegin(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dateBegin = calendar.getActualMinimum(Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.DAY_OF_WEEK,dateBegin);
        return calendar.getTime();
    }

    /**
     * pass
     * 返回当前周的结束时间
     * @return Date
     */
    public static Date getCurrentWeekDateEnd(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dateEnd = calendar.getActualMaximum(Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.DAY_OF_WEEK,dateEnd);
        return calendar.getTime();
    }

}
