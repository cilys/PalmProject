package com.aopcloud.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * @PackageName : com.aopcloud.base.receiver
 * @File : NetworkConnectChangedReceiver.java
 * @Date : 2019/12/26 14:47
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @VersionCode : V 1.0
 * @Describe ：
 */
public class NetworkConnectChangedReceiver  extends BroadcastReceiver {

    private static final String TAG = "NetworkConnectChanged";

    private Context context;

    public NetworkConnectChangedReceiver(Context context) {
        this.context = context;
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
            initMobileNet(context);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initMobileNet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest request = new NetworkRequest.Builder().build();
        connectivityManager.requestNetwork(request, networkCallback);
    }


    ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            super.onAvailable(network);
            Log.e(TAG, "onAvailable()");
            onNetStateChange(context);

        }

        @Override
        public void onLost(Network network) {
            super.onLost(network);
            onNetStateChange(context);
            Log.e(TAG, "onLost()");
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "网络状态发生变化");
        onNetStateChange(context);
    }

    private void onNetStateChange(Context context) {
//        boolean isConnect = SystemUtils.isNetworkConnected(context);
//        Log.e(TAG, "isConnect = " + isConnect);

//        int message = isConnect ? BaseEvent.MESSAGE_TYPE_NET_AVAILABLE : BaseEvent.MESSAGE_TYPE_NET_LOST;
//        EventBus.getDefault().post(new BaseEvent(message, null, null));
    }


}

