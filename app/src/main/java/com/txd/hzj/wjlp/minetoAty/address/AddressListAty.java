package com.txd.hzj.wjlp.minetoAty.address;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.address.adapter.AddressAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/20 0020
 * 时间：下午 7:55
 * 描述：收货地址
 * ===============Txunda===============
 */
public class AddressListAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.address_lv)
    private ListView address_lv;

    private List<String> addresses;
    private AddressAdapter addressAdapter;

    /**
     * 来源
     * 1.个人中心
     * 2.
     */
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("收货地址");
        address_lv.setAdapter(addressAdapter);
        address_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }

    @Override
    @OnClick(R.id.add_address_tv)
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_address_tv:// 新增地址
                startActivity(AddNewAddressAty.class, null);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_address_list;
    }

    @Override
    protected void initialized() {
        type = getIntent().getIntExtra("type", 1);
        addresses = new ArrayList<>();
        addressAdapter = new AddressAdapter(this, addresses);
    }

    @Override
    protected void requestData() {

    }
}
