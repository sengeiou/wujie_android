package com.txd.hzj.wjlp.minetoAty.order.fgt;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.Order;
import com.txd.hzj.wjlp.mainFgt.adapter.AuctionRecordAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：13:34
 * 描述：竞拍碎片
 * ===============Txunda===============
 */

public class AuctionRecordFgt extends BaseFgt {

    private String type;
    private String title;

    @ViewInject(R.id.order_on_line_lv)
    private ListView order_on_line_lv;
    private AuctionRecordAdapter adapter;
    private ArrayList<Order> list;

    public static AuctionRecordFgt getFgt(String title, String type) {
        AuctionRecordFgt fgt = new AuctionRecordFgt();
        fgt.type = type;
        fgt.title = title;
        return fgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 1.竞拍中，2.竞拍成功，3.竞拍结束
    //    order_on_line_lv.setAdapter(adapter);//显示竞拍中list
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order_on_line_fgt;
    }

    @Override
    protected void initialized() {

     //   adapter = new AuctionRecordAdapter(getActivity(), list, Integer.parseInt(type) + 1);
    }

    @Override
    protected void requestData() {

    }

}
