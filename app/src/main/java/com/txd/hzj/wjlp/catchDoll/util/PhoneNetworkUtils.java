package com.txd.hzj.wjlp.catchDoll.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.txd.hzj.wjlp.Constant;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：手机网络判断
 */
public class PhoneNetworkUtils {

    /**
     * 获取当前手机网络类型
     *
     * @return
     */
    public static int getNetWorkType(Context context) {
        int networkType = Constant.NETWORK_CLASS_UNKNOW;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            switch (type) {
                case ConnectivityManager.TYPE_WIFI:
                    networkType = Constant.NETWORK_CLASS_WIFI;
                    break;
                case ConnectivityManager.TYPE_MOBILE: // 移动网络
                    networkType = getMobileType(context);
                    break;
            }
        }
        return networkType;
    }

    private static int getMobileType(Context context) {
        int mobileNetworkType = Constant.NETWORK_CLASS_UNKNOW;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = telephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                mobileNetworkType = Constant.NETWORK_CLASS_2_G;
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                mobileNetworkType = Constant.NETWORK_CLASS_3_G;
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                mobileNetworkType = Constant.NETWORK_CLASS_4_G;
                break;
        }
        return mobileNetworkType;
    }

}
