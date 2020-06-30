package com.aopcloud.palmproject.ui.adapter.staff;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.aopcloud.base.log.Logcat;
import com.aopcloud.palmproject.BuildConfig;
import com.aopcloud.palmproject.R;
import com.aopcloud.palmproject.loader.AppImageLoader;
import com.aopcloud.palmproject.view.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.aopcloud.palmproject.ui.adapter.staff
 * @File : StaffTrendAdapter.java
 * @Date : 2020/5/18 10:16
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class StaffTrendAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    private String avatar;
    public StaffTrendAdapter(int layoutResId, @Nullable List<Object> data) {
        super(layoutResId, data);
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        String json = JSON.toJSONString(item);
        Logcat.e("---------"+json);
        TrendsBean trendsBean =JSON.parseObject(json,TrendsBean.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        date.setTime(trendsBean.getMake_time()*1000);
        helper.setText(R.id.tv_trend, ""+trendsBean.getContent())
                .setText(R.id.tv_time, ""+dateFormat.format(date));
        CircleImageView imageView = helper.getView(R.id.iv_img);
        AppImageLoader.loadCircleImage(mContext
                , BuildConfig.BASE_URL + avatar
                , imageView);


    }


    public static class TrendsBean {


        /**
         * id : 32
         * make_time : 1589618969
         * user_id : 7
         * company_code : CP4B4A4C
         * content : 加入团队测试编辑班组
         * extra :
         */

        private int id;
        private long make_time;
        private int user_id;
        private String company_code;
        private String content;
        private String extra;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getMake_time() {
            return make_time;
        }

        public void setMake_time(long make_time) {
            this.make_time = make_time;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getCompany_code() {
            return company_code;
        }

        public void setCompany_code(String company_code) {
            this.company_code = company_code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }
    }
}
