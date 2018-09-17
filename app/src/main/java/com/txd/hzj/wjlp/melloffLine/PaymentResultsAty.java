package com.txd.hzj.wjlp.melloffLine;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.AppManager;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.PaymentResultBean;
import com.txd.hzj.wjlp.http.OfflineStore;
import com.txd.hzj.wjlp.minetoaty.order.OffLineShopAty;

/**
 * 创建者：voodoo_jie
 * 创建时间：2018/07/20 020 下午 15:19
 * 功能描述：线下店铺支付结果展示页面
 */
public class PaymentResultsAty extends BaseAty {

    // 头像
    @ViewInject(R.id.offlinePaymentResult_head_imgv)
    public ImageView offlinePaymentResult_head_imgv;
    // 店铺名称
    @ViewInject(R.id.offlinePaymentResult_shopName_tv)
    public TextView offlinePaymentResult_shopName_tv;
    // 支付时间
    @ViewInject(R.id.offlinePaymentResult_payTime_tv)
    public TextView offlinePaymentResult_payTime_tv;
    // 返回结果成功或失败的图片
    @ViewInject(R.id.offlinePaymentResult_resultImg_imgv)
    public ImageView offlinePaymentResult_resultImg_imgv;
    // 返回结果，成功或失败文字
    @ViewInject(R.id.offlinePaymentResult_resultText_tv)
    public TextView offlinePaymentResult_resultText_tv;
    // 支付的金额
    @ViewInject(R.id.offlinePaymentResult_money_tv)
    public TextView offlinePaymentResult_money_tv;
    private String order_id;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_offline_payment_results;
    }

    @Override
    protected void initialized() {
        order_id = getIntent().getStringExtra("orderId");

    }

    @Override
    protected void requestData() {
        if (order_id != null && !order_id.isEmpty()) {
            OfflineStore.orderSuccess(order_id, this);
        } else {
            showToast("订单ID异常");
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("orderSuccess")) {
            Gson gson = new Gson();
            PaymentResultBean paymentResultBean = gson.fromJson(jsonStr, PaymentResultBean.class);
            // 设置门头图片
            Glide.with(this).load(paymentResultBean.getData().getLogo()).into(offlinePaymentResult_head_imgv);
            // 店铺名称
            offlinePaymentResult_shopName_tv.setText(paymentResultBean.getData().getMerchant_name());
            // 支付时间
            offlinePaymentResult_payTime_tv.setText(paymentResultBean.getData().getPay_time());
            // 支付成功或是失败图片
            offlinePaymentResult_resultImg_imgv.setImageResource("1".equals(paymentResultBean.getData().getPay_status()) ? R.mipmap.icon_offline_payment_results_img : R.mipmap.icon_offline_payment_results_false_img);
            // 支付成功或是失败提示文字以及文字颜色
            offlinePaymentResult_resultText_tv.setText("1".equals(paymentResultBean.getData().getPay_status()) ? "支付成功" : "支付失败");
            offlinePaymentResult_resultText_tv.setTextColor("1".equals(paymentResultBean.getData().getPay_status()) ? Color.parseColor("#1EAE1C") : Color.parseColor("#F23030"));
            // 支付的金额
            offlinePaymentResult_money_tv.setText(paymentResultBean.getData().getOrder_price() + "");
            // 如果返回成功，则关闭输入付款金额的界面
            if ("1".equals(paymentResultBean.getData().getPay_status())) {
                AppManager.getInstance().killActivity(PaymentAty.class);
            }
        }
    }

    @OnClick({R.id.offlinePaymentResult_goOrder_tv, R.id.offlinePaymentResult_goBack_tv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.offlinePaymentResult_goOrder_tv:
                startActivity(OffLineShopAty.class, new Bundle());
                finish();
                break;
            case R.id.offlinePaymentResult_goBack_tv:
                AppManager.getInstance().killAllActivityNoExit();
                startActivity(MainAty.class, null);
                break;
        }
    }
}
