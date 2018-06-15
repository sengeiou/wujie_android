package com.txd.hzj.wjlp.new_wjyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.commodity.InvoiceBean;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.http.Invoice;
import com.txd.hzj.wjlp.shoppingCart.BuildOrderAty;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 发票选择界面
 */
public class InvoiceAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;

    @ViewInject(R.id.cb1)
    private CheckBox cb1;
    @ViewInject(R.id.cb2)
    private CheckBox cb2;
    @ViewInject(R.id.layout1)
    private LinearLayout linearLayout1;
    @ViewInject(R.id.layout2)
    private LinearLayout linearLayout2;
    @ViewInject(R.id.layout)
    private LinearLayout layout;
    @ViewInject(R.id.recyclerview)
    private RecyclerView recyclerView;
    private int size = 0;
    @ViewInject(R.id.tv_explain)
    private TextView tv_explain;
    private int touch = 0;
    @ViewInject(R.id.layout_type)
    private LinearLayout layout_type;
    private String goods_id = "";
    @ViewInject(R.id.tv_type)
    private TextView tv_type;

    @ViewInject(R.id.tv_tax)
    private TextView tv_tax;
    @ViewInject(R.id.tv_tax_pay)
    private TextView tv_tax_pay;

    @ViewInject(R.id.et_title)
    private EditText et_title;
    @ViewInject(R.id.et_number)
    private EditText et_number;

    // 不需要发票
    @ViewInject(R.id.invoice_no_cb)
    private CheckBox noCb;
    // 需要发票
    @ViewInject(R.id.invoice_need_cb)
    private CheckBox needCb;

    String json = null;
    String text = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("发票");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setText("完成");
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        layout.setVisibility(View.GONE);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_invoice;
    }

    @Override
    protected void initialized() {

        cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cb2.setChecked(false);
                linearLayout1.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.GONE);
            }
        });
        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cb1.setChecked(false);
                linearLayout1.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
            }
        });
        layout_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "发票明细");
                bundle.putString("goods_id", goods_id);
                bundle.putString("invoice_type", list.get(touch).get("t_id"));
                startActivityForResult(TextListAty.class, bundle, 100);
            }
        });
        titlt_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (needCb.isChecked()) {
                    if (invoice1 != null) { // 第二次传入值不为空
//                    invoice1:Invoice1{tax='5', express_fee='5', invoice_type='增值税专用发票', text1='fdkhg', text2='商品明细', text3='', text4='1', text5='null'}

                        // 直接点击完成按钮

                        BuildOrderAty.Bean bean = new BuildOrderAty.Bean();

                        // 获取选择状态
                        if (cb1.isChecked()) { // 如果个人发票选中
                            if (TextUtils.isEmpty(et_title.getText().toString())) {
                                showToast("请输入发票抬头");
                                return;
                            }
                            if (TextUtils.isEmpty(tv_type.getText().toString()) || tv_type.getText().toString().equals("请选择")) {
                                showToast("请选择发票明细");
                                return;
                            }

                            String getInvoiceTypeStr = needCb.getText().toString();
                            String tid = ""; // 初始化为空
                            String invoiceId = ""; // 初始化为空
                            String taxPay = ""; // 初始化为空
                            String expressFee = ""; // 初始化为空
                            // 循环遍历List
                            for (int i = 0; i < list.size(); i++) {
                                Map<String, String> stringStringMap = list.get(i); // 获取List中记录Map
                                if (getInvoiceTypeStr.equals(stringStringMap.get("invoice_type"))) {
                                    // 如果Map记录中的invoice_type和当needCb前显示的字符相同，则赋值tid
                                    tid = stringStringMap.get("t_id");
                                    invoiceId = stringStringMap.get("invoice_id");
                                    taxPay = stringStringMap.get("tax_pay");
                                    expressFee = stringStringMap.get("express_fee");
                                }
                            }
                            bean.setT_id(tid);
                            bean.setRise("1");
                            bean.setRise_name(et_title.getText().toString());
                            bean.setInvoice_detail(tv_type.getText().toString());
                            bean.setInvoice_id(invoiceId);
                            bean.setInvoice_type(getInvoiceTypeStr);
                            bean.setRecognition("");
                            bean.setIs_invoice("1");
                            bean.setTax_pay(taxPay);
                            bean.setExpress_fee(expressFee);
                            invoice1.setText1(et_title.getText().toString());
                            invoice1.setText2(tv_type.getText().toString());
                            invoice1.setText3(et_number.getText().toString());
                            invoice1.setText4(bean.getRise());
                            if (!tv_tax.getText().toString().equals("")) {
                                invoice1.setText6(tv_tax.getText().toString());
                            }
                        } else if (cb2.isChecked()) { // 否则如果公司发票选中
                            if (TextUtils.isEmpty(et_title.getText().toString())) {
                                showToast("请输入发票抬头");
                                return;
                            }
                            if (TextUtils.isEmpty(tv_type.getText().toString())) {
                                showToast("请选择发票明细");
                                return;
                            }
                            if (TextUtils.isEmpty(et_number.getText().toString())) {
                                showToast("请输入纳税人识别号");
                                return;
                            }

                            String getInvoiceTypeStr = needCb.getText().toString();
                            String tid = ""; // 初始化为空
                            String invoiceId = ""; // 初始化为空
                            String taxPay = ""; // 初始化为空
                            String expressFee = ""; // 初始化为空
                            // 循环遍历List
                            for (int i = 0; i < list.size(); i++) {
                                Map<String, String> stringStringMap = list.get(i); // 获取List中记录Map
                                if (getInvoiceTypeStr.equals(stringStringMap.get("invoice_type"))) {
                                    // 如果Map记录中的invoice_type和当needCb前显示的字符相同，则赋值
                                    tid = stringStringMap.get("t_id");
                                    invoiceId = stringStringMap.get("invoice_id");
                                    taxPay = stringStringMap.get("tax_pay");
                                    expressFee = stringStringMap.get("express_fee");
                                }
                            }
                            // 设置回传的值对象
                            bean.setT_id(tid);
                            bean.setRise("2");
                            bean.setRise_name(et_title.getText().toString());
                            bean.setInvoice_detail(tv_type.getText().toString());
                            bean.setInvoice_id(invoiceId);
                            bean.setInvoice_type(getInvoiceTypeStr);
                            bean.setRecognition(et_number.getText().toString());
                            bean.setIs_invoice("1");
                            bean.setTax_pay(taxPay);
                            bean.setExpress_fee(expressFee);
                            invoice1.setText1(et_title.getText().toString());
                            invoice1.setText2(tv_type.getText().toString());
                            invoice1.setText3(et_number.getText().toString());
                            invoice1.setText4(bean.getRise());
                            if (!tv_tax.getText().toString().equals("")) {
                                invoice1.setText6(tv_tax.getText().toString());
                            }
                        }
                        Intent intent = new Intent();
                        intent.putExtra("data", bean);
                        intent.putExtra("data1", invoice1);
                        setResult(RESULT_OK, intent);
                        InvoiceAty.this.finish();
                    } else {
                        // 第一次跳转进界面，invoice1为空
                        BuildOrderAty.Bean bean = new BuildOrderAty.Bean();
                        bean.setT_id(list.get(touch).get("t_id"));
                        bean.setRise("2");
                        bean.setRise_name(et_title.getText().toString());
                        bean.setRise_name(et_title.getText().toString());
                        bean.setInvoice_detail(tv_type.getText().toString());
                        bean.setInvoice_id(list.get(touch).get("invoice_id"));
                        bean.setRecognition(et_number.getText().toString());
                        bean.setTax_pay(list.get(touch).get("tax_pay"));
                        bean.setExpress_fee(list.get(touch).get("express_fee"));
                        bean.setIs_invoice("1");
                        invoice1.setText1(et_title.getText().toString());
                        invoice1.setText2(tv_type.getText().toString());
                        invoice1.setText3(et_number.getText().toString());
                        invoice1.setText4(bean.getRise());
                        if (!tv_tax.getText().toString().equals("")) {
                            invoice1.setText6(tv_tax.getText().toString());
                        }
                        Intent intent = new Intent();
                        intent.putExtra("data", bean);
                        setResult(RESULT_OK, intent);
                        InvoiceAty.this.finish();
                    }
                } else if (noCb.isChecked()) {
                    Intent intent = new Intent();
                    intent.putExtra("data", new BuildOrderAty.Bean());
                    setResult(RESULT_OK, intent);
                    InvoiceAty.this.finish();
                }

            }
        });
    }

    @Override
    protected void requestData() {
        json = getIntent().getStringExtra("json");
        Invoice.invoice(json, this);
        showProgressDialog();
        List<Map<String, String>> map = JSONUtils.parseKeyAndValueToMapList(json);
        goods_id = map.get(0).get("goods_id");

        noCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.GONE);
                et_number.setText("");
                et_title.setText("");
                noCb.setChecked(true); // 点击的时候设置自己始终未选择状态
                needCb.setChecked(false); // 需要发票控件设置为未选择
            }
        });

        needCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("data", json);
                startActivityForResult(InvoiceAty2.class, bundle, 108);
                needCb.setChecked(true); // 点击的时候设置自己始终未选择状态
                noCb.setChecked(false); // 不需要发票 设置为未选中状态
            }
        });

