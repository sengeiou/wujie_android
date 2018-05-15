package com.txd.hzj.wjlp.shoppingCart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.converter.DoubleColumnConverter;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.PayForAppAty;
import com.txd.hzj.wjlp.minetoAty.address.AddressListAty;
import com.txd.hzj.wjlp.new_wjyp.Invoice1;
import com.txd.hzj.wjlp.new_wjyp.InvoiceAty;
import com.txd.hzj.wjlp.new_wjyp.http.AuctionOrder;
import com.txd.hzj.wjlp.new_wjyp.http.Freight;
import com.txd.hzj.wjlp.new_wjyp.http.GroupBuyOrder;
import com.txd.hzj.wjlp.new_wjyp.http.IntegralBuyOrder;
import com.txd.hzj.wjlp.new_wjyp.http.IntegralOrder;
import com.txd.hzj.wjlp.new_wjyp.http.Order;
import com.txd.hzj.wjlp.new_wjyp.http.PreOrder;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.tool.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/25 0025
 * 时间：上午 9:00
 * 描述：确认订单
 * ===============Txunda===============
 */
public class BuildOrderAty extends BaseAty {
    //tijiao
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
    private int p = 1; // 订单类型 0:普通 1限量购 2无界商店 3进口馆 4搭配购
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
    private String freightJson;

    private CommonPopupWindow commonPopupWindow;
    private View view_ads;
    String freight, freight_type;
    @ViewInject(R.id.layout1)
    private LinearLayout layout1;
    @ViewInject(R.id.textview)
    private TextView textview;
    private int index = -1;
    @ViewInject(R.id.tv_invoice)
    private TextView tv_invoice;
    @ViewInject(R.id.et_leave_message)
    private EditText et_leave_message;
    @ViewInject(R.id.tv_youfei)
    private TextView tv_youfei;//邮费
    @ViewInject(R.id.buildOrder_scrollView)
    private ScrollView buildOrder_scrollView;

    private List<Goods> goodsList = new ArrayList<>();//普通商品快递属性list
    private List<GoodsCart> goodsCartList = new ArrayList<>();//购物车商品属性list
    private List<SplitNew> splitNewList = new ArrayList<>();//配送方式属性list
    private List<Goods_info> goods_infoList = new ArrayList<>();//所有商品的神秘参数
    private int choice_goods = -1;//选择商品下标
    private List<Invoice> invoiceList = new ArrayList<>();//商品类型
    private Bundle bundle;
    private double total_price = 0.00f;//总价格
    private double tp = 0.00;
    private String is_pay_password = "0";//是否设置密码
    private int[] same_tem_id;//相同模板id

    private Bean bean;
    private double countryTax = 0.00; // 进口税
    private String  order_id;
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
    protected void onResume() {
        super.onResume();
        view_ads = null;
    }

    @Override
    @OnClick({R.id.build_order_left_layout, R.id.build_order_right_layout, R.id.submit_order_tv, R.id.layout_choose_address, R.id.tv_c_ads, R.id.layout_sle})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.et_leave_message:
                break;
            case R.id.layout_sle:
//                if (!TextUtils.isEmpty(address_id)) {
//                    if (TextUtils.isEmpty(freightJson)) {
//                        Gson gson = new Gson();
//                        freightJson = gson.toJson(list_bean);
//                    }
//                    Freight.split(address_id, freightJson, this);
//                    showProgressDialog();
//                    view_ads = v;
//                } else {
//                    showToast("请选择收货地址！");
//                }
                break;
            case R.id.build_order_left_layout:// 线上商城
                order_type = 0;
                setStyle(order_type);
                break;
            case R.id.build_order_right_layout:// 附近驿站
                order_type = 1;
                setStyle(order_type);
                break;
            case R.id.submit_order_tv:// 提交订单
                if (TextUtils.isEmpty(address_id)) {
                    showToast("请选择收货地址！");
                    return;
                }
                //是否选择了快递
                if (goodsList.size() != 0) {
                    for (Goods temp : goodsList) {
                        while (TextUtils.isEmpty(temp.getTem_id())) {
                            showToast("请完善所有商品的快递方式！");
                            return;
                        }
                    }
                } else {
                    for (GoodsCart temp : goodsCartList) {
                        while (TextUtils.isEmpty(temp.getTem_id())) {
                            showToast("请完善所有商品的快递方式！");
                            return;
                        }
                    }
                }
//                if (TextUtils.isEmpty(tv_sle_right.getText().toString())) {
//                    showToast("请选择配送方式");
//                    return;
//                }
                EventBus.getDefault().post(new MessageEvent("更新购物车列表"));
                Gson gson = new Gson();

                String json = gson.toJson(i_bean);
                bundle = new Bundle();
                bundle.putString("type", type);
                bundle.putString("order_type", type.equals("1") ? "0" : "1");
                bundle.putString("num", num);
                bundle.putString("group_buy_id", group_buy_id);
                bundle.putString("goods_id", goods_id);
                bundle.putString("product_id", product_id);
                bundle.putString("address_id", address_id);
                bundle.putString("shop_name", tv_merchant_name.getText().toString());
                bundle.putString("money", String.valueOf(total_price + tp));
                bundle.putString("cart_id", cart_id);
                bundle.putString("freight", freight);
                bundle.putString("freight_type", freight_type);
                bundle.putString("json", json);
                bundle.putString("leave_message", et_leave_message.getText().toString());
                //判断是普通商品下单还是购物车下单
                if (TextUtils.isEmpty(cart_id)) {
                    //普通下单
                    bundle.putString("goodsList", gson.toJson(goodsList));
                } else {
                    //购物车下单
                    bundle.putString("goodsCartList", gson.toJson(goodsCartList));
                }

