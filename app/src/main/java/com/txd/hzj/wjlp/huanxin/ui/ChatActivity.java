package com.txd.hzj.wjlp.huanxin.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.ants.theantsgo.systemBarUtil.ImmersionBar;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.EasyUtils;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.EaseChatFragment;
import com.yanzhenjie.permission.AndPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/9/26 0026
 * 时间：下午 3:02
 * 描述：环信聊天
 */
public class ChatActivity extends BaseActivity {
    protected static final String TAG = "ChatActivity";
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    public String toChatUsername;
    private EaseUI easeUI;
    private EMMessageListener messageListener;
    private String mSta_mid;
    private Map<String, String> map;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);

        if (!AndPermission.hasPermission(ChatActivity.this, Manifest.permission.RECORD_AUDIO)) {
            AndPermission.with(ChatActivity.this)
                    .requestCode(100)
                    .permission(Manifest.permission.RECORD_AUDIO)
                    .send();
        }

        activityInstance = this;
        // 获取用户id或者群组id
        toChatUsername = getIntent().getExtras().getString("userId");
        // 创建懒加载Fragment
        chatFragment = new ChatFragment();
        easeUI = EaseUI.getInstance();
        // 传递参数给Fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        /* 收到已送达消息回执 *
         * 消息状态变动
         * */
        messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;

            /**
             * 接收到消息
             */
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
                    // in background, do not refresh UI, notify it in notification bar
                    if (!easeUI.hasForegroundActivies()) {
                        getNotifier().onNewMsg(message);
                    }
                    L.e("环信接收消息", "ChatActivity -- onMessageReceived:" + message.toString());
                }
            }

            /**
             * 收到透传消息
             * 透传消息能做什么：头像、昵称的更新等。可以把透传消息理解为一条指令，通过发送这条指令给对方，
             * 告诉对方要做的 action，收到消息可以自定义处理的一种消息。（透传消息不会存入本地数据库中，所以在 UI 上是不会显示的）
             */
            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "receive command message");
                    //get message body
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();//获取自定义action

                    if (action.equals("__Call_ReqP2P_ConferencePattern")) {
                        String title = message.getStringAttribute("em_apns_ext", "conference call");
                        Toast.makeText(getApplication(), title, Toast.LENGTH_LONG).show();
                    }
                    //end of red packet code
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                    L.e("环信接收消息", "ChatActivity -- onCmdMessageReceived:" + message.toString());

                    setPreferencesData(message); // 设置商家的环信账号值

                    EMLog.d(TAG, String.format("Command：action:%s,message:%s", action, message.toString()));
                }
            }

            /**
             * 收到已读回执
             */
            @Override
            public void onMessageRead(List<EMMessage> messages) {
            }

            /**
             * 收到已送达消息回执
             */
            @Override
            public void onMessageDelivered(List<EMMessage> message) {
            }

            /**
             * 消息状态变动
             */
            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                EMLog.d(TAG, "change:");
                EMLog.d(TAG, "change:" + change);
            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
        //        showStatusBar(R.id.container);
    }

    private void setPreferencesData(EMMessage eMessage) {
        String fromIdStr = eMessage.getFrom();
        String uid = eMessage.getStringAttribute("uid", "");
        String bid = eMessage.getStringAttribute("bid", "");
        L.e("huanxinkuozhan", "获取的值：fromIdStr：" + fromIdStr + " --- uid:" + uid + " --- bid:" + bid);

        try {
            // 获取文件共享值
            String string = PreferencesUtils.getString(ChatActivity.this, PreferencesUtils.SHARED_KEY_HUANXIN_USER_ID, "");
            if (string.isEmpty()) { // 如果无该值，则直接创建JsonArray进行储存
//              [{"from":"123123123","uid":"1","bid":"122"}]
                JSONArray jsonArray = new JSONArray();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("from", fromIdStr);
                jsonObject.put("uid", uid);
                jsonObject.put("bid", bid);

                jsonArray.put(jsonObject);
                PreferencesUtils.putString(ChatActivity.this, PreferencesUtils.SHARED_KEY_HUANXIN_USER_ID, jsonArray.toString());
            } else { // 否则的话是有值，将其转换成JsonArray格式的数组
                boolean isHave = false; // 是否已存在
                JSONArray jsonArray = new JSONArray(string);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (fromIdStr.equals(jsonObject.getString("from"))) {
                        isHave = true;
                    }
                }
                if (!isHave) { // 如果回传的数据不存在记录中则将其添加进去并保存
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("from", fromIdStr);
                    jsonObject.put("uid", uid);
                    jsonObject.put("bid", bid);
                    jsonArray.put(jsonObject);
                    PreferencesUtils.putString(ChatActivity.this, PreferencesUtils.SHARED_KEY_HUANXIN_USER_ID, jsonArray.toString());
                }
            }
        } catch (JSONException e) {
            L.e("JSONArray 格式异常");
        }
    }


    /**
     * 修改StatusBar颜色
     *
     * @param vid 标题栏
     */
    public void showStatusBar(int vid) {
        String name = android.os.Build.BRAND;

        if (name.equals("Huawei")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true, 0.2f).init();
            } else {
                ImmersionBar.with(this).titleBar(vid).init();
            }
        } else {
            if (ImmersionBar.isSupportStatusBarDarkFont()) {
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true).init();
            } else {
                ImmersionBar.with(this).titleBar(vid).statusBarDarkFont(true, 0.2f).init();
            }
        }
    }


    public EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 确保只有一个聊天页被打开
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username)) {
            super.onNewIntent(intent);
        } else {
            finish();
            startActivity(intent);
        }

    }

    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();

    }

    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMsgsCount();
    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainAty.class);
            startActivity(intent);
        }
    }

    public String getToChatUsername() {
        return toChatUsername;
    }

}
