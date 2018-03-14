package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.shoppingCart.BuildOrderAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.view.flowlayout.FlowLayout;
import com.txd.hzj.wjlp.view.flowlayout.TagAdapter;
import com.txd.hzj.wjlp.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：下午 7:45
 * 描述：商品属性页面
 * ===============Txunda===============
 */
public class GoodsAttributeAty extends BaseAty {
    @ViewInject(R.id.goods_price_tv)
    private TextView goods_price_tv;

    @ViewInject(R.id.goods_attr_lv)
    private ListView goods_attr_lv;

    /**
     * 首页，客服。。。
     */
    @ViewInject(R.id.at_left_lin_layout)
    private LinearLayout at_left_lin_layout;

    /**
     * 假如购物车
     */
    @ViewInject(R.id.goods_into_cart_tv)
    private TextView goods_into_cart_tv;
    /**
     * 假如购物车
     */
    @ViewInject(R.id.to_buy_must_tv)
    private TextView to_buy_must_tv;
    @ViewInject(R.id.imageview)
    private ImageView imageview;
    private GoodsAttrsAdapter goodsAttrsAdapter;
    private int from = 0;
    private String price = "";
    private String imageurl = "";
    @ViewInject(R.id.tv_num)
    private TextView tv_num;
    private int num = 0;
    private boolean is_go = false;
    private String goods_id = "";
    private String mid = "";
    private String group_buy_id = "";
    private String type;
    List<GoodsAttr> list;
    List<Goods_val> list_val;
    private int maxNumber;
    @ViewInject(R.id.tv_kucun)
    private TextView tv_kucun;
    private String pro_id;
    private String pro_value;
    int pos1;
    int pos2;
    String goods_attr;
    Goods_val val;
    SparseArray<String> list_attrs = new SparseArray<String>();
    private String is_attr;
    private String image;
    private List<Map<String, String>> mapList;
    private int position = 0;