                invoiceList.removeAll(invoiceList); // 移除之前添加的发票信息
                if (i_bean.size() > 0) {
                    for (Bean bean : i_bean) { // 遍历所有发票信息，将信息全部添加到invoiceList列表中
                        if (bean != null && !bean.getExpress_fee().isEmpty() && !bean.getTax_pay().isEmpty()) {
                            invoiceList.add(new Invoice(bean.getT_id(), bean.getRise(), bean.getRise_name(), bean.getInvoice_detail(), bean.getInvoice_id(), bean.getRecognition(), Integer.parseInt(bean.getIs_invoice()))); // 添加最新的发票信息
                        }else {
                            invoiceList.add(new Invoice("", "", "", "", "", "", 0)); // 添加最新的发票信息
                        }
                    }
                }

                bundle.putString("invoiceList", gson.toJson(invoiceList));
//                bundle.putString("bean", gson.toJson());
                bundle.putString("is_pay_password", is_pay_password);
                bundle.putString("order_id",order_id);
                bundle.putString("freight",String.valueOf(tp));
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
        buildOrder_scrollView.smoothScrollTo(0,0);
    }

    @Override
    protected void requestData() {
        Intent intent=getIntent();
        group_buy_id = intent.getStringExtra("group_buy_id");
        if(intent.hasExtra("order_id")){
            order_id= intent.getStringExtra("order_id");
        }
        type = intent.getStringExtra("type");
        mid = intent.getStringExtra("mid");
        cart_id = intent.getStringExtra("json");
        L.e("cart" + cart_id);
        goods_id = getString("goods_id",intent);
        num = getString("num",intent);
        ordertype = getString("order_type",intent);
        product_id = getString("product_id",intent);
        L.e("ccccc" + group_buy_id + "--" + type + "--" + mid + "--" + cart_id + "--" + goods_id + "--" + num + "--" + product_id);
        if (type.equals("0")) {  //主界面购物车
            Order.shoppingCart(cart_id, p, mid, goods_id, num, "0", product_id, "", this);
        } else if (type.equals("1")) {//票券
            Order.shoppingCart(cart_id, p, mid, goods_id, num, "0", product_id, toJSon(), this);
        } else if (type.equals("2")) {//直接购买  拼单单独购买
            GroupBuyOrder.shoppingCart(goods_id, num, "1", product_id, mid, group_buy_id, this);
        } else if (type.equals("3") || type.equals("4")) {//拼单购 开团 （商品详情一键开团）
            GroupBuyOrder.shoppingCart(goods_id,num,"2",product_id,mid,group_buy_id, this);
        } else if (type.equals("5")) {//限量购详情
            Order.shoppingCart(cart_id, p, mid, goods_id, num, "0", product_id, toJSon(), this);
        } else if (type.equals("6")) {//限量购 无界预购
            PreOrder.preShoppingCart(group_buy_id, num, this);
        } else if (type.equals("7")) {// 一元夺宝
            IntegralOrder.ShoppingCart(mid, num, group_buy_id, this);
        } else if (type.equals("9")) {//拍品详情   竞拍汇
            AuctionOrder.ShoppingCart(mid, group_buy_id, "", "0", this);
        } else if (type.equals("10")) {//限量购 无界商店
            IntegralBuyOrder.ShoppingCart(mid, group_buy_id, num, this);
        } else if (type.equals("11")) {//搭配购
            Order.shoppingCart(cart_id, p, mid, goods_id, num, "4", product_id, getString("json",intent), this);
        }
        showProgressDialog();

    }

    private String toJSon() {
        return "[{\"product_id\":\"" + product_id + "\",\"goods_id\":\"" + goods_id + "\"}]";
    }

    private String getString(String key,Intent intent) {
        return TextUtils.isEmpty(intent.getStringExtra(key)) ? "" : intent.getStringExtra(key);
    }

    Map<String, String> map;
    List<Map<String, String>> data;
    List<Map<String, String>> more;
    List<GoodBean> list_bean = new ArrayList<GoodBean>();
    List<Bean> i_bean = new ArrayList<Bean>();
    List<Invoice1> invoice1s = new ArrayList<Invoice1>();
    private String price;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        L.e(requestUrl + "cccc=====>>>>>" + jsonStr);

        // 设置显示的进口税字段
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray jsonArray = data.getJSONArray("item");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemJson = (JSONObject) jsonArray.get(i);
                countryTax += itemJson.getDouble("country_tax");
            }
            if (countryTax > 0) {
                // 刚开始如果有进口税则只显示进口税
                tv_invoice.append("+进口税" + countryTax);
            }
        } catch (JSONException e) {
            L.e("缺少对应字段");
        }

        if (requestUrl.contains("split")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));

            try {
                if (!map.get("tem").equals("[]")) {
                    data = JSONUtils.parseKeyAndValueToMapList(map.get("tem"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("此商品参数问题啊！");
                return;
            }

            if (choice_goods != -1) {
                showPop(view_ads, data);
            }
            if (TextUtils.isEmpty(price)) {
                price = order_price_at_last_tv.getText().toString();
            }

        } else if (requestUrl.contains("setOrder")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);

            if (map.get("code").equals("1")) {
                showToast(map.get("message"));
            } else {
                showToast(map.get("message"));
            }

        } else {

            L.e("wang", "=====>>>>>>>>>jsonStr:" + jsonStr);

            map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));

            is_pay_password = map.get("is_pay_password");
