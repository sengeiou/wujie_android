package com.txd.hzj.wjlp.minetoaty.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.tips.ToastTip;
import com.ants.theantsgo.tools.AlertDialog;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.OffLineOrderInfoBean;
import com.txd.hzj.wjlp.http.OfflineStore;
import com.txd.hzj.wjlp.melloffLine.OffLineEvaluationShopAty;
import com.txd.hzj.wjlp.minetoaty.PayForAppAty;

import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/30 15:20
 * 功能描述：线下商城订单列表详情页
 */
public class OffLineShopDetailsAty extends BaseAty{
    @ViewInject(R.id.lv_details)
    private ListViewForScrollView lv_details;
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    @ViewInject(R.id.tv_btn_left)
    private TextView tv_btn_left;
    @ViewInject(R.id.tv_btn_right)
    private TextView tv_btn_right;

    private List<OffLineOrderInfoBean.DataBean> mList;
    //	订单ID 注意注意 ：从订单列表中点进详情时 需要带上 order_id merchant_id pay_status status 参数 判断 如果pay_status=1时 显示删除按钮 如果pay_status=0是 status=0显示按钮 取消按钮 立即支付 如果status = 5 只显示删除按钮
    private String mOrder_id;
    private String mMerchant_id;
    private String mPay_status;
    private String mStatus;

    //5是取消订单 9是删除订单
    private String order_stats;
    private String mCommon_status;
    private String money;


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_offline_shopdetails;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("订单详情");
        Bundle extras = getIntent().getExtras();
        mOrder_id = extras.getString("order_id");
        mMerchant_id = extras.getString("merchant_id");
        mPay_status = extras.getString("pay_status");
        mStatus = extras.getString("status");
        mCommon_status = extras.getString("common_status");
        money = extras.getString("money");
        if ("1".equals(mPay_status)){
            tv_btn_left.setVisibility(View.GONE);
            tv_btn_right.setVisibility(View.VISIBLE);
            tv_btn_right.setText("删除订单");
            tv_btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delOrderDialog("是否删除订单");
                }
            });
            if ("0".equals(mStatus)){
                tv_btn_left.setVisibility(View.VISIBLE);
                tv_btn_left.setText("\u3000评价\u3000");
                tv_btn_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putString("order_id",mOrder_id);
                        startActivity(OffLineEvaluationShopAty.class,bundle);
                    }
                });
                if ("1".equals(mCommon_status)){
                    tv_btn_left.setVisibility(View.GONE);
                }
            }
        }else if ("0".equals(mPay_status)){
            if ("0".equals(mStatus)){
                tv_btn_left.setVisibility(View.VISIBLE);
                tv_btn_right.setVisibility(View.VISIBLE);
                tv_btn_left.setText("取消订单");
                tv_btn_right.setText("立即支付");
                tv_btn_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delOrderDialog("是否取消订单");
                    }
                });

                tv_btn_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //立即支付
                        Bundle bundle=new Bundle();
                        bundle.putString("order_id",mOrder_id);
                        bundle.putString("type","100");
                        bundle.putString("money",money);
                        bundle.putString("merchant_id",mMerchant_id);
                        startActivity(PayForAppAty.class,bundle);
                        finish();
                    }
                });
            }else if ("5".equals(mStatus)){
                tv_btn_left.setVisibility(View.GONE);
                tv_btn_right.setVisibility(View.VISIBLE);
                tv_btn_right.setText("删除订单");
                tv_btn_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }

    private void delOrderDialog(final String msg) {
        new AlertDialog(OffLineShopDetailsAty.this).builder().setTitle("提示").setMsg(msg).setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (msg.contains("取消")){
                    order_stats ="5";
                }else  if (msg.contains("删除")){
                    order_stats ="9";
                }
                OfflineStore.offLinedelOrder(mOrder_id, order_stats,OffLineShopDetailsAty.this);
            }
        }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mOrder_id!=null){
            OfflineStore.offLineOrderInfo(mOrder_id,this);
        }
    }

    @Override
    protected void requestData() {
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);

        if (requestUrl.contains("orderInfo")){
            OffLineOrderInfoBean offLineOrderInfoBean = JSON.parseObject(jsonStr, OffLineOrderInfoBean.class);
            if (offLineOrderInfoBean.getCode().equals("1")){
                mList=offLineOrderInfoBean.getData();
                lv_details.setAdapter(new OrderAdapter());
            }
        }

        if (requestUrl.contains("delOrder")){
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            if (jsonObject.containsKey("code")) {
                if ("1".equals(jsonObject.getString("code"))) {
                    if (jsonObject.containsKey("message")) {
                        ToastTip.makeText(OffLineShopDetailsAty.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        }

    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
    }

    class OrderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
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
                view = LayoutInflater.from(OffLineShopDetailsAty.this).inflate(R.layout.item_vip_card_details, null);
                viewHolder.tv_left = view.findViewById(R.id.tv_left);
                viewHolder.tv_rigth = view.findViewById(R.id.tv_rigth);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tv_left.setText(mList.get(i).getOrder_name());
            viewHolder.tv_rigth.setText(mList.get(i).getOrder_value());
            return view;
        }

        class ViewHolder {
            private TextView tv_left;
            private TextView tv_rigth;
        }
    }
}
