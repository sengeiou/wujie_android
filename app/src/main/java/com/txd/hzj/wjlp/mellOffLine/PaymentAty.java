package com.txd.hzj.wjlp.mellOffLine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.ShopOffLineBean;
import com.txd.hzj.wjlp.view.keyboard.MyKeyboard;

import java.io.Serializable;

/**
 * 创建者：voodoo_jie
 * 创建时间：2018/07/20 020 下午 15:19
 * 功能描述：线下店铺支付结果展示页面
 */
public class PaymentAty extends BaseAty implements MyKeyboard.OnOkClick, View.OnClickListener {

    @ViewInject(R.id.offlinePay_meny_et)
    private EditText offlinePay_meny_et;

    private MyKeyboard myKeyboard;

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

        ShopOffLineBean shopOffLineBean = (ShopOffLineBean) getIntent().getExtras().getSerializable("ShopOffLineBean");
        L.e("qweqwe", shopOffLineBean.toString());
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onOkClick() {
        // 软键盘上确定按钮点击
        String meny = offlinePay_meny_et.getText().toString();
        if (!meny.equals("")) { // 获取输入的字符串，如果字符串不为空
            try { // 将输入的字符串转换为Float类型
                Float menyF = Float.parseFloat(meny);
                if (menyF > 0.00) { // 如果输入金额正确且大于0
                    Toast.makeText(this, offlinePay_meny_et.getText().toString(), Toast.LENGTH_SHORT).show();

                    // TODO 此处请求接口，然后跳转界面到商品付款界面
//                    startActivity();

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

}
