package com.txd.hzj.wjlp.minetoaty.balance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.balance.BalancePst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.http.UserBalance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 线下充值选择银行卡
 */
public class BankInfoForReChargeAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.titlt_right_tv)
    private TextView titlt_right_tv;

    @ViewInject(R.id.bank_info_lv)
    private ListView bank_info_lv;

    //  private BankInfoAdapter bankInfoAdapter;
    HkBaseAdapter hkBaseAdapter;
    PtBaseAdapter ptBaseAdapter;
    private BalancePst balancePst;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout layout;

    private List<Map<String, String>> list;

    ArrayList<PtEntity> list1 = new ArrayList<>();
    ArrayList<HkEntity> list2 = new ArrayList<>();
    private String auth_status; // 个人认证状态 0 未认证 1认证中 2 已认证 3被拒绝
    private boolean isPlatform = true; // 是否是选择平台银行卡

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("选择银行卡号");
//        bank_info_lv.setEmptyView(layout);
        bank_info_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (PreferencesUtils.getString(BankInfoForReChargeAty.this, "key1").equals("1")) {
                    PreferencesUtils.putString(BankInfoForReChargeAty.this, "band_id", list2.get(i).getBank_num());
                    PreferencesUtils.putString(BankInfoForReChargeAty.this, "band_id1", list2.get(i).getId());
                    System.out.println(list2.get(i).getId() + "======测试测试");
                } else if (PreferencesUtils.getString(BankInfoForReChargeAty.this, "key1").equals("0")) {
                    PreferencesUtils.putString(BankInfoForReChargeAty.this, "band_code", list1.get(i).getBank_card_code());
                    PreferencesUtils.putString(BankInfoForReChargeAty.this, "band_code1", list1.get(i).getBank_card_id());
                }

                Intent intent = new Intent();
                // 卡号
//                    intent.putExtra("card_num", list.get(i-1).get("bank_card_code"));
//                    intent.putExtra("bank_card_id", list.get(i-1).get("bank_card_id"));
//                    intent.putExtra("platform_id", list.get(i).get("id"));
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
        // 查询用户信息，获取认证是否是通过状态
        new UserPst(this).userInfo();

        list = new ArrayList<>();
        balancePst = new BalancePst(this);
        titlt_conter_tv.setText("银行卡");
        titlt_right_tv.setText("+");
        titlt_right_tv.setTextSize(32);
        titlt_right_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        if (getIntent().getBooleanExtra("isPlatform", false)) { // 选择平台银行卡
            titlt_right_tv.setVisibility(View.GONE);
            isPlatform = true;
        } else { // 个人银行卡
            titlt_right_tv.setVisibility(View.VISIBLE);
            isPlatform = false;
        }

    }

    @Override
    @OnClick(R.id.titlt_right_tv)
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlt_right_tv:
                if (auth_status != null && auth_status.equals("2")) { // 已认证通过状态
                    startActivity(AddBankCardAty.class, null);
                } else {
                    showToast("未实名认证或认证还未通过");
                }
                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("userInfo")) { // 获取个人信息
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONObject data = jsonObject.getJSONObject("data");
                auth_status = data.getString("auth_status");
            } catch (JSONException e) {
                L.e("===============BankInfoForReChargeAty，userInfo===========" + e.toString());
            }
        }

        if (requestUrl.contains("UserBalance/bankList")) { // 个人银行卡
            try {
                list1.removeAll(list1);
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String bank_card_id = jsonObject1.getString("bank_card_id");
                    String bank_card_code = jsonObject1.getString("bank_card_code");
                    String open_bank = jsonObject1.getString("open_bank");
                    String bank_name = jsonObject1.getString("bank_name");
                    String name = jsonObject1.getString("name");
                    String phone = jsonObject1.getString("phone");
                    String bank_type_id = jsonObject1.getString("bank_type_id");
                    list1.add(new PtEntity(bank_card_id, bank_card_code, bank_name, open_bank, name, phone, bank_type_id));
                    ptBaseAdapter = new PtBaseAdapter(BankInfoForReChargeAty.this, list1, this, isPlatform);
                    bank_info_lv.setAdapter(ptBaseAdapter);
                }
                ptBaseAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                L.e("===============BankInfoForReChargeAty，UserBalance/bankList===========" + e.toString());
            }
        }

        if (requestUrl.contains("UserBalance/platformAccount")) { // 获取平台银行卡列表
            list2.removeAll(list2);
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String id = jsonObject1.getString("id");
                    String bank_num = jsonObject1.getString("bank_num");
                    String open_bank = jsonObject1.getString("open_bank");
                    String bank_name = jsonObject1.getString("bank_name");
                    list2.add(new HkEntity(id, bank_num, open_bank, bank_name));
                    hkBaseAdapter = new HkBaseAdapter(BankInfoForReChargeAty.this, list2);
                    bank_info_lv.setAdapter(hkBaseAdapter);
                    hkBaseAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                L.e("===============BankInfoForReChargeAty，UserBalance/platformAccount===========" + e.toString());
            }
        }

        if (requestUrl.contains("UserBalance/delBank")) {
            showToast("删除银行卡成功");
            getCardList();
        }

    }

    @Override
    protected void requestData() {
        getCardList();
    }

    /**
     * 获取银行卡列表
     */
    private void getCardList() {
        if (PreferencesUtils.getString(BankInfoForReChargeAty.this, "key1").equals("1")) {
            UserBalance.platformAccount(this); // 平台银行卡列表
        } else if (PreferencesUtils.getString(BankInfoForReChargeAty.this, "key1").equals("0")) {
            UserBalance.bankList(this); // 个人银行卡列表
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getCardList();
    }
}




