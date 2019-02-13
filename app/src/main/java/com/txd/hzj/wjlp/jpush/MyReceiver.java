package com.txd.hzj.wjlp.jpush;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.util.L;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bluetoothPrint.BluetoothUtils;
import com.txd.hzj.wjlp.bluetoothPrint.PrintfUtils;
import com.txd.hzj.wjlp.http.OfflineStore;
import com.txd.hzj.wjlp.mellonLine.MessageAty;
import com.txd.hzj.wjlp.tool.BaiDuTtsSoundUtil;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver implements BaseView {
    private static final String TAG = "JIGUANG-Example";
    private static final String ACTION_CUSTOMIZE_MY_MSG = "com.txd.hzj.wjlp.jpush.CUSTOMIZE_MY_MSG";
    private BluetoothUtils bluetoothUtils;
    private JSONObject printfDataJsonobj;

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

                // 打印
                printfOrder(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                L.e(TAG, "[MyReceiver] 用户点击打开了通知");

                // 打开自定义的Activity
                Intent i = onTouchOpenPage(context, bundle.getString(JPushInterface.EXTRA_EXTRA));
                if (i != null) {
                    i.putExtras(bundle);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                L.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                L.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else if (ACTION_CUSTOMIZE_MY_MSG.equals(intent.getAction())) {

                // 自定义的Action，点击Notification跳转至相应的界面
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(intent.getIntExtra("notificationId", Integer.parseInt(bundle.getString("notificationId"))));

                Intent i = onTouchOpenPage(context, bundle.getString("extras"));
                if (i != null) {
                    i.putExtras(bundle);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }

            } else {
                L.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            L.e(e.toString());
        }

    }

    /**
     * 打印订单添加判断是否打印
     *
     * @param context
     * @param bundle
     */
    private void printfOrder(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        bluetoothUtils = new BluetoothUtils(context);
        if (!"".equals(extras)) {
            try {
                JSONObject extrasJson = new JSONObject(extras);
                String moduleStr = extrasJson.has("module") ? extrasJson.getString("module") : "";
                if ("stage_order_hand".equals(moduleStr) || "stage_order_auto".equals(moduleStr)) {
                    printfDataJsonobj = extrasJson.has("data_print") ? extrasJson.getJSONObject("data_print") : null;
                    if ("stage_order_hand".equals(moduleStr)) { // 手动打印 直接打印
                        printfBluetooth();
                    } else if ("stage_order_auto".equals(moduleStr)) { // 自动打印 需要掉接口
                        OfflineStore.order_print(printfDataJsonobj.getString("order_sn"), printfDataJsonobj.getString("merchant_id"), this);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 调用蓝牙打印机打印订单
     */
    private void printfBluetooth() {
        if (printfDataJsonobj == null) {
            return;
        }
        if (BluetoothUtils.isHasPrinter) { // 如果已经连接
            PrintfUtils.printf_50MM(printfDataJsonobj);
        } else { // 否则的话直接打开默认连接，连接成功之后打印
            bluetoothUtils.setOnConnectSuccess(new BluetoothUtils.ConnectSuccess() {
                @Override
                public void success(String name, String address) {
                    PrintfUtils.printf_50MM(printfDataJsonobj);
                }
            });
            bluetoothUtils.defaultConnection();
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
                if (title == null) {
                    title = extraJson.has("title") ? extraJson.getString("title") : "";
                }
                if (msg == null) {
                    msg = extraJson.has("content") ? extraJson.getString("content") : "";
                }
                if ((extraJson.has("sound") ? extraJson.getString("sound") : "0").equals("0")) {
                    return;
                }
                // 设置一个id和Intent意图，并放入参数
                int id = (int) (System.currentTimeMillis() / 1000);
                Intent intent = new Intent(context, MyReceiver.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("notificationId", id + "");
                bundle1.putString("extras", extras);
                intent.putExtras(bundle1);
                intent.setAction(ACTION_CUSTOMIZE_MY_MSG); // 设置Action

                // 设置跳转
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // 实例化一个Notification并设置参数
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle(title.isEmpty() ? "无界优品" : title);
                builder.setContentText(msg.isEmpty() ? "您有一条新消息" : msg);
                builder.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(id, builder.build());

                // 判断是否播放语音
                String sound = extraJson.has("sound") ? extraJson.getString("sound") : "0";
                if (sound.equals("1")) {
                    BaiDuTtsSoundUtil.getInstance(context).speak(title); // 普通女声
//                    BaiDuTtsSoundUtil.getInstance(context).speak(title, "1"); // 普通男声
//                    BaiDuTtsSoundUtil.getInstance(context).speak(title, "2"); // 特别男声
//                    BaiDuTtsSoundUtil.getInstance(context).speak(title, "3"); // 情感男声<度逍遥>
//                    BaiDuTtsSoundUtil.getInstance(context).speak(title, "4"); // 情感儿童声<度丫丫>
                }

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
            L.e("TAG", "onTouchOpenPage:" + extras);
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
        } catch (Exception e) {
            L.e(e.toString());
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


    // ================================================== 接口回调 ==================================================
    @Override
    public void showDialog() {

    }

    @Override
    public void showDialog(String text) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void removeDialog() {

    }

    @Override
    public void removeContent() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onLoading(long total, long current, boolean isUploading) {

    }

    @Override
    public void onException(Exception exception) {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        if (requestUrl.contains("order_print")) { // 打印订单
            // 打印订单
            printfBluetooth();
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {

    }

    @Override
    public void onErrorTip(String tips) {

    }
    // ================================================== 接口回调 ==================================================
}
