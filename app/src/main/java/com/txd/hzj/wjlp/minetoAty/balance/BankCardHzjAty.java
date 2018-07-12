package com.txd.hzj.wjlp.minetoAty.balance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.balance.BalancePst;
import com.txd.hzj.wjlp.minetoAty.dialog.DeteleBankDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：下午 5:19
 * 描述：银行卡
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

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    private BalancePst balancePst;
    private List<Map<String, String>> bankList;

    private int size = 0;
    private int deletePosion = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("银行卡");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setText("+");
        titlt_right_tv.setTextSize(32);
        titlt_right_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        bank_card_lv.setEmptyView(no_data_layout);

        bank_card_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent data = new Intent();
                data.putExtra("name", bankList.get(i).get("bank_name"));
                data.putExtra("num", bankList.get(i).get("bank_card_code"));
                data.putExtra("bank_card_id", bankList.get(i).get("bank_card_id"));
                setResult(RESULT_OK, data);
                finish();
            }
        });
        bank_card_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                deteleBankDialog = new DeteleBankDialog(BankCardHzjAty.this, new DeteleBankDialog.ClickListener() {
                    @Override
                    public void onClick(View view) {
                        deletePosion = i;
                        balancePst.delBank(bankList.get(i).get("bank_card_id"));
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
        balancePst = new BalancePst(this);
        bankList = new ArrayList<>();
        size = ToolKit.dip2px(this, 70);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        balancePst.bankList();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("bankList")) {
            if (ToolKit.isList(map, "data")) {
                bankList = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                bank_card_lv.setAdapter(bankCardAdapter);
            }
            return;
        }
        if (requestUrl.contains("delBank")) {
            if (deletePosion >= 0 && deletePosion < bankList.size()) {
                showRightTip("删除成功");
                bankList.remove(deletePosion);
                bankCardAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 银行卡列表适配器
     */
    private class BankCardAdapter extends BaseAdapter {

        private BCVH bcvh;

        @Override
        public int getCount() {
            return bankList.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return bankList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> bank = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(BankCardHzjAty.this).inflate(R.layout.item_bank_card_lv, null);
                bcvh = new BCVH();
                ViewUtils.inject(bcvh, view);
                view.setTag(bcvh);
            } else {
                bcvh = (BCVH) view.getTag();
            }

            Glide.with(BankCardHzjAty.this).load(bank.get("bank_pic"))
                    .override(size, size)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(bcvh.card_logo_iv);

            String bankCode = bank.get("bank_card_code");

            if (bankCode.length() >= 16) {
                bankCode = bankCode.replaceAll("(\\d{4})\\d{8,11}(\\w{4})", "$1***********$2");
            }
            bcvh.card_info_tv.setText(bank.get("bank_name") + "\n" + bankCode);

            return view;
        }

        private class BCVH {

            @ViewInject(R.id.card_logo_iv)
            private ImageView card_logo_iv;

            @ViewInject(R.id.card_info_tv)
            private TextView card_info_tv;

        }

    }

}
