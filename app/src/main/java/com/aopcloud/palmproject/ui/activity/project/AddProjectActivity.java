package com.aopcloud.palmproject.ui.activity.project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.base.util.ViewUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.department.DepartmentLeaderSelectActivity;
import com.aopcloud.palmproject.ui.activity.map.SelectLocationActivity;
import com.aopcloud.palmproject.ui.adapter.project.ProjectRangeAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.project
 * @File : AddProjectActivity.java
 * @Date : 2020/4/26 2020/4/26
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_project_add)
public class AddProjectActivity extends BaseActivity {
    @BindView(R.id.ll_header_back)
    LinearLayout mLlHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mTvHeaderTitle;
    @BindView(R.id.ll_header_right)
    LinearLayout mLlHeaderRight;
    @BindView(R.id.ll_common_layout)
    LinearLayout mLlCommonLayout;
    @BindView(R.id.tv_enterprise_name)
    TextView mTvEnterpriseName;
    @BindView(R.id.et_project_name)
    EditText mEtProjectName;
    @BindView(R.id.et_project_title)
    EditText mEtProjectTitle;
    @BindView(R.id.tv_project_type)
    TextView mTvProjectType;
    @BindView(R.id.iv_project_type)
    ImageView mIvProjectType;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_name_more)
    ImageView mIvNameMore;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.iv_address_more)
    ImageView mIvAddressMore;
    @BindView(R.id.tv_range_type)
    TextView mTvRangeType;
    @BindView(R.id.tv_range_more)
    ImageView mTvRangeMore;
    @BindView(R.id.et_price)
    EditText mEtPrice;
    @BindView(R.id.iv_price_more)
    ImageView mIvPriceMore;
    @BindView(R.id.et_area)
    EditText mEtArea;
    @BindView(R.id.iv_area_more)
    ImageView mIvAreaMore;
    @BindView(R.id.et_describe)
    EditText mEtDescribe;
    @BindView(R.id.et_material)
    EditText mEtMaterial;
    @BindView(R.id.tv_more_attribute)
    TextView mTvMoreAttribute;
    @BindView(R.id.ll_more)
    LinearLayout mLLMore;
    @BindView(R.id.iv_more_attribute)
    ImageView mIvMoreAttribute;
    @BindView(R.id.ll_more_attribute)
    LinearLayout mLLMoreAttribute;
    @BindView(R.id.tv_project_status)
    TextView mTvProjectStatus;



    private String longitude;
    private String latitude;
    private String address;
    private String range = "";
    private String name = "";
    private String short_name = "";
    private String leader_id = "";
    private String start_date = "";
    private String end_date = "";
    private String status = "";
    protected String rights = "1";
    private String pid = "0";
    private String project_code = "";
    private String type = "";
    private String price = "";
    private String summary = "";
    private String area = "";


    private boolean more;
    private boolean isChild;


    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isChild = bundle.getBoolean("child", false);
        }
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("新建项目");

        more = true;
        mLLMore.setVisibility(View.GONE);
        leader_id = "" + LoginUserUtil.getLoginUserBean(this).getId();
        mTvName.setText("" + LoginUserUtil.getLoginUserBean(this).getName());


        if (isChild) {
            mTvEnterpriseName.setText("");
            mTvEnterpriseName.setHint("请选择上级项目");
        } else {
            mTvEnterpriseName.setText("" + LoginUserUtil.getCurrentEnterpriseBean(this).getName());
        }

        mTvRangeType.setText("默认公开");
        rights = "0";
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_enterprise_name, R.id.tv_project_type,R.id.tv_range_type, R.id.tv_range_more, R.id.tv_name, R.id.ll_more_attribute
            , R.id.iv_address_more, R.id.tv_address, R.id.tv_start_time, R.id.tv_end_time, R.id.tv_submit, R.id.tv_project_status
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_enterprise_name:
                if (isChild) {
                    gotoActivity(SelectParentProjectActivity.class, 3);
                }
                break;
            case R.id.tv_project_type:
                showType();
                break;

            case R.id.tv_project_status:
                showStatus();
                break;

            case R.id.tv_range_type:
            case R.id.tv_range_more:
                showAddRange();
                break;
            case R.id.tv_name:
                gotoActivity(DepartmentLeaderSelectActivity.class, 2);
                break;
            case R.id.tv_start_time:
                showTimePicker(0);
                break;
            case R.id.tv_end_time:
                showTimePicker(1);
                break;
            case R.id.tv_address:
            case R.id.iv_address_more:
                gotoActivity(SelectLocationActivity.class, 1);
                break;
            case R.id.ll_more_attribute:
                if (more) {
                    mLLMore.setVisibility(View.VISIBLE);
                    mTvMoreAttribute.setText("收起属性");
                    mIvMoreAttribute.setImageResource(R.mipmap.icon_arrow_up);
                } else {
                    mLLMore.setVisibility(View.GONE);
                    mTvMoreAttribute.setText("更多属性");
                    mIvMoreAttribute.setImageResource(R.mipmap.icon_arrows_down);
                }
                more = !more;
                break;
            case R.id.tv_submit:
                checkParams();
                break;
        }
    }

    private void checkParams() {
        name = mEtProjectName.getText().toString();
        short_name = mEtProjectTitle.getText().toString();
        price = mEtPrice.getText().toString();
        area = mEtArea.getText().toString();
        summary = mEtDescribe.getText().toString();
        status = "勘查设计";


        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast("请输入项目名称");
            return;
        }
        if (TextUtils.isEmpty(short_name)) {
            ToastUtil.showToast("请输入项目简称");
            return;
        }
        if (TextUtils.isEmpty(type)) {
            ToastUtil.showToast("请选择项目类型");
            return;
        }
        if (TextUtils.isEmpty(leader_id)) {
            ToastUtil.showToast("请选择项目负责人");
            return;
        }
