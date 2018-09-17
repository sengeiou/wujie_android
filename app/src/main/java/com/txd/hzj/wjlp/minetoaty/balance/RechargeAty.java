package com.txd.hzj.wjlp.minetoaty.balance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.payByThirdParty.AliPay;
import com.ants.theantsgo.payByThirdParty.aliPay.AliPayCallBack;
import com.ants.theantsgo.tool.DateTool;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.Pay;
import com.txd.hzj.wjlp.http.User;
import com.txd.hzj.wjlp.http.balance.BalancePst;
import com.txd.hzj.wjlp.minetoaty.tricket.ParticularsUsedByTricketAty;
import com.txd.hzj.wjlp.new_wjyp.Authentication_aty;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.wxapi.GetPrepayIdTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：下午 1:39
 * 描述：余额充值(16-2-1充值)
 */
public class RechargeAty extends BaseAty {

    private String auth_status = "";
    private String comp_auth_status = "";

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Bundle bb = new Bundle();
            bb.putString("auth_status", auth_status);
            bb.putString("comp_auth_status", comp_auth_status);
            startActivity(Authentication_aty.class, bb);
            return false;
        }
    });

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;
    @ViewInject(R.id.select_card_num_layout1)
    LinearLayout select_card_num_layout1;
    /**
     * 线上充值
     */
    @ViewInject(R.id.re_left_tv)
    private TextView re_left_tv;

    @ViewInject(R.id.re_left_view)
    private View re_left_view;
    /**
     * 线下充值
     */
    @ViewInject(R.id.re_right_tv)
    private TextView re_right_tv;

    @ViewInject(R.id.re_right_view)
    private View re_right_view;

    /**
     * 0：线上充值
     * 1：线下充值
     */
    private int type = 0;

    /**
     * 线上充值
     */
    @ViewInject(R.id.on_line_recharge_layout)
    private LinearLayout on_line_recharge_layout;
    @ViewInject(R.id.et_price)
    private EditText et_price;

    /**
     * 线下充值
     */
    @ViewInject(R.id.off_line_recharge_layout)
    private LinearLayout off_line_recharge_layout;


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

    /**
     * 银行卡号
     */
    @ViewInject(R.id.bank_cart_num_tv)
    private TextView bank_cart_num_tv;


    @ViewInject(R.id.bank_cart_num_tv1)
    TextView bank_cart_num_tv1;
    /**
     * 选择时间
     */
    @ViewInject(R.id.picker_time_tv)
    private TextView picker_time_tv;

    private TimePickerView pvCustomTime;
    /**
     * 银行卡id
     */
    private String bank_card_id = "";
    /**
     * 线下充值金额
     */
    @ViewInject(R.id.off_line_recharge_money_tv)
    private EditText off_line_recharge_money_tv;
    /**
     * 线下充值汇款人
     */
    @ViewInject(R.id.off_line_recharge_name_tv)
    private EditText off_line_recharge_name_tv;
    /**
     * 汇款凭证
     */
    @ViewInject(R.id.off_line_recharge_pic_iv)
    private ImageView off_line_recharge_pic_iv;
    /**
     * 汇款说明
     */
    @ViewInject(R.id.off_line_recharge_desc_ev)
    private EditText off_line_recharge_desc_ev;
    /**
     * 密码
     */
    @ViewInject(R.id.off_line_recharge_pwd_ev)
    private EditText off_line_recharge_pwd_ev;

    @ViewInject(R.id.cb_jfzf)
    private CheckBox cb_jfzf;


    private int picSize = 0;
    private File pic;

    private BalancePst balancePst;
    private WxPayReceiver wxPayReceiver;
    private CommonPopupWindow commonPopupWindow;

    private boolean orderIn; // 订单界面跳转进入
    private String money; // 金额
    private String order_id1; // 订单id

    /**
     * 滚动列表
     * */
    @ViewInject(R.id.balance_scr)
    private NestedScrollView balance_scr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("余额");
        titlt_right_tv.setVisibility(View.GONE);
        titlt_right_tv.setText("明细");
        changeTVAndViewStyle(type);

        pay_by_balance_cb.setVisibility(View.GONE);
        selectCheckBoxBottom(bottom_type);

        cb_jfzf.setVisibility(View.GONE);

        PreferencesUtils.remove(RechargeAty.this, "band_id");
