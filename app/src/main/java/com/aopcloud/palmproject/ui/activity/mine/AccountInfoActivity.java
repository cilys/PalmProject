package com.aopcloud.palmproject.ui.activity.mine;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.AddressDataBean;
import com.aopcloud.palmproject.bean.UserBean;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.dialog.InputDialog;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.enterprise.EnterpriseInfoActivity;
import com.aopcloud.palmproject.ui.adapter.file.FileListAdapter;
import com.aopcloud.palmproject.utils.AssetsUtil;
import com.aopcloud.palmproject.utils.JsonUtil;
import com.aopcloud.palmproject.utils.ListUtil;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.ThreadUtil;
import com.aopcloud.palmproject.view.CircleImageView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
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
 * @PackageName : com.aopcloud.basic.ui.activity.mine
 * @File : AccountInfoActivity.java
 * @Date : 2020/4/10 2020/4/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_mine_info)
public class AccountInfoActivity extends BaseActivity implements FileListAdapter.OnItemClickListener, FileListAdapter.OnItemChildClickListener {


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
    @BindView(R.id.iv_img)
    CircleImageView mIvImg;
    @BindView(R.id.tv_nike_name_tag)
    TextView mTvNikeNameTag;
    @BindView(R.id.et_nike_name)
    EditText mEtNikeName;
    @BindView(R.id.iv_nike_name)
    ImageView mIvNikeName;
    @BindView(R.id.rl_mine_card)
    RelativeLayout mRlMineCard;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.iv_sex)
    ImageView mIvSex;
    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.iv_city)
    ImageView mIvCity;
    @BindView(R.id.tv_desc_tag)
    TextView mTvDescTag;
    @BindView(R.id.et_desc)
    EditText mEtDesc;
    @BindView(R.id.iv_desc)
    ImageView mIvDesc;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.iv_name)
    ImageView mIvName;
    @BindView(R.id.et_mobile)
    EditText mEtMobile;
    @BindView(R.id.iv_mobile)
    ImageView mIvMobile;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.iv_email)
    ImageView mIvEmail;
    @BindView(R.id.et_address)
    EditText mEtAddress;
    @BindView(R.id.iv_address)
    ImageView mIvAddress;
    @BindView(R.id.tv_real_name_tag)
    TextView mTvRealNameTag;
    @BindView(R.id.tv_real_name_state)
    TextView mTvRealNameState;
    @BindView(R.id.tv_real_name)
    TextView mTvRealName;
    @BindView(R.id.iv_real_name)
    ImageView mIvRealName;
    @BindView(R.id.et_blank_name)
    EditText mEtBlankName;
    @BindView(R.id.iv_blank_name)
    ImageView mIvBlankName;
    @BindView(R.id.et_blank_no)
    EditText mEtBlankNo;
    @BindView(R.id.ivblank_no)
    ImageView mIvblankNo;
    @BindView(R.id.et_education)
    EditText mEtEducation;
    @BindView(R.id.iv_education)
    ImageView mIvEducation;
    @BindView(R.id.et_school)
    EditText mEtSchool;
    @BindView(R.id.iv_school)
    ImageView mIvSchool;
    @BindView(R.id.et_work_type)
    EditText mEtWorkType;
    @BindView(R.id.iv_work_type)
    ImageView mIvWorkType;
    @BindView(R.id.et_skill)
    EditText mEtSkill;
    @BindView(R.id.iv_skill)
    ImageView mIvSkill;
    @BindView(R.id.rv_list_img)
    RecyclerView mRvListImg;


    private FileListAdapter mFileListAdapter;
    private List<MediaEntity> mMediaEntities = new ArrayList<>();
    private MediaEntity mAddMediaEntity;
    private UserBean mUserBean;


    private ArrayList<AddressDataBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<AddressDataBean.CityListBean>> options2Items = new ArrayList<>();
    private List<List<List<AddressDataBean.CityListBean.AreaListBean>>> options3Items = new ArrayList<>();


    @Override
    protected void initData() {
        super.initData();
        ThreadUtil.runOnChildThread(new Runnable() {
            @Override
            public void run() {
                String json = AssetsUtil.getJson(AccountInfoActivity.this, "address.json");
                List<AddressDataBean> beanList = JSON.parseArray(json, AddressDataBean.class);
                initAddressData(beanList);
            }
        });
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("我的信息");
//        mTvHeaderRight.setText("编辑");
        mTvHeaderRight.setText("保存");
        mTvHeaderRight.setVisibility(View.GONE);

        toRequest(ApiConstants.EventTags.user_info);

        mAddMediaEntity = new MediaEntity();
        mFileListAdapter = new FileListAdapter(R.layout.item_default_file, mMediaEntities);
        mFileListAdapter.setOnItemClickListener(this);
        mFileListAdapter.setOnItemChildClickListener(this);
        mRvListImg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvListImg.setAdapter(mFileListAdapter);

        findViewById(R.id.rl_mine_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog("昵称", mEtNikeName);
            }
        });
        findViewById(R.id.rl_desc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog("个性签名", mEtDesc);
            }
        });
        findViewById(R.id.rl_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog("姓名", mEtName);
            }
        });
        findViewById(R.id.rl_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog("电子邮箱", mEtEmail);
            }
        });
        findViewById(R.id.rl_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog("联系地址", mEtAddress);
            }
        });
