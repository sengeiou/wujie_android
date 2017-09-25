package com.txd.hzj.wjlp.minetoAty.balance;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.balance.BalancePst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BankInfoForReChargeAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.bank_info_lv)
    private ListView bank_info_lv;

    private BankInfoAdapter bankInfoAdapter;

    private BalancePst balancePst;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout layout;

    private List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("选择银行卡号");
        bank_info_lv.setEmptyView(layout);
        bank_info_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                // 卡号
                intent.putExtra("card_num", list.get(i).get("bank_card_code"));
                intent.putExtra("bank_card_id", list.get(i).get("bank_card_id"));
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
        list = new ArrayList<>();
        balancePst = new BalancePst(this);
        bankInfoAdapter = new BankInfoAdapter();
    }

    @Override
    protected void requestData() {
        balancePst.bankList();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("bankList")) {
            if (ToolKit.isList(map, "data")) {
                list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                bank_info_lv.setAdapter(bankInfoAdapter);
            }
        }
    }

    /**
     * 提现银行卡列表适配器
     */
    private class BankInfoAdapter extends BaseAdapter {

        private BIVH bivh;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> bank = getItem(i);
            if (null == view) {
                view = LayoutInflater.from(BankInfoForReChargeAty.this).inflate(R.layout.item_bank_info_lv, null);
                bivh = new BIVH();
                ViewUtils.inject(bivh, view);
                view.setTag(bivh);
            } else {
                bivh = (BIVH) view.getTag();
            }

            bivh.bank_card_num_tv.setText(bank.get("bank_card_code"));
            bivh.bank_card_owner_name.setText(bank.get("name"));
            bivh.create_card_bank_name_tv.setText(bank.get("open_bank"));

            return view;
        }

        private class BIVH {
            /**
             * 银行卡号
             */
            @ViewInject(R.id.bank_card_num_tv)
            private TextView bank_card_num_tv;
            /**
             * 持卡人
             */
            @ViewInject(R.id.bank_card_owner_name)
            private TextView bank_card_owner_name;
            /**
             * 开户行
             */
            @ViewInject(R.id.create_card_bank_name_tv)
            private TextView create_card_bank_name_tv;

        }

    }

}
