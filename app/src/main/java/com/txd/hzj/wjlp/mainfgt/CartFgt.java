package com.txd.hzj.wjlp.mainfgt;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.CartGoods;
import com.txd.hzj.wjlp.bean.ShopingCart;
import com.txd.hzj.wjlp.http.Goods;
import com.txd.hzj.wjlp.mellonLine.gridClassify.GoodsAttributeAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.MellInfoAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.new_wjyp.Card_bean;
import com.txd.hzj.wjlp.shoppingCart.BuildOrderAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/12 0012
 * 时间：上午 10:44
 * 描述：购物车
 */
public class CartFgt extends BaseFgt {
    @ViewInject(R.id.cart_bottom_lin_layout)
    private LinearLayout cart_bottom_lin_layout;

    @ViewInject(R.id.title_be_back_iv)
    public ImageView title_be_back_iv;
    /**
     * 中间标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 编辑
     */
    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;

    /**
     * 价格
     */
    @ViewInject(R.id.all_goods_price_tv)
    private TextView all_goods_price_tv;

    /**
     * 全选
     */
    @ViewInject(R.id.cart_select_all_cb)
    private CheckBox cart_select_all_cb;

    /**
     * 移到收藏夹
     */
    @ViewInject(R.id.collect_goods_tv)
    private TextView collect_goods_tv;

    /**
     * 去结算，删除
     */
    @ViewInject(R.id.operation_goods_tv)
    private TextView operation_goods_tv;

    /**
     * 是否是可编辑状态
     */
    private boolean canEdit = false;

    @ViewInject(R.id.cart_lv)
    private PullToRefreshListView cart_lv;
    private List<ShopingCart> shopingCarts = new ArrayList<>();
    @ViewInject(R.id.textview)
    private TextView textview;
    private CartAdapter cartAdapter;
    private int all = 0;
    private int position = 0;
    private int position_group = 0;
    /**
     * 选中的商品价格
     */
    private BigDecimal all_price;//总价
    private ArrayList<Card_bean> json_list;
    //
    //    @ViewInject(R.id.swipe_layout)
    //    private SwipeRefreshLayout swipe_layout;


