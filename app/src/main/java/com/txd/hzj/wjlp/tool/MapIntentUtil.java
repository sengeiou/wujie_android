package com.txd.hzj.wjlp.tool;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.ants.theantsgo.util.StringUtils;
import com.txd.hzj.wjlp.bean.CustomoLocation;
import com.txd.hzj.wjlp.melloffLine.ShopMallDetailsAty;

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
     * 打开地图并导航
     *
     * @param context  上下文
     * @param baiDuLat 百度经度
     * @param baiDuLng 百度纬度
     * @param gaoDeLat 高德维度
     * @param gaoDeLng 高德经度
     */
    public void openMap(final Context context, final double baiDuLat, final double baiDuLng, final double gaoDeLat, final double gaoDeLng) {

        List<String> mapList = new ArrayList<>();

        if (isInstallByread(context, "com.baidu.BaiduMap")) {
            mapList.add("百度地图");
        }
        if (isInstallByread(context, "com.autonavi.minimap")) {
            mapList.add("高德地图");
        }

        if (mapList.size() > 0) {
            final String[] items = new String[mapList.size()];
            for (int i = 0; i < mapList.size(); i++) {
                items[i] = mapList.get(i);
            }
            new AlertDialog.Builder(context)
                    .setTitle("选择地图")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if ("百度地图".equals(items[which])) {
                                // 跳转到百度地图APP进行导航
                                if (baiDuLat == 0 && baiDuLng == 0) {
                                    // 如果百度经纬度未设置，则提示
                                    Toast.makeText(context, "百度地图坐标回传失败，请重试或选择其他地图", Toast.LENGTH_SHORT).show();
                                } else {
                                    CustomoLocation customoLocation = new CustomoLocation();
                                    customoLocation.setLat(baiDuLat);
                                    customoLocation.setLng(baiDuLng);
                                    customoLocation.setName("");
                                    startNative_Baidu(context, null, customoLocation);
                                }
                            } else if ("高德地图".equals(items[which])) {
                                // 跳转至高德地图APP进行标注
                                if (gaoDeLat == 0 && gaoDeLng == 0) {
                                    // 如果高德经纬度未设置，则提示
                                    Toast.makeText(context, "高德地图坐标回传失败，请重试或选择其他地图", Toast.LENGTH_SHORT).show();
                                } else {
                                    CustomoLocation customoLocation = new CustomoLocation();
                                    customoLocation.setLat(gaoDeLat);
                                    customoLocation.setLng(gaoDeLng);
                                    customoLocation.setName("");
                                    startNative_Gaode(context, customoLocation);
                                }
                            }
                        }
                    })
                    .setPositiveButton("取消", null)
                    .create().show();
        } else {
            Toast.makeText(context, "请先安装地图导航APP", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 跳转到百度地图APP驾车线路规划
     *
     * @param context             上下文
     * @param currentLocation     起始位置
     * @param destinationLocation 目的地位置
     */
    private void startNative_Baidu(Context context, CustomoLocation currentLocation, CustomoLocation destinationLocation) {
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
     * 调起高德地图APP驾车线路规划
     *
     * @param context //     * @param loc
     */
    private void startNative_Gaode(Context context, CustomoLocation destinationLocation) {

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
    private boolean isInstallByread(Context context, String packageName) {
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

//    /**
//     * 百度坐标转换为高德坐标
//     *
//     * @param baiduLat 百度坐标纬度
//     * @param baiduLng 百度坐标经度
//     * @return 高德坐标Double[]
//     */
//    public static double[] baidu2gaode(double baiduLat, double baiduLng) {
//
//    }

//    /**
//     * 高德坐标转换为百度坐标
//     *
//     * @param gaodeLat 高德坐标纬度
//     * @param gaodeLng 高德坐标经度
//     * @return 百度坐标Double[]
//     */
//    public static double[] gaode2baidu(double gaodeLat, double gaodeLng) {
//
//    }

}
