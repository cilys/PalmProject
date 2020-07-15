package com.aopcloud.palmproject.ui.activity.staff;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.activity.staff.bean.ContactBean;
import com.aopcloud.palmproject.ui.adapter.staff.SelectMobileListAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.staff
 * @File : SelectPhoneListActivity.java
 * @Date : 2020/5/10 2020/5/10
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_rv)
public class SelectPhoneListActivity extends BaseAc implements SelectMobileListAdapter.OnItemClickListener {
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
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private SelectMobileListAdapter mAdapter;
    private List<ContactBean> mBeanList = new ArrayList();


    @Override
    protected void initView() {
        mTvHeaderTitle.setText("通讯录选择");
        mTvHeaderRight.setText("确定");
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(false);

        mAdapter = new SelectMobileListAdapter(R.layout.item_phone_select, mBeanList);
        mAdapter.setOnItemClickListener(this);
        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(mAdapter);
        getAllContacts();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        for (int i = 0; i < mBeanList.size(); i++) {
            if (i == position) {
                mBeanList.get(i).setSelect(!mBeanList.get(i).isSelect());
            } else {
                mBeanList.get(i).setSelect(false);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.ll_header_right)
    public void onViewClicked() {

        ContactBean contactBean = null;
        for (int i = 0; i < mBeanList.size(); i++) {
            if (mBeanList.get(i).isSelect()) {
                contactBean = mBeanList.get(i);
            }
        }
        if (contactBean == null) {
            ToastUtil.showToast("请选择联系人");
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("phone", contactBean.getPhone());
        bundle.putString("name", contactBean.getName());
        intent.putExtras(bundle);
        setResult(0, intent);
        finish();
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

        mBeanList.clear();
        mBeanList.addAll(contacts);
        mAdapter.notifyDataSetChanged();
    }
}
