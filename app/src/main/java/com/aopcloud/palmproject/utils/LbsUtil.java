package com.aopcloud.palmproject.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.net.URISyntaxException;
import java.text.NumberFormat;

/**
 * @PackageName : com.aopcloud.palmproject.utils
 * @File : LbsUtil.java
 * @Date : 2020/5/31 2020/5/31
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class LbsUtil {
    public final static String CoorType_GCJ02 = "gcj02";
    public final static String CoorType_BD09LL = "bd09ll";
    public final static String CoorType_BD09MC = "bd09";


    /***
     *61 ： GPS定位结果，GPS定位成功。
     *62 ： 无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。
     *63 ： 网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。
     *65 ： 定位缓存的结果。
     *66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
     *67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。
     *68 ： 网络连接失败时，查找本地离线定位时对应的返回结果。
     *161： 网络定位结果，网络定位定位成功。
     *162： 请求串密文解析失败。
     *167： 服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。
     *502： key参数错误，请按照说明文档重新申请KEY。
     *505： key不存在或者非法，请按照说明文档重新申请KEY。
     *601： key服务被开发者自己禁用，请按照说明文档重新申请KEY。
     *602： key mcode不匹配，您的ak配置过程中安全码设置有问题，请确保：sha1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请KEY。
     *501～700：key验证失败，请按照说明文档重新申请KEY。
     */

    public static float[] EARTH_WEIGHT = {0.1f, 0.2f, 0.4f, 0.6f, 0.8f}; // 推算计算权重_地球
    //public static float[] MOON_WEIGHT = {0.0167f,0.033f,0.067f,0.1f,0.133f};
    //public static float[] MARS_WEIGHT = {0.034f,0.068f,0.152f,0.228f,0.304f};

    /**
     * 补充：计算两点之间真实距离
     *
     * @param longitude1 维度
     * @param latitude1
     * @param longitude2 经度
     * @param latitude2
     * @return 米
     */
    public static String getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        double lat1 = (Math.PI / 180) * latitude1;
        double lat2 = (Math.PI / 180) * latitude2;
        double lon1 = (Math.PI / 180) * longitude1;
        double lon2 = (Math.PI / 180) * longitude2;
        double R = 6371;// 地球半径
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
        NumberFormat nFormat = NumberFormat.getNumberInstance();  //数字格式化对象
        if (d < 1) {
            nFormat.setMaximumFractionDigits(1);    //已可以设置为0，这样跟百度地图APP中计算的一样
            d *= 1000;
            return nFormat.format(d) + "m";
        } else {
            nFormat.setMaximumFractionDigits(2);
            return nFormat.format(d) + "km";
        }
    }


    /**
     * 高德地图应用是否安装
     *
     * @return
     */
    public static boolean isAmapMapInstalled() {
        return isInstallPackage("com.autonavi.minimap");
    }

    /**
     * 百度地图应用是否安装
     *
     * @return
     */
    public static boolean isBaiduMapInstalled() {
        return isInstallPackage("com.baidu.BaiduMap");
    }

    /**
     * 腾讯地图应用是否安装
     *
     * @return
     */
    public static boolean isTencentInstalled() {
        return isInstallPackage("com.tencent.map");
    }

    /**
     * @return
     */
    private static boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }


    /**
     * 获取打开高德地图应用uri
     * style
     * 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5
     * 不走高速且避免收费；6 不走高速且躲避拥堵；
     * 7 躲避收费和拥堵；8 不走高速躲避收费和拥堵)
     */
    public static String getGdMapUri(String appName, String slat, String slon, String sname, String dlat, String dlon, String dname) {
        String newUri = "androidamap://navi?sourceApplication=%1$s&poiname=%2$s&lat=%3$s&lon=%4$s&dev=1&style=2";
        return String.format(newUri, appName, dname, dlat, dlon);
    }

    public static String getBaiduMapUri(String Lat, String Lng, String address) {
        String newUri = "intent://map/direction?" +
                "destination=latlng:" + Lat + "," + Lng + "|name:我的目的地" +        //终点
                "&mode=driving&" +          //导航路线方式
                "&src=appname#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
        return String.format(newUri, Lat, address);
    }

    public static void openAmap(Context context, String lat, String lng, String shopAddress) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri = Uri.parse("androidamap://navi?sourceApplication=掌项&poiname=fangheng&lat=" + lat + "&lon=" + lng + "&dev=1&style=2");
        intent.setData(uri);
        context.startActivity(intent);
    }


    public static void openBaiduMap(Context context, String lat, String lng, String shopAddress) {
        try {
            double[] d = GPSUtil.gaoDeToBaidu(Double.valueOf(lat), Double.valueOf(lng));
            lat = String.valueOf(d[0]);
            lng = String.valueOf(d[1]);
            Intent intent = Intent.getIntent("intent://map/direction?destination=latlng:"
                    + lat + ","
                    + lng + "|name:"
                    + shopAddress + "" +
                    "&mode=driving&" + "&src=appname#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            context.startActivity(intent); //启动调用
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void openQQMap(Context context, String lat, String lng, String shopAddress) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri = Uri.parse("qqmap://map/routeplan?type=drive&to=" + shopAddress + "&tocoord=" + lat + "," + lng);
        intent.setData(uri);
        context.startActivity(intent);
    }


    public static void openBaiduWebMap(Context context, String lat, String lng, String city) {
        double[] d = GPSUtil.gaoDeToBaidu(Double.valueOf(lat), Double.valueOf(lng));
        lat = String.valueOf(d[0]);
        lng = String.valueOf(d[1]);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        String lat_lng = lat + "," + lng;
        String url = "http://api.map.baidu.com/marker?location=" + lat_lng + "&title=" + "&content=" + city + "&output=html&src=yourComponyName|掌项";
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        intent.setAction("android.intent.action.VIEW");
        context.startActivity(intent);
    }


}

