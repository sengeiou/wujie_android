package com.txd.hzj.wjlp.jpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.ants.theantsgo.util.L;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.mellonLine.MessageAty;
import com.txd.hzj.wjlp.tool.BaiDuTtsSoundUtil;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";
    private static final int NOTIFICATION_SHOW_SHOW_AT_MOST = 3; // 通知显示最多条数

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            L.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                L.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                // send the Registration Id to your server...
                DemoApplication.registrationID = regId;

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                L.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);
                soundMessage(context, bundle); // 播放声音
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                L.e(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                L.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                soundMessage(context, bundle); // 播放声音
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                L.e(TAG, "[MyReceiver] 用户点击打开了通知");

                //打开自定义的Activity
//                Intent i = new Intent(context, MainAty.class);
                Intent i = onTouchOpenPage(context, bundle.getString(JPushInterface.EXTRA_EXTRA));
                if (i != null) {
                    i.putExtras(bundle);
                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                L.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                L.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                L.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    /**
     * 实现自定义推送声音
     * 如果推送内容为空，则不显示极光推送的默认Notification
     * 如果消息内容为空，并且播放语音的话就会显示两条Notification
     *
     * @param context
     * @param bundle
     */
    private void soundMessage(Context context, Bundle bundle) {

        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        if (!"".equals(extras)) {
            try {
                JSONObject extraJson = new JSONObject(extras);
                if ((extraJson.has("sound") ? extraJson.getString("sound") : "0").equals("0")) {
                    return;
                }
                NotificationCompat.Builder notification = new NotificationCompat.Builder(context); // 创建一个消息通知
                Intent intent = onTouchOpenPage(context, extras);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                // 设置Notification
                notification.setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setContentTitle(extraJson.has("title") ? extraJson.getString("title") : "无界优品")
                        .setContentText(extraJson.has("content") ? extraJson.getString("content") : "您有一条新消息")
                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setDefaults(Notification.DEFAULT_ALL) // 使用默认的声音、振动、闪光
                        .setLargeIcon(bitmap);
//                        .setNumber(NOTIFICATION_SHOW_SHOW_AT_MOST);

                String sound = extraJson.has("sound") ? extraJson.getString("sound") : "0";
                if (sound.equals("1")) {
                    BaiDuTtsSoundUtil.playSound(context, title, "4"); // 调用百度语音合成进行语音播放
                }
//                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // 设置默认通知铃声
//                if (sound.equals("1")) { // 播放声音，放资源声音
//                    uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.aiya);
//                }
//                notification.setSound(uri);

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_SHOW_SHOW_AT_MOST, notification.build()); //id随意，正好使用定义的常量做id，0除外，0为默认的Notification
            } catch (JSONException e) {
                L.e(e.toString());
            }
        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    L.e(TAG, "此消息没有额外数据");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" + myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    L.e(TAG, "获取消息额外的JSON错误！");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    /**
     * 点击通知栏跳转的页面
     *
     * @param context 上下文
     * @param extras  回传的扩展消息
     * @return Intent
     */
    private Intent onTouchOpenPage(Context context, String extras) {
        try {
            JSONObject extrasJson = new JSONObject(extras);
            String type = extrasJson.has("type") ? extrasJson.getString("type") : "2";
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (type.equals("2")) { // 跳转至H5界面
                intent.setClass(context, WebViewAty.class);
                Bundle bundleTemp = new Bundle();
                bundleTemp.putString("url", extrasJson.has("data") ? extrasJson.getString("data") : "");
                intent.putExtras(bundleTemp);
            } else if (type.equals("1")) { // 跳转到原生指定页面
                String module = extrasJson.has("module") ? extrasJson.getString("module") : "";
                switch (module) {
                    case "message":
                        intent.setClass(context, MessageAty.class);
                        break;
                    default:
                        intent.setClass(context, MainAty.class);
                        break;
                }
            }
            return intent;
        } catch (JSONException e) {
            return null;
        }
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        if (MainAty.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(MainAty.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MainAty.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (extraJson.length() > 0) {
                        msgIntent.putExtra(MainAty.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }
}
