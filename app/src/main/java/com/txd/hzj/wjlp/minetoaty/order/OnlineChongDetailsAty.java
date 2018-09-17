package com.txd.hzj.wjlp.minetoaty.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.tools.ObserTool;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.HjsInfoBean;
import com.txd.hzj.wjlp.bean.HjsInfoDataBean;
import com.txd.hzj.wjlp.http.UserBalance;
import com.txd.hzj.wjlp.minetoaty.balance.RechargeAty;

import java.util.List;
import java.util.Map;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/6/27 16:01
 * 功能描述：线上充值明细
 * 联系方式：常用邮箱或电话
 */
public class OnlineChongDetailsAty extends BaseAty implements View.OnClickListener {
    @ViewInject(R.id.lv_details)
    private ListViewForScrollView lv_details;
    @ViewInject(R.id.ItemChong_cancel_tv)
    private TextView ItemChong_cancel_tv;
    @ViewInject(R.id.ItemChong_pay_tv)
    private TextView ItemChong_pay_tv;
    @ViewInject(R.id.ItemChong_delete_tv)
    private TextView ItemChong_delete_tv;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    private String order_id;
    private String status;
    private String moneyStr;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_online_chong_detail;
    }

    @Override
    protected void initialized() {

        titlt_conter_tv.setText("订单详情");
        order_id = getIntent().getStringExtra("order_id");
//        "status":
        status = getIntent().getStringExtra("status");
        switch (status) {
            case "0": {//"0"//未支付（取消订单  （灰色） 立即支付（红色））
                ItemChong_cancel_tv.setVisibility(View.VISIBLE);
                ItemChong_cancel_tv.setOnClickListener(this);

                ItemChong_pay_tv.setVisibility(View.VISIBLE);
                ItemChong_pay_tv.setOnClickListener(this);
            }
            break;
            case "1": {// 1已支付（删除订单（红色））
                ItemChong_delete_tv.setVisibility(View.VISIBLE);
                ItemChong_delete_tv.setOnClickListener(this);
            }
            break;
            case "5": {//  5取消 （删除订单（红色））
                ItemChong_delete_tv.setVisibility(View.VISIBLE);
                ItemChong_delete_tv.setOnClickListener(this);
            }
            break;
        }
    }

    @Override
    @OnClick({R.id.ItemChong_cancel_tv, R.id.ItemChong_pay_tv, R.id.ItemChong_delete_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ItemChong_cancel_tv: {//取消订单
                shoTipDialog("是否取消订单？", "提示","确定","取消", "5", this);
            }
            break;
            case R.id.ItemChong_pay_tv: {//充值
                Intent intent = new Intent();
                intent.setClass(OnlineChongDetailsAty.this, RechargeAty.class);
                intent.putExtra("order_id", order_id);
                intent.putExtra("orderIn", true);
                intent.putExtra("money", moneyStr);
//                Intent intent = new Intent(context, RechargeAty.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("order_id", dataBean.getId());
//                bundle.putBoolean("orderIn", true);
//                bundle.putString("money", dataBean.getMoney());
                startActivity(intent);
            }
            break;
            case R.id.ItemChong_delete_tv: {//删除订单
                shoTipDialog("是否删除订单？", "提示", "确定", "取消", "9", this);
            }
            break;
        }
    }

    private void shoTipDialog(String messageStr, String titleStr, String okStr, String noStr, final String param_order_status, final BaseView baseView) {
        new MikyouCommonDialog(OnlineChongDetailsAty.this, messageStr, titleStr, okStr, noStr, true)
                .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                    @Override
                    public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
                        switch (btnType) {
                            case MikyouCommonDialog.OK: {
                                UserBalance.delHjsInfo(order_id, param_order_status, baseView);
                            }
                            break;
                            case MikyouCommonDialog.NO: {
                            }
                            break;
                        }
                    }
                }).showDialog();
    }

    @Override
    protected void requestData() {
        showProgressDialog();
        UserBalance.hjsInfo(this, order_id);

    }


    class OrderAdapter extends BaseAdapter {
        List<HjsInfoDataBean> mapList;

        public void setMapList(List<HjsInfoDataBean> mapList) {
            this.mapList = mapList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mapList.size();
        }

        @Override
        public Object getItem(int i) {
            return mapList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(OnlineChongDetailsAty.this).inflate(R.layout.item_vip_card_details, null);
                viewHolder.tv_left = view.findViewById(R.id.tv_left);
                viewHolder.tv_rigth = view.findViewById(R.id.tv_rigth);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            HjsInfoDataBean hjsInfoDataBean = mapList.get(i);
            if (hjsInfoDataBean.getOrder_name().equals("支付金额")) {
                moneyStr = hjsInfoDataBean.getOrder_value();
            }
            viewHolder.tv_left.setText(hjsInfoDataBean.getOrder_name());
            viewHolder.tv_rigth.setText(mapList.get(i).getOrder_value());
            return view;
        }

        class ViewHolder {
            private TextView tv_left;
            private TextView tv_rigth;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        removeProgressDialog();
        if (requestUrl.contains("UserBalance/hjsInfo")) {
            ObserTool.gainInstance().jsonToBean(jsonStr, HjsInfoBean.class, new ObserTool.BeanListener() {
                @Override
                public void returnObj(Object t) {
                    HjsInfoBean hjsInfoBean = (HjsInfoBean) t;
                    List<HjsInfoDataBean> lists = hjsInfoBean.getData();
                    OrderAdapter adapter = new OrderAdapter();
                    adapter.setMapList(lists);
                    lv_details.setAdapter(adapter);

                }
            });
        } else if (requestUrl.contains("UserBalance/delHjsInfo")) {//取消订单  删除
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if (data.get("code").equals("1")) {
                finish();
            }
            showToast(data.get("message"));
        }
    }

}
