package com.gotoapps.walkin.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.gotoapps.walkin.AppController;

/**
 * Created by C2CSLS on 6/27/2018.
 */

public class NetworkConnectionStatusNotificationUtil {

    private static Context mContext= AppController.getAppContext();

    public static boolean checkConnectionStatus(){
        return isNetworkConnected() || isWifiConnected();
    }

    private static boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)mContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE); // 1
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); // 2
        return networkInfo != null && networkInfo.isConnected(); // 3
    }

    private static boolean isWifiConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)mContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) && networkInfo.isConnected();
    }


}