//        if (TextUtils.isEmpty(start_date) || TextUtils.isEmpty(end_date)) {
//            ToastUtil.showToast("请选择开始时间和结束时间");
//            return;
//        }
        if (TextUtils.isEmpty(end_date)) {
            ToastUtil.showToast("请选择结束时间");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            ToastUtil.showToast("请选择地址");
            return;
        }

        toRequest(ApiConstants.EventTags.project_add);

    }

    public void showType() {
        List list = new ArrayList();
        list.add("土建工程");
        list.add("安装工程");
        list.add("试验工程");
        OptionsPickerView pickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                type = list.get(options1).toString();
                mTvProjectType.setText(""+type);

            }
        }).build();
        pickerView.setNPicker(list,null,null);
        pickerView.show();
    }

    public void showStatus() {
        List list = new ArrayList();
        list.add("勘查设计");
        list.add("开工预备");
        list.add("在建");
        list.add("竣工验收");
        list.add("完结维保");
        list.add("已终止");
        list.add("已停工");
        OptionsPickerView pickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                status = list.get(options1).toString();
                mTvProjectStatus.setText("" + status);

            }
        }).build();
        pickerView.setNPicker(list,null,null);
        pickerView.show();
    }

    public void showAddRange() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_project_range);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setBackgroundDrawableResource(R.color.theme_translucent);
        lp.width = ViewUtil.getScreenWidth(this);
        lp.height = ViewUtil.getScreenHeight(this);
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        List<ProjectRangeAdapter.ProjectRangeBean> list = new ArrayList<>();
        ProjectRangeAdapter.ProjectRangeBean bean = new ProjectRangeAdapter.ProjectRangeBean();
        bean.setType("1");
        bean.setName("私有");
        bean.setDesc("仅项目成员可查看和编辑");
        ProjectRangeAdapter.ProjectRangeBean bean1 = new ProjectRangeAdapter.ProjectRangeBean();
        bean1.setType("2");
        bean1.setName("企业可见");
        bean1.setDesc("企业内所有成员可见，仅项目成员可编辑");
        ProjectRangeAdapter.ProjectRangeBean bean2 = new ProjectRangeAdapter.ProjectRangeBean();
        bean2.setType("0");
        bean2.setName("公开");
        bean2.setDesc("所有人可见");
        list.add(bean);
        list.add(bean1);
        list.add(bean2);
        RecyclerView recyclerView = dialog.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProjectRangeAdapter adapter = new ProjectRangeAdapter(R.layout.item_project_range_add, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < list.size(); i++) {
                    if (position == i) {
                        list.get(i).setSelect(!list.get(i).isSelect());
                    } else {
                        list.get(i).setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean select;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelect()) {
                        rights = list.get(i).getType();
                        mTvRangeType.setText("" + list.get(i).getName());
                    }
                }

            }
        });
    }

    public void showTimePicker(int type) {
        TimePickerView pickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                if (type == 0) {
                    start_date = dateFormat1.format(date);
                    mTvStartTime.setText(dateFormat.format(date));
                } else {
                    end_date = dateFormat1.format(date);
                    mTvEndTime.setText(dateFormat.format(date));
                }
            }
        })
                .isDialog(true)
                .setLineSpacingMultiplier(2).build();
        pickerView.show();


    }

    public void showSuccess() {
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
                setResult(0);
                finish();
                dialog.dismiss();
            }
        });
    }


    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.project_add) {
            map.put("name", "" + name);//项目名称
            map.put("short_name", "" + short_name);//项目简介
            map.put("leader_id", "" + leader_id);//管理员ID
//            map.put("start_date", "" + start_date);//开始时间
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            start_date = dateFormat1.format(new Date(System.currentTimeMillis()));
            map.put("start_date", start_date);
            map.put("end_date", "" + end_date);//结束时间
            map.put("address", "" + address);//地址
            map.put("longitue", "" + longitude);//精度
//            map.put("longitude", "" + longitude);//精度
            map.put("latitude", "" + latitude);//维度
            map.put("status", "" + status);//状态  状态，勘查设计|开工预备|在建|竣工验收|完结维保|已终止|已暂停
            map.put("rights", "" + rights);//范围 范围，0：公开|1：私有|2：企业可见
            map.put("pid", "" + pid);// 上级 (可选)，上级ID,0则代表没有上级，其它则代表为子项目，默认：0
            map.put("project_code", "" + 1);//(可选),项目编号
            map.put("type", "" + type);//(可选)项目类型
            map.put("price", "" + price);//(可选)项目造价，万元
            map.put("area", "" + area);//(可选)项目面积,平方
            map.put("summary", "" + summary);//(可选)项目简介
            map.put("start_date_real", "" + start_date);//(可选)实际开工日期
            map.put("end_date_real", "" + end_date);//(可选)实际竣工日期

            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.project_add, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.project_add) {
                showSuccess();

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (BuildConfig.DEBUG) {
            if (requestCode == 1) {

                longitude = "116.39585";
                latitude = "39.909068";
                address = "北京天安门";
                range = "300";

                mTvAddress.setText("" + address);
            }
        }

        if (data == null) {
            return;
        }
        if (requestCode == 0) {

        } else if (requestCode == 1) {
            Bundle bundle = data.getExtras();
            longitude = bundle.getString("longitude");
            latitude = bundle.getString("latitude");
            address = bundle.getString("address");
            range = bundle.getString("range");

            mTvAddress.setText("" + address);
        } else if (requestCode == 2) {
            Bundle bundle = data.getExtras();
            leader_id = bundle.getString("user_id", "");
            String leader_name = bundle.getString("user_name");
            mTvName.setText("" + leader_name);
        } else if (requestCode == 3) {
            Bundle bundle = data.getExtras();
            pid = bundle.getString("project_id", "");
            String project_name = bundle.getString("project_name");
            mTvEnterpriseName.setText("" + project_name);
        }
    }
}
