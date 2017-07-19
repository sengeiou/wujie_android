package com.txd.hzj.wjlp.minetoAty.order.fgt;


import android.os.Bundle;
import android.widget.ListView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.Order;
import com.txd.hzj.wjlp.mainFgt.adapter.IndianaRecordAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.MyOrderAdapter;

import java.util.ArrayList;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：上午 11:46
 * 描述：线上商城订单碎片
 * ===============Txunda===============
 */
public class OrderOnLineFgt extends BaseFgt {

    /**
     * 订单分类
     */
    private String title;
    /**
     * 订单类型
     */
    private String type;

    @ViewInject(R.id.order_on_line_lv)
    private ListView order_on_line_lv;
    private MyOrderAdapter adapter;
    private ArrayList<Order> list;

    public OrderOnLineFgt() {
    }

    public static OrderOnLineFgt getFgt(String title, String type) {
        OrderOnLineFgt fgt = new OrderOnLineFgt();
        fgt.type = type;
        fgt.title = title;
        return fgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (title.equals("抢宝记录")) {
            // 0.全部，1.代付款，2.代发货，3.待收货
            IndianaRecordAdapter adapter = new IndianaRecordAdapter(getActivity(), list, Integer.parseInt(type));
            order_on_line_lv.setAdapter(adapter);//显示全部list
        } else {
            // 0.全部，1.代付款，2.代发货，3.待收货，4.待评价
            adapter = new MyOrderAdapter(getActivity(), list, Integer.parseInt(type));
            order_on_line_lv.setAdapter(adapter);//显示全部list
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_order_on_line_fgt;
    }

    @Override
    protected void initialized() {
        initView();
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }

    private void initView() {
        list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Order order1 = new Order(1, "宝马官方旗舰店");
            list.add(order1);
            Order order2 = new Order(1, "奔驰官方旗舰店");
            list.add(order2);
            Order order3 = new Order(2, "兰博官方旗舰店");
            list.add(order3);
            Order order4 = new Order(3, "玛莎官方旗舰店");
            list.add(order4);
            Order order5 = new Order(4, "大众官方旗舰店");
            list.add(order5);
        }
    }

}
