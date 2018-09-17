package com.txd.hzj.wjlp.new_wjyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tools.ObserTool;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.commodity.InvoiceBean;
import com.txd.hzj.wjlp.bean.commodity.InvoiceDataBean;
import com.txd.hzj.wjlp.http.Invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/11.
 */

public class InvoiceAty2 extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;
    @ViewInject(R.id.lv)
    public ListView lv;
    @ViewInject(R.id.title_be_back_iv)
    ImageView title_be_back_iv;

    private String goods_id = "";
    private int size = 0;
    Invoce2BaseAdapter Invoce2BaseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("发票类型");
        titlt_right_tv.setVisibility(View.INVISIBLE);
        titlt_right_tv.setText("完成");
        title_be_back_iv.setVisibility(View.VISIBLE);
        title_be_back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //                String json = getIntent().getStringExtra("json1");
                Intent intent = getIntent();
                //                intent.putExtra("data", list.get(position).get("invoice_type"));
                //                PreferencesUtils.putString(InvoiceAty2.this,"tax",list.get(position).get("tax"));
                //                PreferencesUtils.putString(InvoiceAty2.this,"express_fee",list.get(position).get("express_fee"));

                InvoiceBean invoiceBean = list.get(position);
                Invoice1 invoice1 = new Invoice1();
                invoice1.setExpress_fee(invoiceBean.getExpress_fee());
                invoice1.setInvoice_type(invoiceBean.getInvoice_type());
                invoice1.setTax(invoiceBean.getTax());
                invoice1.setText1("");
                invoice1.setText2("");
                invoice1.setText3("");
                invoice1.setText4("");
                invoice1.setT_id(invoiceBean.getT_id());
                invoice1.setTax_pay(invoiceBean.getTax_pay());
                intent.putExtra("data1", invoice1);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        //        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void requestData() {
        String json = getIntent().getStringExtra("data");
        String use_integralStr = getIntent().getStringExtra("shop_price");
        Invoice.invoice(json, use_integralStr, this);
        showProgressDialog();
        List<Map<String, String>> map = JSONUtils.parseKeyAndValueToMapList(json);
        goods_id = map.get(0).get("goods_id");
    }

    List<InvoiceBean> list = new ArrayList<>();
    List<Boolean> status = new ArrayList<>();

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Log.d("test=====", jsonStr);
        ObserTool.gainInstance().jsonToBean(jsonStr, InvoiceDataBean.class, new ObserTool.BeanListener() {
            @Override
            public void returnObj(Object t) {
                InvoiceDataBean invoiceDataBean = (InvoiceDataBean) t;
                com.txd.hzj.wjlp.bean.commodity.Invoice invoice = invoiceDataBean.getData();
                list = invoice.getList();
                //        tv_explain.setText(map.get("explain"));
                Invoce2BaseAdapter = new Invoce2BaseAdapter(list, InvoiceAty2.this);
                lv.setAdapter(Invoce2BaseAdapter);
                Invoce2BaseAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_invoice2;
    }

    @Override
    protected void initialized() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

}
