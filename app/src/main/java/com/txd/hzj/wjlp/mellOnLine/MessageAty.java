package com.txd.hzj.wjlp.mellOnLine;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshScrollView;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.message.UserMessagePst;
import com.txd.hzj.wjlp.mellOnLine.adapter.HXAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    @ViewInject(R.id.message_center_sc)
    private PullToRefreshScrollView message_center_sc;

    private UserMessagePst userMessagePst;
    private int p = 1;

    private List<Map<String, String>> getFridends;
    private HXAdapter adapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlt_conter_tv.setText("消息");
        showStatusBar(R.id.title_re_layout);
        message_center_sc.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                p = 1;
                getData(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                p++;
                getData(false);
            }
        });

        dialogue_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                toChat(getFridends.get(i).get("easemob_account"),
                        getFridends.get(i).get("head_pic"),
                        getFridends.get(i).get("nickname"));
            }
        });

        // 注册环信监听
        DemoApplication.getInstance().setChatListener(this);
    }


    @Override
    @OnClick({R.id.order_message_layout, R.id.noty_message_layout, R.id.annou_message_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.order_message_layout:// 订单消息
                toMsgListPage(0);
                break;
            case R.id.noty_message_layout:// 通知消息
                toMsgListPage(1);
                break;
            case R.id.annou_message_layout:// 公告
                toMsgListPage(2);
                break;
        }
    }

    private void toMsgListPage(int value) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", value);
        startActivity(OrderAndInformMessageListAty.class, bundle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_message;
    }

    @Override
    protected void initialized() {
        // 环信上获取最近会话列表(即聊天记录)
        gson = new Gson();
        getFridends = new ArrayList<>();
        userMessagePst = new UserMessagePst(this);
    }

    @Override
    protected void requestData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        p = 1;
        getData(true);
    }
//刷新消息页面列表方法
    public void getData(boolean show) {
        List<Map<String, String>> fridends = getFriends();
        String account_json = gson.toJson(fridends);
        userMessagePst.newMsg(account_json, p, show);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("newMsg")) {
            if (ToolKit.isList(map, "data")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map != null ? map.get("data") : "");
                if (1 == p) {
                    if (data.get("msg_count").equals("0")) {
                        notice_message_num_tv.setVisibility(View.GONE);
                    } else {
                        notice_message_num_tv.setVisibility(View.VISIBLE);
                        notice_message_num_tv.setText(data.get("msg_count"));
                    }
                    notice_message_time_tv.setText(data.get("msg_time"));
                    notice_message_content_tv.setText(data.get("msg_title"));

                    if (data.get("announce_count").equals("0")) {
                        anno_message_num_tv.setVisibility(View.GONE);
                    } else {
                        anno_message_num_tv.setVisibility(View.VISIBLE);
                        anno_message_num_tv.setText(data.get("announce_count"));
                    }
                    anno_message_time_tv.setText(data.get("announce_time"));
                    anno_message_content_tv.setText(data.get("announce_title"));

                    if (data.get("order_count").equals("0")) {
                        order_message_num_tv.setVisibility(View.GONE);
                    } else {
                        order_message_num_tv.setVisibility(View.VISIBLE);
                        order_message_num_tv.setText(data.get("order_count"));
                    }

                    order_message_time_tv.setText(data.get("order_time"));
                    order_message_content_tv.setText(data.get("order_title"));

                    if (ToolKit.isList(data, "chat_list")) {
                        getFridends = JSONUtils.parseKeyAndValueToMapList(data.get("chat_list"));
                        adapter = new HXAdapter(this, getFridends);
                        dialogue_lv.setAdapter(adapter);
                    }
                } else {
                    if (ToolKit.isList(data, "chat_list")) {
                        List<Map<String, String>> list = JSONUtils.parseKeyAndValueToMapList(data.get("chat_list"));
                        getFridends.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            message_center_sc.onRefreshComplete();
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        message_center_sc.onRefreshComplete();
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
            map.put("last_time", String.valueOf(lastMessage.getMsgTime()));
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

    // TODO==========新消息回调==========
    // TODO==========新消息回调==========
    // TODO==========新消息回调==========
    // TODO==========新消息回调==========
    // TODO==========新消息回调==========
    @Override
    public void onMessageReceived(List<EMMessage> var1) {
        List<Map<String, String>> list = getFriends();
        if (!ListUtils.isEmpty(getFridends)) {
            for (Map<String, String> tempList : list) {
                for (Map<String, String> fri : getFridends) {
                    if (tempList.get("easemob_account").equals(fri.get("easemob_account"))) {
                        fri.put("msg_count", tempList.get("msg_count"));
                    }
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MessageAty.this.adapter.notifyDataSetChanged();
                    L.e("======最总数据======",getFridends.toString());
                }
            });
        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> var1) {

    }

    @Override
    public void onMessageRead(List<EMMessage> var1) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> var1) {

    }

    @Override
    public void onMessageChanged(EMMessage var1, Object var2) {

    }


}
