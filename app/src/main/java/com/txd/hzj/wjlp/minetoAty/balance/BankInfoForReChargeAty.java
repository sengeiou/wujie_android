package com.txd.hzj.wjlp.minetoAty.balance;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

public class BankInfoForReChargeAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.bank_info_lv)
    private ListView bank_info_lv;

    private BankInfoAdapter bankInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("选择银行卡号");
        bank_info_lv.setAdapter(bankInfoAdapter);
        bank_info_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                // 卡号
                intent.putExtra("card_num", "6227 0000 6147 1881 700");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_bank_info_for_re_charge;
    }

    @Override
    protected void initialized() {
        bankInfoAdapter = new BankInfoAdapter();
    }

    @Override
    protected void requestData() {

    }

    /**
     * 提现银行卡列表适配器
     */
    private class BankInfoAdapter extends BaseAdapter {

        private BIVH bivh;

        @Override
        public int getCount() {
            return 10;
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
            if (null == view) {
                view = LayoutInflater.from(BankInfoForReChargeAty.this).inflate(R.layout.item_bank_info_lv, null);
                bivh = new BIVH();
                ViewUtils.inject(bivh, view);
                view.setTag(bivh);
            } else {
                bivh = (BIVH) view.getTag();
            }
            return view;
        }

        private class BIVH {

        }

    }

}
