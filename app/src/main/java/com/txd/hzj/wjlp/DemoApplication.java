/**
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

import android.content.Context;
import android.support.multidex.MultiDex;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.util.L;

import cn.sharesdk.framework.ShareSDK;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/8 0008
 * 时间：下午 1:58
 * 描述：环信Demo的Application
 * ===============Txunda===============
 */
public class DemoApplication extends WeApplication {

    public static Context applicationContext;
    private static DemoApplication instance;
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
     */
    public static String currentUserNick = "";

    @Override
    public void onCreate() {
        L.isDebug= BuildConfig.DEBUG;
        MultiDex.install(this);
        super.onCreate();
        applicationContext = this;
        instance = this;
        DemoHelper.getInstance().init(applicationContext);
        //初始化ShareSDK
        ShareSDK.initSDK(getApplicationContext(),"20e25f8941c82");
    }

    public static DemoApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