//        invoice1 = getIntent().getParcelableExtra("data1");
        invoice1 = (Invoice1) getIntent().getSerializableExtra("data1");
        String data2Str = getIntent().getStringExtra("data2");
        L.e("wang", "=========>>>>>>>>invoice1:" + invoice1);
        L.e("wang", "=========>>>>>>>>data2Str:" + data2Str);
        if (invoice1 != null) {
            // 如果不为空肯定需要发票，所以将不需要发票选中状态设置为false
            noCb.setChecked(false);
            needCb.setChecked(true);
            layout.setVisibility(View.VISIBLE);
//            tv_tax.setText("税金" + invoice1.getText6());
            tv_tax.setText(invoice1.getText6());
            tv_tax_pay.setText("您需要支付发票快递费" + invoice1.getExpress_fee() + "元");

            et_title.setText(invoice1.getText1());
            tv_type.setText(invoice1.getText2());
            et_number.setText(invoice1.getText3());
            needCb.setText(invoice1.getInvoice_type());
            if (invoice1.getText4().equals("1")) {
                cb1.setChecked(true);
                cb2.setChecked(false);
                linearLayout1.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.GONE);
            } else if (invoice1.getText4().equals("2")) {
                cb1.setChecked(false);
                cb2.setChecked(true);
                linearLayout1.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
            }

        } else {
            layout.setVisibility(View.GONE);
        }

    }

    //    InvoiceBean invoice1;
    Invoice1 invoice1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            noCb.setChecked(true);
            needCb.setChecked(false);
            return;
        }
        if (requestCode == 100) {
            tv_type.setText(data.getStringExtra("list"));
        } else if (requestCode == 108) {
            if (data != null) {
                invoice1 = (Invoice1) data.getSerializableExtra("data1");
//                bt2.setText(invoice1.getInvoice_type());
                needCb.setText(invoice1.getInvoice_type());
                if (!TextUtils.isEmpty(invoice1.getTax()))
                    L.e("wang", "===================>>>>>>>>>>>>税率：" + Double.parseDouble(invoice1.getTax()));
//                L.e("wang","  价格：" + Double.parseDouble(getIntent().getStringExtra("wj_price")));
//                DecimalFormat df = new DecimalFormat(".##");
//                tv_tax.setText("税金" + df.format(Double.parseDouble(invoice1.getTax()) * Double.parseDouble(getIntent().getStringExtra("wj_price")) / 100));
                tv_tax.setText("税金" + invoice1.getTax_pay());
                tv_tax_pay.setText("您需要支付发票快递费" + invoice1.getExpress_fee() + "元");
                layout.setVisibility(View.VISIBLE);
//                L.e("===========",invoice1+"");
            }
        }
    }

    Map<String, String> map;
    List<Map<String, String>> list = new ArrayList<>();
    List<Boolean> status = new ArrayList<>();

    @Override
    public void onComplete(String requestUrl, String jsonStr) {

        L.e("wang", "=============>>>>>>>>>>>" + jsonStr);

        super.onComplete(requestUrl, jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        tv_explain.setText(map.get("explain"));
        list = JSONUtils.parseKeyAndValueToMapList(map.get("list"));
        size = list.size();
        size++;
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                status.add(true);
                continue;
            }
            status.add(false);
        }
        recyclerView.setAdapter(new MyAdapter());
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adp_invoice, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!status.get(position)) {
//                        if (position == 0) {
//                            layout.setVisibility(View.GONE);
//                        } else {
//                            layout.setVisibility(View.VISIBLE);
//                        }
                        if (touch != -1) {
                            status.set(touch, false);
                        }
                        tv_type.setText("");
                        et_title.setText("");
                        et_number.setText("");
                        status.set(position, true);
                        touch = position;
                        notifyDataSetChanged();
                    }
//                    if (position > 0) {
                    tv_tax.setText("税金" + list.get(position - 1).get("tax") + "%");
                    tv_tax_pay.setText("您需要支付发票快递费" + list.get(position - 1).get("express_fee") + "元");

//                    }
                }
            });
            if (status.get(position)) {
                holder.tv.setChecked(true);
                holder.tv.setEnabled(false);
            } else {
                holder.tv.setEnabled(true);
                holder.tv.setChecked(false);
            }
            if (position > 0) {
                holder.tv.setText(list.get(position - 1).get("invoice_type"));
            }
        }

        @Override
        public int getItemCount() {
            return size;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CheckBox tv;

            public ViewHolder(View itemView) {
                super(itemView);
                tv = (CheckBox) itemView.findViewById(R.id.checkbox);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
