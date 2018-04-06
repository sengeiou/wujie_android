package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.UserBalanceHjs;
import com.txd.hzj.wjlp.minetoAty.PayForAppAty;
import com.txd.hzj.wjlp.minetoAty.balance.RechargeAty;
import com.txd.hzj.wjlp.new_wjyp.http.UserBalance;

import java.util.List;

/**
 * 开发者： Txd_WangJJ
 * 创建时间： 2018/4/6 006 12:53:31.
 * 功能描述：
 * 联系方式： jingjie.office@qq.com
 */

public class OnlineChongAdapter extends BaseAdapter {

    private BaseView baseView;
    private Context context;
    private List<UserBalanceHjs.DataBean> list;

    public OnlineChongAdapter(Context context, List<UserBalanceHjs.DataBean> list, BaseView baseView) {
        this.baseView = baseView;
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_online_chong, null);
            viewHolder.itemChong_payType_tv = convertView.findViewById(R.id.ItemChong_payType_tv);
            viewHolder.itemChong_time_tv = convertView.findViewById(R.id.ItemChong_time_tv);
            viewHolder.itemChong_state_tv = convertView.findViewById(R.id.ItemChong_state_tv);
            viewHolder.itemChong_meny_tv = convertView.findViewById(R.id.ItemChong_meny_tv);
            viewHolder.itemChong_orderNum_tv = convertView.findViewById(R.id.ItemChong_orderNum_tv);
            viewHolder.itemChong_cancel_tv = convertView.findViewById(R.id.ItemChong_cancel_tv);
            viewHolder.itemChong_pay_tv = convertView.findViewById(R.id.ItemChong_pay_tv);
            viewHolder.itemChong_delete_tv = convertView.findViewById(R.id.ItemChong_delete_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final UserBalanceHjs.DataBean dataBean = list.get(position);
        L.e(dataBean.toString());

        viewHolder.itemChong_payType_tv.setText(dataBean.getPay_type());
        viewHolder.itemChong_time_tv.setText(dataBean.getCreate_time());
        viewHolder.itemChong_state_tv.setText(dataBean.getStatus().equals("0") ? "未支付" : dataBean.getStatus().equals("1") ? "已支付" : dataBean.getStatus().equals("5") ? "已取消" : "");
        viewHolder.itemChong_meny_tv.setText(dataBean.getMoney());
        viewHolder.itemChong_orderNum_tv.setText(dataBean.getOrder_sn());

        if (dataBean.getStatus().equals("0")) { // 未支付
            viewHolder.itemChong_cancel_tv.setVisibility(View.VISIBLE); // 取消显示
            viewHolder.itemChong_pay_tv.setVisibility(View.VISIBLE); // 付款显示
        } else if (dataBean.getStatus().equals("1")) { // 已支付
            viewHolder.itemChong_delete_tv.setVisibility(View.VISIBLE); // 显示删除订单
        } else if (dataBean.getStatus().equals("5")) { // 取消
            viewHolder.itemChong_cancel_tv.setVisibility(View.VISIBLE); // 取消按钮显示
            viewHolder.itemChong_cancel_tv.setText("已取消"); // 设置为取消状态
            viewHolder.itemChong_delete_tv.setVisibility(View.VISIBLE); // 删除订单显示
        }

        viewHolder.itemChong_cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消按钮
                if (dataBean.getStatus().equals("5")) {
                    return;
                } else {
                    UserBalance.delHjsInfo(dataBean.getId(), "5", baseView);
                }
            }
        });
        viewHolder.itemChong_pay_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 付款
                Intent intent = new Intent(context, RechargeAty.class);
                Bundle bundle = new Bundle();
                bundle.putString("order_id", dataBean.getId());
                bundle.putBoolean("orderIn", true);
                bundle.putString("money", dataBean.getMoney());
                context.startActivity(intent, bundle);
            }
        });
        viewHolder.itemChong_delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除按钮订单
                UserBalance.delHjsInfo(dataBean.getId(), "9", baseView);
            }
        });

        return convertView;
    }

    class ViewHolder {
        private TextView itemChong_payType_tv;
        private TextView itemChong_time_tv;
        private TextView itemChong_state_tv;
        private TextView itemChong_meny_tv;
        private TextView itemChong_orderNum_tv;
        private TextView itemChong_cancel_tv;
        private TextView itemChong_pay_tv;
        private TextView itemChong_delete_tv;
    }

}