//        PreferencesUtils.remove(RechargeAty.this,"band_id1");
        PreferencesUtils.remove(RechargeAty.this, "band_code");
        balance_scr.smoothScrollTo(0,0);
    }

    /**
     * 选择充值方式
     *
     * @param type 0，线上充值，1.线下充值
     */
    private void changeTVAndViewStyle(int type) {
        re_left_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        re_left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        re_right_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        re_right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        if (0 == type) { // 线上充值
//            et_price.setHint("最低充值金额100元");
            re_left_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            re_left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            on_line_recharge_layout.setVisibility(View.VISIBLE);
            off_line_recharge_layout.setVisibility(View.GONE);
            titlt_right_tv.setVisibility(View.GONE);
        } else { // 线下充值
//            off_line_recharge_money_tv.setHint("最低转账金额100元");
            re_right_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            re_right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            on_line_recharge_layout.setVisibility(View.GONE);
            off_line_recharge_layout.setVisibility(View.VISIBLE);
            initCustomTimePicker();
            titlt_right_tv.setVisibility(View.GONE);
        }

    }

    /**
     * 支付方式选择
     *
     * @param type 方式
     */
    private void selectCheckBoxBottom(int type) {
//        L.e("============", String.valueOf(type));
        pay_by_wechat_cb.setChecked(false);
        pay_by_ali_cb.setChecked(false);
        pay_by_balance_cb.setChecked(false);
        if (0 == type) {
            pay_by_wechat_cb.setChecked(true);
        } else if (1 == type) {
            pay_by_ali_cb.setChecked(true);
        }
    }

    @Override
    @OnClick({R.id.re_left_layout, R.id.re_right_layout, R.id.pay_by_wechat_cb,
            R.id.pay_by_ali_cb, R.id.select_card_num_layout, R.id.picker_time_layout,
            R.id.off_line_recharge_tv, R.id.off_line_recharge_pic_iv, R.id.tv_submit, R.id.titlt_right_tv, R.id.select_card_num_layout1})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.titlt_right_tv:
                if (type == 0) {
                    Intent intent = new Intent();
                    intent.setClass(RechargeAty.this, ParticularsUsedByTricketAty.class);
                    intent.putExtra("from", 6); // 线上充值明细
                    startActivity(intent);
                    finish();
                } else if (type == 1) {
                    Intent intent = new Intent();
                    intent.setClass(RechargeAty.this, ParticularsUsedByTricketAty.class);
                    intent.putExtra("from", 4); // 线下充值明细
                    startActivity(intent);
                    finish();
                }

                break;
            case R.id.tv_submit:
                    if (!TextUtils.isEmpty(et_price.getText().toString())) {
                        try {
                            // 获取输入的金额并转换成float
                            float price = Float.parseFloat(et_price.getText().toString());
                            if (price > 0) { // 如果金额大于（等于）100
                                showPwdPop(v); // 弹出密码输入框
                            } else {
                                showToast("充值金额输入有误！");
                            }
                        } catch (Exception e) { // 捕捉类型转换异常
                            showToast("充值金额输入有误！");
                        }
                    } else { // 没有输入金额
                        showToast("请输入充值金额！");
                    }

                break;
            case R.id.re_left_layout: // 线上充值
                type = 0;
                changeTVAndViewStyle(type);
                break;
            case R.id.re_right_layout: // 线下充值
                type = 1;
                changeTVAndViewStyle(type);
                break;
            case R.id.pay_by_wechat_cb: // 微信
                bottom_type = 0;
                selectCheckBoxBottom(bottom_type);
                break;
            case R.id.pay_by_ali_cb: // 支付宝
                bottom_type = 1;
                selectCheckBoxBottom(bottom_type);
                break;
            case R.id.select_card_num_layout: // 线下支付，选择银行卡号

                User.userInfo(this); // 获取用户信息

                break;
            case R.id.select_card_num_layout1: // 线下支付，选择平台银行卡号
                Bundle bundle = new Bundle();
                bundle.putBoolean("isPlatform", true); // 选择平台银行卡
                PreferencesUtils.putString(RechargeAty.this, "key1", "1");
                startActivityForResult(BankInfoForReChargeAty.class, bundle, 100);
                break;
            case R.id.picker_time_layout:// 线下支付，选择汇款时间
                if (pvCustomTime != null) {
                    pvCustomTime.show(picker_time_tv);
                }
                break;
            case R.id.off_line_recharge_pic_iv:// 汇款凭证(选择图片)
                startActivityForResult(ImageGridActivity.class, null, 101);
                break;
            case R.id.off_line_recharge_tv:// 线下充值

                // 将 2017-09-25 13:10(或其他数据) 转成时间戳，精确到秒
                String act_time = DateTool.date2TimeStamp(picker_time_tv.getText().toString(), "yyy-MM-dd HH:mm");
                String money = off_line_recharge_money_tv.getText().toString().trim();
                String name = off_line_recharge_name_tv.getText().toString().trim();
                String desc = off_line_recharge_desc_ev.getText().toString().trim();
                String pay_pwd = off_line_recharge_pwd_ev.getText().toString();