    private boolean is_all = false;
    private ShopingCart sc;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titlt_conter_tv.setText("购物车");
        title_be_back_iv.setVisibility(View.GONE);
        titlt_right_tv.setVisibility(View.VISIBLE);
        toChangePrice();
    }

    /**
     * 修改商品价格
     */
    private void toChangePrice() {
        ChangeTextViewStyle.getInstance().forCartPrice(getActivity(), all_goods_price_tv, "¥" + all_price + "\n不含配送费");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_cart;
    }

    @Override
    protected void initialized() {
        EventBus.getDefault().register(this);
        cartAdapter = new CartAdapter();
        all_price = new BigDecimal("0.00");
        //        cart_lv.setRefreshing();
    }

    private void getDate() {
        //        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        //            @Override
        //            public void onRefresh() {
        //                RequestParams params = new RequestParams();
        //                ApiTool2 apiTool2 = new ApiTool2();
        //                apiTool2.postApi(Config.BASE_URL + "Cart/cartList", params, CartFgt.this);
        //            }
        //        });
    }

    @Override
    protected void requestData() {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(Config.BASE_URL + "Cart/cartList", params, this);
        cart_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                RequestParams params = new RequestParams();
                ApiTool2 apiTool2 = new ApiTool2();
                apiTool2.postApi(Config.BASE_URL + "Cart/cartList", params, CartFgt.this);
            }
        });
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams();
                ApiTool2 apiTool2 = new ApiTool2();
                apiTool2.postApi(Config.BASE_URL + "Cart/cartList", params, CartFgt.this);
                showProgressDialog();
            }
        });
    }

    Map<String, String> map;
    List<Map<String, String>> list;

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        //  super.onError(requestUrl, error);
        removeContent();
        removeDialog();
        //        swipe_layout.setRefreshing(false);
        if (requestUrl.contains("Cart/cartList")) {
            EventBus.getDefault().post(new MessageEvent("更新购物车"));
            cart_lv.onRefreshComplete();
            titlt_right_tv.setVisibility(View.GONE);
            cart_bottom_lin_layout.setVisibility(View.GONE);
            textview.setText(error.get("message"));
            textview.setVisibility(View.VISIBLE);
            shopingCarts.clear();
            cartAdapter.notifyDataSetChanged();
            cart_lv.setEmptyView(textview);
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);

        //        swipe_layout.setRefreshing(false);
        if (requestUrl.contains("Goods/attrApi")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));

            String goods_id = "";
            if (map.containsKey("first_val")) {
                try {
                    JSONArray jsonArray = new JSONArray(map.get("first_val"));
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                    goods_id = jsonObject.getString("goods_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Intent intent = new Intent();
            intent.setClass(getActivity(), GoodsAttributeAty.class);
            intent.putExtra("from", 2);
            intent.putExtra("imageurl", map.get("goods_img"));
            intent.putExtra("num", map.get("goods_num"));
            intent.putExtra("price", map.get("shop_price"));
            intent.putExtra("is_attr", map.get("is_attr") + "-" + map.get("goods_num"));
            intent.putExtra("goods_attr", map.get("first_list"));
            intent.putExtra("goods_val", map.get("first_val"));
            intent.putExtra("goods_id", goods_id);
            startActivityForResult(intent, 8888);
            //            Gson gson = new Gson();
            //            String json = gson.toJson(cg.getGoods_attr_first());
            //            intent.putExtra("goods_attr", json);
            ////                    intent.putParcelableArrayListExtra("list", (ArrayList) getItem(i).getGoods_attr());
            ////                    intent.putParcelableArrayListExtra("list_p", (ArrayList) getItem(i).getProduct());
        }
        if (requestUrl.contains("Cart/cartList")) {
            EventBus.getDefault().post(new MessageEvent("更新购物车"));
            cart_lv.onRefreshComplete();
            all_price = new BigDecimal("0.00");
            toChangePrice();
            shopingCarts.clear();
            map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            cart_bottom_lin_layout.setVisibility(View.VISIBLE);
            titlt_right_tv.setVisibility(View.VISIBLE);
            shopingCarts = GsonUtil.getObjectList(map.get("data"), ShopingCart.class);
            for (ShopingCart shopCard : shopingCarts) {
            }
            cartAdapter = new CartAdapter();
            cartAdapter.notifyDataSetChanged();
            list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            cart_lv.setAdapter(cartAdapter);
        }
        if (requestUrl.contains("Cart/delCart")) {
            showToast("删除成功！");
            RequestParams params = new RequestParams();
            ApiTool2 apiTool2 = new ApiTool2();
            apiTool2.postApi(Config.BASE_URL + "Cart/cartList", params, this);
        }
        if (requestUrl.contains("Cart/addCollect")) {
            showToast("移入成功！");
            RequestParams params = new RequestParams();
            ApiTool2 apiTool2 = new ApiTool2();
            apiTool2.postApi(Config.BASE_URL + "Cart/cartList", params, this);
        }
        if (requestUrl.contains("Cart/editCart")) {
            showToast("修改成功！");
        }

    }

    @Override
    @OnClick({R.id.titlt_right_tv, R.id.operation_goods_tv, R.id.cart_select_all_cb, R.id.collect_goods_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlt_right_tv:// 编辑，保存
                String str = titlt_right_tv.getText().toString();
                if (str.equals("编辑")) {// 之前是不可编辑状态，点击之后是可编辑状态,现在是保存状态
                    canEdit = true;
                    titlt_right_tv.setText("保存");
                    // 全选按钮显示
                    cart_select_all_cb.setVisibility(View.VISIBLE);
                    // 隐藏价格
                    all_goods_price_tv.setVisibility(View.GONE);
                    operation_goods_tv.setText("删除");
                    // 显示移入收藏夹
                    collect_goods_tv.setVisibility(View.VISIBLE);

                    if (all >= shopingCarts.size()) {
                        cart_select_all_cb.setChecked(true);
                    } else {
                        cart_select_all_cb.setChecked(false);
                    }


                } else {// 之前是可编辑状态，点击之后是不可编辑状态
                    canEdit = false;
                    titlt_right_tv.setText("编辑");
                    // 全选按钮隐藏
                    cart_select_all_cb.setVisibility(View.GONE);
                    // 显示价格
                    all_goods_price_tv.setVisibility(View.VISIBLE);
                    // 隐藏移入收藏夹
                    collect_goods_tv.setVisibility(View.INVISIBLE);
                    operation_goods_tv.setText("去结算");
                    json_list = new ArrayList<>();
                    Gson gson = new Gson();
                    RequestParams params = new RequestParams();
                    ApiTool2 apiTool2 = new ApiTool2();
                    for (ShopingCart sc : shopingCarts) {
                        for (int i = 0; i < sc.getGoodsInfo().size(); i++) {
                            json_list.add(new Card_bean(sc.getGoodsInfo().get(i).getCart_id(), sc.getGoodsInfo().get(i).getGoods_id(), sc.getGoodsInfo().get(i).getProduct_id(), sc.getGoodsInfo().get(i).getNum()));
                        }
                    }
                    String json = gson.toJson(json_list);
                    params.addBodyParameter("cart_json", json);
                    apiTool2.postApi(Config.BASE_URL + "Cart/editCart", params, this);
                    all_price = new BigDecimal("0.00");
                    //当前编辑修改价格之后，保存会更新总价数据
                    for (ShopingCart sc : shopingCarts) {
                        List<CartGoods> cartGoodses = sc.getGoodsInfo();
                        for (CartGoods cg : cartGoodses) {
                            if (cg.isCheck()) {
                                BigDecimal price = new BigDecimal(cg.getShop_price());
                                price = price.multiply(new BigDecimal(cg.getNum()));
                                all_price = all_price.add(price);
                                toChangePrice();
                            }
                        }
                    }
                    showProgressDialog();
                }

                cartAdapter.notifyDataSetChanged();
                break;
            case R.id.operation_goods_tv:// 去结算，删除

                if (canEdit) {// 删除
                    new MikyouCommonDialog(getActivity(), "确定要删除商品么?", "提示", "确定", "取消", true).setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {

                        @Override
                        public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int
                                which) {
                            switch (btnType) {
                                case MikyouCommonDialog.OK:// 确定
                                    json_list = new ArrayList<Card_bean>();
                                    for (ShopingCart shopingCart : shopingCarts) {
                                        for (CartGoods cartGoods : shopingCart.getGoodsInfo()) {
                                            if (cartGoods.isCheck()) {
                                                json_list.add(new Card_bean(cartGoods.getCart_id()));
                                            }
                                        }
                                    }
                                    Gson gson = new Gson();
                                    String json = gson.toJson(json_list);
                                    toJson(1, json);
                                    break;
                                case MikyouCommonDialog.NO:// 取消
                                    break;
                            }
                        }
                    }).showDialog();
                } else {// 去结算
                    String mId = ""; // 商家ID
                    StringBuffer stringBuffer = new StringBuffer();
                    for (ShopingCart shopingCart : shopingCarts) {
                        for (CartGoods cartGoods : shopingCart.getGoodsInfo()) {
                            if (cartGoods.isCheck()) {
                                stringBuffer.append(cartGoods.getCart_id()); // 购物车ID
                                stringBuffer.append(","); // 此处上传值字符串最后会多加一个逗号，后台已处理
                            }
                        }
                        if (!TextUtils.isEmpty(stringBuffer.toString())) {
                            is_all = true;
                            mId = shopingCart.getMerchant_id(); // 获取商家ID
                            //                            showToast("请选择同商店的商品");
                            break;
                        }
                    }
                    if (is_all) {
                        is_all = false;
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "0");
                        bundle.putString("mid", mId);
                        //                        String string = stringBuffer.toString();
                        //                        string = string.substring(0 , string.length()- 1);
                        bundle.putString("json", stringBuffer.toString());
                        startActivity(BuildOrderAty.class, bundle);
                    } else {
                        showToast("您还没有选择商品哦");
                    }
                }
                break;
            case R.id.cart_select_all_cb:// 全选
                boolean b = cart_select_all_cb.isChecked();// 全选按钮的选中状态
                if (b) {
                    all = shopingCarts.size();
                } else {
                    all = 0;
                }
                all_price = new BigDecimal("0.00");
                for (ShopingCart sc : shopingCarts) {
                    sc.setAllCheck(b);
                    List<CartGoods> cartGoodses = sc.getGoodsInfo();
                    for (CartGoods cg : cartGoodses) {
                        cg.setCheck(b);
                        if (b) {
                            BigDecimal price = new BigDecimal(cg.getShop_price());
                            price = price.multiply(new BigDecimal(cg.getNum()));
                            all_price = all_price.add(price);
                        }
                    }
                }

                toChangePrice();
                cartAdapter.notifyDataSetChanged();

                break;
            case R.id.collect_goods_tv:
                json_list = new ArrayList<Card_bean>();
                for (ShopingCart shopingCart : shopingCarts) {
                    for (CartGoods cartGoods : shopingCart.getGoodsInfo()) {
                        if (cartGoods.isCheck()) {
                            json_list.add(new Card_bean(
                                    cartGoods.getCart_id())

                            );
                        }
                    }
                }
                if (json_list.size() == 0) {
                    showToast("请先选择商品");
                    return;
                }
                Gson gson = new Gson();
                String json = gson.toJson(json_list);
                toJson(2, json);
                break;
        }
    }

    @Override
    protected void immersionInit() {
        showStatusBar(R.id.title_re_layout);
    }

    private void toJson(int type, String json) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("cart_id_json", json);
        if (type == 1) {
            apiTool2.postApi(Config.BASE_URL + "Cart/delCart", params, this);
        } else if (type == 2) {
            apiTool2.postApi(Config.BASE_URL + "Cart/addCollect", params, this);
        } else if (type == 3) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == 8888) {
            shopingCarts.get(position_group).getGoodsInfo().get(position).setGoods_attr_name(data.getStringExtra("pro_value"));
            shopingCarts.get(position_group).getGoodsInfo().get(position).setProduct_id(data.getStringExtra("product_id"));
            shopingCarts.get(position_group).getGoodsInfo().get(position).setNum(String.valueOf(data.getIntExtra("num", 0)));
            shopingCarts.get(position_group).getGoodsInfo().get(position).setGoods_img(data.getStringExtra("image"));
            cartAdapter.notifyDataSetChanged();
        }
    }

    private class CartAdapter extends BaseAdapter {

        private CartVh cartVh;

        @Override
        public int getCount() {
            return shopingCarts.size();
        }

        @Override
        public ShopingCart getItem(int i) {
            return shopingCarts.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            sc = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_cart_lv_hzj, null);
                cartVh = new CartVh();
                ViewUtils.inject(cartVh, view);
                view.setTag(cartVh);
            } else {
                cartVh = (CartVh) view.getTag();
            }

            // 判断店铺中的商品是否被全部选中
            if (shopingCarts.get(i).isAllCheck()) {
                cartVh.mell_goods_all_select_iv.setImageResource(R.drawable.icon_cart_goods_selected);
            } else {
                cartVh.mell_goods_all_select_iv.setImageResource(R.drawable.icon_cart_goods_unselect);
            }

            cartVh.cart_mell_name_tv.setText(sc.getMerchant_name());
            // 点击店铺名称直接跳转到店铺首页
            cartVh.cart_mell_name_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("mell_id", getItem(i).getMerchant_id());
                    startActivity(MellInfoAty.class, bundle);
                }
            });

            cartVh.cart_mell_goods_lv.setAdapter(new CartGoodsAdapter(shopingCarts, i));

            cartVh.mell_goods_all_select_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shopingCarts.get(i).isAllCheck()) {
                        shopingCarts.get(i).setAllCheck(false);
                        for (CartGoods cg : shopingCarts.get(i).getGoodsInfo()) {
                            cg.setCheck(false);
                            BigDecimal price = new BigDecimal(cg.getShop_price());
                            price = price.multiply(new BigDecimal(cg.getNum()));
                            all_price = all_price.subtract(price);
                            if (all_price.compareTo(new BigDecimal("0.00")) <= 0) {
                                all_price = new BigDecimal("0.00");
                            }
                        }
                        if (all > 0) {
                            all--;
                        } else {
                            all = 0;
                        }
                        // 取消全选
                        cart_select_all_cb.setChecked(false);
                    } else {
                        shopingCarts.get(i).setAllCheck(true);
                        for (CartGoods cg : shopingCarts.get(i).getGoodsInfo()) {
                            if (!cg.isCheck()) {
                                cg.setCheck(true);
                                BigDecimal price = new BigDecimal(cg.getShop_price());
                                price = price.multiply(new BigDecimal(cg.getNum()));
                                all_price = all_price.add(price);
                            }
                        }
                        all++;
                        // 判断是否被全选
                        if (all >= shopingCarts.size()) {
                            cart_select_all_cb.setChecked(true);
                        }
                    }
                    toChangePrice();
                    notifyDataSetChanged();
                }
            });
            return view;
        }

        class CartVh {
            /**
             * 是否被选中
             */
            @ViewInject(R.id.mell_goods_all_select_iv)
            private ImageView mell_goods_all_select_iv;
            /**
             * 进入商店
             */
            @ViewInject(R.id.cart_into_mell_layout)
            private LinearLayout cart_into_mell_layout;

            /**
             * 店铺名称
             */
            @ViewInject(R.id.cart_mell_name_tv)
            private TextView cart_mell_name_tv;

            /**
             * 商品列表
             */
            @ViewInject(R.id.cart_mell_goods_lv)
            private ListViewForScrollView cart_mell_goods_lv;

        }
    }

    /**
     * 商品适配器
     */
    private class CartGoodsAdapter extends BaseAdapter {
        private List<ShopingCart> list;
        private List<CartGoods> cartGoodses;
        private CGVH cgvh;
        private int select_num = 0;
        private int groupPosion = 0;

        public CartGoodsAdapter(List<ShopingCart> list, int groupPosion) {
            this.list = list;
            this.cartGoodses = list.get(groupPosion).getGoodsInfo();
            this.groupPosion = groupPosion;
        }

        @Override
        public int getCount() {
            return cartGoodses.size();
        }

        @Override
        public CartGoods getItem(int i) {
            return cartGoodses.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final CartGoods cg = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_child_cart_lv_hzj, null);
                cgvh = new CGVH();
                ViewUtils.inject(cgvh, view);
                view.setTag(cgvh);
            } else {
                cgvh = (CGVH) view.getTag();
            }
            if (cg.isCheck()) {
                cgvh.cart_goods_select_iv.setImageResource(R.drawable.icon_cart_goods_selected);
                for (ShopingCart cat : shopingCarts) {
                    if (!cat.isAllCheck()) {
                        cart_select_all_cb.setChecked(false);
                    } else {
                        if (sc.isAllCheck()) {
                            cart_select_all_cb.setChecked(true);
                        }
                    }
                }

            } else {
                cgvh.cart_goods_select_iv.setImageResource(R.drawable.icon_cart_goods_unselect);
                cart_select_all_cb.setChecked(false);
            }
            Glide.with(getActivity()).load(getItem(i).getGoods_img()).into(cgvh.im_goods);
            cgvh.goods_name_tv.setText(cg.getGoods_name());
            // 属性

            String goodsAttrNameStr = cg.getGoods_attr_name();
            if (!goodsAttrNameStr.isEmpty() && !goodsAttrNameStr.equals("")) {
                int lastIndexOf = goodsAttrNameStr.lastIndexOf("+");
                if (lastIndexOf == goodsAttrNameStr.length() - 1) {
                    goodsAttrNameStr = goodsAttrNameStr.substring(0, lastIndexOf);
                }
            }

            String return_integral = cg.getReturn_integral();
            String jifen="";
            if (!TextUtils.isEmpty(return_integral) && Double.parseDouble(return_integral)!=0){
                jifen = "（赠送:" + return_integral+ "积分）";
            }
            cgvh.goods_attrs_tv.setText(Html.fromHtml("<font color=#333333>" + goodsAttrNameStr + "</font> " + "<font color=#FF0000>" + jifen + "</font>"));
            String goods_attr_name = cg.getGoods_attr_name(); // 获取规格字符串
            if (!goods_attr_name.isEmpty() && !goods_attr_name.equals("")) { // 如果名称不为空则查找字符串最后的加号
                if (goods_attr_name.substring(goods_attr_name.length() - 1, goods_attr_name.length()).equals("+")) { // 如果字符串最后一位是“+”
                    goods_attr_name = goods_attr_name.substring(0, goods_attr_name.length() - 1); // 那么截取前面的字符串，去掉加号
                }
            }
            cgvh.reset_goods_attrs_tv.setText(goods_attr_name + "(库存：" + cg.getGoods_num() + ")");
            cgvh.cart_goods_price_tv.setText("¥" + cg.getShop_price());//当前物品单价

            // 当前选择物品数量
            cgvh.cart_goods_num_tv.setText("x" + String.valueOf(cg.getNum()));
            cgvh.operation_goods_num_tv.setText(String.valueOf(cg.getNum()));
            if (canEdit) {
                cgvh.cart_num_attrs_layout.setVisibility(View.VISIBLE);
                cgvh.cart_goods_info_layout.setVisibility(View.GONE);
            } else {
                cgvh.cart_num_attrs_layout.setVisibility(View.GONE);
                cgvh.cart_goods_info_layout.setVisibility(View.VISIBLE);
            }

            cgvh.cart_goods_select_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cg.isCheck()) {
                        cg.setCheck(false);
                        // 获取价格
                        BigDecimal price = new BigDecimal(cg.getShop_price());
                        // 计算选中商品价格
                        price = price.multiply(new BigDecimal(cg.getNum()));
                        // 计算总价
                        all_price = all_price.subtract(price);
                        if (all_price.compareTo(new BigDecimal("0.00")) <= 0) {
                            all_price = new BigDecimal("0.00");
                        }
                    } else {
                        cg.setCheck(true);
                        // 获取价格
                        BigDecimal price = new BigDecimal(cg.getShop_price());
                        // 计算单件商品价格
                        price = price.multiply(new BigDecimal(cg.getNum()));
                        // 计算总价
                        all_price = all_price.add(price);
                    }
                    // 重新加载数据
                    for (CartGoods cartGoods : list.get(groupPosion).getGoodsInfo()) {
                        if (cartGoods.isCheck()) {
                            select_num++;
                        }
                    }
                    if (select_num >= getCount()) {
                        list.get(groupPosion).setAllCheck(true);
                    } else {
                        list.get(groupPosion).setAllCheck(false);
                    }
                    toChangePrice();
                    cartAdapter.notifyDataSetChanged();
                }
            });

            // 数量加
            cgvh.goods_num_increase_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = Integer.parseInt(cg.getNum());
                    num++;
                    if (num <= Integer.parseInt(cg.getGoods_num())) {
                        cg.setNum(String.valueOf(num));
                        notifyDataSetChanged();
                    }

                }
            });
            // 数量减
            cgvh.goods_num_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = Integer.parseInt(cg.getNum());
                    if (num > 1) {
                        num--;
                        cg.setNum(String.valueOf(num));
                        notifyDataSetChanged();
                    }
                }
            });

            // 选择商品属性
            cgvh.reset_goods_attrs_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    position = i;
                    position_group = groupPosion;
                    Goods.attrApi(getItem(i).getGoods_id(), "", CartFgt.this);
                    showProgressDialog();
                }
            });
            cgvh.lin_goods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ticket_buy_id", getItem(i).getGoods_id());
                    bundle.putInt("from", 1);
                    startActivity(TicketGoodsDetialsAty.class, bundle);
                }
            });
            return view;
        }

        class CGVH {
            /**
             * 商品被选中
             */
            @ViewInject(R.id.cart_goods_select_iv)
            private ImageView cart_goods_select_iv;

            /**
             * 商品标题
             */
            @ViewInject(R.id.goods_name_tv)
            private TextView goods_name_tv;
            /**
             * 商品属性
             */
            @ViewInject(R.id.goods_attrs_tv)
            private TextView goods_attrs_tv;
            /**
             * 显示返回的积分
             */
            @ViewInject(R.id.goods_jifen_tv)
            private TextView goods_jifen_tv;
            /**
             * 商品价格
             */
            @ViewInject(R.id.cart_goods_price_tv)
            private TextView cart_goods_price_tv;
            /**
             * 商品数量
             */
            @ViewInject(R.id.cart_goods_num_tv)
            private TextView cart_goods_num_tv;

            /**
             * 商品基本属性布局
             */
            @ViewInject(R.id.cart_goods_info_layout)
            private LinearLayout cart_goods_info_layout;
            /**
             * 商品数量，属性操作布局
             */
            @ViewInject(R.id.cart_num_attrs_layout)
            private LinearLayout cart_num_attrs_layout;

            /**
             * 数量减
             */
            @ViewInject(R.id.goods_num_reduce)
            private ImageView goods_num_reduce;
            /**
             * 数量操作
             */
            @ViewInject(R.id.operation_goods_num_tv)
            private TextView operation_goods_num_tv;

            /**
             * 数量加
             */
            @ViewInject(R.id.goods_num_increase_tv)
            private ImageView goods_num_increase_tv;

            @ViewInject(R.id.reset_goods_attrs_tv)
            private TextView reset_goods_attrs_tv;
            @ViewInject(R.id.im_goods)
            private ImageView im_goods;
            /**
             * 跳转商品详情
             */
            @ViewInject(R.id.lin_goods)
            private LinearLayout lin_goods;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("更新购物车列表")) {
            if (Config.isLogin()) {

            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        requestData(); // TODO 进入的时候重新加载
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /**
         * 解除事件总线
         */
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
