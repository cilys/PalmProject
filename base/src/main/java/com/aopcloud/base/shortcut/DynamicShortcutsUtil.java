package com.aopcloud.base.shortcut;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.DrawableRes;

import com.aopcloud.base.common.AppException;
import com.aopcloud.base.common.AppHelper;
import com.aopcloud.base.log.Logcat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @PackageName : com.aopcloud.base.shortcut
 * @File : DynamicShortcutsUtil.java
 * @Date : 2020/1/17 10:03
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class DynamicShortcutsUtil {

    private Context mContext;
    private ShortcutManager mShortcutManager;
    private List<ShortcutInfo> mShortcutInfoList;
    private static DynamicShortcutsUtil mShortcutManagerUtil;


    public static DynamicShortcutsUtil getInstance() {
        if (mShortcutManagerUtil == null) {
            mShortcutManagerUtil = new DynamicShortcutsUtil();
        }
        return mShortcutManagerUtil;
    }

    private DynamicShortcutsUtil() {
        init();
    }

    private void init() {
        Logcat.i("init DynamicShortcuts");
        mContext = AppHelper.getInstance().getContext();
        if (mContext == null) {
            throw new AppException("context is null,please init AppHelper . ");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            mShortcutManager = mContext.getSystemService(ShortcutManager.class);
            mShortcutInfoList = new ArrayList();
        }

    }


    public void add(ShortcutsBean bean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            Intent intent = new Intent(mContext, bean.getClazz());
            intent.setAction(Intent.ACTION_VIEW);
            intent.putExtra("msg", "" + bean.getTitle());
            ShortcutInfo info = new ShortcutInfo.Builder(mContext, "" + bean.getTitle())
                    .setShortLabel(bean.getTitle())
                    .setLongLabel(bean.getTitle())
                    .setIcon(Icon.createWithResource(mContext, bean.icon))
                    .setIntent(intent)
                    .build();
            List<ShortcutInfo> infoList = new ArrayList<>();
            infoList.add(info);
            mShortcutManager.setDynamicShortcuts(infoList);
        }
    }


    /**
     * @param list
     */
    public void addAll(List<ShortcutsBean> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            List<ShortcutInfo> infoList = new ArrayList<>(list.size());
            for (int i = 0; i < list.size(); i++) {
                Intent intent = new Intent(mContext, list.get(i).clazz);
                intent.setAction(Intent.ACTION_VIEW);
                intent.putExtra("msg", "" + list.get(i).extra);
                ShortcutInfo info = new ShortcutInfo.Builder(mContext, ""+list.get(i).getTitle())
                        .setShortLabel(list.get(i).title)
                        .setLongLabel(list.get(i).title)
                        .setIcon(Icon.createWithResource(mContext, list.get(i).icon))
                        .setIntent(intent)
                        .build();
                infoList.add(info);
            }
            mShortcutInfoList.addAll(infoList);
            mShortcutManager.addDynamicShortcuts(mShortcutInfoList);
        }
    }


    /**
     * @param title
     */
    private void removeItem(String title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            List<ShortcutInfo> shortcuts = mShortcutManager.getPinnedShortcuts();
            for (ShortcutInfo info : shortcuts) {
                if (info.getShortLabel().equals("" + title)) {
                    mShortcutManager.disableShortcuts(Arrays.asList(info.getId()), "");
                }
            }
            mShortcutManager.removeDynamicShortcuts(Arrays.asList("" + title));
        }
    }


    /**
     *
     */
    private void removeAll() {
        if (mShortcutManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            mShortcutManager.removeAllDynamicShortcuts();
        }

    }


    /**
     * @return List<ShortcutInfo>
     */
    private List<ShortcutInfo> getShortcuts() {
        if (mShortcutManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            mShortcutManager.getDynamicShortcuts();
        }

        return null;
    }


    /**
     * @return
     */
    private int getMaxShortcutCountPerActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            return mShortcutManager == null ? 0 : mShortcutManager.getMaxShortcutCountPerActivity();
        }
        return 0;
    }


    public static class ShortcutsBean {

        private @DrawableRes
        int icon;
        private String title;
        private Class clazz;
        private String extra;

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }
    }

}
