package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/17 0017
 * 时间：下午 1:06
 * 描述：支付(会员支付)
 * ===============Txunda===============
 */
public class PayForAppAty extends BaseAty {

    /**
     * 标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 传递使者，传递大使(选中标识)
     */
    private int top_type = 0;

    /**
     * 传递使者CheckBox
     */
    @ViewInject(R.id.top_cb)
    private CheckBox top_cb;
    /**
     * 传递大使CheckBox
     */
    @ViewInject(R.id.bottom_cb)
    private CheckBox bottom_cb;
    /**
     * 微信支付
     */
    @ViewInject(R.id.pay_by_wechat_cb)
    private CheckBox pay_by_wechat_cb;
    /**
     * 支付宝支付
     */
    @ViewInject(R.id.pay_by_ali_cb)
    private CheckBox pay_by_ali_cb;
    /**
     * 余额支付
     */
    @ViewInject(R.id.pay_by_balance_cb)
    private CheckBox pay_by_balance_cb;

    private int bottom_type = 0;

    private int order_type = 0;
    /**
     * 传递使者，传递大使
     */
    @ViewInject(R.id.for_member_layout)
    private LinearLayout for_member_layout;
    /**
     * 购买商品
     */
    @ViewInject(R.id.for_order_layout)
    private LinearLayout for_order_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("支付");

        if (0 == order_type) {
            for_member_layout.setVisibility(View.VISIBLE);
            for_order_layout.setVisibility(View.GONE);
        } else {
            for_member_layout.setVisibility(View.GONE);
            for_order_layout.setVisibility(View.VISIBLE);
        }

        selectCheckBoxTop(top_type);
        selectCheckBoxBottom(bottom_type);
    }

    @Override
    @OnClick({R.id.top_lin_layout, R.id.top_cb, R.id.bottom_lin_layout, R.id.bottom_cb, R.id.pay_by_wechat_cb,
            R.id.pay_by_ali_cb, R.id.pay_by_balance_cb})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.top_lin_layout:
            case R.id.top_cb:// 传递使者
                top_type = 0;
                selectCheckBoxTop(top_type);
                break;
            case R.id.bottom_lin_layout:
            case R.id.bottom_cb:// 传递大使
                top_type = 1;
                selectCheckBoxTop(top_type);
                break;
            case R.id.pay_by_wechat_cb:// 微信
                bottom_type = 0;
                selectCheckBoxBottom(bottom_type);
                break;
            case R.id.pay_by_ali_cb:// 支付宝
                bottom_type = 1;
                selectCheckBoxBottom(bottom_type);
                break;
            case R.id.pay_by_balance_cb:// 余额
                bottom_type = 2;
                selectCheckBoxBottom(bottom_type);
                break;
        }
    }

    /**
     * 传递使者，传递大使选中状态
     *
     * @param type 方式
     */
    private void selectCheckBoxTop(int type) {
        top_cb.setChecked(false);
        bottom_cb.setChecked(false);
        if (0 == type) {
            top_cb.setChecked(true);
        } else {
            bottom_cb.setChecked(true);
        }
    }

    /**
     * 支付方式选择
     *
     * @param type 方式
     */
    private void selectCheckBoxBottom(int type) {
        L.e("============", String.valueOf(type));
        pay_by_wechat_cb.setChecked(false);
        pay_by_ali_cb.setChecked(false);
        pay_by_balance_cb.setChecked(false);
        if (0 == type) {
            pay_by_wechat_cb.setChecked(true);
        } else if (1 == type) {
            pay_by_ali_cb.setChecked(true);
        } else {
            pay_by_balance_cb.setChecked(true);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_pay_for_app;
    }

    @Override
    protected void initialized() {
        order_type = getIntent().getIntExtra("order_type", 0);
    }

    @Override
    protected void requestData() {

    }
}