//        findViewById(R.id.rl_real_name).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showInputDialog("实名认证", mTvRealName);
//            }
//        });
        findViewById(R.id.rl_blank_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog("开户行", mEtBlankName);

            }
        });
        findViewById(R.id.rl_blank_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog("银行账号", mEtBlankNo);

            }
        });
        findViewById(R.id.rl_education).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog("学历", mEtEducation);
            }
        });
        findViewById(R.id.rl_school).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog("毕业院校", mEtSchool);
            }
        });
        findViewById(R.id.rl_work_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog("工种", mEtWorkType);
            }
        });
        findViewById(R.id.rl_skill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog("技能", mEtSkill);
            }
        });
        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkParams();
            }
        });
    }

    private InputDialog inputDialog;
    private void showInputDialog(String title, final EditText ed){
        InputMethodManager inputManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        inputDialog = new InputDialog(this);
        inputDialog.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        if (ed != null) {
            inputDialog.setMsg(ed.getText().toString());
        }
        inputDialog.setTitle(title)
                .setCanceledOnTouchOutside(false)
                .setCancelBtn(new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view, CharSequence input) {
                        dismissInputDialog();
                    }
                }).setCommitBtn(new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, View view, CharSequence input) {
                        if (input != null) {
                            if (ed != null) {
                                ed.setText(input);
                            }
                        }
                    }
                }).show();
    }
    private void dismissInputDialog(){
        if (inputDialog != null){
            inputDialog.dismiss();

//            InputMethodManager inputManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        inputDialog = null;
    }

    /**
     * 收起键盘
     *
     * @param v 一般为光标所在的EditView
     */
    protected void hideInput(View v) {
        if (v == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissInputDialog();
    }

    private void setLoginUser(UserBean userBean) {
        mUserBean = userBean;
        LoginUserUtil.setLoginUserBean(this, userBean);
        AppImageLoader.loadCircleImage(this, BuildConfig.BASE_URL + userBean.getAvatar(), mIvImg);

        avatar = userBean.getAvatar();
        mTvRealNameState.setText(userBean.getStatus() == 1 ? "已实名" : "未实名");
        mTvRealNameState.setBackgroundResource(userBean.getStatus() == 1 ? R.drawable.shape_real_name_s : R.drawable.shape_real_name_n);

        mTvRealName.setText(userBean.getName());
        mEtNikeName.setText("" + userBean.getNickname());
        mTvSex.setText("" + userBean.getSex());
        mTvCity.setText("" + userBean.getDistrict());
        mEtDesc.setText("" + userBean.getSinature());
        mEtName.setText("" + userBean.getName());
        mEtMobile.setText("" + userBean.getTel());
        mEtEmail.setText("" + userBean.getEmail());
        mEtAddress.setText("" + userBean.getAddress());
        mEtBlankName.setText("" + userBean.getAccount_type());
        mEtBlankNo.setText("" + userBean.getAccount_cardno());
        mEtSchool.setText("" + userBean.getSchool());
        mEtEducation.setText("" + userBean.getEducation());
        mEtWorkType.setText("" + "");
        mEtSkill.setText("" + userBean.getSkills());


        mEtName.setFocusableInTouchMode(false);
        mEtName.setFocusable(false);
        mEtNikeName.setFocusableInTouchMode(false);
        mEtNikeName.setFocusable(false);
        mEtBlankName.setFocusableInTouchMode(false);
        mEtBlankName.setFocusable(false);
        mEtBlankNo.setFocusableInTouchMode(false);
        mEtBlankNo.setFocusable(false);
        mEtDesc.setFocusableInTouchMode(false);
        mEtDesc.setFocusable(false);
        mEtEmail.setFocusableInTouchMode(false);
        mEtEmail.setFocusable(false);
        mEtSchool.setFocusableInTouchMode(false);
        mEtSchool.setFocusable(false);
        mEtEducation.setFocusableInTouchMode(false);
        mEtEducation.setFocusable(false);
        mEtWorkType.setFocusableInTouchMode(false);
        mEtWorkType.setFocusable(false);
        mEtSkill.setFocusableInTouchMode(false);
        mEtSkill.setFocusable(false);


        mEtAddress.setFocusableInTouchMode(false);
        mEtAddress.setFocusable(false);

        mEtMobile.setFocusableInTouchMode(false);
        mEtMobile.setFocusable(false);
    }

    public void showEdit() {
        mEtNikeName.setHint("请输入昵称");
        mEtDesc.setHint("请输入个性签名");
        mEtMobile.setHint("请输入手机好号");
        mEtEmail.setHint("请输入邮件地址");
        mEtAddress.setHint("请输入地址");
        mEtBlankName.setHint("请输入开户银行");
        mEtBlankNo.setHint("请输入银行账户");
        mEtSchool.setHint("请输入学校");
        mEtEducation.setHint("请输入学历");
        mEtWorkType.setHint("请输入工种");
        mEtSkill.setHint("请输入技能");

        mTvHeaderRight.setText("保存");


        mEtName.setFocusableInTouchMode(true);
        mEtName.setFocusable(true);
        mEtName.requestFocus();

        mEtNikeName.setFocusableInTouchMode(true);
        mEtNikeName.setFocusable(true);
        mEtNikeName.requestFocus();

        mEtBlankName.setFocusableInTouchMode(true);
        mEtBlankName.setFocusable(true);
        mEtBlankName.requestFocus();

        mEtBlankNo.setFocusableInTouchMode(true);
        mEtBlankNo.setFocusable(true);
        mEtBlankNo.requestFocus();

        mEtDesc.setFocusableInTouchMode(true);
        mEtDesc.setFocusable(true);
        mEtDesc.requestFocus();

        mEtEmail.setFocusableInTouchMode(true);
        mEtEmail.setFocusable(true);
        mEtEmail.requestFocus();

        mEtSchool.setFocusableInTouchMode(true);
        mEtSchool.setFocusable(true);
        mEtSchool.requestFocus();

        mEtEducation.setFocusableInTouchMode(true);
        mEtEducation.setFocusable(true);
        mEtEducation.requestFocus();

        mEtWorkType.setFocusableInTouchMode(true);
        mEtWorkType.setFocusable(true);
        mEtWorkType.requestFocus();


        mEtSkill.setFocusableInTouchMode(true);
        mEtSkill.setFocusable(true);
        mEtSkill.requestFocus();


        mEtAddress.setFocusableInTouchMode(true);
        mEtAddress.setFocusable(true);
        mEtAddress.requestFocus();

        mEtMobile.setFocusableInTouchMode(true);
        mEtMobile.setFocusable(true);
        mEtMobile.requestFocus();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (mTvHeaderRight.getText().equals("保存")) {
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
                    .start(AccountInfoActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 2);
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

    @OnClick({R.id.ll_header_back, R.id.iv_img, R.id.tv_sex, R.id.tv_city, R.id.tv_header_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.iv_img:
                if (mTvHeaderRight.getText().equals("编辑")) {
                } else {
                    Phoenix.with()
                            .theme(PhoenixOption.THEME_DEFAULT)// 主题
                            .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                            .maxPickNumber(1)// 最大选择数量
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
//                        .pickedMediaList(mMediaAdapter.getData())// 已选图片数据
                            .videoFilterTime(0)//显示多少秒以内的视频
                            .mediaFilterSize(10000)//显示多少kb以下的图片/视频，默认为0，表示不限制
                            //如果是在Activity里使用就传Activity，如果是在Fragment里使用就传Fragment
                            .start(AccountInfoActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 1);
                }
                break;
            case R.id.tv_sex:
                if (mTvHeaderRight.getText().equals("保存")) {
                    sexPickerView();
                }
                break;
            case R.id.tv_city:
                if (mTvHeaderRight.getText().equals("保存")) {
                    showAddress();
                }
                break;
            case R.id.tv_header_right:
                String str = mTvHeaderRight.getText().toString();
                if (str.equals("编辑")) {
                    showEdit();
                } else {
                    checkParams();
                }
                break;
        }
    }

    private void checkParams() {
        nickname = mEtNikeName.getText().toString();
        sex = mTvSex.getText().toString();
        district = mTvCity.getText().toString();
        sinature = mEtDesc.getText().toString();
        name = mEtName.getText().toString();
        email = mEtEmail.getText().toString();
        address = mEtAddress.getText().toString();
        account_type = mEtBlankName.getText().toString();
        account_cardno = mEtBlankNo.getText().toString();
        education = mEtEducation.getText().toString();
        school = mEtSchool.getText().toString();
        skills = mEtSkill.getText().toString();

        if (TextUtils.isEmpty(nickname)) {
            nickname = "";
        }
        if (TextUtils.isEmpty(sex)) {
            sex = "";
        }
        if (!TextUtils.isEmpty(sex)) {
            sex = sex.trim();
            if (!sex.equals("男") && !sex.equals("女")) {
                ToastUtil.showToast("请输入正确的性别");
                return;

            }
        }
        if (TextUtils.isEmpty(district)) {
            district = "";
        }
        if (TextUtils.isEmpty(sinature)) {
            sinature = "";
        }
        if (TextUtils.isEmpty(name)) {
            name = "";
        }
        if (TextUtils.isEmpty(email)) {
            email = "";
        }
        if (TextUtils.isEmpty(address)) {
            address = "";
        }
        if (TextUtils.isEmpty(account_type)) {
            account_type = "";
        }
        if (TextUtils.isEmpty(account_cardno)) {
            account_cardno = "";
        }
        if (TextUtils.isEmpty(education)) {
            education = "";
        }
        if (TextUtils.isEmpty(school)) {
            school = "";
        }
        if (TextUtils.isEmpty(skills)) {
            skills = "";
        }
        toRequest(ApiConstants.EventTags.user_info_update);
    }

    private String avatar;
    private String nickname;
    private String sex;
    private String district;
    private String sinature;
    private String name;
    private String email;
    private String address;
    private String account_type;
    private String account_cardno;
    private String education;
    private String school;

    private String skills;

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        if (eventTag == ApiConstants.EventTags.user_info) {
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.user_info, map);
        } else if (eventTag == ApiConstants.EventTags.user_info_update) {
            map.put("avatar", "" + avatar);
            map.put("nickname", "" + nickname);
            map.put("sex", "" + sex);
            map.put("district", "" + district);
            map.put("sinature", "" + sinature);
            map.put("name", "" + name);
            map.put("email", "" + email);
            map.put("address", "" + address);
            map.put("account_type", "" + account_type);
            map.put("account_cardno", "" + account_cardno);
            map.put("education", "" + education);
            map.put("school", "" + school);
            map.put("skills", "" + skills);
//            map.put("id_no", "" );
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(map));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.user_info_update, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.user_info) {
                UserBean userBean = JSON.parseObject(bean.getData(), UserBean.class);
                setLoginUser(userBean);
            } else if (eventTag == ApiConstants.EventTags.user_info_update) {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            List<MediaEntity> result = Phoenix.result(data);
            Logcat.i("------------" + JSON.toJSONString(result));
            uploadImg(result.get(0));
        }
    }

    private void uploadImg(MediaEntity imageItem) {
        OkHttpUtils.post().url(ApiConstants.file_upload)
                .addParams("token", "" + LoginUserUtil.getToken(this))
                .addFile("file", getPictureSuffix(imageItem.getLocalPath()), new File(imageItem.getLocalPath()))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dismissPopupLoading();
                        Logcat.w("add live video exception :" + e + "/");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dismissPopupLoading();
                        Logcat.i("add Serices Course  response :" + response);
                        ResultBean bean = JSON.parseObject(response, ResultBean.class);
                        if (bean != null && bean.getCode() == 0) {
                            avatar = JsonUtil.parserField(bean.getData(), "path");
                            AppImageLoader.loadCircleImage(AccountInfoActivity.this, BuildConfig.BASE_URL + "" + avatar, mIvImg);
                        } else {
                            ToastUtil.showToast(bean != null ? bean.getMsg() : "上传失败，请重试");
                        }
                    }
                });


    }

    private String getPictureSuffix(String path) {
        if (path.contains(".")) {
            return "live_" + System.currentTimeMillis() + path.substring(path.lastIndexOf("."), path.length());
        }
        return "live_" + System.currentTimeMillis() + ".jpg";
    }


    private void showAddress() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                district = options1Items.get(options1).getRegion_name() + "\t\t"
                        + options2Items.get(options1).get(options2).getRegion_name() + "\t\t"
                        + options3Items.get(options1).get(options2).get(options3).getRegion_name();
                Logcat.i("选择的地址：" + district);
                mTvCity.setText(district);
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


    public void sexPickerView() {
        List<String> mSexBeans = new ArrayList<>();
        mSexBeans.add("男");
        mSexBeans.add("女");
        OptionsPickerView options = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                sex = mSexBeans.get(options1);
                mTvSex.setText(mSexBeans.get(options1));
            }
        })
                .setTitleText("选择性别")
                .setSubCalSize(16)
                .setContentTextSize(14)
                .build();
        options.setPicker(mSexBeans);
        options.show();
    }


}
