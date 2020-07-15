package com.aopcloud.palmproject.ui.activity.camera;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.annotation.Layout;
import com.aopcloud.base.util.ResourceUtil;
import com.aopcloud.base.util.ToastUtil;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.bean.ImageItem;
import com.aopcloud.palmproject.ui.activity.BaseAc;
import com.aopcloud.palmproject.ui.adapter.file.TodayCacheAdapter;
import com.aopcloud.palmproject.utils.RegUtil;
import com.aopcloud.palmproject.utils.ThreadUtil;
import com.aopcloud.palmproject.view.decoration.DividerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @PackageName : com.aopcloud.palmproject.ui.activity.camera
 * @File : SelectSceneActivity.java
 * @Date : 2020/6/7 2020/6/7
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
@Layout(R.layout.activity_rv_bottom)
public class SelectSceneActivity extends BaseAc implements TodayCacheAdapter.OnItemClickListener {
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
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;


    private TodayCacheAdapter mAdapter;
    private List<ImageItem> mImageItems = new ArrayList<>();

    @Override
    protected void initData() {
        super.initData();

    }

    /**
     * @param s 要判断是否在当天24h内的时间
     * @return boolean
     * @Description 是否为当天24h内
     * @author 刘鹏博
     */
    public static boolean isToday(long s) {

        Date addTime = new Date();
        addTime.setTime(s);
        boolean flag = false;
        //获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        //定义每天的24h时间范围
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date parseBeginTime = null;
        Date parseEndTime = null;
        try {
            parseBeginTime = dateFormat.parse(beginTime);
            parseEndTime = dateFormat.parse(endTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (addTime.after(parseBeginTime) && addTime.before(parseEndTime)) {
            flag = true;
        }
        return flag;
    }


    @Override
    protected void initView() {
        mTvHeaderTitle.setText("今日缓存");

        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(false);

        mAdapter = new TodayCacheAdapter(R.layout.item_select_scenes_file, mImageItems);
        mAdapter.setOnItemClickListener(this::onItemClick);
        DividerItemDecoration itemDecoration = new DividerItemDecoration.Builder()
                .color(ResourceUtil.getColor(R.color.theme_background_f5))
                .height(20)
                .width(20)
                .build();
        mRvList.addItemDecoration(itemDecoration);
        mRvList.setLayoutManager(new GridLayoutManager(this, 3));
        mRvList.setAdapter(mAdapter);



        showPopXupLoading("加载中");
        List<String> list = new ArrayList<>();
        list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
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

    private void getAllContacts() {
        String path = Environment.getExternalStorageDirectory() + "/PalmProject";
        mImageItems.clear();
        ImageItem palm_default_add = new ImageItem();
        palm_default_add.name = "palm_default_add";
        mImageItems.add(palm_default_add);

        String[] selectionArgs = {path + "%"};
        String FileColumns = MediaStore.Files.FileColumns.DATA + " like ?";
        Cursor cursor = this.getContentResolver().query(MediaStore.Files.getContentUri("external"),
                null, FileColumns, selectionArgs, null);
        while (cursor.moveToNext()) {

            String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));

            long addTime = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED));

            String DISPLAY_NAME = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME));
            String fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE));

            File file = new File(filePath);
            if (!file.exists() || file.length() <= 0) {
                continue;
            }


            if (RegUtil.isNumeric(fileName)) {
                long s = Long.valueOf(fileName);
                if (isToday(s)) {
                    ImageItem imageItem = new ImageItem();
                    imageItem.name = "" + DISPLAY_NAME;
                    imageItem.path = filePath;
                    imageItem.addTime = addTime;
                    mImageItems.add(imageItem);
                }
            } else {

            }
        }
        cursor.close();
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissPopupLoading();
                mAdapter.notifyDataSetChanged();

            }
        });
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

                List<ImageItem> list = new ArrayList();
                for (int i = 0; i < mImageItems.size(); i++) {
                    if (mImageItems.get(i).select && !mImageItems.get(i).name.equals("palm_default_add")) {
                        list.add(mImageItems.get(i));
                    }
                }
                if (list.size() == 0) {
                    ToastUtil.showToast("请选择文件");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) list);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(101, intent);
                finish();
                break;
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (position == 0) {
            gotoActivity(PictureOrVideoActivity.class, 0);
            finish();
        } else {
            mImageItems.get(position).select = !mImageItems.get(position).select;
            mAdapter.notifyDataSetChanged();
        }
    }
}
