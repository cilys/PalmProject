package com.aopcloud.palmproject.ui.activity.enterprise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.base.BaseActivity;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.bean.AddressDataBean;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.ui.activity.camera.PreviewActivity;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseInfoBean;
import com.aopcloud.palmproject.ui.activity.enterprise.bean.EnterpriseManagerBean;
import com.aopcloud.palmproject.ui.activity.mine.AccountInfoActivity;
import com.aopcloud.palmproject.ui.adapter.file.FileListAdapter;
import com.aopcloud.palmproject.ui.adapter.file.PreviewAdapter;
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
import java.io.Serializable;
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
 * @PackageName : com.aopcloud.palmproject.ui.activity.enterprise
 * @File : EnterpriseInfoActivity.java
 * @Date : 2020/4/20 2020/4/20
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_enterprise_info)
public class EnterpriseInfoActivity extends BaseActivity implements FileListAdapter.OnItemClickListener, FileListAdapter.OnItemChildClickListener {


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
    @BindView(R.id.iv_logo)
    CircleImageView mIvLogo;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.iv_name)
    ImageView mIvName;
    @BindView(R.id.tv_industry)
    TextView mTvIndustry;
    @BindView(R.id.et_industry)
    EditText mEtIndustry;
    @BindView(R.id.iv_industry)
    ImageView mIvIndustry;
    @BindView(R.id.tv_area_tag)
    TextView mTvAreaTag;
    @BindView(R.id.tv_district)
    TextView mTvDistrict;
    @BindView(R.id.iv_area)
    ImageView mIvArea;
    @BindView(R.id.et_manger_name)
    EditText mEtMangerName;
    @BindView(R.id.iv_manger_name)
    ImageView mIvMangerName;
    @BindView(R.id.et_amount)
    EditText mEtAmount;
    @BindView(R.id.et_account_name)
    EditText mEtAccountName;
    @BindView(R.id.et_blank_name)
    EditText mEtBlankName;
    @BindView(R.id.et_blank_no)
    EditText mEtBlankNo;
    @BindView(R.id.rv_list_img)
    RecyclerView mRvListImg;
    private EnterpriseInfoBean mEnterpriseInfoBean;

    private String logo;
    private String name;
    private String district;
    private String industry;
    private String leader_id;
    private String registered_capital;
    private String account_name;
    private String account_type;
    private String account_cardno;
    private String id_photo = "";


    private FileListAdapter mFileListAdapter;

    private List<MediaEntity> mMediaEntities = new ArrayList<>();
    private MediaEntity mAddMediaEntity;

    private ArrayList<AddressDataBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<AddressDataBean.CityListBean>> options2Items = new ArrayList<>();
    private List<List<List<AddressDataBean.CityListBean.AreaListBean>>> options3Items = new ArrayList<>();


    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mEnterpriseInfoBean = JSON.parseObject(bundle.getString("enterpriseInfoBean"), EnterpriseInfoBean.class);
        }
        ThreadUtil.runOnChildThread(new Runnable() {
            @Override
            public void run() {
                String json = AssetsUtil.getJson(EnterpriseInfoActivity.this, "address.json");
                List<AddressDataBean> beanList = JSON.parseArray(json, AddressDataBean.class);
                initAddressData(beanList);
            }
        });
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("企业基本信息");
        mTvHeaderRight.setText("编辑");
        mAddMediaEntity = new MediaEntity();
        mFileListAdapter = new FileListAdapter(R.layout.item_default_file, mMediaEntities);
        mFileListAdapter.setOnItemClickListener(this);
        mFileListAdapter.setOnItemChildClickListener(this);
        mRvListImg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRvListImg.setAdapter(mFileListAdapter);


        if (mEnterpriseInfoBean != null) {
            logo = mEnterpriseInfoBean.getLogo();
            leader_id = "" + LoginUserUtil.getLoginUserBean(this).getId();
            AppImageLoader.loadCircleImage(this, BuildConfig.BASE_URL + "" + mEnterpriseInfoBean.getLogo(), mIvLogo);
            mEtName.setText(mEnterpriseInfoBean.getName());
            mEtMangerName.setText(mEnterpriseInfoBean.getLeader_name());
            mEtIndustry.setText(mEnterpriseInfoBean.getIndustry());
            mTvDistrict.setText(mEnterpriseInfoBean.getDistrict());
            mEtAmount.setText(mEnterpriseInfoBean.getRegistered_capital() + "");
            mEtAccountName.setText(mEnterpriseInfoBean.getAccount_name());
            mEtBlankNo.setText(mEnterpriseInfoBean.getAccount_cardno());
            mEtBlankName.setText(mEnterpriseInfoBean.getAccount_type());
            mEtAmount.setText("" + mEnterpriseInfoBean.getRegistered_capital());


            if (!TextUtils.isEmpty(mEnterpriseInfoBean.getId_photo())) {
                String[] strings = mEnterpriseInfoBean.getId_photo().split(",");
                for (int i = 0; i < strings.length; i++) {
                    MediaEntity entity = new MediaEntity();
                    entity.setMediaName("img_add_network");
                    entity.setLocalPath("" + strings[i]);
                    mMediaEntities.add(entity);
                }
            }
        }
        setEdit(false);

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
                    .start(EnterpriseInfoActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 2);
        } else {
            List<PreviewAdapter.PreviewBean> list = new ArrayList();
            for (int i = 0; i < mMediaEntities.size(); i++) {
                if (mMediaEntities.get(i).getMediaName().equals("img_add_network")){
                    list.add(new PreviewAdapter.PreviewBean(BuildConfig.BASE_URL + mMediaEntities.get(i).getLocalPath()));
                }else {
                    list.add(new PreviewAdapter.PreviewBean(mMediaEntities.get(i).getLocalPath()));
                }
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("PreviewBean", (Serializable) list);
            gotoActivity(PreviewActivity.class, bundle);
        }
    }



    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.iv_del) {
            mMediaEntities.remove(position);
            mFileListAdapter.notifyDataSetChanged();
        }


    }

    public void setEdit(boolean edit) {
        mIvArea.setVisibility(edit ? View.VISIBLE : View.INVISIBLE);
        mIvIndustry.setVisibility(edit ? View.VISIBLE : View.INVISIBLE);
        mIvMangerName.setVisibility(edit ? View.VISIBLE : View.INVISIBLE);
        mIvName.setVisibility(edit ? View.VISIBLE : View.INVISIBLE);
        if (edit) {
            mEtAccountName.setFocusableInTouchMode(true);
            mEtAccountName.setFocusable(true);
            mEtAccountName.requestFocus();

            mEtBlankName.setFocusableInTouchMode(true);
            mEtBlankName.setFocusable(true);
            mEtBlankName.requestFocus();

            mEtAccountName.setFocusableInTouchMode(true);
            mEtAccountName.setFocusable(true);
            mEtAccountName.requestFocus();

            mEtBlankNo.setFocusableInTouchMode(true);
            mEtBlankNo.setFocusable(true);
            mEtBlankNo.requestFocus();

            mEtIndustry.setFocusableInTouchMode(true);
            mEtIndustry.setFocusable(true);
            mEtIndustry.requestFocus();

            mEtName.setFocusableInTouchMode(true);
            mEtName.setFocusable(true);
            mEtName.requestFocus();

            mEtBlankNo.setFocusableInTouchMode(true);
            mEtBlankNo.setFocusable(true);
            mEtBlankNo.requestFocus();

            mEtMangerName.setFocusableInTouchMode(true);
            mEtMangerName.setFocusable(true);
            mEtMangerName.requestFocus();

            mEtAmount.setFocusableInTouchMode(true);
            mEtAmount.setFocusable(true);
            mEtAmount.requestFocus();


            mTvHeaderRight.setText("保存");

            mEtAccountName.setHint("");
            mEtBlankNo.setHint("");
            mEtBlankName.setHint("");

            mMediaEntities.add(mAddMediaEntity);
            mFileListAdapter.setEdit(true);
            mFileListAdapter.notifyDataSetChanged();
        } else {
            mEtName.setFocusableInTouchMode(false);
            mEtName.setFocusable(false);
            mEtAccountName.setFocusableInTouchMode(false);
            mEtAccountName.setFocusable(false);
            mEtBlankName.setFocusableInTouchMode(false);
            mEtBlankName.setFocusable(false);
            mEtBlankNo.setFocusableInTouchMode(false);
            mEtBlankNo.setFocusable(false);
            mEtIndustry.setFocusableInTouchMode(false);
            mEtIndustry.setFocusable(false);
            mEtMangerName.setFocusableInTouchMode(false);
            mEtMangerName.setFocusable(false);
            mEtAmount.setFocusableInTouchMode(false);
            mEtAmount.setFocusable(false);

            mEtAccountName.setHint("请输入账户名称");
            mEtBlankNo.setHint("请输入账号");
            mEtBlankName.setHint("请输入名称");
        }


    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.iv_logo, R.id.tv_district, R.id.tv_industry, R.id.iv_area})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                toRequest(ApiConstants.EventTags.manage_all);
                break;
            case R.id.iv_logo:
                if (mTvHeaderRight.getText().equals("编辑")) {

                } else {
                    Phoenix.with()
                            .theme(PhoenixOption.THEME_DEFAULT)// 主题
                            .fileType(MimeType.ofAll())//显示的文件类型图片、视频、图片和视频
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
                            .start(EnterpriseInfoActivity.this, PhoenixOption.TYPE_PICK_MEDIA, 1);
                }
                break;
            case R.id.tv_district:
                if (mTvHeaderRight.getText().equals("保存")) {
                    showAddress();
                }
                break;
            case R.id.iv_area:
                break;
            case R.id.tv_industry:
                if (mTvHeaderRight.getText().equals("保存")) {
                    onPickerIndustry();
                }
                break;
        }
    }

    private void checkParams() {
        name = mEtName.getText().toString();
        industry = mEtIndustry.getText().toString();
        registered_capital = mEtMangerName.getText().toString();
        account_name = mEtAccountName.getText().toString();
        account_type = mEtBlankName.getText().toString();
        account_cardno = mEtBlankNo.getText().toString();

        id_photo = "";
        List<MediaEntity> list = new ArrayList();
        list.addAll(mMediaEntities);
        if (list.contains(mAddMediaEntity)) {
            list.remove(mAddMediaEntity);
        }
        showPopXupLoading("更新中");

        Logcat.i("-------mAddMediaEntity-----" + JSON.toJSONString(mMediaEntities));
        uploadFile(list);
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map params = new HashMap();
        params.put("token", "" + LoginUserUtil.getToken(this));
        params.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.company_update) {
            params.put("logo", "" + logo);
            params.put("name", "" + name);
            params.put("district", "" + district);
            params.put("industry", "" + industry);
            params.put("leader_id", "" + leader_id);
            params.put("registered_capital", "" + registered_capital);
            params.put("account_name", "" + account_name);
            params.put("account_type", "" + account_type);
            params.put("account_cardno", "" + account_cardno);
            params.put("id_photo", "" + id_photo);
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(params));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_update, params);
        } else if (eventTag == ApiConstants.EventTags.manage_all) {
            Logcat.i("------------" + eventTag + "/" + JSON.toJSONString(params));
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.manage_all, params);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        Logcat.i("------------" + eventTag + "/" + result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_update) {
                setResult(0);
                finish();
            } if (eventTag == ApiConstants.EventTags.manage_all) {
                List<EnterpriseManagerBean> beanList = JSON.parseArray(bean.getData(),EnterpriseManagerBean.class);
                boolean s = false;
                for (int i = 0; i <beanList.size() ; i++) {
                    if (beanList.get(i).getUser_id()==LoginUserUtil.getLoginUserBean(this).getId()
                    &&beanList.get(i).getRights().contains("company:info")){
                        s= true;
                    }
                }
                if (s){
                    if (mTvHeaderRight.getText().equals("编辑")) {
                        setEdit(true);
                    } else {
                        checkParams();
                    }
                }else {
                    ToastUtil.showToast("无权访问");
                }

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
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
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
                if (TextUtils.isEmpty(id_photo)) {
                    id_photo = entity.getLocalPath();
                } else {
                    id_photo = id_photo + "," + entity.getLocalPath();
                }
                result.remove(entity);
                uploadFile(result);
            } else {
                Logcat.d("-----file-----" + JSON.toJSONString(entity));
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
                                    if (TextUtils.isEmpty(id_photo)) {
                                        id_photo = JsonUtil.parserField(bean.getData(), "path");
                                    } else {
                                        id_photo = id_photo + "," + JsonUtil.parserField(bean.getData(), "path");
                                    }
                                    Logcat.i("-------" + id_photo);
                                    result.remove(result.size() - 1);
                                    uploadFile(result);
                                } else {
                                    ToastUtil.showToast(bean != null ? bean.getMsg() : "上传失败，请重试");
                                }
                            }
                        });
            }
        } else {

            toRequest(ApiConstants.EventTags.company_update);
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
                            logo = JsonUtil.parserField(bean.getData(), "path");
                            AppImageLoader.loadCircleImage(EnterpriseInfoActivity.this, BuildConfig.BASE_URL + "" + logo, mIvLogo);
                        } else {
                            ToastUtil.showToast(bean != null ? bean.getMsg() : "上传失败，请重试");
                        }
                    }
                });


    }

    private String getPictureSuffix(String path) {
        Logcat.i("----------" + path);
        if (path.contains(".")) {
            return "live_" + System.currentTimeMillis() + path.substring(path.lastIndexOf("."), path.length());
        }
        return "live_" + System.currentTimeMillis() + ".jpg";
    }

    private void onPickerIndustry() {
        List<String> list = new ArrayList<>();
        list.add("电力");
        list.add("水利");
        list.add("公路");
        list.add("IT");
        OptionsPickerView optionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mTvIndustry.setText(list.get(options1));
            }
        }).build();
        optionsPickerView.setNPicker(list, null, null);
        optionsPickerView.show();
    }

    private void showAddress() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                district = options1Items.get(options1).getRegion_name() + "\t\t"
                        + options2Items.get(options1).get(options2).getRegion_name() + "\t\t"
                        + options3Items.get(options1).get(options2).get(options3).getRegion_name();
                Logcat.i("选择的地址：" + district);
                mTvDistrict.setText(district);
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
