package com.txd.hzj.wjlp.minetoAty.balance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.dialog.DeteleBankDialog;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：下午 5:19
 * 描述：银行卡
 * ===============Txunda===============
 */
public class BankCardHzjAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;

    /**
     * 银行卡列表
     */
    @ViewInject(R.id.bank_card_lv)
    private ListView bank_card_lv;

    private BankCardAdapter bankCardAdapter;

    private DeteleBankDialog deteleBankDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("银行卡");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setText("+");
        titlt_right_tv.setTextSize(32);
        titlt_right_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        bank_card_lv.setAdapter(bankCardAdapter);
        bank_card_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent data = new Intent();
                data.putExtra("name", "中国建设银行");
                data.putExtra("num", "6227 0000 6147 1881 701");
                setResult(RESULT_OK, data);
                finish();
            }
        });
        bank_card_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                deteleBankDialog = new DeteleBankDialog(BankCardHzjAty.this, new DeteleBankDialog.ClickListener() {
                    @Override
                    public void onClick(View view) {
                        deteleBankDialog.dismiss();
                    }
                });
                deteleBankDialog.show();
                return true;
            }
        });
    }

    @Override
    @OnClick({R.id.titlt_right_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlt_right_tv:// 添加银行卡
                startActivity(AddBankCardAty.class, null);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_bank_card_hzj;
    }

    @Override
    protected void initialized() {
        bankCardAdapter = new BankCardAdapter();
    }

    @Override
    protected void requestData() {

    }

    private class BankCardAdapter extends BaseAdapter {

        private BCVH bcvh;

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(BankCardHzjAty.this).inflate(R.layout.item_bank_card_lv, null);
                bcvh = new BCVH();
                ViewUtils.inject(bcvh, view);
                view.setTag(bcvh);
            } else {
                bcvh = (BCVH) view.getTag();
            }
            return view;
        }

        private class BCVH {

        }

    }

}
