package com.txd.hzj.wjlp.mellOnLine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.List;
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

    @ViewInject(R.id.system_lv)
    private ListViewForScrollView system_lv;
    @ViewInject(R.id.dialogue_lv)
    private ListViewForScrollView dialogue_lv;

    private MssageAdapter mssageAdapter;
    private List<String> data;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlt_conter_tv.setText("消息");
        showStatusBar(R.id.title_re_layout);
        system_lv.setAdapter(new MssageAdapter(1, data));
        dialogue_lv.setAdapter(new MssageAdapter(2, data));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_message;
    }

    @Override
    protected void initialized() {
        data = new ArrayList<>();
    }

    @Override
    protected void requestData() {

    }

    private class MssageAdapter extends BaseAdapter {
        private int type;
        private List<String> list;

        private MessageViewHolder mvh;

        public MssageAdapter(int type, List<String> list) {
            this.type = type;
            this.list = list;
        }

        @Override
        public int getCount() {
            if (1 == type) {
                return 3;
            }
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (null == view) {
                view = LayoutInflater.from(MessageAty.this).inflate(R.layout.aty_message_tips, viewGroup, false);
                mvh = new MessageViewHolder();
                ViewUtils.inject(mvh, view);
                view.setTag(mvh);
            } else {
                mvh = (MessageViewHolder) view.getTag();
            }

            if (1 == type) {
                switch (i) {
                    case 0:
                        mvh.message_type_icon_iv.setImageResource(R.drawable.icon_message_for_order);
                        mvh.message_type_tv.setText("订单消息");
                        mvh.message_content_tv.setText("订单已签收！");
                        break;
                    case 1:
                        mvh.message_type_icon_iv.setImageResource(R.drawable.icon_message_for_inform);
                        mvh.message_type_tv.setText("通知消息");
                        mvh.message_content_tv.setText("第233期iPhone7一元夺宝已失效");
                        break;
                    case 2:
                        mvh.message_type_icon_iv.setImageResource(R.drawable.icon_message_for_notice);
                        mvh.message_type_tv.setText("公告");
                        mvh.message_content_tv.setText("第233期iPhone7一元夺宝已失效");
                        break;
                }
            } else {
                mvh.message_type_icon_iv.setImageResource(R.drawable.icon_temp_head);
                mvh.message_type_tv.setText("太平鸟");
                mvh.message_content_tv.setText("亲，您看下您的地址是这个么？");
            }
            mvh.message_lin_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (1 == type) {
                        bundle = new Bundle();
                        bundle.putInt("type", i);
                        startActivity(OrderAndInformMessageListAty.class, bundle);
                    } else {

                    }
                }
            });
            return view;
        }

        private class MessageViewHolder {

            @ViewInject(R.id.message_lin_layout)
            private LinearLayout message_lin_layout;
            /**
             * 头像
             */
            @ViewInject(R.id.message_type_icon_iv)
            private ImageView message_type_icon_iv;
            /**
             * 消息来源
             */
            @ViewInject(R.id.message_type_tv)
            private TextView message_type_tv;
            /**
             * 消息时间
             */
            @ViewInject(R.id.order_message_time_tv)
            private TextView order_message_time_tv;
            /**
             * 消息内容
             */
            @ViewInject(R.id.message_content_tv)
            private TextView message_content_tv;

        }

    }

}