//            if (map.get("welfare_status").equals("1")) {
//                layout1.setVisibility(View.VISIBLE);
//                textview.setText("成交后卖家将捐款" + map.get("welfare_pay") + "元给公益计划");
//            } else {
//                layout1.setVisibility(View.GONE);
//            }
            if (map.get("is_default").equals("1")) {
                tv_name.setText("收货人：" + map.get("receiver"));
                tv_tel.setText(map.get("phone"));
                tv_address.setText("收货地址：" + map.get("province") + map.get("city") + map.get("area") + map.get("address"));
                tv_c_ads.setVisibility(View.GONE);
                layout_choose_address.setVisibility(View.VISIBLE);
            } else {
                tv_c_ads.setVisibility(View.VISIBLE);
                layout_choose_address.setVisibility(View.GONE);
            }
            address_id = map.get("address_id");

            tv_merchant_name.setText(map.get("merchant_name"));
            if (!type.equals("10")) {
                total_price = Double.parseDouble(map.get("sum_shop_price"));
//            tv_sum_discount.setText("总抵扣¥" + map.get("sum_discount"));
                order_price_at_last_tv.setText("合计：¥" + total_price);
//            if (map.get("sum_discount").equals("0")) {
                tv_sum_discount.setVisibility(View.GONE);
            } else {
                tv_sum_discount.setVisibility(View.GONE);
                order_price_at_last_tv.setText("合计：" + map.get("sum_shop_price") + "积分");
            }
            if (ToolKit.isList(map, "item")) { // 判断该Map是否可解析成List
                /*
                * json , 请按顺序传入！！！
[{"发票类型id":"1","发票抬头（1->个人，2->公司）":"1","发票抬头名":"name","发票明细":"detail","发票id":"1","识别号”:1111,"是否开发票（1->是，0->否）”:1},
{"t_id":"1","rise":"1","rise_name":"name","invoice_detail":"detail","invoice_id":"3",,"recognition”:1111,"is_invoice”:1}]
                * */
                if (p == 1) {
                    data = JSONUtils.parseKeyAndValueToMapList(map.get("item"));
                    for (Map<String, String> temp : data) {
                        if (TextUtils.isEmpty(cart_id)) {
                            goodsList.add(new Goods(temp.get("product_id"), temp.get("goods_id"), "", temp.get("num"), "", "", "0", "", "", ""));
                        } else {
                            goodsCartList.add(new GoodsCart(temp.get("cart_id"), "", "", temp.get("num"), "", "", "", "", "", temp.get("goods_id")));
                        }
                        goods_infoList.add(new Goods_info(temp.get("goods_id"), temp.get("product_id"), temp.get("num")));

                        splitNewList.add(new SplitNew("", "", "", "", "", "", "0", ""));
                        invoiceList.add(new Invoice("", "", "", "", "", "", 0));
                    }
                    Log.i("一直蹦",data.toString());
                    goodsAdapter = new GoodsByOrderAdapter(this, data, goodsList);
                    goods_fot_order_lv.setAdapter(goodsAdapter);
                } else {

                    more = JSONUtils.parseKeyAndValueToMapList(map.get("item"));
                    for (Map<String, String> temp : more) {
                        if (TextUtils.isEmpty(cart_id)) {
                            goodsList.add(new Goods(temp.get("product_id"), temp.get("goods_id"), "", temp.get("num"), "", "", "0", "", "", ""));
                        } else {
                            goodsCartList.add(new GoodsCart(temp.get("cart_id"), "", "", temp.get("num"), "", "", "", "", "", temp.get("goods_id")));
                        }
                        goods_infoList.add(new Goods_info(temp.get("goods_id"), temp.get("product_id"), temp.get("num")));
                        splitNewList.add(new SplitNew("", "", "", "", "", "", "0", ""));
                        invoiceList.add(new Invoice("", "", "", "", "", "", 0));
                    }
                    data.addAll(more);
                    goodsAdapter.notifyDataSetChanged();
                }
            }
            for (int i = 0; i < data.size(); i++) {
                list_bean.add(new GoodBean(data.get(i).get("num"), data.get(i).get("goods_id")));
                i_bean.add(new Bean());
                invoice1s.add(new Invoice1()); // 创建同等长度的Invoice1集合
            }
        }

    }

    @ViewInject(R.id.tv_sle_left)
    private TextView tv_sle_left;
    @ViewInject(R.id.tv_sle_right)
    private TextView tv_sle_right;

    /**
     * 选择配送方式
     */
    public void showPop(View view, final List<Map<String, String>> data) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_sle_address)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayHeight / 3)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        ListView listview = (ListView) view.findViewById(R.id.popup_listview);
                        listview.setAdapter(new AdsAdapter(data));
                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                                tv_sle_left.setText("配送方式");
