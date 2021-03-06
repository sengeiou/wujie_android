/************************************************************
 *  * Hyphenate CONFIDENTIAL
 * __________________
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of Hyphenate Inc..
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Hyphenate Inc.
 */
package com.txd.hzj.wjlp.huanxin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.ants.theantsgo.tips.ToastTip;
import com.ants.theantsgo.util.L;
import com.hyphenate.chat.EMChatService;
import com.hyphenate.util.EMLog;
import com.txd.hzj.wjlp.DemoApplication;

/**
 * @deprecated instead of use {@link EMReceiver}
 */
public class StartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)
                && !intent.getAction().equals("android.intent.action.QUICKBOOT_POWERON")) {
            return;
        }
        EMLog.d("boot", "start IM service on boot");
        try {
            Intent startServiceIntent = new Intent(context, EMChatService.class);
            startServiceIntent.putExtra("reason", "boot");
//            context.startService(startServiceIntent); // 此方式在Android_8.0+会报IllegalStateException异常
            // 参考解决地址：https://blog.csdn.net/u010784887/article/details/79675147
            // 遗留问题：未service里再调用startForeground方法
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(startServiceIntent);
            } else {
                context.startService(startServiceIntent);
            }
        } catch (IllegalStateException e) {
            L.e("huanxin.receiver.StartServiceReceiver:" + e.toString());
            Toast.makeText(DemoApplication.applicationContext, "环信服务启动异常", Toast.LENGTH_SHORT);
        }
    }
}
