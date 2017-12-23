package com.txd.hzj.wjlp.txunda_lh;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.payByThirdParty.AliPay;
import com.ants.theantsgo.payByThirdParty.aliPay.AliPayCallBack;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.txunda_lh.http.CarOrder;
import com.txd.hzj.wjlp.txunda_lh.http.HouseOrder;
import com.txd.hzj.wjlp.txunda_lh.http.Pay;

import java.util.Map;

/**
 * by Txunda_LH on 2017/11/28.
 */

public class aty_pay extends BaseAty {
    @ViewInject(R.id.tv_price)
    private TextView tv_price;
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    private Map<String, String> data;

    @ViewInject(R.id.pay_by_balance_cb)
    private CheckBox pay_by_balance_cb;
    @ViewInject(R.id.pay_by_ali_cb)
    private CheckBox pay_by_ali_cb;
    @ViewInject(R.id.pay_by_wechat_cb)
    private CheckBox pay_by_wechat_cb;
    @ViewInject(R.id.cb_jifen)
    private CheckBox cb_jifen;
    private int type = -1;
    private CommonPopupWindow commonPopupWindow;

    private String im_type = "0";
    private int res = 0;
    @ViewInject(R.id.im1)
    private ImageView im1;
    @ViewInject(R.id.im2)
    private ImageView im2;
    @ViewInject(R.id.im3)
    private ImageView im3;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_pay;
    }

    @OnClick({R.id.pay_by_balance_cb, R.id.pay_by_ali_cb, R.id.pay_by_wechat_cb, R.id.cb_jifen, R.id.pay})
    public void
    OnClick(View view) {
        switch (view.getId()) {
            case R.id.pay_by_balance_cb:
                pay_by_ali_cb.setChecked(false);
                pay_by_wechat_cb.setChecked(false);
                cb_jifen.setChecked(false);
                break;
            case R.id.pay_by_ali_cb:
                pay_by_balance_cb.setChecked(false);
                pay_by_wechat_cb.setChecked(false);
                cb_jifen.setChecked(false);
                break;
            case R.id.pay_by_wechat_cb:
                pay_by_balance_cb.setChecked(false);
                pay_by_ali_cb.setChecked(false);
                cb_jifen.setChecked(false);
                break;
            case R.id.cb_jifen:
                pay_by_balance_cb.setChecked(false);
                pay_by_ali_cb.setChecked(false);
                pay_by_wechat_cb.setChecked(false);
                break;
            case R.id.pay:
                if (pay_by_balance_cb.isChecked()) {
                    if (getIntent().getStringExtra("type").equals("1")) {
                        CarOrder.balancePay(data.get("order_id"), im_type, this);
                    } else {
                        HouseOrder.balancePay(data.get("order_id"), im_type, this);
                    }
                    showProgressDialog();
                } else if (cb_jifen.isChecked()) {
                    CarOrder.integralPay(data.get("order_id"), this);
                    showProgressDialog();
                } else if (pay_by_ali_cb.isChecked()) {
                    Pay.getAlipayParam(data.get("order_id"), im_type, String.valueOf(type), this);
                    showProgressDialog();
                }
                break;
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("支付");
        data = JSONUtils.parseKeyAndValueToMap(getIntent().getStringExtra("data"));
        tv_price.setText("¥" + data.get("order_price"));
        type = Integer.parseInt(getIntent().getStringExtra("type")) + 1;
        cb_jifen.setVisibility(data.get("is_integral").equals("1") ? View.VISIBLE : View.GONE);
        String str = "余额支付（¥" + data.get("balance") + "）";
        SpannableString msp = new SpannableString(str);
        msp.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), 4, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置色
        msp.setSpan(new AbsoluteSizeSpan(26), 4, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        pay_by_balance_cb.setText(msp);
        pay_by_balance_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showPop(pay_by_balance_cb, 1);
                } else {
                    im1.setVisibility(View.GONE);
                }

            }
        });
        pay_by_ali_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showPop(pay_by_ali_cb, 2);
                } else {
                    im2.setVisibility(View.GONE);
                }

            }
        });
        pay_by_wechat_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showPop(pay_by_wechat_cb, 3);
                } else {
                    im3.setVisibility(View.GONE);
                }

            }
        });
    }

    public void showPop(View view, final int type) {
        if (data.get("discount").equals("0") && data.get("yellow_discount").equals("0") && data.get("blue_discount").equals("0"))
            return;
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_layout_djq)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        TextView r = (TextView) view.findViewById(R.id.r);
                        TextView y = (TextView) view.findViewById(R.id.y);
                        TextView b = (TextView) view.findViewById(R.id.b);
                        r.setText(data.get("red_desc"));
                        y.setText(data.get("yellow_desc"));
                        b.setText(data.get("blue_desc"));
                        TextView cancel = (TextView) view.findViewById(R.id.cancel);
                        r.setVisibility(data.get("discount").equals("1") ? View.VISIBLE : View.GONE);
                        y.setVisibility(data.get("yellow_discount").equals("1") ? View.VISIBLE : View.GONE);
                        b.setVisibility(data.get("blue_discount").equals("1") ? View.VISIBLE : View.GONE);
                        r.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commonPopupWindow.dismiss();
                                im_type = "1";
                                res = R.mipmap.icon_daijinquan_r;
                                setImage(type);
                            }
                        });
                        y.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commonPopupWindow.dismiss();
                                im_type = "2";
                                res = R.mipmap.icon_daijinquan_y;
                                setImage(type);
                            }
                        });
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commonPopupWindow.dismiss();
                                im_type = "3";
                                res = R.mipmap.icon_daijinquan_b;
                                setImage(type);
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commonPopupWindow.dismiss();
                                res = 0;
                                im_type = "0";
                                setImage(type);
                            }
                        });
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    private void setImage(int from) {
        switch (from) {
            case 1:
                if (res != 0) {
                    im1.setImageResource(res);
                    im1.setVisibility(View.VISIBLE);
                } else {
                    im1.setVisibility(View.GONE);
                }
                im2.setVisibility(View.GONE);
                im3.setVisibility(View.GONE);
                break;
            case 2:
                if (res != 0) {
                    im2.setImageResource(res);
                    im2.setVisibility(View.VISIBLE);
                } else {
                    im2.setVisibility(View.GONE);
                }
                im1.setVisibility(View.GONE);
                im3.setVisibility(View.GONE);
                break;
            case 3:
                if (res != 0) {
                    im3.setImageResource(res);
                    im3.setVisibility(View.VISIBLE);
                } else {
                    im3.setVisibility(View.GONE);
                }
                im1.setVisibility(View.GONE);
                im2.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    protected void requestData() {

    }

    Map<String, String> map;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        if (requestUrl.contains("balancePay") || requestUrl.contains("integralPay")) {
            showToast("支付成功！");
            finish();
        }
        if (requestUrl.contains("getAlipayParam")) {
            showProgressDialog();
            AliPay aliPay = new AliPay(map.get("pay_string"), new AliPayCallBack() {
                @Override
                public void onComplete() {
                    Pay.findPayResult(data.get("order_id"), String.valueOf(type), aty_pay.this);
                }

                @Override
                public void onFailure() {
                    showToast("支付失败！");
                    removeProgressDialog();
                }

                @Override
                public void onProcessing() {

                }
            });
            aliPay.pay();
        }
        if (requestUrl.contains("findPayResult")) {
            showToast("支付成功！");
            removeProgressDialog();
            finish();
        }
    }
}