    @Override
    @OnClick({R.id.to_buy_must_tv, R.id.im_jian, R.id.im_jia})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_buy_must_tv:// 立即购买，确定
                L.e("cccccc"+list.size()+"--"+pro_id+"--"+from);
                if (2 == from) {
                    Intent intent = new Intent();
                    intent.putExtra("num", num);

                    if (ListUtils.isEmpty(list)) {
                        intent.putExtra("product_id", "");
                        intent.putExtra("pro_value", "");
                        intent.putExtra("image", "");
                        intent.putExtra("num", num);
                        setResult(RESULT_OK, intent);
                        finish();
                        return;
                    }
                    if (!TextUtils.isEmpty(pro_id)) {
                        intent.putExtra("product_id", pro_id);
                        intent.putExtra("pro_value", pro_value);
                        intent.putExtra("num", num);
                        intent.putExtra("image", image);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        showToast("库存不足！");
                    }
                    return;
                }
                if (4 == from) {

                    Intent intent = new Intent();
                    if (!TextUtils.isEmpty(pro_id)) {
                        L.e("cccc444");
                        intent.putExtra("product_id", pro_id);
                        intent.putExtra("pro_value", pro_value);
                        intent.putExtra("num", num);
                        intent.putExtra("shop_price", val.getShop_price());
                        intent.putExtra("market_price", val.getMarket_price());
                        intent.putExtra("settlement_price", val.getSettlement_price());
                        intent.putExtra("red_return_integral", val.getRed_return_integral());
                        intent.putExtra("discount", val.getDiscount());
                        intent.putExtra("yellow_discount", val.getYellow_discount());
                        intent.putExtra("blue_discount", val.getBlue_discount());
                        intent.putExtra("wy_price", val.getWy_price());
                        intent.putExtra("yx_price", val.getYx_price());
                        intent.putExtra("data", mapList.get(position).get("dj_ticket"));
                        setResult(0x0002, intent);
                        finish();
                    } else {
                        showToast("库存不足！");
                    }
                    return;

                }
                goodAttChange();
                break;
            case R.id.im_jia:
                if (num >= maxNumber) {
                    return;
                }
                num++;
                tv_num.setText(String.valueOf(num));
                break;
            case R.id.im_jian:
                if (num <= 1) {
                    return;
                }
                num--;
                tv_num.setText(String.valueOf(num));
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_goods_attribute;
    }

    @Override
    protected void initialized() {
        from = getIntent().getIntExtra("from", 0);
        goods_id = getIntent().getStringExtra("goods_id");
        type = getIntent().getStringExtra("type");
        group_buy_id = getIntent().getStringExtra("group_buy_id");
        is_attr = getIntent().getStringExtra("is_attr");
        if (1 == from || 0 == from) {
            if (0 == from) {
                is_go = true;
                to_buy_must_tv.setText("立即购买");
                from = 1;
                String a[] = goods_id.split("-");
                goods_id = a[0];
                mid = a[1];
            } else {
                to_buy_must_tv.setText("加入购物车");
            }
            goods_into_cart_tv.setVisibility(View.GONE);
            at_left_lin_layout.setVisibility(View.GONE);
        }
        if (2 == from) {
            to_buy_must_tv.setText("修改");
            goods_into_cart_tv.setVisibility(View.GONE);
            at_left_lin_layout.setVisibility(View.GONE);
            num = getIntent().getIntExtra("num", 0);
            tv_num.setText(String.valueOf(num));
        }
        if (3 == from) {
            to_buy_must_tv.setText("参团");
            goods_into_cart_tv.setVisibility(View.GONE);
            at_left_lin_layout.setVisibility(View.GONE);
            num = getIntent().getIntExtra("num", 0);
            tv_num.setText(String.valueOf(num));
        }
        if (4 == from) {
            to_buy_must_tv.setText("确定");
            goods_into_cart_tv.setVisibility(View.GONE);
            at_left_lin_layout.setVisibility(View.GONE);
            num = getIntent().getIntExtra("num", 0);
            tv_num.setText(String.valueOf(num));
        }
    }


    @Override
    protected void requestData() {
        imageurl = getIntent().getStringExtra("imageurl");
        price = getIntent().getStringExtra("price");
        Glide.with(this).load(imageurl).into(imageview);
        ChangeTextViewStyle.getInstance().forGoodsPrice24(this, goods_price_tv, "￥" + price);
        list = GsonUtil.getObjectList(getIntent().getStringExtra("goods_attr"), GoodsAttr.class);
        list_val = GsonUtil.getObjectList(getIntent().getStringExtra("goods_val"), Goods_val.class);
        mapList = JSONUtils.parseKeyAndValueToMapList(getIntent().getStringExtra("goods_val"));


        String string[] = is_attr.split("-");
        is_attr = string[0];
        if (!ListUtils.isEmpty(list) && is_attr.equals("1")) {
            goodsAttrsAdapter = new GoodsAttrsAdapter();
            goods_attr_lv.setAdapter(goodsAttrsAdapter);


        } else {
            maxNumber = Integer.parseInt(string[1]);
            tv_kucun.setText("(库存：" + string[1] + ")");
            num = 1;
            tv_num.setText(String.valueOf(num));
            list.clear();
        }
        if (list.size() == 1) {
            for (Goods_val goods_val : list_val) {
                String v = list.get(0).getFirst_list_val().get(0).getVal() + "+";
                if (v.equals(goods_val.getArrtValue())) {
                    GoodsAttributeAty.this.val = goods_val;
                    tv_kucun.setText("(库存：" + goods_val.getGoods_num() + ")");
                    maxNumber = Integer.parseInt(goods_val.getGoods_num());
                    Glide.with(GoodsAttributeAty.this).load(goods_val.getGoods_img()).into(imageview);
                    ChangeTextViewStyle.getInstance().forGoodsPrice24(GoodsAttributeAty.this, goods_price_tv, "￥" + goods_val.getShop_price());
                    tv_num.setText(String.valueOf(1));
                    pro_value = goods_val.getArrtValue();
                    image = goods_val.getGoods_img();
                    pro_id = goods_val.getId();
                    num = 1;
                    break;
                }
            }
        }

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        showToast("添加成功");
        finish();
    }
    //直接购买
    private void goodAttChange() {
        if (ListUtils.isEmpty(list)) {
            if (is_go) {
                Intent intent = new Intent();
                intent.putExtra("type", type);
                intent.putExtra("mid", mid);
                intent.putExtra("goods_id", goods_id);
                intent.putExtra("group_buy_id", group_buy_id);
                intent.putExtra("num", String.valueOf(num));
                intent.setClass(this, BuildOrderAty.class);
                startActivity(intent);
                finish();
                return;
            }
            RequestParams params = new RequestParams();
            ApiTool2 apiTool2 = new ApiTool2();
            params.addBodyParameter("goods_id", goods_id);
            params.addBodyParameter("num", String.valueOf(num));
            apiTool2.postApi(Config.BASE_URL + "Cart/addCart", params, this);
            return;
        }

        if (!TextUtils.isEmpty(pro_id)) {

            if (is_go) {

                Intent intent = new Intent();
                intent.putExtra("mid", mid);
                intent.putExtra("type", type);
                intent.putExtra("goods_id", goods_id);
                intent.putExtra("group_buy_id", group_buy_id);
                intent.putExtra("num", String.valueOf(num));
                intent.putExtra("product_id", pro_id);
                L.e("cccc"+mid+"--"+type+"--"+goods_id+"--"+group_buy_id+"--"+num+"--"+pro_id);
//                intent.setClass(this, BuildOrderAty.class);
                setResult(0x0001,intent);
//                startActivity(intent);
                finish();
                return;
            }
            RequestParams params = new RequestParams();
            ApiTool2 apiTool2 = new ApiTool2();
            params.addBodyParameter("goods_id", goods_id);
            params.addBodyParameter("product_id", pro_id);
            params.addBodyParameter("num", String.valueOf(num));
            apiTool2.postApi(Config.BASE_URL + "Cart/addCart", params, this);
        } else {
            showToast("库存不足！");
        }
    }


    private class GoodsAttrsAdapter extends BaseAdapter {

        private AttrsVh avh;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public GoodsAttr getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (null == view) {
                view = LayoutInflater.from(GoodsAttributeAty.this).inflate(R.layout.item_goods_attr_lv, null);
                avh = new AttrsVh();
                view.setTag(avh);
                ViewUtils.inject(avh, view);
            } else {
                avh = (AttrsVh) view.getTag();
            }
            avh.goods_attrs_title.setText(getItem(i).getFirst_list_name());
            TagAdapter tagAdapter = new TagAdapter<GoodsAttr.valBean>(getItem(i).getFirst_list_val()) {
                @Override
                public View getView(FlowLayout parent, int position, GoodsAttr.valBean goodsAttrses) {
                    TextView tv = (TextView) LayoutInflater.from(GoodsAttributeAty.this).inflate(R.layout
                                    .item_goods_attrs_tfl,
                            parent, false);
                    tv.setText(goodsAttrses.getVal());
                    if (TextUtils.isEmpty(getItem(i).getFirst_list_val().get(position).getStatus())) {
                        getItem(i).getFirst_list_val().get(position).setStatus("2");
                    }
//                    if (getCount() == 1) {
                        getItem(0).getFirst_list_val().get(0).setStatus("1");

//                    }
                    switch (getItem(i).getFirst_list_val().get(position).getStatus()) {
                        case "1":
                            tv.setEnabled(true);
                            tv.setClickable(true);
                            tv.setBackgroundResource(R.drawable.shape_tag_checked_bg);
                            tv.setTextColor(Color.WHITE);
                            break;
                        case "2":
                            tv.setEnabled(false);
                            tv.setClickable(false);
                            tv.setBackgroundResource(R.drawable.shape_tag_normal_bg);
                            tv.setTextColor(Color.BLACK);
                            break;
                        case "3":
                            tv.setEnabled(true);
                            tv.setClickable(true);
                            tv.setTextColor(Color.WHITE);
                            tv.setBackgroundResource(R.drawable.shape_tag_normal_bg2);
                            break;
                        default:
                            tv.setEnabled(true);
                            tv.setClickable(true);
                            tv.setTextColor(Color.WHITE);
                            tv.setBackgroundResource(R.drawable.shape_tag_normal_bg2);
                            break;
                    }
                    return tv;
                }
            };
            if (i == 0) {
                tagAdapter.setSelectedList(0);
//                getItem(i).getFirst_list_val().get(0).setStatus("1");
            }
            avh.goods_attr_tfl.setAdapter(tagAdapter);

            avh.goods_attr_tfl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    if( getItem(i).getFirst_list_val().get(0).getStatus().equals("3")){
                        return true;
                    }

                    getItem(i).getFirst_list_val().get(position).setStatus("1");
                    for (int j = 0; j < getItem(i).getFirst_list_val().size(); j++) {
                        if (position == j) {
                            continue;
                        }
                        getItem(i).getFirst_list_val().get(j).setStatus("2");
                    }
                    goods_attr = list.get(i).getFirst_list_val().get(position).getVal();
                    try {
                        if (list.get(i + 1).getFirst_list_val().size() == 1) {
                            for (Goods_val val : list_val) {
                                String s = goods_attr + "+" + list.get(i + 1).getFirst_list_val().get(0).getVal();
                                if (!val.arrtValue.contains(s)) {
                                    getItem(i).getFirst_list_val().get(position).setStatus("3");
                                } else {
                                    getItem(i).getFirst_list_val().get(position).setStatus("1");
                                    break;
                                }
                            }
                            list_attrs.put(i + 1, list.get(i + 1).getFirst_list_val().get(0).getVal());
                        } else {
                            for (int j = 0; j < list.get(i + 1).getFirst_list_val().size(); j++) {
                                for (Goods_val val : list_val) {
                                    String s = goods_attr + "+" + list.get(i + 1).getFirst_list_val().get(j).getVal();
                                    if (!val.arrtValue.contains(s)) {
                                        getItem(i + 1).getFirst_list_val().get(j).setStatus("3");
                                    } else {
                                        getItem(i + 1).getFirst_list_val().get(j).setStatus("2");
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        getItem(i).getFirst_list_val().get(position).setStatus("1");
                    }

                    list_attrs.put(i, goods_attr);
                    if (list_attrs.size() == list.size()) {
                        StringBuffer attrs = new StringBuffer();
                        for (int k = 0; k < list_attrs.size(); k++) {
//                            if (k == list_attrs.size() - 1) {
                            attrs.append(list_attrs.get(k) + "+");
//                            } else {
//                                attrs.append(list_attrs.get(k));
//                            }
                        }
                        for (Goods_val val : list_val) {
                            if (attrs.toString().contains(val.getArrtValue())) {
                                GoodsAttributeAty.this.val = val;
                                tv_kucun.setText("(库存：" + val.getGoods_num() + ")");
                                maxNumber = Integer.parseInt(val.getGoods_num());
                                Glide.with(GoodsAttributeAty.this).load(val.getGoods_img()).into(imageview);
                                ChangeTextViewStyle.getInstance().forGoodsPrice24(GoodsAttributeAty.this, goods_price_tv, "￥" + val.getShop_price());
                                tv_num.setText(String.valueOf(1));
                                pro_value = val.getArrtValue();
                                image = val.getGoods_img();
                                pro_id = val.getId();
                                num = 1;
                                position++;
                                break;
                            } else {
                                maxNumber = 0;
                                tv_kucun.setText("(库存：0)");
                                num = 0;
                                tv_num.setText(String.valueOf(maxNumber));
                                pro_id = "";
                                position = -1;
                            }
                        }


                    } else {
                        pro_id = "";
                    }
                    notifyDataSetChanged();
                    return true;
                }
            });

            //多选用的  这个在单选中貌似并没有用(多选记录下标)
//            avh.goods_attr_tfl.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
//                @Override
//                public void onSelected(Set<Integer> selectPosSet) {
//                    L.e("=====选中的=====", selectPosSet.toString());
//                    showToast(selectPosSet.toString());
//                }
//            });
            return view;
        }

        class AttrsVh {
            /**
             * 标题
             */
            @ViewInject(R.id.goods_attrs_title)
            private TextView goods_attrs_title;
            /**
             * 属性值TagFlowLayout
             */
            @ViewInject(R.id.goods_attr_tfl)
            private TagFlowLayout goods_attr_tfl;
        }

    }

    class GoodsAttr {

        /**
         * first_list_name : 颜色
         * first_list_val : ["红色","黄色"]
         */

        private String first_list_name;
        private List<valBean> first_list_val;


        public String getFirst_list_name() {
            return first_list_name;
        }

        public void setFirst_list_name(String first_list_name) {
            this.first_list_name = first_list_name;
        }

        public List<valBean> getFirst_list_val() {
            return first_list_val;
        }

        public void setFirst_list_val(List<valBean> first_list_val) {
            this.first_list_val = first_list_val;

        }


        class valBean {
            String val;
            //            boolean check;
            String status = "";

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getVal() {
                return val;
            }

            public void setVal(String val) {
                this.val = val;
            }
        }

    }


    class Goods_val {

        /**
         * id : 118
         * goods_id : 59
         * attr_combine_id : 79+80+81
         * arrt_name : 红
         * arrt_value : L+工装
         * settlement_price : 777.00
         * shop_price : 777.00
         * market_price : 777.00
         * goods_num : 777
         * goods_code : rtr564
         * goods_img : 11274
         * is_default : 1
         * create_time : 0
         * all_goods_num : 777
         * arrtValue : 红+L+工装
         */

        private String id;
        private String goods_id;
        private String attr_combine_id;
        private String arrt_name;
        private String arrt_value;
        private String settlement_price;
        private String shop_price;
        private String market_price;
        private String goods_num;
        private String goods_code;
        private String goods_img;
        private String is_default;
        private String create_time;
        private String all_goods_num;
        private String arrtValue;
        private String discount;
        private String red_return_integral;
        private String yellow_discount;
        private String blue_discount;
        private String wy_price;
        private String yx_price;
        private List<dj_ticket> dt;

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getRed_return_integral() {
            return red_return_integral;
        }

        public void setRed_return_integral(String red_return_integral) {
            this.red_return_integral = red_return_integral;
        }

        public String getYellow_discount() {
            return yellow_discount;
        }

        public void setYellow_discount(String yellow_discount) {
            this.yellow_discount = yellow_discount;
        }

        public String getBlue_discount() {
            return blue_discount;
        }

        public void setBlue_discount(String blue_discount) {
            this.blue_discount = blue_discount;
        }

        public String getWy_price() {
            return wy_price;
        }

        public void setWy_price(String wy_price) {
            this.wy_price = wy_price;
        }

        public String getYx_price() {
            return yx_price;
        }

        public void setYx_price(String yx_price) {
            this.yx_price = yx_price;
        }

        public List<dj_ticket> getDj_ticket() {
            return dt;
        }

        public void setDj_ticket(List<dj_ticket> dj_ticket) {
            this.dt = dj_ticket;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getAttr_combine_id() {
            return attr_combine_id;
        }

        public void setAttr_combine_id(String attr_combine_id) {
            this.attr_combine_id = attr_combine_id;
        }

        public String getArrt_name() {
            return arrt_name;
        }

        public void setArrt_name(String arrt_name) {
            this.arrt_name = arrt_name;
        }

        public String getArrt_value() {
            return arrt_value;
        }

        public void setArrt_value(String arrt_value) {
            this.arrt_value = arrt_value;
        }

        public String getSettlement_price() {
            return settlement_price;
        }

        public void setSettlement_price(String settlement_price) {
            this.settlement_price = settlement_price;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getGoods_code() {
            return goods_code;
        }

        public void setGoods_code(String goods_code) {
            this.goods_code = goods_code;
        }

        public String getGoods_img() {
            return goods_img;
        }

        public void setGoods_img(String goods_img) {
            this.goods_img = goods_img;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getAll_goods_num() {
            return all_goods_num;
        }

        public void setAll_goods_num(String all_goods_num) {
            this.all_goods_num = all_goods_num;
        }

        public String getArrtValue() {
            return arrtValue;
        }

        public void setArrtValue(String arrtValue) {
            this.arrtValue = arrtValue;
        }
    }


    public class Goods_Attr {

        /**
         * arrt_name : 尺码1
         * value_list : [{"arrt_value":"X","shop_price":"999.00","abs_url":""},{"arrt_value":"L","shop_price":"999.00","abs_url":""}]
         */

        private String arrt_name;
        private List<ValueListBean> value_list;

        public String getArrt_name() {
            return arrt_name;
        }

        public void setArrt_name(String arrt_name) {
            this.arrt_name = arrt_name;
        }

        public List<ValueListBean> getValue_list() {
            return value_list;
        }

        public void setValue_list(List<ValueListBean> value_list) {
            this.value_list = value_list;
        }

        class ValueListBean {
            /**
             * arrt_value : X
             * shop_price : 999.00
             * abs_url :
             * pro_id :
             * goods_num :
             */

            private String arrt_value;
            private String shop_price;
            private String abs_url;
            private String pro_id;
            private String goods_num;


            public String getArrt_value() {
                return arrt_value;
            }

            public void setArrt_value(String arrt_value) {
                this.arrt_value = arrt_value;
            }

            public String getShop_price() {
                return shop_price;
            }

            public void setShop_price(String shop_price) {
                this.shop_price = shop_price;
            }

            public String getAbs_url() {
                return abs_url;
            }

            public void setAbs_url(String abs_url) {
                this.abs_url = abs_url;
            }

            public String getPro_id() {
                return pro_id;
            }

            public void setPro_id(String pro_id) {
                this.pro_id = pro_id;
            }

            public String getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(String goods_num) {
                this.goods_num = goods_num;
            }
        }
    }

    public class dj_ticket {

        String type;
        String discount_desc;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDiscount_desc() {
            return discount_desc;
        }

        public void setDiscount_desc(String discount_desc) {
            this.discount_desc = discount_desc;
        }
    }
}