//
//                L.e("======时间=====", act_time);
//                L.e("======金额=====", money);
//                L.e("======名称=====", name);
//                L.e("======凭证=====", pic.getAbsolutePath());
//                L.e("======说明=====", desc);
//                L.e("======密码=====", pay_pwd);
                L.e("========>>>>>>>>>>" + PreferencesUtils.getString(RechargeAty.this, "band_id1"));
                if (!act_time.isEmpty() && !money.isEmpty() && !name.isEmpty() && !desc.isEmpty() && !pay_pwd.isEmpty()) { // 如果输入数值
//                    try {
//                        float moneyF = Float.parseFloat(money);
//                        if (moneyF >= 100) {
//                download(PreferencesUtils.getString(RechargeAty.this,"band_code1"),act_time,money,name,pic,desc,pay_pwd,PreferencesUtils.getString(RechargeAty.this,"band_id1"));
                    balancePst.underMoney(PreferencesUtils.getString(RechargeAty.this, "band _code1"), act_time, money, name, pic, desc, pay_pwd, PreferencesUtils.getString(RechargeAty.this, "band_id1"));
//                        } else {
//                            showToast("最低转账金额100元");
//                        }
//                    } catch (Exception e) {
//                        showToast("转账金额输入有误！");
//                    }
                } else { // 否则未输入数值
                    showToast("请完善以上信息");
                }
                break;


        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_recharge;
    }

    @Override
    protected void initialized() {
        picSize = ToolKit.dip2px(this, 100);

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载
        imagePicker.setCrop(false);// 不裁剪
        imagePicker.setMultiMode(false);// 单选
        imagePicker.setShowCamera(true);// 显示拍照按钮
        balancePst = new BalancePst(this);

        orderIn = getIntent().getBooleanExtra("orderIn", false); // 是否是订单中心传入的
        if (orderIn) { // 如果是订单中心传入的
            order_id1 = getIntent().getStringExtra("order_id"); // 获取订单id
            money = getIntent().getStringExtra("money"); // 获取金额
            et_price.setText(money); // 设置金额
            // 这两行设置金额输入框的Exittext不可编辑
            et_price.setFocusable(false);
            et_price.setFocusableInTouchMode(false);
        }

    }

    @Override
    protected void requestData() {
        wxPayReceiver = new WxPayReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("wjyp.wxPay");
        registerReceiver(wxPayReceiver, intentFilter);
    }

    private String order_id;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        L.e("wang", requestUrl + "\tjsonStr:" + jsonStr);
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("underMoney")) {
            L.e("==========", jsonStr);
            showRightTip(map.get("message"));
            finish();
        }
        if (requestUrl.contains("findPayResult")) {
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (map.get("status").equals("1")) {
                showRightTip("支付成功！");
                finish();
            }
        }
        if (requestUrl.contains("getHjsp")) {

            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            order_id = map.get("order_id");

            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String code = jsonObject.getString("code");
                if (code.equals("1")) {
                    JSONObject jsonData = jsonObject.getJSONObject("data");
                    L.e("sign:" + jsonData.getString("sign") + "\nappid:" + jsonData.getString("appid") + "\nnonce_str:" +
                            jsonData.getString("nonce_str") + "\npackage:" + jsonData.getString("package") + "\ntime_stamp:" + jsonData.getString("time_stamp") + "\nprepay_id:" + jsonData.getString("prepay_id") +
                            "\nmch_id:" + jsonData.getString("mch_id"));

                    GetPrepayIdTask wxPay = new GetPrepayIdTask(this, jsonData.getString("sign"), jsonData.getString("appid"),
                            jsonData.getString("nonce_str"), jsonData.getString("package"), jsonData.getString("time_stamp"), jsonData.getString("prepay_id"),
                            jsonData.getString("mch_id"), "");
                    wxPay.execute();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (requestUrl.contains("getJsTine")) {
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            GetPrepayIdTask wxPay = new GetPrepayIdTask(this, map.get("sign"), map.get("appid"),
                    map.get("nonce_str"), map.get("package"), map.get("time_stamp"), map.get("prepay_id"),
                    map.get("mch_id"), "");
            wxPay.execute();

        }
        if (requestUrl.contains("getAlipayParam")) {
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            AliPay aliPay = new AliPay(map.get("pay_string"), new AliPayCallBack() {
                @Override
                public void onComplete() {
                    Pay.findPayResult(order_id, "1", RechargeAty.this);
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
        if (requestUrl.contains("upMoney")) {
            L.e("wang", "orderid=========" + jsonStr);
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            order_id = map.get("order_id");
            if (pay_by_ali_cb.isChecked()) {
                Pay.getAlipayParam(order_id, "", "1", this);
            } else if (pay_by_wechat_cb.isChecked()) {
//                Pay.getJsTine(order_id, "", "1", this);

            }
            showProgressDialog();

        }
        if (requestUrl.contains("userInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if (data.get("code").equals("1")) {
                data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
                auth_status = data.get("auth_status");
                comp_auth_status = data.get("comp_auth_status");
                if (auth_status.equals("0") || auth_status.equals("3")) {
                    showToast(auth_status.equals("0") ? "请先进行实名认证" : "您的认证被拒绝，请重新进行认证");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message message = new Message();
                            message.what = 0;
                            message.obj = 0;
                            handler.sendMessage(message);
                        }
                    }).start();
                    return;
                }
                if (auth_status.equals("1")) {
                    showToast("正在实名认证中，请耐心等待");
                    return;
                }
                if (auth_status.equals("2")) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isPlatform", false); // 选择平台银行卡
                    PreferencesUtils.putString(RechargeAty.this, "key1", "0");
                    startActivityForResult(BankInfoForReChargeAty.class, bundle, 100);
                    return;
                }
            }
            return;

        }

        if (requestUrl.contains("verificationPayPwd")) {
            if (map.get("code").equals("1")) {
                if (pay_by_ali_cb.isChecked()) { // 支付宝支付选中
                    balancePst.upMoney(orderIn ? order_id1 : "", et_price.getText().toString(), "2", "");
                } else if (pay_by_wechat_cb.isChecked()) { // 微信支付选中
                    Pay.getHjsp(orderIn ? order_id1 : "", et_price.getText().toString(), RechargeAty.this);
                }
                showProgressDialog();
            }
        }

    }

    private class WxPayReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            int errCode = intent.getIntExtra("errCode", 5);
            if (errCode == 0) {
//
//                showToast("支付成功");
                L.e("wang", "orderid:" + order_id);
                Pay.findPayResult(order_id, "1", RechargeAty.this);
                showProgressDialog();
            } else {
                removeProgressDialog();
                showToast("支付失败");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }
        // 选择银行卡号
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case 100:// 银行卡号
                    bank_cart_num_tv1.setText(PreferencesUtils.getString(RechargeAty.this, "band_id"));
                    bank_cart_num_tv.setText(PreferencesUtils.getString(RechargeAty.this, "band_code"));
//                    String card_num = data.getStringExtra("card_num");
//                    bank_cart_num_tv.setText(card_num);
//                    bank_card_id = data.getStringExtra("bank_card_id");
//                    String platform_id = data.getStringExtra("platform_id");
//                    bank_cart_num_tv1.setText(platform_id);

                    break;

            }
            return;
        }
        // 上传汇款凭证图片
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(
                    ImagePicker.EXTRA_RESULT_ITEMS);
            String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);
            switch (requestCode) {
                case 101:
                    pic = new File(pic_path);
                    Glide.with(this).load(pic).override(picSize, picSize)
                            .centerCrop()
                            .error(R.drawable.ic_default)
                            .placeholder(R.drawable.ic_default)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(off_line_recharge_pic_iv);
                    break;
            }
        }
    }

    private void initCustomTimePicker() {

        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(System.currentTimeMillis());
        // 开始年份
        int start_year = startDate.get(Calendar.YEAR) - 1;
        // 结束年份
        int end_year = start_year + 100;
        // 月份
        int mon = startDate.get(Calendar.MONTH);
        // 日期
        int day = startDate.get(Calendar.DAY_OF_MONTH);

        startDate.set(start_year, mon, day);
        Calendar endDate = Calendar.getInstance();
        endDate.set(end_year, mon, day);
        //时间选择器
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                TextView btn = (TextView) v;
                btn.setText(getTime(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "点", "分", "")

                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(18)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .setCancelColor(ContextCompat.getColor(RechargeAty.this, R.color.colorAccent))
                .setSubmitColor(ContextCompat.getColor(RechargeAty.this, R.color.colorAccent))
                .setTitleText("选择时间")
                .setTitleColor(ContextCompat.getColor(RechargeAty.this, R.color.app_text_color))
                .build();
    }

    /**
     * 设置选中的汇款时间
     *
     * @param date 时间
     * @return String
     */
    private String getTime(Date date) {//可根据需要自行截取数据显示
        // 中国时区格式化时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return format.format(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wxPayReceiver != null) {
            unregisterReceiver(wxPayReceiver);
            wxPayReceiver = null;
        }
    }


    private void download(String s1, String s2, String s3, String s4, File s5, String s6, String s7, String s8) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("token", Config.getToken());
        Map<String, Object> params = new HashMap<String, Object>();
        String json = null;
