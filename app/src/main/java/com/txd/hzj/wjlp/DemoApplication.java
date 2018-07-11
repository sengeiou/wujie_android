/*
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.txd.hzj.wjlp;

import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.os.Vibrator;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.txdHxListener.ChatListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.txd.hzj.wjlp.baidu.LocationService;
import com.umeng.commonsdk.UMConfigure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/8 0008
 * 时间：下午 1:58
 * 描述：环信Demo的Application
 * ===============Txunda===============
 */
public class DemoApplication extends WeApplication implements EMMessageListener {

    public static boolean LOGIN_ACTIVITY_IS_RUN = false;

    public static Context applicationContext;
    private static DemoApplication instance;
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
     */
    public static String currentUserNick = "";

    /**
     * 百度地图服务
     */
    public LocationService locationService;
    public Vibrator mVibrator;

    // 用户信息
    private Map<String, String> locationInfo;

    private ChatListener chatListener;

 /*   public static RefWatcher getRefWatcher(Context context) {
        DemoApplication application = (DemoApplication) context.getApplicationContext();
        return application.refWatcher;
    }*/

    //    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();

        // 该处只在正式版需要打印Log日志的时候打开，调完之后及时关闭，其他地方非特殊情况不要添加
//        L.isDebug = false; // 正式版头部信息

        if (!L.isDebug) { // 如果是正式版则开启异常上报，意在防止在测试过程中上报的异常影响正常用户上报的真实数据
            // 腾讯Bugly初始化，第三个参数为SDK调试模式开关，建议在测试阶段建议设置成true，发布时设置为false。
            CrashReport.initCrashReport(getApplicationContext(), "c07fb2c1b8", false);
            initYouMeng(); // 初始化友盟
        }

        MultiDex.install(this);
        applicationContext = this;
        instance = this;
        DemoHelper.getInstance().init(applicationContext);
        // 初始化ShareSDK
        ShareSDK.initSDK(getApplicationContext(), "20e25f8941c82");
        // 极光推送初始化
        JPushInterface.init(this);

        /*
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
        initLocInfo();
        try {
            EMClient.getInstance().chatManager().addMessageListener(this);
        } catch (NullPointerException e) {
            L.e("=====Application=====" + e.toString());
        }
//        if (L.isDebug) {
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                // This process is dedicated to LeakCanary for heap analysis.
//                // You should not init your app in this process.
//                return;
//            }
//            refWatcher = LeakCanary.install(this);
//        }
    }

    /**
     * 初始化common库
     * 参数1:上下文，必须的参数，不能为空
     * 参数2:友盟 app key，非必须参数，如果Manifest文件中已配置app key，该参数可以传空，则使用Manifest中配置的app key，否则该参数必须传入
     * 参数3:友盟 channel，非必须参数，如果Manifest文件中已配置channel，该参数可以传空，则使用Manifest中配置的channel，否则该参数必须传入，channel命名请详见channel渠道命名规范
     * 参数4:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机
     * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
     */
    private void initYouMeng() {
        // 如果AndroidManifest.xml清单配置中没有设置appkey和channel，则可以在这里设置
        // UMConfigure.init(this, "58edcfeb310c93091c000be2", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "1fe6a20054bcef865eeb0991ee84525b");
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    /**
     * 初始化定位信息
     */
    private void initLocInfo() {
        locationInfo = new HashMap<>();
        // 获取存在本地的定位信息信息(key)
        String keys = PreferencesUtils.getString(this, "LocInfoKey");
        if (!TextUtils.isEmpty(keys)) {
            // 按照","分割keys
            String[] userInfos = keys.split(ListUtils.DEFAULT_JOIN_SEPARATOR);
            for (String key : userInfos) { // 获取用户信息，将其保存到Map中
                locationInfo.put(key, PreferencesUtils.getString(this, key));
            }
        }
    }

    public Map<String, String> getLocInfo() {
        return locationInfo;
    }

    public void setLocInfo(Map<String, String> userInfo) {
        this.locationInfo = userInfo;
        // 把用户信息存进Preferences
        // 将userInfo Map的所有key存到列表中
        List<String> keys = new ArrayList<>();
        // 迭代。。。获取所有key
        Iterator<String> iterator = this.locationInfo.keySet().iterator();
        // 迭代遍历Key
        while (iterator.hasNext()) {
            String key = iterator.next();
            keys.add(key);
            String value = this.locationInfo.get(key);
            // 将key和value保存到本地
            PreferencesUtils.putString(this, key, value);
        }
        // 将keys转成字符串(key1,key2...)
        PreferencesUtils.putString(this, "LocInfoKey", ListUtils.join(keys));
    }

    public static DemoApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 环信
     * //收到消息
     *
     * @param list
     */
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        if (chatListener != null)
            chatListener.onMessageReceived(list);
    }

    /**
     * 环信
     * 收到透传消息
     *
     * @param list
     */
    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
        if (chatListener != null)
            chatListener.onMessageReceived(list);
    }

    /**
     * 环信
     * //收到已读回执
     *
     * @param list
     */
    @Override
    public void onMessageRead(List<EMMessage> list) {
        if (chatListener != null)
            chatListener.onMessageRead(list);
    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {
        if (chatListener != null)
            chatListener.onMessageDelivered(list);
    }

    /**
     * //消息状态变动
     *
     * @param emMessage 环信
     * @param o
     */
    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {
        if (chatListener != null)
            chatListener.onMessageChanged(emMessage, o);
    }

    public void setChatListener(ChatListener chatListener) {
        this.chatListener = chatListener;
    }

    public void removeLisetener() {
        this.chatListener = null;
//        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    /**
     * 解决系统改变字体大小的时候导致的界面布局混乱的问题
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }
}
