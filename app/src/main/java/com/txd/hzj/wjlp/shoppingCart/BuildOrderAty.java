package com.txd.hzj.wjlp.shoppingCart;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.PostAdapter;
import com.txd.hzj.wjlp.minetoAty.PayForAppAty;
import com.txd.hzj.wjlp.shoppingCart.adapter.GoodsByOrderAdapter;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.util.ArrayList;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/25 0025
 * 时间：上午 9:00
 * 描述：确认订单
 * ===============Txunda===============
 */
public class BuildOrderAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 商品清单
     */
    @ViewInject(R.id.goods_fot_order_lv)
    private ListViewForScrollView goods_fot_order_lv;
    private GoodsByOrderAdapter goodsAdapter;

    /**
     * 线上商城下单
     */
    @ViewInject(R.id.build_order_left_tv)
    private TextView build_order_left_tv;
    @ViewInject(R.id.build_order_left_view)
    private View build_order_left_view;
    /**
     * 附近驿站自提
     */
    @ViewInject(R.id.build_order_right_tv)
    private TextView build_order_right_tv;
    @ViewInject(R.id.build_order_right_view)
    private View build_order_right_view;

    private int order_type = 0;

    /**
     * 附近驿站
     */
    @ViewInject(R.id.near_by_point_layout)
    private LinearLayout near_by_point_layout;

    @ViewInject(R.id.point_near_by_lv)
    private ListViewForScrollView point_near_by_lv;

    private PostAdapter postAdapter;

    /**
     * 所有商品价格(不含邮费)
     */
    @ViewInject(R.id.price_for_all_goods_tv)
    private TextView price_for_all_goods_tv;
    /**
     * 订单最终价格
     */
    @ViewInject(R.id.order_price_at_last_tv)
    private TextView order_price_at_last_tv;
    private ArrayList<String> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("确认订单");

        goods_fot_order_lv.setAdapter(goodsAdapter);
        point_near_by_lv.setAdapter(postAdapter);

        setStyle(order_type);
        ChangeTextViewStyle.getInstance().forOrderPrice2(this, price_for_all_goods_tv, "共1件商品  合计￥14.80");
        ChangeTextViewStyle.getInstance().forOrderPrice2(this, order_price_at_last_tv, "合计：￥14.80");

    }

    @Override
    @OnClick({R.id.build_order_left_layout, R.id.build_order_right_layout, R.id.submit_order_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.build_order_left_layout:// 线上商城
                order_type = 0;
                setStyle(order_type);
                break;
            case R.id.build_order_right_layout:// 附近驿站
                order_type = 1;
                setStyle(order_type);
                break;
            case R.id.submit_order_tv:// 提交订单
                Bundle bundle = new Bundle();
                bundle.putInt("order_type", 1);
                startActivity(PayForAppAty.class, bundle);
                break;
        }
    }

    private void setStyle(int type) {
        build_order_left_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        build_order_left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        build_order_right_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        build_order_right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        if (0 == type) {
            build_order_left_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
            build_order_left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            near_by_point_layout.setVisibility(View.GONE);
        } else {
            build_order_right_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
            build_order_right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            near_by_point_layout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_build_order;
    }

    @Override
    protected void initialized() {
        points = new ArrayList<>();
        goodsAdapter = new GoodsByOrderAdapter(this);
        postAdapter = new PostAdapter(this, points);
    }

    @Override
    protected void requestData() {

    }
}
