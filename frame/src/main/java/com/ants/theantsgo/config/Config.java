package com.ants.theantsgo.config;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.util.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class Config {

    /**
     * 登录状态
     */
    public static final String PREF_KEY_LOGIN_STATE = "PREF_KEY_LOGIN_STATE";

    private static final String PREF_KEY_CHECK_STATE = "PREF_KEY_CHECK_STATE";

    private static View[] aty = new View[1];

//    public final static String OFFICIAL_WEB = "http://api.wujiemall.com/"; // 正式服务器
//    public final static String OFFICIAL_WEB = "http://fztest.wujiemall.com/"; // 测试库 打包发送
//    public final static String OFFICIAL_WEB = "http://dev.wujiemall.com/"; // 测试库 打包发送

    public final static String OFFICIAL_WEB = "http://test2.wujiemall.com/";// 线上技术内部测试，不需要打包
//    public final static String OFFICIAL_WEB = "http://test3.wujiemall.com/"; // 线下技术内部测试

    public final static  String SHARE_URL=OFFICIAL_WEB.contains("api")?"http://www.wujiemall.com/":OFFICIAL_WEB;

    public final static String BASE_URL = OFFICIAL_WEB + "index.php/Api/";

    //分销的base_url
    public static final String DISTRIBUTION_URL = OFFICIAL_WEB + "Api/Distribution/";

    //赠品专区的base_url
    public static final String GIVEAWAYAREA_URL=OFFICIAL_WEB+"Api/GiftGoods/";

    public static List<Activity> list = new ArrayList<>();

    /**
     * 判断是否登录
     *
     * @return boolean
     */
    public static boolean isLogin() {
        // AppManager.getInstance().getTopActivity()获取栈顶的Activity对象
        return PreferencesUtils.getBoolean(AppManager.getInstance().getTopActivity(), PREF_KEY_LOGIN_STATE);
    }

    public static boolean isCheck() {
        // AppManager.getInstance().getTopActivity()获取栈顶的Activity对象
        return PreferencesUtils.getBoolean(AppManager.getInstance().getTopActivity(), PREF_KEY_CHECK_STATE);
    }

    public static void AddAty(View view) {
        aty[0] = view;
    }

    /**
     * 设置登录状态
     *
     * @param isLogin true登录状态，flase未登录状态
     */
    public static void setLoginState(boolean isLogin) {
        PreferencesUtils.putBoolean(AppManager.getInstance().getTopActivity(), PREF_KEY_LOGIN_STATE, isLogin);
    }

    public static void setCheckState(boolean isCheck) {
        PreferencesUtils.putBoolean(AppManager.getInstance().getTopActivity(), PREF_KEY_CHECK_STATE, isCheck);
    }

    /**
     * 添加Activity
     *
     * @param activity Activity
     */
    public static void AddAty(Activity activity) {
        list.add(activity);
    }

    /**
     * 销毁Activity
     */
    public static void FinishAty() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).finish();
        }
    }

    /**
     * 屏幕密度(0.75 / 1.0 / 1.5)
     *
     * @param activity Activity
     * @return float
     */
    public static float screenDensity(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.density;
    }

    /**
     * 屏幕密度DPI(120 / 160 / 240)
     *
     * @param activity Activity
     * @return int
     */
    public static int getScreenDensityDpi(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.densityDpi;
    }

    /**
     * 屏幕的宽
     *
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 屏幕的高
     *
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    public static String getToken() {
        return PreferencesUtils.getString(AppManager.getInstance().getTopActivity(), "token", "");
    }

}