//                                freight = data.get(position).get("pay");
//                                freight_type = data.get(position).get("type");
//                                tv_sle_right.setText(data.get(position).get("type") + "(¥" + data.get(position).get("pay") + ")");
                                same_tem_id = getSameID(data.get(position).get("same_tem_id"));
                                for (int temp : same_tem_id) {
                                    String same_id = String.valueOf(temp);
                                    if (goodsList.size() != 0) {
                                        for (int g = 0; g < goodsList.size(); g++) {
                                            if (goodsList.get(g).getGoods_id().equals(same_id)) {
                                                goodsList.get(g).setShipping_id(data.get(position).get("shipping_id"));
                                                goodsList.get(g).setTem_id(data.get(position).get("id"));
                                                goodsList.get(g).setPay(data.get(position).get("pay"));
                                                goodsList.get(g).setType_status(data.get(position).get("type_status"));
                                                goodsList.get(g).setType(data.get(position).get("type"));
                                                goodsList.get(g).setDesc(data.get(position).get("desc"));
                                                goodsList.get(g).setSame_tem_id(data.get(position).get("same_tem_id"));
                                                splitNewList.get(g).setId(data.get(position).get("id"));
                                                splitNewList.get(g).setType_status(data.get(position).get("type_status"));
                                                splitNewList.get(g).setShipping_id(data.get(position).get("shipping_id"));
                                                splitNewList.get(g).setShipping_name(data.get(position).get("shipping_name"));
                                                splitNewList.get(g).setType(data.get(position).get("type"));
                                                splitNewList.get(g).setPay("0");
                                                splitNewList.get(g).setDesc(data.get(position).get("desc"));
                                                splitNewList.get(g).setType_name(data.get(position).get("type_name"));
                                            }
                                        }
                                    } else if (goodsCartList.size() != 0) {
                                        for (int g = 0; g < goodsCartList.size(); g++) {
                                            L.e("same_id=" + same_id + "goodsCartList=" + goodsCartList.get(g).getGoods_id());
                                            L.e("same_id" + goodsCartList.get(g).getGoods_id().equals(same_id));
                                            if (goodsCartList.get(g).getGoods_id().equals(same_id)) {
                                                goodsCartList.get(g).setShipping_id(data.get(position).get("shipping_id"));
                                                goodsCartList.get(g).setTem_id(data.get(position).get("id"));
                                                goodsCartList.get(g).setPay(data.get(position).get("pay"));
                                                goodsCartList.get(g).setType_status(data.get(position).get("type_status"));
                                                goodsCartList.get(g).setType(data.get(position).get("type"));
                                                goodsCartList.get(g).setDesc(data.get(position).get("desc"));
                                                goodsCartList.get(g).setSame_tem_id(data.get(position).get("same_tem_id"));
                                                splitNewList.get(g).setId(data.get(position).get("id"));
                                                splitNewList.get(g).setType_status(data.get(position).get("type_status"));
                                                splitNewList.get(g).setShipping_id(data.get(position).get("shipping_id"));
                                                splitNewList.get(g).setShipping_name(data.get(position).get("shipping_name"));
                                                splitNewList.get(g).setType(data.get(position).get("type"));
                                                splitNewList.get(g).setPay("0");
                                                splitNewList.get(g).setDesc(data.get(position).get("desc"));
                                                splitNewList.get(g).setType_name(data.get(position).get("type_name"));
                                            }
                                        }

                                    }
                                }
                                splitNewList.get(choice_goods).setId(data.get(position).get("id"));
                                splitNewList.get(choice_goods).setType_status(data.get(position).get("type_status"));
                                splitNewList.get(choice_goods).setShipping_id(data.get(position).get("shipping_id"));
                                splitNewList.get(choice_goods).setShipping_name(data.get(position).get("shipping_name"));
                                splitNewList.get(choice_goods).setType(data.get(position).get("type"));
                                splitNewList.get(choice_goods).setPay(data.get(position).get("pay"));
                                splitNewList.get(choice_goods).setDesc(data.get(position).get("desc"));
                                splitNewList.get(choice_goods).setType_name(data.get(position).get("type_name"));
                                if (TextUtils.isEmpty(cart_id)) {
                                    goodsList.get(choice_goods).setShipping_id(data.get(position).get("shipping_id"));
                                    goodsList.get(choice_goods).setTem_id(data.get(position).get("id"));
                                    goodsList.get(choice_goods).setPay(data.get(position).get("pay"));
                                    goodsList.get(choice_goods).setType_status(data.get(position).get("type_status"));
                                    goodsList.get(choice_goods).setType(data.get(position).get("type"));
                                    goodsList.get(choice_goods).setDesc(data.get(position).get("desc"));
                                    goodsList.get(choice_goods).setSame_tem_id(data.get(position).get("same_tem_id"));
                                } else {
                                    goodsCartList.get(choice_goods).setShipping_id(data.get(position).get("shipping_id"));
                                    goodsCartList.get(choice_goods).setTem_id(data.get(position).get("id"));
                                    goodsCartList.get(choice_goods).setPay(data.get(position).get("pay"));
                                    goodsCartList.get(choice_goods).setType_status(data.get(position).get("type_status"));
                                    goodsCartList.get(choice_goods).setType(data.get(position).get("type"));
                                    goodsCartList.get(choice_goods).setDesc(data.get(position).get("desc"));
                                    goodsCartList.get(choice_goods).setSame_tem_id(data.get(position).get("same_tem_id"));
                                }

