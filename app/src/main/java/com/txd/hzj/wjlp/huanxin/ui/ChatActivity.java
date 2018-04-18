package com.txd.hzj.wjlp.huanxin.ui;

import android.content.Intent;
import android.os.Bundle;

import com.txd.hzj.wjlp.base.EaseChatFragment;
import com.hyphenate.util.EasyUtils;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/26 0026
 * 时间：下午 3:02
 * 描述：环信聊天
 * ===============Txunda===============
 */
public class ChatActivity extends BaseActivity{
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    public String toChatUsername;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        activityInstance = this;
        // 获取用户id或者群组id
        toChatUsername = getIntent().getExtras().getString("userId");
        // use EaseChatFratFragment
        chatFragment = new ChatFragment();
        // pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
    	// make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainAty.class);
            startActivity(intent);
        }
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }
}
