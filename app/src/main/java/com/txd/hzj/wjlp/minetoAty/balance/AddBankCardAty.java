package com.txd.hzj.wjlp.minetoAty.balance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：下午 7:49
 * 描述：添加银行卡
 * ===============Txunda===============
 */
public class AddBankCardAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.card_type_tv)
    private TextView card_type_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("添加银行卡");
    }

    @Override
    @OnClick({R.id.card_type_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.card_type_layout:
                Bundle bundle = new Bundle();
                bundle.putString("title", "银行卡类型");
                startActivityForResult(TextListAty.class, bundle, 100);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_add_bank_card;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (RESULT_OK == resultCode) {
            if (100 == requestCode) {
                card_type_tv.setText(data.getStringExtra("card_type"));
            }
        }
    }
}
