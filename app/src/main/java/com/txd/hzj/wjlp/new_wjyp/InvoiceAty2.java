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

import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
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
    aty_invoce2BaseAdapter aty_invoce2BaseAdapter;

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

                String json = getIntent().getStringExtra("json1");
                Intent intent = getIntent();
//                intent.putExtra("data", list.get(position).get("invoice_type"));
//                PreferencesUtils.putString(InvoiceAty2.this,"tax",list.get(position).get("tax"));
//                PreferencesUtils.putString(InvoiceAty2.this,"express_fee",list.get(position).get("express_fee"));

                Invoice1 invoice = new Invoice1();
                invoice.setTax(list.get(position).get("tax"));
                invoice.setExpress_fee(list.get(position).get("express_fee"));
                invoice.setInvoice_type(list.get(position).get("invoice_type"));
                invoice.setText1("");
                invoice.setText2("");
                invoice.setText3("");
                invoice.setText4("");

                intent.putExtra("data1", invoice);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void requestData() {
        String json = getIntent().getStringExtra("data");
        Invoice.invoice(json, this);
        showProgressDialog();
        List<Map<String, String>> map = JSONUtils.parseKeyAndValueToMapList(json);
        goods_id = map.get(0).get("goods_id");
    }

    Map<String, String> map;
    List<Map<String, String>> list = new ArrayList<>();
    List<Boolean> status = new ArrayList<>();

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Log.d("test=====", jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
//        tv_explain.setText(map.get("explain"));
        list = JSONUtils.parseKeyAndValueToMapList(map.get("list"));

        aty_invoce2BaseAdapter = new aty_invoce2BaseAdapter(list, this);
        lv.setAdapter(aty_invoce2BaseAdapter);
        aty_invoce2BaseAdapter.notifyDataSetChanged();
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
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

}
