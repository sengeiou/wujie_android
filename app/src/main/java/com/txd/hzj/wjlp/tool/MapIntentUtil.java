package com.txd.hzj.wjlp.tool;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.ants.theantsgo.util.StringUtils;
import com.txd.hzj.wjlp.bean.CustomoLocation;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * 创建时间：2018/08/02 002 下午 14:20
 * 功能描述：跳转到地图导航APP工具类
 */
public class MapIntentUtil {

    /**
     * 跳转到百度地图APP驾车导航
     *
     * @param context             上下文
     * @param currentLocation     起始位置
     * @param destinationLocation 目的地位置
     */
    public static void startNative_Baidu(Context context, CustomoLocation currentLocation, CustomoLocation destinationLocation) {
        if (null == destinationLocation) {
            return;
        }
        // 百度地图包名 com.baidu.BaiduMap
        Intent intent = null;
        String uri = ""; // 跳转uri http://lbsyun.baidu.com/index.php?title=uri/api/android
        try {
            if (null == currentLocation || (StringUtils.isEmpty(currentLocation.getName()) || (0.0 == currentLocation.getLng() && 0.0 == currentLocation.getLat()))) {
                uri = "intent://map/direction?" +
                        "origin=name:我的位置|latlng:0,0" + // 从我的位置出发
                        "&destination=name:" + destinationLocation.getName() + "|latlng:" + destinationLocation.getLat() + "," + destinationLocation.getLng() +
                        "&mode=driving#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
            } else {
                uri = "intent://map/direction?" +
                        "origin=name:" + currentLocation.getName() + "|latlng:" + currentLocation.getLat() + "," + currentLocation.getLng() +
                        "&destination=name:" + destinationLocation.getName() + "|latlng:" + destinationLocation.getLat() + "," + destinationLocation.getLng() +
                        "&mode=driving#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
            }
            intent = Intent.getIntent(uri);
            if (isInstallByread(context, "com.baidu.BaiduMap")) {
                context.startActivity(intent); //启动调用
            } else {
                Toast.makeText(context, "没有安装百度地图客户端", Toast.LENGTH_SHORT).show();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Toast.makeText(context, "地址解析错误", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 调起高德地图
     *
     * @param context //     * @param loc
     */
    public static void startNative_Gaode(Context context, CustomoLocation destinationLocation) {

        if (null == destinationLocation) {
            return;
        }
        // 高德地图包名 com.autonavi.minimap
        Intent intent = null;
        String uri = "";
        try {
            uri = "androidamap://navi?sourceApplication=" + destinationLocation.getName() + "&poiname=" + destinationLocation.getName()
                    + "&lat=" +
                    destinationLocation.getLat() +
                    "&lon=" +
                    destinationLocation.getLng() +
                    "&dev=0";
            intent = Intent.getIntent(uri);
            if (isInstallByread(context, "com.autonavi.minimap")) {
                context.startActivity(intent); // 启动调用
            } else {
                Toast.makeText(context, "没有安装高德地图客户端", Toast.LENGTH_SHORT).show();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Toast.makeText(context, "地址解析错误", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 判断是否安装目标应用
     *
     * @param context     上下文
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    public static boolean isInstallByread(Context context, String packageName) {
//        return new File("/data/data/" + packageName).exists();

        PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);
        //存储所有已安装程序的包名
        List<String> pName = new ArrayList<>();
        //从info中将报名字逐一取出
        if (pInfo != null) {
            for (int i = 0; i < pInfo.size(); i++) {
                String pn = pInfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);

    }

//    public static double[] baidu2gaode(double baiduLat, double baiduLng) {
//
//    }
//
//    public static double[] gaode2baidu(double gaodeLat, double gaodeLng) {
//
//    }

}
