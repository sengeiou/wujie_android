package com.txd.hzj.wjlp.mellonLine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.StringUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.message.UserMessagePst;
import com.txd.hzj.wjlp.minetoaty.order.OffLineShopDetailsAty;
import com.txd.hzj.wjlp.minetoaty.order.OrderDetailsAty;
import com.txd.hzj.wjlp.minetoaty.order.VipCardDetailsAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：上午 10:07
 * 描述：订单消息和通知消息列表
 */
public class OrderAndInformMessageListAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 标题
     */
    private String title = "";
    /**
     * 消息类型
     */
    private int type = 0;

    @ViewInject(R.id.message_lv)
    private PullToRefreshListView message_lv;

    private MessageAdapter messageAdapter;

    @ViewInject(R.id.no_data_layout)
    public LinearLayout no_data_layout;

    private UserMessagePst userMessagePst;
    private int p = 1;

    private List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlt_conter_tv.setText(title);
        showStatusBar(R.id.title_re_layout);

        message_lv.setEmptyView(no_data_layout);

        message_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;
                userMessagePst.messageList(p, type, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                p++;
                userMessagePst.messageList(p, type, false);
            }
        });

        message_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (type) {
                    case 0: // 订单消息
                        Map<String, String> orderMap = list.get(i - 1);
                        L.e("voodoo_jie", orderMap.toString());
                        String orderType = orderMap.containsKey("type") ? orderMap.get("type") : "";
                        if (!orderType.isEmpty()) {
                            Bundle bundle = new Bundle();
                            switch (Integer.parseInt(orderType)) {
                                case 0: // 0商品订单
                                case 2: // 2拼单购
                                    bundle.putString("id", orderMap.containsKey("order_id") ? orderMap.get("order_id") : "");
                                    if (Integer.parseInt(orderType) == 0) {
                                        bundle.putString("type", "0"); // 此处Type为订单类型，0是普通商品，4是拼单购。。。。
                                    } else if (Integer.parseInt(orderType) == 2) {
                                        bundle.putString("type", "3");
                                    }
                                    startActivity(OrderDetailsAty.class, bundle);
                                    break;
                                case 1: // 会员卡
                                    bundle.putString("order_id", orderMap.containsKey("order_id") ? orderMap.get("order_id") : "");
                                    bundle.putString("member_coding", "");
                                    startActivity(VipCardDetailsAty.class, bundle);
                                    break;
                                case 3: // 无界预购
                                    break;
                                case 4: // 比价购
                                    break;
                                case 5: // 无界商店
                                    break;
                                case 6: // 积分抽奖
                                    break;
                                case 9: // 线下店铺
                                    bundle.putString("order_id", orderMap.containsKey("order_id") ? orderMap.get("order_id") : "");
                                    bundle.putString("status", orderMap.containsKey("status") ? orderMap.get("status") : "");
                                    startActivity(OffLineShopDetailsAty.class, bundle); // 跳转至线下店铺订单详情页面
                                    break;
                            }
                        }
                        break;
                    case 1: // 通知消息
                        break;
                    case 2:
                        Bundle bundle = new Bundle();
                        bundle.putInt("from", 0);
                        bundle.putString("id", list.get(i - 1).get("id"));
                        startActivity(NoticeDetailsAty.class, bundle);
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_order_and_inform_message_list;
    }

    @Override
    protected void initialized() {
        type = getIntent().getIntExtra("type", 0);
        if (0 == type) {
            title = "订单消息";
        } else if (1 == type) {
            title = "通知消息";
        } else {
            title = "公告";
        }
        list = new ArrayList<>();
        messageAdapter = new MessageAdapter();
        userMessagePst = new UserMessagePst(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        userMessagePst.messageList(p, type, true);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        L.e("voodoo_jie", jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (ToolKit.isList(map, "data")) {
            if (1 == p) {
                list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                message_lv.setAdapter(messageAdapter);
            } else {
                List<Map<String, String>> list2 = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                list.addAll(list2);
                messageAdapter.notifyDataSetChanged();
            }
            message_lv.onRefreshComplete();
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        message_lv.onRefreshComplete();
    }

    private class MessageAdapter extends BaseAdapter {

        private MessageViewHolder mvh;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> msg = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(OrderAndInformMessageListAty.this).inflate(R.layout.item_message_lv,
                        viewGroup, false);
                mvh = new MessageViewHolder();
                ViewUtils.inject(mvh, view);
                view.setTag(mvh);
            } else {
                mvh = (MessageViewHolder) view.getTag();
            }
            if (0 == type) {
                mvh.item_message_type_tv.setText("订单消息");
                mvh.item_message_type_tv.setTextColor(Color.parseColor("#FFC729"));
            } else if (1 == type) {
                mvh.item_message_type_tv.setText("通知消息");
                mvh.item_message_type_tv.setTextColor(Color.parseColor("#3FA8FF"));
            } else {
                mvh.item_message_type_tv.setText("公告");
                mvh.item_message_type_tv.setTextColor(Color.parseColor("#F95757"));
            }

            mvh.msg_time_tv.setText(msg.get("create_time"));

            if (2 == type) {
                mvh.item_message_content_tv.setText(msg.get("title"));
                mvh.is_read_tv.setVisibility(msg.get("is_read").equals("0") ? View.VISIBLE : View.GONE);
            } else {
                mvh.item_message_content_tv.setText(msg.get("content"));
            }

            if (msg.get("status").equals("0")) {// 未读
                mvh.item_message_content_tv.setTextColor(ContextCompat.getColor(OrderAndInformMessageListAty.this,
                        R.color.app_text_color));
            } else {// 已读
                mvh.item_message_content_tv.setTextColor(ContextCompat.getColor(OrderAndInformMessageListAty.this,
                        R.color.gray_text_color));
            }
            return view;
        }

        private class MessageViewHolder {

            @ViewInject(R.id.msg_time_tv)
            private TextView msg_time_tv;

            /**
             * 消息类型
             */
            @ViewInject(R.id.item_message_type_tv)
            private TextView item_message_type_tv;

            @ViewInject(R.id.item_message_content_tv)
            private TextView item_message_content_tv;

            @ViewInject(R.id.is_read_tv)
            private TextView is_read_tv;

        }
    }

}
