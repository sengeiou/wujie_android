package com.txd.hzj.wjlp.huanxin.ui;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.txdHxListener.ChatListener;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.util.EMLog;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.DemoHelper;
import com.txd.hzj.wjlp.base.EaseChatFragment;
import com.hyphenate.util.EasyUtils;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/26 0026
 * 时间：下午 3:02
 * 描述：环信聊天
 * ===============Txunda===============
 */
public class ChatActivity extends BaseActivity {
    protected static final String TAG = "ChatActivity";
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    public String toChatUsername;
    private EaseUI easeUI;
    private EaseContactListFragment easeContactListFragment;
    private EMMessageListener messageListener;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        activityInstance = this;
        // 获取用户id或者群组id
        toChatUsername = getIntent().getExtras().getString("userId");
        // 创建懒加载Fragment
        chatFragment = new ChatFragment();
        easeContactListFragment = new EaseContactListFragment();
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
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
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
