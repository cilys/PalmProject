package com.aopcloud.palmproject.ui.activity.team;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.team
 * @File : EnterpriseTeamListActivity.java
 * @Date : 2020/5/5 2020/5/5
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_team_edit)
public class TeamEditActivity extends BaseActivity {

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
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.spinner_type)
    Spinner mSpinnerType;
    @BindView(R.id.et_function)
    EditText mEtFunction;


    private String team_id = "";

    private String name;
    private String industry;
    private String district = "广州市";
    private String status = "1";


    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            team_id = bundle.getString("team_id");
            name = bundle.getString("name");
            industry = bundle.getString("industry","");
        }
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("班组设置");
        mTvHeaderRight.setText("删除");


        mEtName.setText(""+name);
        mEtFunction.setText(""+industry);

//
//        String[] m = ResourceUtil.getStringArray(R.array.spinner_team_function);
//        for (int i = 0; i <m.length ; i++) {
//            if (m[i].equals(industry)){
//                mSpinnerFunction.setSelection(i);
//            }
//        }

    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                toRequest(ApiConstants.EventTags.team_del);
                break;
            case R.id.tv_submit:
                name = mEtName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入班组名称");
                    return;
                }
                industry = mEtFunction.getText().toString();
                if (TextUtils.isEmpty(industry)) {
                    ToastUtil.showToast("请输入班组功能类型");
                    return;
                }
                toRequest(ApiConstants.EventTags.team_add);
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("team_id", "" + team_id);
        if (eventTag == ApiConstants.EventTags.team_edit) {
            map.put("name", "" + name);
            map.put("industry", "" + industry);
            map.put("district", "" + district);
            map.put("status", "" + status);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.team_edit, map);
        }else if (eventTag==ApiConstants.EventTags.team_del) {
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.team_del, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.team_edit) {
                setResult(0);
                finish();
            } if (eventTag == ApiConstants.EventTags.team_del) {
                setResult(-1);
                finish();
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
        ToastUtil.showToast("网络错误，请重试");
    }


    private OptionsPickerView pvOptions;


    public void showIindustry() {
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
                industry = list.get(options1);
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


}
