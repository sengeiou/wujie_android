package com.txd.hzj.wjlp.minetoAty.balance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.cityselect1.ac.activity.SortAdapter;
import com.txd.hzj.wjlp.cityselect1.ac.activity.SortCityActivity;
import com.txd.hzj.wjlp.cityselect1.ac.utils.PinyinComparator;
import com.txd.hzj.wjlp.http.balance.BalancePst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
        list = new ArrayList<>();
        balancePst = new BalancePst(this);
        titlt_conter_tv.setText("银行卡");
        titlt_right_tv.setText("+");
        titlt_right_tv.setTextSize(32);
        titlt_right_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        if (getIntent().getBooleanExtra("isPlatform", false)) { // 选择线上银行卡
            titlt_right_tv.setVisibility(View.GONE);
        } else {
            titlt_right_tv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    @OnClick(R.id.titlt_right_tv)
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.titlt_right_tv:
                startActivity(AddBankCardAty.class, null);
                break;
        }

    }

    @Override
    protected void requestData() {
        if (PreferencesUtils.getString(BankInfoForReChargeAty.this, "key1").equals("1")) {
            download("UserBalance/platformAccount");
        } else if (PreferencesUtils.getString(BankInfoForReChargeAty.this, "key1").equals("0")) {
            download("UserBalance/bankList");
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (PreferencesUtils.getString(BankInfoForReChargeAty.this, "key1").equals("1")) {
            download("UserBalance/platformAccount");
        } else if (PreferencesUtils.getString(BankInfoForReChargeAty.this, "key1").equals("0")) {
            download("UserBalance/bankList");
        }
    }

    private void download(final String str) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("token", Config.getToken());
        new Novate.Builder(this)
                .baseUrl(Config.BASE_URL)
                .addHeader(parameters)
                .build()
                .rxPost(str, parameters, new RxStringCallback() {


                    @Override
                    public void onNext(Object tag, String response) {
//                        Toast.makeText(SortCityActivity.this, response, Toast.LENGTH_SHORT).show();
                        L.e("wang", "=========>>>>>>>>>>>>response:" + response);
                        try {
                            if (str.equals("UserBalance/bankList")) {
                                list1.removeAll(list1);

                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String bank_card_id = jsonObject1.getString("bank_card_id");
                                    String bank_card_code = jsonObject1.getString("bank_card_code");
                                    String open_bank = jsonObject1.getString("open_bank");
                                    String bank_name = jsonObject1.getString("bank_name");
                                    list1.add(new PtEntity(bank_card_id, bank_card_code, bank_name, open_bank, null));
                                    ptBaseAdapter = new PtBaseAdapter(BankInfoForReChargeAty.this, list1);
                                    bank_info_lv.setAdapter(ptBaseAdapter);
                                    ptBaseAdapter.notifyDataSetChanged();

                                }


                            } else if (str.equals("UserBalance/platformAccount")) {
                                list2.removeAll(list2);

                                JSONObject jsonObject = new JSONObject(response);
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
                            }

//
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Object tag, Throwable e) {

                    }

                    @Override
                    public void onCancel(Object tag, Throwable e) {

                    }

                });

    }

}

/**
 * 提现银行卡列表适配器
 */
//    private class BankInfoAdapter extends BaseAdapter {
//
//        private BIVH bivh;
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Map<String, String> getItem(int i) {
//            return list.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            Map<String, String> bank = getItem(i);
//            if (null == view) {
//                view = LayoutInflater.from(BankInfoForReChargeAty.this).inflate(R.layout.item_bank_info_lv, null);
//                bivh = new BIVH();
//                ViewUtils.inject(bivh, view);
//                view.setTag(bivh);
//            } else {
//                bivh = (BIVH) view.getTag();
//            }
//            if(PreferencesUtils.getString(BankInfoForReChargeAty.this,"key1").equals("0")){
//                Log.d(Novate.TAG,"1111111111111");
//                bivh.bank_card_num_tv.setText(bank.get("bank_num"));
//                bivh.bank_card_owner_name.setText(bank.get("bank_name"));
//                bivh.create_card_bank_name_tv.setText(bank.get("open_bank"));
//                PreferencesUtils.putString(BankInfoForReChargeAty.this,"band_id",bank.get("bank_num"));
//            }else if (PreferencesUtils.getString(BankInfoForReChargeAty.this,"key1").equals("1")){
//                Log.d(Novate.TAG,"22222222222222");
//                bivh.bank_card_num_tv.setText(bank.get("bank_card_code"));
//                bivh.bank_card_owner_name.setText(bank.get("name"));
//                bivh.create_card_bank_name_tv.setText(bank.get("open_bank"));
//                PreferencesUtils.putString(BankInfoForReChargeAty.this,"band_code",bank.get("bank_card_code"));
//            }
//
//
//
//            return view;
//        }
//
//        private class BIVH {
//            /**
//             * 银行卡号
//             */
//            @ViewInject(R.id.bank_card_num_tv)
//            private TextView bank_card_num_tv;
//            /**
//             * 持卡人
//             */
//            @ViewInject(R.id.bank_card_owner_name)
//            private TextView bank_card_owner_name;
//            /**
//             * 开户行
//             */
//            @ViewInject(R.id.create_card_bank_name_tv)
//            private TextView create_card_bank_name_tv;
//
//        }
//
//    }





