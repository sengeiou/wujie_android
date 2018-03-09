package com.hyphenate.easeui.txdHxListener;

import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * 代替环信的监听
 *
 * @author txunda_lh
 * @date 2017/9/4.
 */

public interface ChatListener {
    void onMessageReceived(List<EMMessage> var1);

    void onCmdMessageReceived(List<EMMessage> var1);

    void onMessageRead(List<EMMessage> var1);

    void onMessageDelivered(List<EMMessage> var1);

    void onMessageChanged(EMMessage var1, Object var2);
}
