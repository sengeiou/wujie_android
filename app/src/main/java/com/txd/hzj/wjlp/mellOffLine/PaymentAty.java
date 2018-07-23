package com.txd.hzj.wjlp.mellOffLine;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.view.keyboard.MyKeyboard;

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
        offlinePay_meny_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myKeyboard.attachTo(offlinePay_meny_et, false); // eiditext绑定keyboard，false表示普通数字键盘
            }
        });
        offlinePay_meny_et.setSelection(offlinePay_meny_et.getText().toString().length());
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onOkClick() {
        // 软键盘上确定按钮点击
        Toast.makeText(this, offlinePay_meny_et.getText().toString(), Toast.LENGTH_SHORT).show();
    }

}