//                                order_price_at_last_tv.setText(price + "+" + data.get(position).get("pay") + "运费");
                                commonPopupWindow.dismiss();
                                goodsAdapter.notifyDataSetChanged();
                                tp = 0.00;
                                for (SplitNew temp : splitNewList) {
                                    L.e("tp" + temp.getPay());
                                    tp += Double.parseDouble(temp.getPay());
                                }
                                tp = (double) Math.round(tp * 100) / 100;
                                BigDecimal bg = new BigDecimal(tp);
                                tp = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                if (tp == 0.00) {
                                    tv_youfei.setVisibility(View.GONE);
                                } else {
                                    tv_youfei.setVisibility(View.VISIBLE);
                                    tv_youfei.setText("+" + tp + "元运费");
                                }
                                order_price_at_last_tv.setText("合计：¥" + total_price);

                            }
                        });
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    class AdsAdapter extends BaseAdapter {
        List<Map<String, String>> data;

        public AdsAdapter(List<Map<String, String>> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(BuildOrderAty.this, R.layout.item_sle_address, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_name.setText(data.get(position).get("type_name") + "(" + data.get(position).get("shipping_name") + ")\n" + data.get(position).get("desc"));
            tv_price.setText(data.get(position).get("pay").equals("0") ? "包邮" : data.get(position).get("pay") + "元");
            return view;
        }
    }

    class GoodBean {

        private String num;
        private String goods_id;

        public GoodBean(String num, String goods_id) {
            this.num = num;
            this.goods_id = goods_id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        finish();
    }

    Invoice1 invoice1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 8888) {
            tv_name.setText("收货人：" + data.getStringExtra("receiver"));
            tv_tel.setText(data.getStringExtra("phone"));

            tv_address.setText("收货地址：" + data.getStringExtra("ads"));
            if (!TextUtils.isEmpty(address_id) && !data.getStringExtra("id").equals(address_id)) {
                if (TextUtils.isEmpty(freightJson)) {
                    Gson gson = new Gson();
                    freightJson = gson.toJson(list_bean);
                }
            }
            address_id = data.getStringExtra("id");
            tv_c_ads.setVisibility(View.GONE);
            layout_choose_address.setVisibility(View.VISIBLE);
        }
        if (requestCode == 1000) {
            if (data != null) {
                invoice1 = data.getParcelableExtra("data1");
                invoice1s.set(index, invoice1);

                Bean b = data.getParcelableExtra("data");
                bean = b; // 将回传的Bean赋值给成员变量

                i_bean.set(index, b); // 设置对应的bean

                goodsAdapter.notifyDataSetChanged(); // 设置adapter刷新
                double tax_pay = 0; // 税金
                double express_fee = 0; // 发票运费
                for (Bean bean : i_bean) {
                    if (!TextUtils.isEmpty(bean.getIs_invoice()) && !bean.getIs_invoice().equals("0")) {
                        tax_pay += Double.parseDouble(bean.getTax_pay().equals("") ? "0" : bean.getTax_pay());
                        express_fee += Double.parseDouble(bean.getExpress_fee().equals("") ? "0" : bean.getExpress_fee());
                    }
                }

                // 设置税金四舍五入显示到界面上
                DecimalFormat df = new DecimalFormat(".##");
                tv_invoice.setText("+税金:" + df.format(tax_pay) + "\n+发票运费:" + express_fee);
                if (countryTax > 0) {
                    // 界面返回的时候显示税金、发票运费等，如果先前包含进口税，则添加进口税显示
                    tv_invoice.append("\n+进口税" + countryTax);
                }
            }
        }
    }

    private String toJson(String s1, String s2, String s3) {
        return "[{\"goods_id\":\"" + s1 + "\",\"num\":\"" + s2 + "\",\"product_id\":\"" + s3 + "\"}]";
    }


    class GoodsByOrderAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;
        private GoodsByOrderAdapter.GOVH govh;
        List<Map<String, String>> data;
        private List<Goods> goodsList;

        public GoodsByOrderAdapter(Context context, List<Map<String, String>> data, List<Goods> goodsList) {
            this.data = data;
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.goodsList = goodsList;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = inflater.inflate(R.layout.item_goods_by_order_lv, null);
                govh = new GOVH();
                ViewUtils.inject(govh, view);
                view.setTag(govh);
            } else {
                govh = (GOVH) view.getTag();
            }
            Glide.with(context).load(getItem(i).get("goods_img")).into(govh.goods_comment_pic);
            govh.tv_number.setText("x" + getItem(i).get("num"));
            //govh.jifen_tv.setText("（赠送:" + getItem(i).get("return_integral") + "积分）");
            L.e("wang", "====>>>>>>>>>>>getItem(i):" + getItem(i));
            govh.goods_title_for_evaluate_tv.setText(getItem(i).get("goods_name"));

            if (!TextUtils.isEmpty(getItem(i).get("goods_attr_first"))) {
                govh.price_for_goods_tv.setText(getItem(i).get("goods_attr_first") + "\n¥" + getItem(i).get("shop_price"));
            } else {
                govh.price_for_goods_tv.setText("¥" + getItem(i).get("shop_price"));
            }
//            if (getItem(i).get("invoice_status").equals("1")) {
//                govh.layout.setVisibility(View.VISIBLE);
//                govh.textview.setText(TextUtils.isEmpty(i_bean.get(i).getInvoice_type()) ? "不开发票" : i_bean.get(i).getInvoice_type());
//            } else {
//                govh.layout.setVisibility(View.GONE);
//            }
            govh.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = i;
                    Bundle bundle = new Bundle();
                    bundle.putString("json", toJson(getItem(i).get("goods_id"), getItem(i).get("num"), getItem(i).get("product_id")));
                   // bundle.putString("wj_price", getItem(i).get("wj_price"));
                    if (invoice1s.get(index) == null || invoice1s.get(index).getExpress_fee() == null || invoice1s.get(index).getExpress_fee() == null) {
                        bundle.putParcelable("data1", null); // 如果当前位置的值为空，那么传一个空值给发票选择界面
                    } else {
                        bundle.putParcelable("data1", invoice1s.get(index));
                    }

                    if (i_bean.get(i).getExpress_fee().isEmpty()) {
                        bundle.putString("data2", "data1 is null so intent data2.");
                    }

                    startActivityForResult(InvoiceAty.class, bundle, 1000);
