package com.txd.hzj.wjlp.mellOnLine;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/7 0007
 * 时间：上午 10:07
 * 描述：订单消息和通知消息列表
 * ===============Txunda===============
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
    private ListView message_lv;

    private MessageAdapter messageAdapter;

    private List<String> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlt_conter_tv.setText(title);
        showStatusBar(R.id.title_re_layout);
        message_lv.setAdapter(messageAdapter);
        message_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (2 == type) {
                    startActivity(NoticeDetailsAty.class, null);
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
        data = new ArrayList<>();
        messageAdapter = new MessageAdapter();
    }

    @Override
    protected void requestData() {

    }

    private class MessageAdapter extends BaseAdapter {

        private MessageViewHolder mvh;

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
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
            return view;
        }

        private class MessageViewHolder {
            /**
             * 消息类型
             */
            @ViewInject(R.id.item_message_type_tv)
            private TextView item_message_type_tv;

        }
    }

}
