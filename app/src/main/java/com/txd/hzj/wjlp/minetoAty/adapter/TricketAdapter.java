package com.txd.hzj.wjlp.minetoAty.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：10:08
 * 描述：
 */

public class TricketAdapter extends BaseAdapter {
    private MCVH mcvh;
    private int type = 0;
    private Context context;

    private List<Map<String, String>> data;

    public TricketAdapter(int type, Context context, List<Map<String, String>> data) {
        this.type = type;
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Map<String, String> getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Map<String, String> map = getItem(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_tricket_lv_hzj, null);
            mcvh = new MCVH();
            ViewUtils.inject(mcvh, view);
            view.setTag(mcvh);
        } else {
            mcvh = (MCVH) view.getTag();
        }
        if (0 == type) { // 正常的
            mcvh.itemTricket_statusBackground_imgv.setImageResource(R.drawable.img_tricket_right_red); // 红色的
            mcvh.itemTricket_status_imgv.setImageResource(R.drawable.img_tricket_right_weishiyong); // 未使用
//            mcvh.ticket_lin_layout.setBackgroundResource(R.drawable.icon_valid_ticket_bg_hzj);
            mcvh.tricket_nowMoney_tv.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else { // 过期的
            // icon_un_valid_ticket_bg_hzj
            // icon_no_uses_tick_bg_hzj
            mcvh.itemTricket_statusBackground_imgv.setImageResource(R.drawable.img_tricket_right_gray); // 灰色的
            mcvh.itemTricket_status_imgv.setImageResource(R.drawable.img_tricket_right_yishixiao); // 已过期
//            mcvh.ticket_lin_layout.setBackgroundResource(R.drawable.icon_past_due_ticket_bg_hzj);
            mcvh.tricket_nowMoney_tv.setTextColor(ContextCompat.getColor(context, R.color.gray_text_color));
        }

        Glide.with(context).load(map.get("logo")).into(mcvh.img_cover);
        mcvh.tricket_nowMoney_tv.setText("￥" + map.get("now_money"));
        mcvh.tricket_userticketId_tv.setText("代金券编码：" + map.get("id"));
        mcvh.tricket_money_tv.setText("代金券面值：" + map.get("money"));
        mcvh.end_sourceStatus_tv.setText("获取途径：" + map.get("source_status"));
        mcvh.end_time_tv.setText("失效时间：" + map.get("end_time"));
        return view;
    }

    class MCVH {
        @ViewInject(R.id.ticket_lin_layout)
        private LinearLayout ticket_lin_layout;

        @ViewInject(R.id.tricket_nowMoney_tv) // 当前金额
        private TextView tricket_nowMoney_tv;
        @ViewInject(R.id.tricket_userticketId_tv) // 编码
        private TextView tricket_userticketId_tv;
        @ViewInject(R.id.tricket_money_tv) // 面值
        private TextView tricket_money_tv;
        @ViewInject(R.id.end_sourceStatus_tv) // 途径
        private TextView end_sourceStatus_tv;
        @ViewInject(R.id.end_time_tv) // 失效时间
        private TextView end_time_tv;
        @ViewInject(R.id.img_cover)
        private ImageView img_cover;

        @ViewInject(R.id.itemTricket_status_imgv)
        private ImageView itemTricket_status_imgv; // 右侧状态
        @ViewInject(R.id.itemTricket_statusBackground_imgv)
        private ImageView itemTricket_statusBackground_imgv; // 右侧图背景

    }
}
