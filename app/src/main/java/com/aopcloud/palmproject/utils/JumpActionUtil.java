package com.aopcloud.palmproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @PackageName : com.aopcloud.palmproject.utils
 * @File : JumpActionUtil.java
 * @Date : 2020/5/30 2020/5/30
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class JumpActionUtil {

    public static void jump2SendEmail(Context context, String address, String subject) {
        String[] addresses= new  String[]{address};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void jump2CallPhone(Activity activity, String phone) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));//跳转到拨号界面，同时传递电话号码
        activity.startActivity(dialIntent);
    }

    public static void jump2SendSms(Activity activity, String phone) {
        Intent send = new Intent();
        send.setAction("android.intent.action.SENDTO");
        send.addCategory("android.intent.category.DEFAULT");
        send.setData(Uri.parse("smsto:" + phone)); //发消息给5559
        send.putExtra("sms_body", ""); //要发送的消息内容
        activity.startActivity(send);
    }


}
