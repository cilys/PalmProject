package com.aopcloud.palmproject.ui.activity.enterprise;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.MainActivity;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.AddressDataBean;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.mine.AccountInfoActivity;
import com.aopcloud.palmproject.utils.AssetsUtil;
import com.aopcloud.palmproject.utils.JsonUtil;
import com.aopcloud.palmproject.utils.ListUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.ThreadUtil;
import com.aopcloud.palmproject.view.TipsDialog;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : CreateEnterpriseActivity.java
 * @Date : 2020/4/19 2020/4/19
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_enterprise_create)
public class CreateEnterpriseActivity extends BaseActivity {
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
    @BindView(R.id.spinner_industry)
    Spinner mSpinnerIndustry;
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.rl_area)
    RelativeLayout mRlArea;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    private String name;
    private String district = "";
    private String industry = "";

    private ArrayList<AddressDataBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<AddressDataBean.CityListBean>> options2Items = new ArrayList<>();
    private List<List<List<AddressDataBean.CityListBean.AreaListBean>>> options3Items = new ArrayList<>();


    @Override
    protected void initData() {
        super.initData();
        ThreadUtil.runOnChildThread(new Runnable() {
            @Override
            public void run() {
                String json = AssetsUtil.getJson(CreateEnterpriseActivity.this, "address.json");
                List<AddressDataBean> beanList = JSON.parseArray(json, AddressDataBean.class);
                initAddressData(beanList);
            }
        });
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("创建企业团队");

        mSpinnerIndustry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                industry = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.ll_header_back, R.id.tv_area, R.id.rl_area, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                gotoActivity(MainActivity.class);
                finish();
                break;
            case R.id.tv_area:
            case R.id.rl_area:
                showAddress();
                break;
            case R.id.tv_submit:
                name = mEtName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showToast("请输入企业名称");
                    return;
                }
                if (TextUtils.isEmpty(industry)) {
                    ToastUtil.showToast("请选择行业");
                    return;
                }
                if (TextUtils.isEmpty(district)) {
                    ToastUtil.showToast("请选择地区");
                    return;
                }
                showPopXupLoading("创建中...");
                toRequest(ApiConstants.EventTags.company_add);

                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        if (eventTag == ApiConstants.EventTags.company_add) {
            map.put("name", "" + name);
            map.put("industry", "" + industry);
            map.put("district", "" + district);
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_add, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_add) {
                String code = JsonUtil.parserField(bean.getData(), "code");
                LoginUserUtil.setCurrentEnterpriseNo(this, code);
                TipsDialog.wrap(this).setMsg("恭喜您已成功创建“" + name + "”，系统将为团队提供免费的60天会员体验！系统并将切换到新创建企业管理页面！").setTitle("创建企业团队成功")
                        .setShowCancel(false).setOnActionClickListener(new TipsDialog.onActionClickListener() {
                    @Override
                    public void onSure(Dialog dialog) {
                        dialog.dismiss();
                        gotoActivity(EnterpriseHomeActivity.class);
                        setResult(0);
                        finish();

                    }
                }).show();
            }
        } else {
            ToastUtil.showToast(bean != null ? bean.getMsg() : "加载错误，请重试");
        }

    }

    @Override
    public void onRequestFailureException(int eventTag, String msg) {
        super.onRequestFailureException(eventTag, msg);
        Logcat.i("------------" + eventTag + "/" + msg);
    }


    private void showAddress() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                district = options1Items.get(options1).getRegion_name() + "\t\t"
                        + options2Items.get(options1).get(options2).getRegion_name() + "\t\t"
                        + options3Items.get(options1).get(options2).get(options3).getRegion_name();
                Logcat.i("选择的地址：" + district);
                mTvArea.setText(district);
            }
        })
                .setTitleText("地区选择")
                .setContentTextSize(20)
                .isCenterLabel(false)
                .setBackgroundId(0x66000000) //设置外部遮罩颜色
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }

    private void initAddressData(List<AddressDataBean> beanList) {
        if (options1Items.size() > 0) {
            options1Items.clear();
        }
        if (options2Items.size() > 0) {
            options2Items.clear();
        }
        if (options3Items.size() > 0) {
            options2Items.clear();
        }

        if (ListUtil.isEmpty(beanList)) {
            Logcat.i("empty");
            return;
        }
        Logcat.i("------开始---------" + System.currentTimeMillis() / 1000 + "/"
                + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:").format(new Date()));
        int p = beanList.size();
        options1Items.addAll(beanList);
        for (int i = 0; i < p; i++) {
            List<AddressDataBean.CityListBean> cityListBeans = beanList.get(i).getCityList();
            options2Items.add((ArrayList<AddressDataBean.CityListBean>) cityListBeans);
            int c = beanList.get(i).getCityList().size();
            List<List<AddressDataBean.CityListBean.AreaListBean>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int j = 0; j < c; j++) {
                List<AddressDataBean.CityListBean.AreaListBean> City_AreaList = beanList.get(i).getCityList().get(j).getAreaList();
                Province_AreaList.add(City_AreaList);
            }
            options3Items.add(Province_AreaList);
        }
        Logcat.i("------结束---------" + System.currentTimeMillis() / 1000 + "/"
                + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));

    }

}