//       try {
//           JSONObject params = new JSONObject();
        params.put("bank_card_id", s1);
        params.put("act_time", s2);
        params.put("money", s3);
        params.put("name", s4);
        params.put("pic", s5);
        params.put("desc", s6);
        params.put("pay_password", s7);
        params.put("platform_account_id", s8);
//           json=params.toString();
//       }catch (Exception e){
//           e.printStackTrace();
//       }
        new Novate.Builder(this)
                .baseUrl(Config.BASE_URL)
                .addHeader(parameters)
                .addLog(true)
                .build()

                .rxPost("UserBalance/underMoney", params, new RxStringCallback() {
                    @Override
                    public void onNext(Object tag, String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            showToast(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Object tag, Throwable e) {

                    }

                    @Override
                    public void onCancel(Object tag, Throwable e) {

                    }
                });


//
//                .rxPost(str, parameters, new RxStringCallback() {
//
//
//                    @Override
//                    public void onError(Object tag, Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onCancel(Object tag, Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Object tag, String response) {
////                        Toast.makeText(SortCityActivity.this, response, Toast.LENGTH_SHORT).show();

//
//
//
//                });

    }

    public void showPwdPop(View view) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()){ return;}
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
                                com.txd.hzj.wjlp.http.user.User.verificationPayPwd(et_password.getText().toString(), RechargeAty.this);
                                showProgressDialog();
                                commonPopupWindow.dismiss();
                            }
                        });
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
