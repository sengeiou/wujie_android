package com.txd.hzj.wjlp.mellOnLine;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.DateUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.huanxin.ui.ChatActivity;
import com.txd.hzj.wjlp.mellOnLine.adapter.HXAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：下午 12:02
 * 描述：消息
 * ===============Txunda===============
 */
public class MessageAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.dialogue_lv)
    private ListViewForScrollView dialogue_lv;

    private Bundle bundle;

    /**
     * 订单消息数量
     */
    @ViewInject(R.id.new_message_num_tv1)
    private TextView order_message_num_tv;
    /**
     * 订单消息时间
     */
    @ViewInject(R.id.order_message_time_tv1)
    private TextView order_message_time_tv;
    /**
     * 消息内容
     */
    @ViewInject(R.id.message_content_tv1)
    private TextView order_message_content_tv;
    /**
     * 通知消息数量
     */
    @ViewInject(R.id.new_message_num_tv2)
    private TextView notice_message_num_tv;
    /**
     * 通知消息时间
     */
    @ViewInject(R.id.order_message_time_tv2)
    private TextView notice_message_time_tv;
    /**
     * 通知消息内容
     */
    @ViewInject(R.id.message_content_tv2)
    private TextView notice_message_content_tv;
    /**
     * 公告数量
     */
    @ViewInject(R.id.new_message_num_tv3)
    private TextView anno_message_num_tv;
    /**
     * 公告发布时间
     */
    @ViewInject(R.id.order_message_time_tv3)
    private TextView anno_message_time_tv;
    /**
     * 消息内容
     */
    @ViewInject(R.id.message_content_tv3)
    private TextView anno_message_content_tv;

    private List<Map<String,String>> fridends;

    private String account_json = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlt_conter_tv.setText("消息");
        showStatusBar(R.id.title_re_layout);
        dialogue_lv.setAdapter(new HXAdapter(this, fridends));

        dialogue_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle = new Bundle();
                bundle.putString("userId",fridends.get(i).get("easemob_account"));// 对方环信账号
                bundle.putString("userHead",fridends.get(i).get("head_pic"));// 对方头像
                bundle.putString("userName",fridends.get(i).get("nickname"));// 对方昵称
                bundle.putString("myName",application.getUserInfo().get("nickname"));// 我的昵称
                bundle.putString("myHead",application.getUserInfo().get("head_pic"));// 我的头像
                startActivity(ChatActivity.class,bundle);
            }
        });
    }

    @Override
    @OnClick({R.id.order_message_layout, R.id.noty_message_layout, R.id.annou_message_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.order_message_layout:// 订单消息
                bundle = new Bundle();
                bundle.putInt("type", 0);
                startActivity(OrderAndInformMessageListAty.class, bundle);
                break;
            case R.id.noty_message_layout:// 通知消息
                bundle = new Bundle();
                bundle.putInt("type", 1);
                startActivity(OrderAndInformMessageListAty.class, bundle);
                break;
            case R.id.annou_message_layout:// 公告
                bundle = new Bundle();
                bundle.putInt("type", 2);
                startActivity(OrderAndInformMessageListAty.class, bundle);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_message;
    }

    @Override
    protected void initialized() {
        // 环信上获取最近会话列表(即聊天记录)
        fridends = getFriends();
    }

    @Override
    protected void requestData() {

    }

    private List<Map<String, String>> getFriends() {
        List<EMConversation> list = loadConversationList();

        List<Map<String, String>> friends = new ArrayList<>();
        for (EMConversation ecm : list) {
            EMMessage lastMessage = ecm.getLastMessage();

            Map<String, String> map = new HashMap<>();
            // 环信账号
            map.put("easemob_account", ecm.conversationId());
            // 消息数量
            map.put("msg_count", String.valueOf(ecm.getUnreadMsgCount()));
            // 最后一条内容
            map.put("last_content", EaseCommonUtils.getMessageDigest(lastMessage, this));
            // 最后一条消息时间
            map.put("last_time", DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
            friends.add(map);
        }
        return friends;
    }

    protected List<EMConversation> loadConversationList() {
        // get all conversations(获取所有会话)
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<>();
        /*
         * lastMsgTime will change if there is new message during sorting
         * so use synchronized to make sure timestamp of last message won't change.
         *
         * 如果在排序过程中有新消息，那么lastMsgTime将会发生变化，
         * 因此使用synchronized来确保最后消息的时间戳不会改变。
         */
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            // 根据时间排序，有bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    /**
     * sort conversations according time stamp of last message
     *
     * @param conversationList 列表
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

}
