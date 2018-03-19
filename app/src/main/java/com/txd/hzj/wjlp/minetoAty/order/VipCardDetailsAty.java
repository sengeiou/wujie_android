package com.txd.hzj.wjlp.minetoAty.order;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.new_wjyp.VipPayAty;
import com.txd.hzj.wjlp.new_wjyp.http.MemberOrder;
import com.txd.hzj.wjlp.tool.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

/**
 * Created by Txd_lienchao on 2018/3/16 0016 下午 1:24.
 * 功能描述:会员卡订单详情
 * email:470360046@qq.com
 * 不急不躁，BUG圆如，
 * 说改就改，不撕不怒，
 * 种种需求，过眼云烟，
 * 敏捷迭代，自在随心，
 * 立项结项，不如吃瓜。
 */

public class VipCardDetailsAty extends BaseAty {
    @ViewInject(R.id.lv_details)
    private ListViewForScrollView lv_details;
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    @ViewInject(R.id.tv_btn_left)
    private TextView tv_btn_left;
    @ViewInject(R.id.tv_btn_right)
    private TextView tv_btn_right;
    private List<Map<String, String>> mapList;
    private String order_id;
    private String member_coding;//会员卡编码
    private boolean isDel=false;//是否是删除订单


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_vip_card_details;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("订单详情");
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if(requestUrl.contains("memberOrderInfo")){
            Map<String,String>data= JSONUtils.parseKeyAndValueToMap(jsonStr);
            mapList = JSONUtils.parseKeyAndValueToMapList(data.get("data"));
            lv_details.setAdapter(new OrderAdapter());
            for(Map<String,String> temp:mapList){
                if(temp.get("order_name").equals("支付状态")){
                    switch (temp.get("order_value")){
                        case "未支付":
                            tv_btn_left.setVisibility(View.VISIBLE);
                            tv_btn_right.setVisibility(View.VISIBLE);
                            tv_btn_left.setText("取消订单");
                            tv_btn_right.setText("立即支付");
                            tv_btn_left.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(VipCardDetailsAty.this);
                                    builder.setMessage("是否取消订单");
                                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int j) {

                                        }
                                    });
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int j) {
                                            MemberOrder.delMemberOrder(order_id,"5",VipCardDetailsAty.this);
                                            showProgressDialog();
                                        }
                                    });
                                    builder.show();
                                }
                            });
                            tv_btn_right.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //立即支付
                                    MemberOrder.settlement(member_coding,VipCardDetailsAty.this);
                                    showProgressDialog();

                                }
                            });
                            break;
                        case "已支付":
                            tv_btn_left.setVisibility(View.GONE);
                            tv_btn_right.setVisibility(View.VISIBLE);
                            tv_btn_right.setText("删除订单");
                            tv_btn_right.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(VipCardDetailsAty.this);
                                    builder.setMessage("是否删除订单");
                                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int j) {

                                        }
                                    });
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int j) {
                                            isDel=true;
                                            MemberOrder.delMemberOrder(order_id,"9",VipCardDetailsAty.this);
                                            showProgressDialog();
                                        }
                                    });
                                    builder.show();
                                }
                            });
                            break;
                        case "已取消":
                            tv_btn_left.setVisibility(View.GONE);
                            tv_btn_right.setVisibility(View.VISIBLE);
                            tv_btn_right.setText("删除订单");
                            tv_btn_right.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(VipCardDetailsAty.this);
                                    builder.setMessage("是否删除订单");
                                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int j) {

                                        }
                                    });
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int j) {
                                            isDel=true;
                                            MemberOrder.delMemberOrder(order_id,"9",VipCardDetailsAty.this);
                                            showProgressDialog();
                                        }
                                    });
                                    builder.show();
                                }
                            });
                            break;
                    }
                    break;
                }
            }

        }
        if(requestUrl.contains("delMemberOrder")){
            Map<String,String>map=JSONUtils.parseKeyAndValueToMap(jsonStr);
            if(map.get("code").equals("1")){
                EventBus.getDefault().post(new MessageEvent("更新会员卡列表"));
                if(isDel){
                    finish();
                    return;
                }
                MemberOrder.memberOrderInfo(order_id,this);
                showProgressDialog();
            }

        }
        if(requestUrl.contains("settlement")){
            Map<String,String>data=JSONUtils.parseKeyAndValueToMap(jsonStr);
            data=JSONUtils.parseKeyAndValueToMap(data.get("data"));
            Bundle bundle = new Bundle();
            bundle.putString("data", String.valueOf(data));
            bundle.putString("order_id", order_id);
            startActivity(VipPayAty.class, bundle);
        }
    }
    class OrderAdapter extends BaseAdapter{

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
            if(view==null){
                viewHolder=new ViewHolder();
                view= LayoutInflater.from(VipCardDetailsAty.this).inflate(R.layout.item_vip_card_details,null);
                viewHolder.tv_left=view.findViewById(R.id.tv_left);
                viewHolder.tv_rigth=view.findViewById(R.id.tv_rigth);
                view.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) view.getTag();
            }
            viewHolder.tv_left.setText(mapList.get(i).get("order_name"));
            viewHolder.tv_rigth.setText(mapList.get(i).get("order_value"));
            return view;
        }
        class ViewHolder{
            private TextView tv_left;
            private TextView tv_rigth;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle=this.getIntent().getExtras();
        order_id = bundle.getString("order_id");
        member_coding = bundle.getString("member_coding");
        MemberOrder.memberOrderInfo(order_id,this);
        showProgressDialog();
    }
}
