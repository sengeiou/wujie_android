package com.txd.hzj.wjlp.minetoAty.balance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.balance.BalancePst;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;

import java.util.Map;

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

    private BalancePst balancePst;

    @ViewInject(R.id.card_name_ev)
    private EditText card_name_ev;
    @ViewInject(R.id.open_card_ev)
    private EditText open_card_ev;
    @ViewInject(R.id.card_num_ev)
    private EditText card_num_ev;
    @ViewInject(R.id.phone_ev)
    private EditText phone_ev;
    private String bank_type_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("添加银行卡");
    }

    @Override
    @OnClick({R.id.card_type_layout, R.id.add_bank_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.card_type_layout:
                Bundle bundle = new Bundle();
                bundle.putString("title", "银行卡类型");
                startActivityForResult(TextListAty.class, bundle, 100);
                break;
            case R.id.add_bank_tv:// 添加银行卡

                String name = card_name_ev.getText().toString();
                String open_bank = open_card_ev.getText().toString();
                String card_number = card_num_ev.getText().toString();
                String phone = phone_ev.getText().toString();

                balancePst.addBank(name, bank_type_id, open_bank, card_number, phone);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_add_bank_card;
    }

    @Override
    protected void initialized() {
        balancePst = new BalancePst(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("addBank")) {
            showRightTip("添加成功");
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (RESULT_OK == resultCode) {
            if (100 == requestCode) {
                card_type_tv.setText(data.getStringExtra("card_type"));
                bank_type_id = data.getStringExtra("bank_type_id");

                open_card_ev.setText(""); // 清空开户行
                card_num_ev.setText(""); // 清空银行卡号
            }
        }
    }
}
