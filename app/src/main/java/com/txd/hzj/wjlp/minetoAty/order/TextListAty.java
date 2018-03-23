package com.txd.hzj.wjlp.minetoAty.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.address.AddressPst;
import com.txd.hzj.wjlp.http.balance.BalancePst;
import com.txd.hzj.wjlp.http.merchant.MerchantPst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.new_wjyp.http.AfterSale;
import com.txd.hzj.wjlp.new_wjyp.http.Invoice;
import com.txd.hzj.wjlp.new_wjyp.http.Recommending;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/20 0020
 * 时间：下午 4:48
 * 描述：售后的各种原因，快递。。。。
 * ===============Txunda===============
 */
public class TextListAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;

    @ViewInject(R.id.all_text_lv)
    private ListView all_text_lv;

    private String title;

    private TextAdapter tAdapter;

    private AddressPst addressPst;

    private List<Map<String, String>> dataList;

    private UserPst userPst;

    private MerchantPst merchantPst;

    private BalancePst balancePst;
    Map<String, String> map;
    List<Map<String, String>> list = new ArrayList<>();
    ArrayList<Integer> list_check = new ArrayList<Integer>();

    private String cate_id = "";
    private String scope = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText(title);
        all_text_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent data = new Intent();
                if (title.equals("售后类型")) {
                    data.putExtra("type", dataList.get(i).get("name"));
                } else if (title.equals("原因")) {
                    data.putExtra("cause", dataList.get(i).get("name"));
                } else if (title.equals("货物状态")) {
                    data.putExtra("status", list.get(i).get("name"));
                } else if (title.equals("选择快递")) {
                    data.putExtra("express", dataList.get(i).get("shipping_name"));
                    data.putExtra("id", dataList.get(i).get("shopping_id"));
                } else if (title.equals("选择经营范围")) {
//                    data.putExtra("scope", dataList.get(i).get("short_name"));
//                    data.putExtra("cate_id", dataList.get(i).get("cate_id"));
                    if (list_check.get(i) == 0) {
                        list_check.set(i, 1);
                    } else {
                        list_check.set(i, 0);
                    }
                    tAdapter.notifyDataSetChanged();
                    return;
                } else if (title.equals("选择街道")) {
                    data.putExtra("street", dataList.get(i).get("street_name"));
                    data.putExtra("street_id", dataList.get(i).get("street_id"));
                } else if (title.equals("举报类型")) {
                    data.putExtra("type", dataList.get(i).get("title"));
                    data.putExtra("report_type_id", dataList.get(i).get("report_type_id"));
                } else if (title.equals("银行卡类型")) {
                    data.putExtra("card_type", dataList.get(i).get("bank_name"));
                    data.putExtra("bank_type_id", dataList.get(i).get("bank_type_id"));
                } else if (title.equals("选择类型")) {
                    data.putExtra("type", dataList.get(i).get("type"));
                    data.putExtra("rec_type_id", dataList.get(i).get("rec_type_id"));
                } else if (title.equals("发票明细")) {
                    data.putExtra("list", dataList.get(i).get("list"));
                }
                setResult(RESULT_OK, data);
                finish();
            }
        });
        titlt_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list_check.size(); i++) {
                    if (list_check.get(i) == 1) {
                        scope += dataList.get(i).get("short_name") + ",";
                        cate_id += dataList.get(i).get("cate_id") + ",";

                    } else {

                    }
                }
                if (TextUtils.isEmpty(scope)) {
                    showToast("至少选择一条！");
                    return;
                }
                Intent data = new Intent();
                data.putExtra("scope", scope);
                data.putIntegerArrayListExtra("number", list_check);
                data.putExtra("cate_id", cate_id);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_text_list;
    }

    @Override
    protected void initialized() {
        title = getIntent().getStringExtra("title");

        addressPst = new AddressPst(this);

        dataList = new ArrayList<>();

        userPst = new UserPst(this);

        merchantPst = new MerchantPst(this);

        balancePst = new BalancePst(this);

    }

    @Override
    protected void requestData() {
        if (title.equals("选择街道")) {
            String area_id = getIntent().getStringExtra("area_id");
            addressPst.getStreet(area_id);
        } else if (title.equals("选择经营范围")) {
            userPst.getRange();
            titlt_right_tv.setVisibility(View.VISIBLE);
            titlt_right_tv.setText("确定");
            titlt_right_tv.setTextColor(Color.parseColor("#F23030"));
            list_check = getIntent().getIntegerArrayListExtra("number");
        } else if (title.equals("举报类型")) {
            merchantPst.reportType();
        } else if (title.equals("银行卡类型")) {
            balancePst.getBankType();
        } else if (title.equals("售后类型")) {
            AfterSale.backApplyType(getIntent().getStringExtra("order_goods_id"), this);
            showProgressDialog();
//            map = new HashMap<>();
//            map.put("name", "我要退款");
//            list.add(map);
//            map = new HashMap<>();
//            map.put("name", "我要退货");
//            list.add(map);
//            tAdapter = new TextAdapter(list);
//            all_text_lv.setAdapter(tAdapter);
        } else if (title.equals("货物状态")) {
            map = new HashMap<>();
            map.put("name", "已收到货");
            list.add(map);
            map = new HashMap<>();
            map.put("name", "未收到货");
            list.add(map);
            tAdapter = new TextAdapter(list);
            all_text_lv.setAdapter(tAdapter);
        } else if (title.equals("原因")) {
            AfterSale.cause(this);
        } else if (title.equals("选择快递")) {
            AfterSale.shipping(this);
            showProgressDialog();
        } else if (title.equals("选择类型")) {
            Recommending.businessType(this);
            showProgressDialog();
        } else if (title.equals("发票明细")) {
            Invoice.type(getIntent().getStringExtra("goods_id"), getIntent().getStringExtra("invoice_type"), this);
            showProgressDialog();
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        L.e("wang", "========>>>>>>>>>requestUrl：" + requestUrl + "  jsonStr:" + jsonStr);
        super.onComplete(requestUrl, jsonStr);
        Map<String, Object> map = GsonUtil.GsonToMaps(jsonStr);
        if (requestUrl.contains("getStreet")) {//街道
            dataList = (List<Map<String, String>>) map.get("data");
            tAdapter = new TextAdapter();
            all_text_lv.setAdapter(tAdapter);
            return;
        }
        if (requestUrl.contains("businessType")) {
            dataList = (List<Map<String, String>>) map.get("data");
            tAdapter = new TextAdapter(dataList);
            all_text_lv.setAdapter(tAdapter);
        }
        if (requestUrl.contains("getRange")) {
            dataList = (List<Map<String, String>>) map.get("data");
            tAdapter = new TextAdapter();
            all_text_lv.setAdapter(tAdapter);
            int num = dataList.size();
            if (ListUtils.isEmpty(list_check)) {
                for (int i = 0; i < num; i++) {
                    list_check.add(0);
                }
            }
            return;
        }
        if (requestUrl.contains("reportType")) {
            dataList = (List<Map<String, String>>) map.get("data");
            L.e(dataList.toString());
            tAdapter = new TextAdapter();
            all_text_lv.setAdapter(tAdapter);
            return;
        }
        if (requestUrl.contains("getBankType")) {
            dataList = (List<Map<String, String>>) map.get("data");
            tAdapter = new TextAdapter();
            all_text_lv.setAdapter(tAdapter);
        }
        if (requestUrl.contains("cause")) {
            dataList = (List<Map<String, String>>) map.get("data");
            tAdapter = new TextAdapter(dataList);
            all_text_lv.setAdapter(tAdapter);
        }
        if (requestUrl.contains("shipping")) {
            dataList = (List<Map<String, String>>) map.get("data");
            tAdapter = new TextAdapter(dataList);
            all_text_lv.setAdapter(tAdapter);
        }
        if (requestUrl.contains("type")) {
            dataList = (List<Map<String, String>>) map.get("data");
            tAdapter = new TextAdapter(dataList);
            all_text_lv.setAdapter(tAdapter);
        }
        if (requestUrl.contains("backApplyType")) {
            map = (Map<String, Object>) map.get("data");
            dataList = (List<Map<String, String>>) map.get("list");
            tAdapter = new TextAdapter(dataList);
            all_text_lv.setAdapter(tAdapter);

        }
    }

    private class TextAdapter extends BaseAdapter {
        List<Map<String, String>> list;
        private TVVh tvvh;

        public TextAdapter() {
            this.list = dataList;
        }

        public TextAdapter(List<Map<String, String>> list) {
            this.list = list;
        }

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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            Map<String, String> map = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(TextListAty.this).inflate(R.layout.item_text_lv_hzj, null);
                tvvh = new TVVh();
                ViewUtils.inject(tvvh, view);
                view.setTag(tvvh);
            } else
                tvvh = (TVVh) view.getTag();

            if (title.equals("售后类型") || title.equals("货物状态") || title.equals("原因")) {
                tvvh.text_context_tv.setText(map.get("name"));
            } else if (title.equals("选择快递")) {
                tvvh.text_context_tv.setText(map.get("shipping_name"));
            } else if (title.equals("选择经营范围")) {
//                tvvh.text_context_tv.setText(map.get("short_name"));
                tvvh.layout.setVisibility(View.GONE);
                tvvh.layout2.setVisibility(View.VISIBLE);
                tvvh.tv.setText(map.get("short_name"));
                if (list_check.get(i) == 1) {
                    tvvh.im.setImageResource(R.drawable.icon_cart_goods_selected);
                } else {
                    tvvh.im.setImageResource(R.drawable.icon_cart_goods_unselect);
                }

            } else if (title.equals("选择类型")) {
                tvvh.text_context_tv.setText(map.get("type"));
            } else if (title.equals("选择街道")) {
                tvvh.text_context_tv.setText(map.get("street_name"));
            } else if (title.equals("发票明细")) {
                tvvh.text_context_tv.setText(map.get("list"));
            } else if (title.equals("举报类型")) {
                tvvh.text_context_tv.setText(map.get("title"));
            } else if (title.equals("银行卡类型")) {
                tvvh.text_context_tv.setText(map.get("bank_name"));
                Glide.with(getApplicationContext()).load(map.get("bank_pic")).into(tvvh.imageview);
                tvvh.imageview.setVisibility(View.VISIBLE);
            }

            return view;
        }

        private class TVVh {
            @ViewInject(R.id.text_context_tv)
            private TextView text_context_tv;
            @ViewInject(R.id.imageview)
            private ImageView imageview;
            @ViewInject(R.id.layout)
            private LinearLayout layout;
            @ViewInject(R.id.layout2)
            private LinearLayout layout2;
            @ViewInject(R.id.tv)
            private TextView tv;
            @ViewInject(R.id.im)
            private ImageView im;
        }

    }

}
