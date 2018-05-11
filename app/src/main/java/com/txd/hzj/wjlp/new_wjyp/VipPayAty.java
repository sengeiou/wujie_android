package com.txd.hzj.wjlp.new_wjyp;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.payByThirdParty.AliPay;
import com.ants.theantsgo.payByThirdParty.aliPay.AliPayCallBack;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.User;
import com.txd.hzj.wjlp.mellOnLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.minetoAty.order.VipCardAty;
import com.txd.hzj.wjlp.minetoAty.setting.EditPayPasswordAty;
import com.txd.hzj.wjlp.new_wjyp.http.BalancePay;
import com.txd.hzj.wjlp.new_wjyp.http.IntegralPay;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.new_wjyp.http.MemberOrder;
import com.txd.hzj.wjlp.tool.MessageEvent;
import com.txd.hzj.wjlp.wxapi.GetPrepayIdTask;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 购买会员卡支付界面
 */
public class VipPayAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    @ViewInject(R.id.tv_type)
    private TextView tv_type;
    @ViewInject(R.id.prescription)
    private TextView tv_prescription;
    @ViewInject(R.id.tv_money)
    private TextView tv_money;
    private String data123123;
    private int num = 1;
    @ViewInject(R.id.tv_num)
    private TextView tv_num;

    //    private Map<String, String> map;
    @ViewInject(R.id.im_1)
    private ImageView im_1;
    @ViewInject(R.id.im_2)
    private ImageView im_2;
    private double money; // 总价格

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
    String order_id = "";
    private String is_pay_password = "0";//是否有支付密码
    private String sale_status;
    private String rank_name;
    private String prescription;
    private String big_gift;
    private String score_status;
    private String abs_url;
    private String member_coding;

    @OnClick({R.id.im_1, R.id.im_2, R.id.pay_by_balance_cb, R.id.cb_jfzf, R.id.pay_by_ali_cb, R.id.pay_by_wechat_cb, R.id.tv_xieyi, R.id.tv_submit})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.im_1:
                if (num == 1) {
                    return;
                }
                num--; // 设置数量减1
                tv_num.setText(String.valueOf(num)); // 设置显示数量
                tv_money.setText("¥" + money * num); // 设置总金额
                is_c = false; //
                can(); //
                break;
            case R.id.im_2:
                num++;
                tv_num.setText(String.valueOf(num));
                tv_money.setText("¥" + money * num);
                is_c = false;
                can();
                break;

            case R.id.pay_by_balance_cb: // 余额支付
                setCheck(1);
                if (is_c) {
                    this_num = 1;
                    this_view = view;
                    MemberOrder.ticket(member_coding, String.valueOf(num), this);
                    return;
                }
                showPop(view, 1);
                break;
            case R.id.cb_jfzf:
                setCheck(5);
                break;
            case R.id.pay_by_ali_cb:
                setCheck(2);
                if (is_c) {
                    this_num = 2;
                    this_view = view;
                    MemberOrder.ticket(member_coding, String.valueOf(num), this);
                    return;
                }
                showPop(view, 2);
                break;
            case R.id.pay_by_wechat_cb:
                setCheck(3);
                if (is_c) {
                    this_num = 3;
                    this_view = view;
                    MemberOrder.ticket(member_coding, String.valueOf(num), this);
                    return;
                }
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

                // 余额支付
                User.settings(this);
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
                                setImage(type, r.isChecked(), y.isChecked(), b.isChecked());
                                commonPopupWindow.dismiss();
                            }
                        });
                        y.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setCheckPup(2);
                                setImage(type, r.isChecked(), y.isChecked(), b.isChecked());
                                commonPopupWindow.dismiss();
                            }
                        });
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setCheckPup(3);
                                setImage(type, r.isChecked(), y.isChecked(), b.isChecked());
                                commonPopupWindow.dismiss();
                            }
                        });


                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pay_by_balance_cb.setChecked(false);
                                cb_jfzf.setChecked(false);
                                pay_by_ali_cb.setChecked(false);
                                pay_by_wechat_cb.setChecked(false);
                                setImage(type, r.isChecked(), y.isChecked(), b.isChecked());
                                commonPopupWindow.dismiss();
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

        L.e("wang", "================>>>>>>>>>>>>>>>>> from = " + from);

        this.is_r = is_r;
        this.is_y = is_y;
        this.is_b = is_b;

        // TODO 减扣代金券后应付金额显示设置
        if (is_r) {
            L.e("wang", "===>>>>>>>>red_desc:" + ticker_map.get("red_desc"));
            BigDecimal bd = new BigDecimal(money - Double.parseDouble(ticker_map.get("discount_price")));
            tv_money.setText("¥" + bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (is_y) {
            L.e("wang", "===>>>>>>>>yellow_desc:" + ticker_map.get("yellow_desc"));
            BigDecimal bd = new BigDecimal(money - Double.parseDouble(ticker_map.get("yellow_price")));
            tv_money.setText("¥" + bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (is_b) {
            L.e("wang", "===>>>>>>>>blue_desc:" + ticker_map.get("blue_desc"));
            BigDecimal bd = new BigDecimal(money - Double.parseDouble(ticker_map.get("blue_price")));
            tv_money.setText("¥" + bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if (!is_r && !is_y && !is_b) {
            tv_money.setText("¥" + (money - 0));
        }

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
//        data = getIntent().getStringExtra("data");
        order_id = getIntent().getStringExtra("order_id");
        sale_status = getIntent().getStringExtra("sale_status");
        rank_name = getIntent().getStringExtra("rank_name");
        money = Double.parseDouble(getIntent().getStringExtra("money"));
        prescription = getIntent().getStringExtra("prescription");
        big_gift = getIntent().getStringExtra("big_gift");
        score_status = getIntent().getStringExtra("score_status");
        abs_url = getIntent().getStringExtra("abs_url");
        member_coding = getIntent().getStringExtra("member_coding");
//                    bundle.putString("data", data);

//        map = JSONUtils.parseKeyAndValueToMap(data);
        titlt_conter_tv.setText("购买" + rank_name);
        tv_type.setText(rank_name);
        tv_prescription.setText(prescription.equals("0") ? "永久" : "按年付费");
        if (!TextUtils.isEmpty(money + "")) {
            tv_money.setText("¥" + money);
            money = Double.parseDouble(money + "");
        } else {
            tv_money.setText("¥" + money);
            money = Double.parseDouble(money + "");
        }

    }

    @Override
    protected void requestData() {
        MemberOrder.settlement(member_coding, this);
        MemberOrder.ticket(member_coding, String.valueOf(num), this);
        showProgressDialog();

        wxPayReceiver = new WxPayReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("wjyp.wxPay");
        registerReceiver(wxPayReceiver, intentFilter);
    }

    Map<String, String> date;
    Map<String, String> ticker_map;

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        L.e("cccc" + error.get("message"));
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("setOrder")) {
            switch (type) {
                case "1":
                case "2":
                    showToast("支付成功！");
                    startActivity(VipCardAty.class, null);
                    EventBus.getDefault().post(new MessageEvent("更新会员卡列表"));
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
                            startActivity(VipCardAty.class, null);
                            EventBus.getDefault().post(new MessageEvent("更新会员卡列表"));
                            finish();
                        }

                        @Override
                        public void onFailure() {
                            showToast("支付失败！");
                            removeProgressDialog();
                            startActivity(VipCardAty.class, null);
                            EventBus.getDefault().post(new MessageEvent("更新会员卡列表"));
                            finish();
                        }

                        @Override
                        public void onProcessing() {

                        }
                    });
                    aliPay.pay();
                    break;
                case "4":
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
            if (ticker_map.get("discount").equals("0") && ticker_map.get("yellow_discount").equals("0") && ticker_map.get("blue_discount").equals("0")) {
                is_c = true;
            }
//            if (is_c) {
//                setCheck(this_num);
//                showPop(this_view, this_num);
//            }
        }
        if (requestUrl.contains("settlement")) {
            date = JSONUtils.parseKeyAndValueToMap(jsonStr);
            date = JSONUtils.parseKeyAndValueToMap(date.get("data"));
            pay_by_balance_cb.setText("余额支付 (¥" + date.get("balance") + ")");
            cb_jfzf.setVisibility(View.GONE);
            cb_jfzf.setVisibility(date.get("is_integral").equals("1") ? View.VISIBLE : View.GONE);
            cb_jfzf.setText("积分支付 (" + date.get("integral") + ")");

        }
        if (requestUrl.contains("verificationPayPwd")) {
            commonPopupWindow.dismiss();
            Map<String, String> maps = JSONUtils.parseKeyAndValueToMap(jsonStr);
            maps = JSONUtils.parseKeyAndValueToMap(maps.get("data"));
            if (maps.get("status").equals("1")) {
//                if (pay_by_balance_cb.isChecked()) {
                // 验证成功
                // 余额支付
                MemberOrder.setOrder(member_coding, String.valueOf(num), getType(), type, order_id, this);
                showProgressDialog();
//                }
            } else {
                showToast("请设置支付密码");
                Bundle bundle = new Bundle();
                bundle.putString("is_pay_password", "0");
                bundle.putString("phone", "");
                startActivity(EditPayPasswordAty.class, bundle);
            }

        }
        if (requestUrl.contains("setting")) {
            Map<String, String> maps = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if (maps.get("code").equals("1")) {
                maps = JSONUtils.parseKeyAndValueToMap(maps.get("data"));
                is_pay_password = maps.get("is_pay_password");
                if (is_pay_password.equals("0")) {
                    showToast("请先设置支付密码！");
                    Bundle bundles = new Bundle();
                    bundles.putString("is_pay_password", "0");
                    bundles.putString("phone", "");
                    startActivity(EditPayPasswordAty.class, bundles);
                    return;
                }
                showPwdPop(titlt_conter_tv);
            }
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
                startActivity(VipCardAty.class, null);
                EventBus.getDefault().post(new MessageEvent("更新会员卡列表"));
                finish();
            } else {
                removeProgressDialog();
                showToast("支付失败");
                startActivity(VipCardAty.class, null);
                EventBus.getDefault().post(new MessageEvent("更新会员卡列表"));
                finish();
            }
        }
    }

    /**
     * 输入支付密码
     *
     * @param view
     */
    public void showPwdPop(View view) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_pwd)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 4)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        final EditText et_password = view.findViewById(R.id.et_password);
                        TextView submit = view.findViewById(R.id.submit);
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TextUtils.isEmpty(et_password.getText().toString())) {
                                    showToast("请输入支付密码");
                                    return;
                                }
                                User.verificationPayPwd(et_password.getText().toString(), VipPayAty.this);
                                showProgressDialog();
                            }
                        });
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
