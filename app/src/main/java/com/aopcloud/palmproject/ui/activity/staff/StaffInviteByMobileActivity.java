package com.aopcloud.palmproject.ui.activity.staff;

import android.Manifest;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.api.ApiConstants;
import com.aopcloud.palmproject.common.ResultBean;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.staff.bean.ContactBean;
import com.aopcloud.palmproject.ui.adapter.staff.SelectMobileListAdapter;
import com.aopcloud.palmproject.utils.LoginUserUtil;
import com.aopcloud.palmproject.utils.ThreadUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.staff
 * @File : StaffApprovalActivity.java
 * @Date : 2020/4/23 9:18
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_staff_invite_mobile)
public class StaffInviteByMobileActivity extends BaseAc implements SelectMobileListAdapter.OnItemClickListener {
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
    @BindView(R.id.et_mobile)
    EditText mEtMobile;
    @BindView(R.id.ll_mobile)
    LinearLayout mLlMobile;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    private String mobile;

    private SelectMobileListAdapter mAdapter;
    private List<ContactBean> mBeanList = new ArrayList();

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        mTvHeaderTitle.setText("邀请新成员");

        mAdapter = new SelectMobileListAdapter(R.layout.item_phone_select, mBeanList);
        mAdapter.setOnItemClickListener(this);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.layout_common_empty,mRvList);
        mAdapter.isUseEmpty(true);

        showPopXupLoading("加载中");
        List<String> list = new ArrayList<>();
        list.add(Manifest.permission.READ_CONTACTS);
        XXPermissions.with(this).permission(list).request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                if (isAll) {
                    ThreadUtil.runOnChildThread(new Runnable() {
                        @Override
                        public void run() {
                            getAllContacts();
                        }
                    });
                } else {
                    ToastUtil.showToast("请先开启权限");
                }
            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {

            }
        });
    }

    public void getAllContacts() {
        ArrayList<ContactBean> contacts = new ArrayList<ContactBean>();

        Cursor cursor = this.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            //新建一个联系人实例
            ContactBean temp = new ContactBean();
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            temp.setName(name);
            ;

            //获取联系人电话号码
            Cursor phoneCursor = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            while (phoneCursor.moveToNext()) {
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phone = phone.replace("-", "");
                phone = phone.replace(" ", "");
                temp.setPhone(phone);
                ;
            }

            //获取联系人备注信息
            Cursor noteCursor = this.getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    new String[]{ContactsContract.Data._ID, ContactsContract.CommonDataKinds.Nickname.NAME},
                    ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE + "'",
                    new String[]{contactId}, null);
            if (noteCursor.moveToFirst()) {
                do {
                    String note = noteCursor.getString(noteCursor
                            .getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
                    temp.setNote(note);
                    Log.i(TAG, note);
                } while (noteCursor.moveToNext());
            }
            contacts.add(temp);
            //记得要把cursor给close掉
            phoneCursor.close();
            noteCursor.close();
        }
        cursor.close();


        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissPopupLoading();
                mBeanList.clear();
                mBeanList.addAll(contacts);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mEtMobile.setText(mBeanList.get(position).getPhone());
    }

    @OnClick({R.id.ll_header_back, R.id.ll_header_right, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_header_back:
                finish();
                break;
            case R.id.ll_header_right:
                break;
            case R.id.tv_submit:
                mobile = mEtMobile.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.showToast("请输入或者选择手机号");
                    return;
                }
                toRequest(ApiConstants.EventTags.company_invite);
                break;
        }
    }

    @Override
    public void toRequest(int eventTag) {
        super.toRequest(eventTag);
        Map map = new HashMap();
        map.put("token", "" + LoginUserUtil.getToken(this));
        map.put("code", "" + LoginUserUtil.getCurrentEnterpriseNo(this));
        if (eventTag == ApiConstants.EventTags.company_invite) {
            map.put("tel", "" + mobile);
            iCommonRequestPresenter.requestPost(eventTag, this, ApiConstants.company_invite, map);
        }
    }

    @Override
    public void getRequestData(int eventTag, String result) {
        super.getRequestData(eventTag, result);
        dismissPopupLoading();
        ResultBean bean = JSON.parseObject(result, ResultBean.class);
        if (bean != null && bean.getCode() == 0) {
            if (eventTag == ApiConstants.EventTags.company_invite) {
                ToastUtil.showToast(bean != null ? bean.getMsg() : "提交成功");
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
}