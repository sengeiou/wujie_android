package com.txd.hzj.wjlp.mellOffLine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.ShopOffLineBean;
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

        shopOffLineBean = (ShopOffLineBean) getIntent().getExtras().getSerializable("ShopOffLineBean");
        if (shopOffLineBean == null) {
            L.e("传入的shopOffLineBean为空");
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
                if (menyD > 0.00) { // 如果输入金额正确且大于0
                    DecimalFormat df = new DecimalFormat("0.00"); // 将数值保留两位小数

                    if (shopOffLineBean == null) {
                        // 加载头像
                        Glide.with(this).load(shopOffLineBean.getData().getLogo())
                                .placeholder(R.drawable.ic_default)
                                .error(R.drawable.ic_default)
                                .centerCrop()
                                .into(offLinePay_head_imgv);

                        offLinePay_merchantName_tv.setText(shopOffLineBean.getData().getMerchant_name());

                        Bundle bundle = new Bundle();
                        bundle.putString("type", "100");
                        // 需要传入下一页的数据
//                        type = getString("type");
//                        goods_id = getString("goods_id");
//                        num = getString("num");
//                        //  ordertype = getString("order_type");
//                        product_id = getString("product_id");
//                        inte_id = getString("inte_id");
//                        order_type = getString("order_type");
//                        address_id = getString("address_id");
//                        shop_name = getString("shop_name");
//                        cart_id = getString("cart_id");
//                        order_id = getString("order_id");
//                        group_buy_id = getString("group_buy_id");
//                        freight = getString("freight");
//                        freight_type = getString("freight_type");
                        startActivity(PayForAppAty.class, bundle);
                    } else {
                        showToast("为传入商家信息，请返回重新尝试");
                    }

                } else { // 否则输入的金额小于0或等于0
                    Toast.makeText(this, "输入有效金额", Toast.LENGTH_SHORT).show();
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
}