//                    startActivityForResult(InvoiceAty2.class, bundle, 1000);
                }
            });
            govh.tv_sle_left.setText(TextUtils.isEmpty(splitNewList.get(i).getId()) ? "请选择配送方式" : "");
            if (!TextUtils.isEmpty(splitNewList.get(i).getId())) {
                govh.tv_sle_right.setText("配送方式：" + (splitNewList.get(i).getPay().equals("0") ? splitNewList.get(i).getType_name() + "(" + splitNewList.get(i).getShipping_name() + ")" + "包邮" : splitNewList.get(i).getType_name() + "(" + splitNewList.get(i).getShipping_name() + ")" + " ¥" + splitNewList.get(i).getPay()));
            }

            //是否存在公益宝贝
            L.e("aaaa" + getItem(i).get("is_welfare"));

            // 正品保证
            govh.layout_pinzhibaozhang.setVisibility(getItem(i).get("integrity_a").isEmpty() ? View.GONE : View.VISIBLE);
            govh.tv_pinzhibaozhang.setText(getItem(i).get("integrity_a").isEmpty() ? "" : getItem(i).get("integrity_a"));
            // 服务承诺
            govh.layout_fuwuchengnuo.setVisibility(getItem(i).get("integrity_b").isEmpty() ? View.GONE : View.VISIBLE);
            govh.tv_fuwuchengnuo.setText(getItem(i).get("integrity_b").isEmpty() ? "" : getItem(i).get("integrity_b"));
            // 发货时间
            govh.layout_fahuoshijian.setVisibility(getItem(i).get("integrity_c").isEmpty() ? View.GONE : View.VISIBLE);
            govh.tv_fahuoshijian.setText(getItem(i).get("integrity_c").isEmpty() ? "" : getItem(i).get("integrity_c"));
            // 公益宝贝
            if(null==getItem(i).get("welfare")){
                govh.layout_gongyi.setVisibility(View.GONE);
            }else{
                govh.layout_gongyi.setVisibility(getItem(i).get("welfare").isEmpty() ? View.GONE : View.VISIBLE);
                govh.tv_gongyi.setText("成交后卖家将捐赠" + getItem(i).get("welfare") + "元给公益计划");
            }
            // 售后
            govh.layout_shouhou.setVisibility(getItem(i).get("after_sale_status").equals("1")?View.VISIBLE:View.GONE);
            govh.tv_shouhou.setText(getItem(i).get("after_sale_type"));

            /**
             * 选择配送方式
             */
            govh.layout_sle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(address_id)) {
                        if (TextUtils.isEmpty(freightJson)) {
                            Gson gson = new Gson();
                            freightJson = gson.toJson(list_bean);
                        }
                        view_ads = view;

                        Freight.split(getItem(i).get("goods_id"), address_id, getItem(i).get("product_id"), getItem(i).get("num"), new Gson().toJson(goods_infoList), BuildOrderAty.this);
                        showProgressDialog();
                        choice_goods = i;
                    } else {
                        showToast("请选择收货地址！");
                    }
                }
            });
            return view;
        }


        class GOVH {
            @ViewInject(R.id.goods_comment_pic)
            private ImageView goods_comment_pic;
            @ViewInject(R.id.goods_title_for_evaluate_tv)
            private TextView goods_title_for_evaluate_tv;
            @ViewInject(R.id.price_for_goods_tv)
            private TextView price_for_goods_tv;
            @ViewInject(R.id.jifen_tv)
            private TextView jifen_tv;
            @ViewInject(R.id.tv_number)
            private TextView tv_number;
            @ViewInject(R.id.layout)
            private LinearLayout layout;
            @ViewInject(R.id.textview)
            private TextView textview;
            @ViewInject(R.id.layout_sle)
            private LinearLayout layout_sle;
            @ViewInject(R.id.tv_sle_left)
            private TextView tv_sle_left;
            @ViewInject(R.id.tv_sle_right)
            private TextView tv_sle_right;//配送金额
            @ViewInject(R.id.lin_server_status)
            private LinearLayout lin_server_status;

            @ViewInject(R.id.layout_shouhou)
            private LinearLayout layout_shouhou; // 售后服务类型
            @ViewInject(R.id.tv_shouhou)
            private TextView tv_shouhou; // 售后服务类型

            @ViewInject(R.id.layout_gongyi) // 公益
            private LinearLayout layout_gongyi;
            @ViewInject(R.id.tv_gongyi) // 公益
            private TextView tv_gongyi;

            @ViewInject(R.id.layout_pinzhibaozhang) // 品质保障
            private LinearLayout layout_pinzhibaozhang;
            @ViewInject(R.id.tv_pinzhibaozhang) // 品质保障
            private TextView tv_pinzhibaozhang;

            @ViewInject(R.id.layout_fuwuchengnuo) // 服务承诺
            private LinearLayout layout_fuwuchengnuo;
            @ViewInject(R.id.tv_fuwuchengnuo) // 服务承诺
            private TextView tv_fuwuchengnuo;

            @ViewInject(R.id.layout_fahuoshijian) // 发货时间
            private LinearLayout layout_fahuoshijian;
            @ViewInject(R.id.tv_fahuoshijian) // 发货时间
            private TextView tv_fahuoshijian;
        }
    }

    public static class Bean implements Parcelable {
        String t_id = "";
        String rise = "";
        String rise_name = "";
        String invoice_detail = "";
        String invoice_id = "";
        String recognition = "";
        String is_invoice = "";
        String invoice_type = "";
        String express_fee = "";
        String tax_pay = "";

        @Override
        public String toString() {
            return "Bean{" +
                    "t_id='" + t_id + '\'' +
                    ", rise='" + rise + '\'' +
                    ", rise_name='" + rise_name + '\'' +
                    ", invoice_detail='" + invoice_detail + '\'' +
                    ", invoice_id='" + invoice_id + '\'' +
                    ", recognition='" + recognition + '\'' +
                    ", is_invoice='" + is_invoice + '\'' +
                    ", invoice_type='" + invoice_type + '\'' +
                    ", express_fee='" + express_fee + '\'' +
                    ", tax_pay='" + tax_pay + '\'' +
                    '}';
        }

        public String getExpress_fee() {
            return express_fee;
        }

        public void setExpress_fee(String express_fee) {
            this.express_fee = express_fee;
        }

        public String getTax_pay() {
            return tax_pay;
        }

        public void setTax_pay(String tax_pay) {
            this.tax_pay = tax_pay;
        }

        public String getInvoice_type() {
            return invoice_type;
        }

        public void setInvoice_type(String invoice_type) {
            this.invoice_type = invoice_type;
        }

        public String getT_id() {
            return t_id;
        }

        public void setT_id(String t_id) {
            this.t_id = t_id;
        }

        public String getRise() {
            return rise;
        }

        public void setRise(String rise) {
            this.rise = rise;
        }

        public String getRise_name() {
            return rise_name;
        }

        public void setRise_name(String rise_name) {
            this.rise_name = rise_name;
        }

        public String getInvoice_detail() {
            return invoice_detail;
        }

        public void setInvoice_detail(String invoice_detail) {
            this.invoice_detail = invoice_detail;
        }

        public String getInvoice_id() {
            return invoice_id;
        }

        public void setInvoice_id(String invoice_id) {
            this.invoice_id = invoice_id;
        }

        public String getRecognition() {
            return recognition;
        }

        public void setRecognition(String recognition) {
            this.recognition = recognition;
        }

        public String getIs_invoice() {
            return is_invoice;
        }

        public void setIs_invoice(String is_invoice) {
            this.is_invoice = is_invoice;
        }

        public static Creator<Bean> getCREATOR() {
            return CREATOR;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.t_id);
            dest.writeString(this.rise);
            dest.writeString(this.rise_name);
            dest.writeString(this.invoice_detail);
            dest.writeString(this.invoice_id);
            dest.writeString(this.recognition);
            dest.writeString(this.is_invoice);
            dest.writeString(this.invoice_type);
            dest.writeString(this.express_fee);
            dest.writeString(this.tax_pay);
        }

        public Bean() {

        }

        public Bean(String t_id, String rise, String rise_name, String invoice_detail, String invoice_id, String recognition, String is_invoice, String invoice_type) {
            this.t_id = t_id;
            this.rise = rise;
            this.rise_name = rise_name;
            this.invoice_detail = invoice_detail;
            this.invoice_id = invoice_id;
            this.recognition = recognition;
            this.is_invoice = is_invoice;
            this.invoice_type = invoice_type;
        }

        protected Bean(Parcel in) {
            this.t_id = in.readString();
            this.rise = in.readString();
            this.rise_name = in.readString();
            this.invoice_detail = in.readString();
            this.invoice_id = in.readString();
            this.recognition = in.readString();
            this.is_invoice = in.readString();
            this.invoice_type = in.readString();
            this.express_fee = in.readString();
            this.tax_pay = in.readString();
        }

        public static final Parcelable.Creator<Bean> CREATOR = new Parcelable.Creator<Bean>() {
            @Override
            public Bean createFromParcel(Parcel source) {
                return new Bean(source);
            }

            @Override
            public Bean[] newArray(int size) {
                return new Bean[size];
            }
        };
    }

    //快递公司实体
    class SplitNew {
        private String id;
        private String type_status;
        private String shipping_id;
        private String shipping_name;
        private String type;
        private String type_name;
        private String pay;
        private String desc;

        public SplitNew(String id, String type_status, String shipping_id, String shipping_name, String type, String type_name, String pay, String desc) {
            this.id = id;
            this.type_status = type_status;
            this.shipping_id = shipping_id;
            this.shipping_name = shipping_name;
            this.type = type;
            this.type_name = type_name;
            this.pay = pay;
            this.desc = desc;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType_status() {
            return type_status;
        }

        public void setType_status(String type_status) {
            this.type_status = type_status;
        }

        public String getShipping_id() {
            return shipping_id;
        }

        public void setShipping_id(String shipping_id) {
            this.shipping_id = shipping_id;
        }

        public String getShipping_name() {
            return shipping_name;
        }

        public void setShipping_name(String shipping_name) {
            this.shipping_name = shipping_name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    /**
     * 普通商品下单goods参数实体类型
     */
    class Goods {
        private String product_id;//属性id
        private String goods_id;//商品id
        private String tem_id;//模板id
        private String num;//购买数量
        private String type_status;//配送类型
        private String shipping_id;//快递公司id
        private String pay;//邮费
        private String type;//快递类型
        private String desc;//描述
        private String same_tem_id;//相同模板的商品ID

        public Goods(String product_id, String goods_id, String tem_id, String num, String type_status, String shipping_id, String pay, String type, String desc, String same_tem_id) {
            this.product_id = product_id;
            this.goods_id = goods_id;
            this.tem_id = tem_id;
            this.num = num;
            this.type_status = type_status;
            this.shipping_id = shipping_id;
            this.pay = pay;
            this.type = type;
            this.desc = desc;
            this.same_tem_id = same_tem_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getTem_id() {
            return tem_id;
        }

        public void setTem_id(String tem_id) {
            this.tem_id = tem_id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getType_status() {
            return type_status;
        }

        public void setType_status(String type_status) {
            this.type_status = type_status;
        }

        public String getShipping_id() {
            return shipping_id;
        }

        public void setShipping_id(String shipping_id) {
            this.shipping_id = shipping_id;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSame_tem_id() {
            return same_tem_id;
        }

        public void setSame_tem_id(String same_tem_id) {
            this.same_tem_id = same_tem_id;
        }
    }

    class GoodsCart {
        private String cate_ids;//购物车id
        private String tem_id;//模板id
        private String type_status;//配送方式类型
        private String num;//购买数量
        private String shipping_id;//快递公司id
        private String pay;//快递金额
        private String type;//快递类型
        private String desc;//描述
        private String same_tem_id;//相同模板的商品id
        private String goods_id;//商品id

        public GoodsCart(String cate_ids, String tem_id, String type_status, String num, String shipping_id, String pay, String type, String desc, String same_tem_id, String goods_id) {
            this.cate_ids = cate_ids;
            this.tem_id = tem_id;
            this.type_status = type_status;
            this.num = num;
            this.shipping_id = shipping_id;
            this.pay = pay;
            this.type = type;
            this.desc = desc;
            this.same_tem_id = same_tem_id;
            this.goods_id = goods_id;
        }

        public String getCate_ids() {
            return cate_ids;
        }

        public void setCate_ids(String cate_ids) {
            this.cate_ids = cate_ids;
        }

        public String getTem_id() {
            return tem_id;
        }

        public void setTem_id(String tem_id) {
            this.tem_id = tem_id;
        }

        public String getType_status() {
            return type_status;
        }

        public void setType_status(String type_status) {
            this.type_status = type_status;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getShipping_id() {
            return shipping_id;
        }

        public void setShipping_id(String shipping_id) {
            this.shipping_id = shipping_id;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSame_tem_id() {
            return same_tem_id;
        }

        public void setSame_tem_id(String same_tem_id) {
            this.same_tem_id = same_tem_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }
    }

    public class Invoice {
        private String t_id;//发票类型
        private String rise;//发票抬头
        private String rise_name;//发票抬头名
        private String invoice_detail;//发票明细
        private String invoice_id;//发票id
        private String recognition;//识别号
        private int is_invoice;//是否开发票

        public Invoice(String t_id, String rise, String rise_name, String invoice_detail, String invoice_id, String recognition, int is_invoice) {
            this.t_id = t_id;
            this.rise = rise;
            this.rise_name = rise_name;
            this.invoice_detail = invoice_detail;
            this.invoice_id = invoice_id;
            this.recognition = recognition;
            this.is_invoice = is_invoice;
        }

        public String getT_id() {
            return t_id;
        }

        public void setT_id(String t_id) {
            this.t_id = t_id;
        }

        public String getRise() {
            return rise;
        }

        public void setRise(String rise) {
            this.rise = rise;
        }

        public String getRise_name() {
            return rise_name;
        }

        public void setRise_name(String rise_name) {
            this.rise_name = rise_name;
        }

        public String getInvoice_detail() {
            return invoice_detail;
        }

        public void setInvoice_detail(String invoice_detail) {
            this.invoice_detail = invoice_detail;
        }

        public String getInvoice_id() {
            return invoice_id;
        }

        public void setInvoice_id(String invoice_id) {
            this.invoice_id = invoice_id;
        }

        public String getRecognition() {
            return recognition;
        }

        public void setRecognition(String recognition) {
            this.recognition = recognition;
        }

        public int getIs_invoice() {
            return is_invoice;
        }

        public void setIs_invoice(int is_invoice) {
            this.is_invoice = is_invoice;
        }

        @Override
        public String toString() {
            return "Invoice{" +
                    "t_id='" + t_id + '\'' +
                    ", rise='" + rise + '\'' +
                    ", rise_name='" + rise_name + '\'' +
                    ", invoice_detail='" + invoice_detail + '\'' +
                    ", invoice_id='" + invoice_id + '\'' +
                    ", recognition='" + recognition + '\'' +
                    ", is_invoice=" + is_invoice +
                    '}';
        }
    }

    /**
     * 一天改一遍系列，永无止境
     */
    class Goods_info {
        private String goods_id;
        private String product_id;
        private String goods_num;

        public Goods_info(String goods_id, String product_id, String goods_num) {
            this.goods_id = goods_id;
            this.product_id = product_id;
            this.goods_num = goods_num;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }
    }

    private int[] getSameID(String str) {
        String[] arr = str.split(",");
        int[] intArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }
        return intArr;
    }


}
