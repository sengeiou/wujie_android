package com.txd.hzj.wjlp.txunda_lh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.payByThirdParty.AliPay;
import com.ants.theantsgo.payByThirdParty.aliPay.AliPayCallBack;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.minetoAty.PayForAppAty;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.txunda_lh.http.MemberOrder;
import com.txd.hzj.wjlp.txunda_lh.http.Pay;
import com.txd.hzj.wjlp.wxapi.GetPrepayIdTask;

import java.util.HashMap;
import java.util.Map;


/**
 * by Txunda_LH on 2018/1/31.
 */

public class VipPayAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    @ViewInject(R.id.tv_type)
    private TextView tv_type;
    @ViewInject(R.id.prescription)
    private TextView prescription;
    @ViewInject(R.id.tv_money)
    private TextView tv_money;
    private String data;
    private int num = 1;
    @ViewInject(R.id.tv_num)
    private TextView tv_num;

    private Map<String, String> map;
    @ViewInject(R.id.im_1)
    private ImageView im_1;
    @ViewInject(R.id.im_2)
    private ImageView im_2;
    private double money;

    @ViewInject(R.id.pay_by_balance_cb)
    private CheckBox pay_by_balance_cb;
    @ViewInject(R.id.cb_jfzf)
    private CheckBox cb_jfzf;
    @ViewInject(R.id.pay_by_ali_cb)
    private CheckBox pay_by_ali_cb;
    @ViewInject(R.id.pay_by_wechat_cb)
    private CheckBox pay_by_wechat_cb;

    private boolean is_c = false;

    @ViewInject(R.id.im1)
    private ImageView im1;
    @ViewInject(R.id.im2)
    private ImageView im2;
    @ViewInject(R.id.im3)
    private ImageView im3;
    @ViewInject(R.id.im4)
    private ImageView im4;
    @ViewInject(R.id.im5)
    private ImageView im5;
    @ViewInject(R.id.im6)
    private ImageView im6;
    @ViewInject(R.id.im7)
    private ImageView im7;
    @ViewInject(R.id.im8)
    private ImageView im8;
    @ViewInject(R.id.im9)
    private ImageView im9;

    private CommonPopupWindow commonPopupWindow;

    private int this_num = 0;
    private View this_view;
    String type = "";

    @OnClick({R.id.im_1, R.id.im_2, R.id.pay_by_balance_cb, R.id.cb_jfzf, R.id.pay_by_ali_cb, R.id.pay_by_wechat_cb, R.id.tv_xieyi, R.id.tv_submit})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.im_1:
                if (num == 1) {
                    return;
                }
                num--;
                tv_num.setText(String.valueOf(num));
                tv_money.setText("¥" + money * num);
                is_c = true;
                can();
                break;
            case R.id.im_2:
                num++;
                tv_num.setText(String.valueOf(num));
                tv_money.setText("¥" + money * num);
                is_c = true;
                can();
                break;

            case R.id.pay_by_balance_cb:

                if (is_c) {
                    this_num = 1;
                    this_view = view;
                    MemberOrder.ticket(map.get("rank_id"), String.valueOf(num), this);
                    return;
                }
                setCheck(1);
                showPop(view, 1);
                break;
            case R.id.cb_jfzf:
                setCheck(5);
                break;
            case R.id.pay_by_ali_cb:

                if (is_c) {
                    this_num = 2;
                    this_view = view;
                    MemberOrder.ticket(map.get("rank_id"), String.valueOf(num), this);
                    return;
                }
                setCheck(2);
                showPop(view, 2);
                break;
            case R.id.pay_by_wechat_cb:
                if (is_c) {
                    this_num = 3;
                    this_view = view;
                    MemberOrder.ticket(map.get("rank_id"), String.valueOf(num), this);
                    return;
                }
                setCheck(3);
                showPop(view, 3);
                break;
            case R.id.tv_xieyi:
                Bundle bundle = new Bundle();
                bundle.putInt("from", 5);
                startActivity(NoticeDetailsAty.class, bundle);
                break;
            case R.id.tv_submit:
                if (pay_by_balance_cb.isChecked()) {
                    type = "1";
                }
                if (cb_jfzf.isChecked()) {
                    type = "2";
                }
                if (pay_by_ali_cb.isChecked()) {
                    type = "3";
                }
                if (pay_by_wechat_cb.isChecked()) {
                    type = "4";
                }
                if (TextUtils.isEmpty(type)) {
                    showToast("请选择支付方式！");
                    return;
                }
                MemberOrder.setOrder(map.get("rank_id"), String.valueOf(num), getType(), type, this);
                showProgressDialog();

                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_vippay;
    }

    private boolean is_r, is_y, is_b;//红黄蓝
    private CheckBox r;
    private CheckBox y;
    private CheckBox b;

    public void showPop(View view, final int type) {
        if (ticker_map.get("discount").equals("0") && ticker_map.get("yellow_discount").equals("0") && ticker_map.get("blue_discount").equals("0")) {
            return;
        }
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;

        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_layout_djq)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        LinearLayout layout_cb = (LinearLayout) view.findViewById(R.id.layout_cb);
                        LinearLayout layout_tv = (LinearLayout) view.findViewById(R.id.layout_tv);
                        layout_cb.setVisibility(View.VISIBLE);
                        layout_tv.setVisibility(View.GONE);
                        r = (CheckBox) view.findViewById(R.id.cb_1);
                        y = (CheckBox) view.findViewById(R.id.cb_2);
                        b = (CheckBox) view.findViewById(R.id.cb_3);
                        r.setText(ticker_map.get("red_desc"));
                        y.setText(ticker_map.get("yellow_desc"));
                        b.setText(ticker_map.get("blue_desc"));
                        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
                        r.setVisibility(ticker_map.get("discount").equals("1") ? View.VISIBLE : View.GONE);
                        y.setVisibility(ticker_map.get("yellow_discount").equals("1") ? View.VISIBLE : View.GONE);
                        b.setVisibility(ticker_map.get("blue_discount").equals("1") ? View.VISIBLE : View.GONE);
                        r.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                setCheckPup(1);
                            }
                        });
                        y.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                setCheckPup(2);
                            }
                        });
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                setCheckPup(3);
                            }
                        });


                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commonPopupWindow.dismiss();
                                setImage(type, r.isChecked(), y.isChecked(), b.isChecked());
                            }
                        });
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    private void can() {
        pay_by_balance_cb.setChecked(false);
        cb_jfzf.setChecked(false);
        pay_by_ali_cb.setChecked(false);
        pay_by_wechat_cb.setChecked(false);
        im1.setVisibility(View.GONE);
        im2.setVisibility(View.GONE);
        im3.setVisibility(View.GONE);
        im4.setVisibility(View.GONE);
        im5.setVisibility(View.GONE);
        im6.setVisibility(View.GONE);
        im7.setVisibility(View.GONE);
        im8.setVisibility(View.GONE);
        im9.setVisibility(View.GONE);
        is_r = false;
        is_y = false;
        is_b = false;
    }

    private void setImage(int from, boolean is_r, boolean is_y, boolean is_b) {
        this.is_r = is_r;
        this.is_y = is_y;
        this.is_b = is_b;
        switch (from) {
            case 1:
                im1.setVisibility(is_r ? View.VISIBLE : View.GONE);
                im2.setVisibility(is_y ? View.VISIBLE : View.GONE);
                im3.setVisibility(is_b ? View.VISIBLE : View.GONE);
                im4.setVisibility(View.GONE);
                im5.setVisibility(View.GONE);
                im6.setVisibility(View.GONE);
                im7.setVisibility(View.GONE);
                im8.setVisibility(View.GONE);
                im9.setVisibility(View.GONE);
                break;
            case 2:
                im1.setVisibility(View.GONE);
                im2.setVisibility(View.GONE);
                im3.setVisibility(View.GONE);
                im4.setVisibility(is_r ? View.VISIBLE : View.GONE);
                im5.setVisibility(is_y ? View.VISIBLE : View.GONE);
                im6.setVisibility(is_b ? View.VISIBLE : View.GONE);
                im7.setVisibility(View.GONE);
                im8.setVisibility(View.GONE);
                im9.setVisibility(View.GONE);
                break;
            case 3:
                im1.setVisibility(View.GONE);
                im2.setVisibility(View.GONE);
                im3.setVisibility(View.GONE);
                im4.setVisibility(View.GONE);
                im5.setVisibility(View.GONE);
                im6.setVisibility(View.GONE);
                im7.setVisibility(is_r ? View.VISIBLE : View.GONE);
                im8.setVisibility(is_y ? View.VISIBLE : View.GONE);
                im9.setVisibility(is_b ? View.VISIBLE : View.GONE);
                break;

        }
    }

    private String getType() {
        StringBuffer sb = new StringBuffer();
        if (is_r && is_y && is_b) {
            return "1,2,3";
        }
        if (is_r) {
            sb.append("1,");
        }
        if (is_y) {
            sb.append("2,");
        }
        if (is_b) {
            sb.append("3");
        }
        if (TextUtils.isEmpty(sb.toString())) {
            return "0";
        }
        return sb.toString();
    }

    private void setCheckPup(int s) {
        r.setChecked(false);
        y.setChecked(false);
        b.setChecked(false);
        switch (s) {
            case 1:
                r.setChecked(true);
                break;
            case 2:
                y.setChecked(true);
                break;
            case 3:
                b.setChecked(true);
                break;
        }

    }

    private void setCheck(int s) {
        pay_by_balance_cb.setChecked(false);
        cb_jfzf.setChecked(false);
        pay_by_ali_cb.setChecked(false);
        pay_by_wechat_cb.setChecked(false);
        switch (s) {
            case 1:
                pay_by_balance_cb.setChecked(true);
                break;
            case 5:
                cb_jfzf.setChecked(true);
                break;
            case 2:
                pay_by_ali_cb.setChecked(true);
                break;
            case 3:
                pay_by_wechat_cb.setChecked(true);
                break;
        }
    }

    @Override
    protected void initialized() {
        data = getIntent().getStringExtra("data");
        map = JSONUtils.parseKeyAndValueToMap(data);
        titlt_conter_tv.setText("购买" + map.get("rank_name"));
        tv_type.setText(map.get("rank_name"));
        prescription.setText(map.get("prescription").equals("0") ? "永久" : "按年付费");
        tv_money.setText("¥" + map.get("money"));
        money = Double.parseDouble(map.get("money"));

    }

    @Override
    protected void requestData() {
        MemberOrder.settlement(map.get("rank_id"), this);
        MemberOrder.ticket(map.get("rank_id"), String.valueOf(num), this);
        showProgressDialog();

        wxPayReceiver = new WxPayReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("wjyp.wxPay");
        registerReceiver(wxPayReceiver, intentFilter);
    }

    Map<String, String> date;
    Map<String, String> ticker_map;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("setOrder")) {
            switch (type) {
                case "1":
                case "2":
                    showToast("支付成功！");
                    finish();
                    break;
                case "3":
                    Map<String, String> ali_map = JSONUtils.parseKeyAndValueToMap(jsonStr);
                    ali_map = JSONUtils.parseKeyAndValueToMap(ali_map.get("data"));
                    showProgressDialog();
                    AliPay aliPay = new AliPay(ali_map.get("pay_string"), new AliPayCallBack() {
                        @Override
                        public void onComplete() {
                            showToast("支付成功！");
                            finish();
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
                    break;
                case "4":
                    L.e(jsonStr);
                    Map<String, String> wx_map = JSONUtils.parseKeyAndValueToMap(jsonStr);
                    wx_map = JSONUtils.parseKeyAndValueToMap(wx_map.get("data"));
                    GetPrepayIdTask wxPay = new GetPrepayIdTask(this, wx_map.get("sign"), wx_map.get("appid"),
                            wx_map.get("nonce_str"), wx_map.get("package"), wx_map.get("time_stamp"), wx_map.get("prepay_id"),
                            wx_map.get("mch_id"), "");
                    wxPay.execute();
                    break;
            }
        }
        if (requestUrl.contains("ticket")) {
            ticker_map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            ticker_map = JSONUtils.parseKeyAndValueToMap(ticker_map.get("data"));
            if (is_c) {
                setCheck(this_num);
                showPop(this_view, this_num);
            }
        }
        if (requestUrl.contains("settlement")) {
            date = JSONUtils.parseKeyAndValueToMap(jsonStr);
            date = JSONUtils.parseKeyAndValueToMap(date.get("data"));
            pay_by_balance_cb.setText("余额支付 (¥" + date.get("balance") + ")");
            cb_jfzf.setVisibility(View.GONE);
//            cb_jfzf.setVisibility(map.get("is_integral").equals("1") ? View.VISIBLE : View.GONE);
//            cb_jfzf.setText("积分支付 (" + date.get("integral") + ")");

        }
    }

    private WxPayReceiver wxPayReceiver;

    private class WxPayReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            int errCode = intent.getIntExtra("errCode", 5);
            if (errCode == 0) {
                showToast("支付成功");
                finish();
            } else {
                removeProgressDialog();
                showToast("支付失败");
            }
        }
    }
}
