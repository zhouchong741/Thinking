package com.zc741.thinking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by chong on 2015/12/14.
 */
public class ListenNetWorkBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = connectivity.getActiveNetworkInfo();

        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                if (mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    System.out.println("mNetworkInfo.getType()==ConnectivityManager.TYPE_WIFI");
                    
                } else if (mNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    System.out.println("mNetworkInfo.getType()==ConnectivityManager.TYPE_MOBILE");
                    System.out.println("更新服务器");
                } else {
                    System.out.println("断开的网络");
                }
            }
        }
    }
}

