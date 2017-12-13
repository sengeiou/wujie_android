package com.txd.hzj.wjlp.shoppingCart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.baidu.mapapi.map.Text;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.PostAdapter;
import com.txd.hzj.wjlp.minetoAty.PayForAppAty;
import com.txd.hzj.wjlp.minetoAty.address.AddressListAty;
import com.txd.hzj.wjlp.shoppingCart.adapter.GoodsByOrderAdapter;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.txunda_lh.http.GroupBuyOrder;
import com.txd.hzj.wjlp.txunda_lh.http.IntegralOrder;
import com.txd.hzj.wjlp.txunda_lh.http.Order;
import com.txd.hzj.wjlp.txunda_lh.http.PreOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

//    @ViewInject(R.id.point_near_by_lv)
//    private ListViewForScrollView point_near_by_lv;

//    private PostAdapter postAdapter;

    /**
     * 所有商品价格(不含邮费)
     */
    @ViewInject(R.id.price_for_all_goods_tv)
    private TextView price_for_all_goods_tv;
    /**
     * 订单最终价格
     */
//    @ViewInject(R.id.order_price_at_last_tv)
//    private TextView order_price_at_last_tv;
    private String mid = "";
    private String cart_id = "";
    private int p = 1;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_tel)
    private TextView tv_tel;
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    @ViewInject(R.id.tv_merchant_name)
    private TextView tv_merchant_name;
    @ViewInject(R.id.tv_c_ads)
    private TextView tv_c_ads;
    @ViewInject(R.id.layout_choose_address)
    private LinearLayout layout_choose_address;
    private String address_id;
    @ViewInject(R.id.order_price_at_last_tv)
    private TextView order_price_at_last_tv;
    @ViewInject(R.id.tv_sum_discount)
    private TextView tv_sum_discount;
    String goods_id, num, ordertype, product_id;
    private String type;
    private String group_buy_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("确认订单");
//        point_near_by_lv.setAdapter(postAdapter);
        setStyle(order_type);
        ChangeTextViewStyle.getInstance().forOrderPrice2(this, price_for_all_goods_tv, "共1件商品  合计￥14.80");
//        ChangeTextViewStyle.getInstance().forOrderPrice2(this, order_price_at_last_tv, "合计：￥14.80");
    }

    @Override
    @OnClick({R.id.build_order_left_layout, R.id.build_order_right_layout, R.id.submit_order_tv, R.id.layout_choose_address, R.id.tv_c_ads})
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
                bundle.putString("type", type);
                bundle.putString("order_type", type.equals("1") ? "0" : "1");
                bundle.putString("num", num);
                bundle.putString("group_buy_id", group_buy_id);
                bundle.putString("goods_id", goods_id);
                bundle.putString("product_id", product_id);
                bundle.putString("address_id", address_id);
                bundle.putString("shop_name", tv_merchant_name.getText().toString());
                bundle.putString("cart_id", cart_id);
                startActivity(PayForAppAty.class, bundle);
                finish();
                break;
            case R.id.layout_choose_address:
                bundle = new Bundle();
                bundle.putInt("type", 2);
                startActivityForResult(AddressListAty.class, bundle, 8888);
                break;
            case R.id.tv_c_ads:
                bundle = new Bundle();
                bundle.putInt("type", 2);
                startActivityForResult(AddressListAty.class, bundle, 8888);
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
//        postAdapter = new PostAdapter(this, points);
    }

    @Override
    protected void requestData() {
        group_buy_id = getIntent().getStringExtra("group_buy_id");
        type = getIntent().getStringExtra("type");
        mid = getIntent().getStringExtra("mid");
        cart_id = getIntent().getStringExtra("json");
        goods_id = getString("goods_id");
        num = getString("num");
        //  ordertype = getString("order_type");
        product_id = getString("product_id");
        if (type.equals("1") || type.equals("5")) {
            Order.shoppingCart(cart_id, p, mid, goods_id, num, "0", product_id, this);
        } else if (type.equals("2")) {
            GroupBuyOrder.shoppingCart(goods_id, num, "1", product_id, mid, this);
        } else if (type.equals("3") || type.equals("4")) {
            GroupBuyOrder.shoppingCart(goods_id, num, "2", product_id, mid, this);
        } else if (type.equals("6")) {
            PreOrder.preShoppingCart(group_buy_id, num, this);
        } else if (type.equals("7")) {
            IntegralOrder.ShoppingCart(mid, num, group_buy_id, this);
        }
        showProgressDialog();

    }

    private String getString(String key) {
        return TextUtils.isEmpty(getIntent().getStringExtra(key)) ? "" : getIntent().getStringExtra(key);
    }

    Map<String, String> map;
    List<Map<String, String>> data;
    List<Map<String, String>> more;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        if (map.get("is_default").equals("1")) {
            tv_name.setText("收货人：" + map.get("receiver"));
            tv_tel.setText(map.get("phone"));
            tv_address.setText("收货地址：" + map.get("province") + map.get("city") + map.get("area") +
                    map.get("address"));
            tv_c_ads.setVisibility(View.GONE);
            layout_choose_address.setVisibility(View.VISIBLE);
        } else {
            tv_c_ads.setVisibility(View.VISIBLE);
            layout_choose_address.setVisibility(View.GONE);
        }
        address_id = map.get("address_id");
        order_price_at_last_tv.setText("合计：¥" + map.get("sum_shop_price"));
        tv_merchant_name.setText(map.get("merchant_name"));
        tv_sum_discount.setText("总抵扣¥" + map.get("sum_discount"));
        if (ToolKit.isList(map, "item")) {
            if (p == 1) {
                data = JSONUtils.parseKeyAndValueToMapList(map.get("item"));
                goodsAdapter = new GoodsByOrderAdapter(this, data);
                goods_fot_order_lv.setAdapter(goodsAdapter);
            } else {
                more = JSONUtils.parseKeyAndValueToMapList(map.get("item"));
                data.addAll(more);
                goodsAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 8888) {
            tv_name.setText("收货人：" + data.getStringExtra("receiver"));
            tv_tel.setText(data.getStringExtra("phone"));
            tv_address.setText("收货地址：" + data.getStringExtra("ads"));
            address_id = data.getStringExtra("id");
            tv_c_ads.setVisibility(View.GONE);
            layout_choose_address.setVisibility(View.VISIBLE);
        }
    }
}
