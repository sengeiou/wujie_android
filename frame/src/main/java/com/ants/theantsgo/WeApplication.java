package com.ants.theantsgo;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.FileManager;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.util.PreferencesUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/6/30 0030
 * 时间：下午 2:30
 * 描述：Application
 */
public class WeApplication extends Application {

    private final String PREF_USERINFO = "user_info";
    private final String PREF_CITY_PROVINCE = "city_province";
    // APP管理类
    private AppManager appManager;
    // 用户信息
    private Map<String, String> userInfo;

    private static WeApplication application = null;

    private Handler handler = new Handler();
    private ExecutorService cachedThreadPool;

    public static WeApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            }
        });

        appManager = AppManager.getInstance();
        start();
        initUserInfo();
    }

    /**
     * 设置全局变量
     */
    private void start() {
        // 获得屏幕宽度（像素）
        Settings.displayWidth = ToolKit.getScreenWidth(this);
        // 获得屏幕高度（像素）
        Settings.displayHeight = ToolKit.getScreenHeight(this);
        // 文件路径设置
        Settings.cacheCompressPath = FileManager.getCompressFilePath();
        // 创建路径
        new File(Settings.cacheCompressPath).mkdirs();
    }

    /**
     * 退出系统时把所有的Activity清掉
     */
    private void finish() {
        // 关掉所有的运行中的Activity
        appManager.AppExit(this);
        // 调用系统的垃圾处理机制
        System.gc();
    }

    /**
     * 初始化用户信息
     */
    private void initUserInfo() {
        userInfo = new HashMap<>();
        // 获取存在本地的用户信息(key)
        String keys = PreferencesUtils.getString(this, PREF_USERINFO);
        if (!TextUtils.isEmpty(keys)) {
            // 按照","分割keys
            String[] userInfos = keys.split(ListUtils.DEFAULT_JOIN_SEPARATOR);
            for (String key : userInfos) {// 获取用户信息，将其保存到Map中
                userInfo.put(key, PreferencesUtils.getString(this, key));
            }
        }
    }

    /**
     * 获取用户信息
     *
     * @return Map
     */
    public Map<String, String> getUserInfo() {
        return userInfo;
    }

    /**
     * 设置用户信息
     */
    public void setUserInfo(Map<String, String> userInfo) {
        this.userInfo = userInfo;
        // 把用户信息存进Preferences
        // 将userInfo Map的所有key存到列表中
        List<String> keys = new ArrayList<>();
        // 迭代。。。获取所有key
        Iterator<String> iterator = this.userInfo.keySet().iterator();
        // 迭代遍历Key
        while (iterator.hasNext()) {
            String key = iterator.next();
            keys.add(key);
            String value = this.userInfo.get(key);
            // 将key和value保存到本地
            PreferencesUtils.putString(this, key, value);
        }
        // 将keys转成字符串(key1,key2...)
        PreferencesUtils.putString(this, PREF_USERINFO, ListUtils.join(keys));
    }

    /**
     * 获取Application对象
     *
     * @return
     */
    public static Application getApplication() {
        return application;
    }

    public void setCityProvience(String stringJson) {
        PreferencesUtils.putString(this, PREF_CITY_PROVINCE, stringJson);
    }

    public String getCityProvienceJson() {
        return PreferencesUtils.getString(this, PREF_CITY_PROVINCE);
    }

    public Handler getHandler() {
        return handler;
    }

    public ExecutorService getCachedThreadPool() {
        return cachedThreadPool;
    }

}
