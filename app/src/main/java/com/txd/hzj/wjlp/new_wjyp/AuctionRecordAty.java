package com.txd.hzj.wjlp.new_wjyp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.AuctionOrder;

import java.util.List;
import java.util.Map;

public class AuctionRecordAty extends BaseAty {
    private String order_status;

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.tv_state)
    public TextView tv_state;
    @ViewInject(R.id.tv_merchant_name)
    public TextView tv_merchant_name;
    @ViewInject(R.id.name)
    public TextView name;
    @ViewInject(R.id.tv_order_sn)
    public TextView tv_order_sn;
    @ViewInject(R.id.tv_create_time)
    public TextView tv_create_time;
    @ViewInject(R.id.image)
    public ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("订单详情");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_auctionrecord;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {
        AuctionOrder.preDetails(getIntent().getStringExtra("id"), this);
    }

    Map<String, String> data;
    List<Map<String, String>> list;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
        order_status = data.get("order_status");
        list = JSONUtils.parseKeyAndValueToMapList(data.get("list"));
        switch (order_status) {
            case "10":
                tv_state.setText("竞拍中");
                break;
            case "11":
                tv_state.setText("竞拍成功");
                break;
            case "12":
                tv_state.setText("竞拍结束");
                break;
        }
        tv_merchant_name.setText(data.get("merchant_name"));
        tv_order_sn.setText("订单编号：" + data.get("order_sn"));
        tv_create_time.setText("创建时间：" + data.get("create_time"));
        Glide.with(this).load(list.get(0).get("goods_img")).into(image);
        name.setText(list.get(0).get("goods_name"));
    }
}
