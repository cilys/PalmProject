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
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.team.bean.TeamMemberBean;
import com.aopcloud.palmproject.utils.LoginUserUtil;

import java.util.HashMap;
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

@Layout(R.layout.activity_team_member_add)
public class TeamEditMemberActivity extends BaseActivity {


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
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.spinner_type)
    Spinner mSpinnerType;
    @BindView(R.id.spinner_majors)
    Spinner mSpinnerMajors;
    @BindView(R.id.et_day_wage)
    EditText mEtDayWage;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    private String team_id = "";
    private String user_id = "";

    private TeamMemberBean mTeamMemberBean;
    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            team_id = bundle.getString("team_id");
            String json  = bundle.getString("TeamMemberBean");
            mTeamMemberBean =JSON.parseObject(json,TeamMemberBean.class);
        }
    }


    @Override
    protected void initView() {
        mTvHeaderTitle.setText("添加班组成员");
        if (mTeamMemberBean!=null){
            user_id= mTeamMemberBean.getUser_id()+"";
            mTvName.setText("" + mTeamMemberBean.getUser_name());

            String[] m = ResourceUtil.getStringArray(R.array.spinner_team_majors);
            for (int i = 0; i <m.length ; i++) {
                if (m[i].equals(mTeamMemberBean.getMajors())){
                    mSpinnerMajors.setSelection(i);
                }
            }

            mSpinnerType.setSelection(mTeamMemberBean.getType()==1?0:1);
            mEtDayWage.setText(""+mTeamMemberBean.getSalary());
        }
    }


    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_name, R.id.tv_submit})
    public void onViewClicked(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_name:
                break;
            case R.id.tv_submit:
                String selectedItem = (String) mSpinnerType.getSelectedItem();
                majors = (String) mSpinnerMajors.getSelectedItem();
                salary = mEtDayWage.getText().toString();
                if (selectedItem.equals("班长")) {
                    type = "1";
                } else {
                    type = "0";
                }
                if (TextUtils.isEmpty(user_id)) {
                    ToastUtil.showToast("请选择成员");
                    return;
                }
                if (TextUtils.isEmpty(salary)) {
                    ToastUtil.showToast("请输入日薪资");
                    return;
                }
                toRequest(ApiConstants.EventTags.teammember_edit);
                break;
        }
    }

    private String majors;
    private String salary;
    private String type;

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        map.put("team_id", "" + team_id);
        if (eventTag == ApiConstants.EventTags.teammember_edit) {
            map.put("user_id", "" + user_id);
            map.put("majors", "" + majors);
            map.put("salary", "" + salary);
            map.put("type", "" + type);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.teammember_edit, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.teammember_edit) {
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
        ToastUtil.showToast("网络错误，请重试");
    }

}
