package com.txd.hzj.wjlp.mellOffLine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.util.StringUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.ShopOffLineBean;
import com.txd.hzj.wjlp.http.offlineStoreInfo.StoreInfoPst;
import com.txd.hzj.wjlp.minetoAty.PayForAppAty;
import com.txd.hzj.wjlp.view.keyboard.MyKeyboard;

import java.text.DecimalFormat;

/**
 * 创建者：voodoo_jie
 * 创建时间：2018/07/20 020 下午 15:19
 * 功能描述：线下店铺支付结果展示页面
 */
public class PaymentAty extends BaseAty implements MyKeyboard.OnOkClick, View.OnClickListener {

    @ViewInject(R.id.offLinePay_head_imgv)
    private ImageView offLinePay_head_imgv;
    @ViewInject(R.id.offLinePay_merchantName_tv)
    private TextView offLinePay_merchantName_tv;
    @ViewInject(R.id.offlinePay_meny_et)
    private EditText offlinePay_meny_et;
    @ViewInject(R.id.offLinePay_goback_imgv)
    private EditText offLinePay_goback_imgv;
    @ViewInject(R.id.offLinePay_closePage_imgv)
    private EditText offLinePay_closePage_imgv;

    private MyKeyboard myKeyboard;
    private ShopOffLineBean shopOffLineBean;
    private StoreInfoPst storeInfoPst;
    //店铺ID
    private String mStage_merchant_id;

    private String order_id = "";

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_offline_pay;
    }

    @Override
    protected void initialized() {
        // 获取到keyboard对象
        myKeyboard = new MyKeyboard(this);
        myKeyboard.setOnOkClick(this); // 确定按钮点击
        myKeyboard.attachTo(offlinePay_meny_et, false); // eiditext绑定keyboard，false表示普通数字键盘
        offlinePay_meny_et.setFocusable(true);
        offlinePay_meny_et.setFocusableInTouchMode(true);
        offlinePay_meny_et.setSelection(offlinePay_meny_et.getText().toString().length());
        offlinePay_meny_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputStr = offlinePay_meny_et.getText().toString().trim();
                int posDot = inputStr.indexOf(".");
                if (posDot < 0) {
                    return;
                }
                if (inputStr.length() - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4);
                }
            }
        });

        // 获取前一页传入的商家id
        mStage_merchant_id = getIntent().getStringExtra("stage_merchant_id");
        if (!StringUtils.isEmpty(mStage_merchant_id)) {
            // 如果传入的商家id不为空 则直接请求接口
            storeInfoPst = new StoreInfoPst(this);
            storeInfoPst.offlineStoreInfo(mStage_merchant_id);
        }
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onOkClick() {
        // 软键盘上确定按钮点击
        String meny = offlinePay_meny_et.getText().toString();
        if (!meny.equals("")) { // 获取输入的字符串，如果字符串不为空
            try { // 将输入的字符串转换为Double类型
                Double menyD = Double.parseDouble(meny);
                if (menyD >= 1.00) { // 如果输入金额正确且大于0
                    DecimalFormat df = new DecimalFormat("0.00"); // 将数值保留两位小数
                    if (shopOffLineBean != null) {
                        // 加载头像
                        Glide.with(this).load(shopOffLineBean.getData().getLogo())
                                .placeholder(R.drawable.ic_default)
                                .error(R.drawable.ic_default)
                                .centerCrop()
                                .into(offLinePay_head_imgv);

                        offLinePay_merchantName_tv.setText(shopOffLineBean.getData().getMerchant_name());

                        if (!TextUtils.isEmpty(mStage_merchant_id)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("type", "100");
                            // 需要传入下一页的数据
                            bundle.putString("order_id",order_id);
                            bundle.putString("money",meny);
                            bundle.putString("merchant_id",mStage_merchant_id);
                            startActivity(PayForAppAty.class, bundle);
                        }

                    } else {
                        showToast("未请求到回传的店铺信息");
                    }
                } else { // 否则输入的金额小于0或等于0
                    Toast.makeText(this, "输入金额必须大余1元", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) { // 类型转换异常，说明有其他字符，提示其自行检查重新输入
                Toast.makeText(this, "输入有误，请检查", Toast.LENGTH_SHORT).show();
            }
        } else { // 否则字符串为空，直接提示
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.offLinePay_goback_imgv, R.id.offLinePay_closePage_imgv})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.offLinePay_goback_imgv:
                finish();
                break;
            case R.id.offLinePay_closePage_imgv:
                backMain(0); // 回到首界面第一个Fragment
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);

        // 获取线下店铺详情
        if (requestUrl.contains("offlineStoreInfo")) {
            Gson gson = new Gson();
            shopOffLineBean = gson.fromJson(jsonStr, ShopOffLineBean.class);
            // 设置头像
            Glide.with(this).load(shopOffLineBean.getData().getLogo())
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .centerCrop()
                    .into(offLinePay_head_imgv);
            // 设置店铺名称
            offLinePay_merchantName_tv.setText(shopOffLineBean.getData().getMerchant_name());
        }

//        if (requestUrl.contains("setOrder")) {
//            JSONObject jsonObject = JSON.parseObject(jsonStr);
//            if ("1".equals(jsonObject.getString("code"))) {
//                JSONObject data = JSON.parseObject(jsonObject.getString("data"));
//                if (data.containsKey("order_id")) {
//                    order_id = data.getString("order_id");
//
//                }
//            }
//        }
    }
}
