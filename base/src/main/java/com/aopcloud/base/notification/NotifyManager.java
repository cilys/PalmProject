package com.aopcloud.base.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * @PackageName : com.aopcloud.base.notification
 * @File : NotifyManager.java
 * @Date : 2020/1/2 11:23
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class NotifyManager {
    public static Context mContext;

    public static NotificationManager getNm() {
        return nm;
    }

    private static NotificationManager nm;

    public static void init(Context context) {
        mContext = context;
        nm = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
    }


    public NotifyManager(Builder builder) {


    }


    public void show() {

    }


    public void cancel(int id) {
        if (nm != null) {
            nm.cancel(id);
        }
    }

    public static void cancelAll() {
        if (nm != null) {
            nm.cancelAll();
        }
    }


    public static class Builder {


        private int id;
        private int icon;
        private CharSequence title;
        private CharSequence describe;
        private NoTYpe notifyType;
        private NotificationCompat.Style style;
        private PendingIntent mPendingIntent;

        private CharSequence bigText;
        private int bigPic;
        public CharSequence ticker = "您有新的消息";


        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setIcon(int icon) {
            this.icon = icon;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setDescribe(CharSequence describe) {
            this.describe = describe;
            return this;
        }

        public Builder setNotifyType(NoTYpe notifyType) {
            this.notifyType = notifyType;
            return this;
        }

        public Builder setStyle(NotificationCompat.Style style) {
            this.style = style;
            return this;
        }

        public Builder setPendingIntent(PendingIntent pendingIntent) {
            mPendingIntent = pendingIntent;
            return this;
        }

        public Builder setBigText(CharSequence bigText) {
            this.bigText = bigText;
            return this;
        }

        public Builder setBigPic(int bigPic) {
            this.bigPic = bigPic;
            return this;
        }

        public Builder setTicker(CharSequence ticker) {
            this.ticker = ticker;
            return this;
        }

        public NotifyManager build() {
            return new NotifyManager(this);
        }
    }


}