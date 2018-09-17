package com.txd.hzj.wjlp.mellonLine.gridClassify;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.commodity.FirstListBean;
import com.txd.hzj.wjlp.bean.commodity.FirstListValBean;
import com.txd.hzj.wjlp.bean.commodity.FirstValBean;
import com.txd.hzj.wjlp.http.cart.Cart;
import com.txd.hzj.wjlp.shoppingCart.BuildOrderAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.view.flowlayout.FlowLayout;
import com.txd.hzj.wjlp.view.flowlayout.TagAdapter;
import com.txd.hzj.wjlp.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：下午 7:45
 * 描述：商品属性页面
 * <p>
 * from
 * 直接购买0
 * 购物车1
 * 设置 4
 */
public class GoodsAttributeAty extends BaseAty {
    public final String SELECTED = "1", UNSELECT = "2", CANNOT_SELECT = "3";
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
    /**
     * from
     * 直接购买0
     * 购物车1
     * 设置 4
     */
    private int from = 0;
    //    private String price = "";
    //    private String imageurl = "";
    @ViewInject(R.id.et_num)
    private EditText et_num;
    private int num = 0; // 输入的购买件数
    private boolean is_go = false;
    private String goods_id = "";
    private String mid = "";
    private String group_buy_id = "";
    /**
     * "0"   主界面购物车, "1" 票券 "2" 拼单单独购买 "3" 拼单参团 "4" 参团 "5" 限量购  "6" 限量购 无界预购 "10" 限量购 积分商店 "11" 搭配购
     */
    private String type;
    private List<FirstListBean> list;
    private List<FirstValBean> list_val;
    private long maxNumber = 0; // 库存件数
    @ViewInject(R.id.tv_kucun)
    private TextView tv_kucun;
    @ViewInject(R.id.lite)
    private View lite;
    @ViewInject(R.id.lite1)
    private View lite1;
    private String pro_id;
    private String pro_value;
    int pos1;
    int pos2;
    //    String goods_attr;
    FirstValBean val;
    SparseArray<String> list_attrs = new SparseArray<String>();
    private String is_attr;
    private String image;
    //    private List<Map<String, String>> mapList;
    private List<Map<Integer, String>> recordMutilMapList;
    private Map<Integer, String> recordMutilMap;
    private int position = 0;
    @ViewInject(R.id.tv_xg)//限购
    private TextView tv_xg;

    @Override
    @OnClick({R.id.to_buy_must_tv, R.id.im_jian, R.id.im_jia})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_buy_must_tv:// 立即购买，确定
                if (maxNumber == 0) {
                    showErrorTip("库存不足请您下次再买");
                    return;
                }
                if (!"1".equals(groupType)) {

                    String et_string = et_num.getText().toString().trim();
                    if (et_string.equals("")) {
                        et_string = "1";
                        num = Integer.parseInt(et_string);
                    }

                } else {
                    num = 1;
                }
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
                    intent.putExtra("type", type);
                    if (!TextUtils.isEmpty(pro_id)) {
                        intent.putExtra("product_id", pro_id);
                        intent.putExtra("pro_value", pro_value);
                        intent.putExtra("num", num);
                        intent.putExtra("shop_price", val.getShop_price());
                        intent.putExtra("market_price", val.getMarket_price());
                        intent.putExtra("settlement_price", val.getSettlement_price());
                        if (type.equals("3") || "4".equals(type)) {//拼单或者参团
                            intent.putExtra("red_return_integral", val.getIntegral());
                            intent.putExtra("p_integral", val.getP_integral()); //"原积分"
                            intent.putExtra("p_shop_price", val.getP_shop_price());
                        } else if (type.equals("10")) {//积分商店
                            intent.putExtra("use_integral", val.getUse_integral());
                            intent.putExtra("integral_buy_id", val.getIntegral_buy_id());
                        } else {
                            intent.putExtra("red_return_integral", val.getRed_return_integral());

                        }
                        intent.putExtra("discount", val.getDiscount());
                        intent.putExtra("yellow_discount", val.getYellow_discount());
                        intent.putExtra("blue_discount", val.getBlue_discount());
                        intent.putExtra("wy_price", val.getWy_price());
                        intent.putExtra("yx_price", val.getYx_price());
                        intent.putExtra("goods_num", val.getGoods_num());
                        //                        intent.putExtra("data", (Serializable) list_val.get(position).getDj_ticket());
                        intent.putExtra("data", (Serializable) val.getDj_ticket());
                        intent.putExtra("mid", mid);
                        intent.putExtra("type", type);
                        intent.putExtra("goods_id", val.getGoods_id());
                        if (group_buy_id.contains("-")) {
                            String[] string = group_buy_id.split("-");
                            group_buy_id = string[0];
                            order_id = string[1];
                        }
                        if (!TextUtils.isEmpty(order_id)) {
                            intent.putExtra("order_id", order_id);
                        }
                        intent.putExtra("group_buy_id", val.getGroup_buy_id());
                        if (getIntent().hasExtra("group_type")) {//体验拼单
                            intent.putExtra("group_type", getIntent().getStringExtra("group_type"));
                        }
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
                if (tv_xg.getVisibility() == View.VISIBLE && num >= Long.parseLong(val.getMax_num())) {
                    return;
                }
                if (num >= maxNumber) {
                    return;
                }
                num++;
                et_num.setText(String.valueOf(num));
                et_num.setSelection(et_num.getText().length());
                break;
            case R.id.im_jian:
                if (num <= 1) {
                    return;
                }
                num--;
                et_num.setText(String.valueOf(num));
                et_num.setSelection(et_num.getText().length());
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_goods_attribute;
    }

    private String order_id;

    @Override
    protected void initialized() {
        et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                et_num.removeTextChangedListener(this);
                // 文字改变之后
                String numStr = s.toString().trim();
                if (!numStr.isEmpty()) {
                    if (numStr.equals("0")) {
                        numStr = "1";
                    }
                    num = Integer.parseInt(numStr);
                    if (tv_xg.getVisibility() == View.VISIBLE && num >= Long.parseLong(val.getMax_num())) {
                        num = Integer.parseInt(val.getMax_num());
                    } else if (num > maxNumber) {
                        num = (int) maxNumber;
                    }
                    et_num.setText(String.valueOf(num));
                    et_num.setSelection(et_num.getText().length());
                }
                et_num.addTextChangedListener(this);
            }
        });
        from = getIntent().getIntExtra("from", 0);
        goods_id = getIntent().getStringExtra("goods_id");
        type = getIntent().getStringExtra("type");
        if (getIntent().hasExtra("group_buy_id")) { // 如果传入的值中包含Key：group_buy_id则去获取该值
            String getgroup_buy_id = getIntent().getStringExtra("group_buy_id");
            group_buy_id = getgroup_buy_id == null ? "" : getgroup_buy_id;
        } else { // 否则不包含该Key，将其设置为空字符串
            group_buy_id = "";
        }
        if (group_buy_id.contains("-")) {
            order_id = group_buy_id.split("-")[1];
        }
        is_attr = getIntent().getStringExtra("is_attr");
        String[] a = goods_id.split("-");
        goods_id = a[0];
        if (1 == from || 0 == from) {
            if (0 == from) {
                is_go = true;
                if ("10".equals(type)) {
                    to_buy_must_tv.setText("立即兑换");
                } else {
                    to_buy_must_tv.setText("确定");
                    //                    to_buy_must_tv.setText("立即购买");
                }
                from = 1;
                if (a.length > 1) {
                    mid = a[1];
                }
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
            et_num.setText(String.valueOf(num));
        }
        if (3 == from) {
            to_buy_must_tv.setText("拼单");
            goods_into_cart_tv.setVisibility(View.GONE);
            at_left_lin_layout.setVisibility(View.GONE);
            num = getIntent().getIntExtra("num", 0);
            et_num.setText(String.valueOf(num));
        }
        if (4 == from) {
            //            if ("2".equals(type) && getIntent().hasExtra("group_type")) {//拼单购选择选规格
            //                is_go = true;
            //            }
            to_buy_must_tv.setText("确定");
            goods_into_cart_tv.setVisibility(View.GONE);
            at_left_lin_layout.setVisibility(View.GONE);
            num = getIntent().getIntExtra("num", 0);
            et_num.setText(String.valueOf(num));
        }
    }

    private boolean isMapCome = false;
    private String groupType = "";

    @Override
    protected void requestData() {
        Intent intent = getIntent();
        if (intent.hasExtra("group_type")) {
            groupType = intent.getStringExtra("group_type");
            if ("1".equals(groupType)) {// "group_type": "类型 1试用品拼单 2常规拼单",
                lite.setVisibility(View.GONE);
                lite1.setVisibility(View.GONE);
            }
        }
        String imageurl = intent.getStringExtra("imageurl");
        String price = getIntent().getStringExtra("price");
        Glide.with(this).load(imageurl).into(imageview);//加载默认商品图
        ChangeTextViewStyle.getInstance().forGoodsPrice24(this, goods_price_tv, "￥" + price);

        if (intent.hasExtra("goods_attr_Serializable")) {
            list = (List<FirstListBean>) intent.getSerializableExtra("goods_attr_Serializable");
            isMapCome = false;
        } else {
            isMapCome = true;
            list = GsonUtil.getObjectList(intent.getStringExtra("goods_attr"), FirstListBean.class);
        }

        for (int i = 0; i < list.size(); i++) {
            list.get(i).getFirst_list_val().get(0).setStatus(SELECTED);
        }
        if (intent.hasExtra("goods_val_Serializable")) {
            isMapCome = false;
            list_val = (List<FirstValBean>) intent.getSerializableExtra("goods_val_Serializable");
        } else {
            list_val = GsonUtil.getObjectList(intent.getStringExtra("goods_val"), FirstValBean.class);
            isMapCome = true;
        }


        recordMutilMapList = new ArrayList<>();
        list = dealData(list, list_val, 0);
        String[] string = is_attr.split("-");
        is_attr = string[0];
        if (!ListUtils.isEmpty(list) && is_attr.equals(SELECTED)) {
            goodsAttrsAdapter = new GoodsAttrsAdapter();
            goods_attr_lv.setAdapter(goodsAttrsAdapter);
        } else {
            maxNumber = Long.parseLong(string[1]);
            tv_kucun.setText("（库存：" + string[1] + "）");
            et_num.setText(String.valueOf(num));
            list.clear();
        }
        //        if (list.size() == 1) {
        if (null != list_val && list_val.size() > 0) {
            for (FirstValBean goods_val : list_val) {
                //                String v = list.get(0).getFirst_list_val().get(0).getVal() + "+";
                //                if (v.equals(goods_val.getArrtValue()))

                StringBuffer recordStr = new StringBuffer();
                for (int k = 0; k < list_attrs.size(); k++) {
                    recordStr.append(list_attrs.get(k));
                    if (k < list_attrs.size() - 1) {
                        recordStr.append("+");
                        //                        recordStr.append("@");
                    }
                }
                if (goods_val.getArrtValue().contains(recordStr)) {
                    GoodsAttributeAty.this.val = goods_val;

                    if (TextUtils.isEmpty(val.getMax_num()) || "0".equals(val.getMax_num())) {
                        tv_xg.setVisibility(View.INVISIBLE);
                    } else {
                        tv_xg.setVisibility(View.VISIBLE);
                        tv_xg.setText("限购（" + val.getMax_num() + "）");
                    }

                    tv_kucun.setText("（库存：" + goods_val.getGoods_num() + "）");
                    try {
                        maxNumber = Long.parseLong(goods_val.getGoods_num());
                    } catch (Exception e) {
                        L.e("商品属性库存数量转换异常：" + e.toString());
                    }
                    Glide.with(GoodsAttributeAty.this).load(goods_val.getGoods_img()).into(imageview);
                    if ("10".equals(type)) {
                        //                        ChangeTextViewStyle.getInstance().forGoodsPrice24(GoodsAttributeAty.this, goods_price_tv, price + "积分");
                        //此处为设置积分商店商品属性积分view
                        ChangeTextViewStyle.getInstance().forGoodsPrice24(GoodsAttributeAty.this, goods_price_tv, goods_val.getUse_integral() + " 积分");
                    } else {
                        //                        if (!TextUtils.isEmpty(type) && ("2".equals(type)||"3".equals(type) || "4".equals(type))) {
                        //                            ChangeTextViewStyle.getInstance().forGoodsPrice24(GoodsAttributeAty.this, goods_price_tv, "￥" + val.getGroup_price());
                        //                        } else
                        ChangeTextViewStyle.getInstance().forGoodsPrice24(GoodsAttributeAty.this, goods_price_tv, "￥" + goods_val.getShop_price());
                    }
                    // 设置如果库存大于0 将初始的选择数量设为1，否则设为0
                    et_num.setText(String.valueOf(Integer.parseInt(goods_val.getGoods_num()) > 0 ? 1 : 0));
                    num = Integer.parseInt(goods_val.getGoods_num()) > 0 ? 1 : 0;

                    pro_value = goods_val.getArrtValue();
                    image = goods_val.getGoods_img();
                    pro_id = goods_val.getId();
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
                if (group_buy_id.contains("-")) {
                    String[] string = group_buy_id.split("-");
                    group_buy_id = string[0];
                    order_id = string[1];
                }
                if (!TextUtils.isEmpty(order_id)) {
                    intent.putExtra("order_id", order_id);
                }
                intent.putExtra("group_buy_id", group_buy_id);
                intent.putExtra("num", String.valueOf(num));
                intent.setClass(this, BuildOrderAty.class);
                startActivity(intent);
                finish();
                return;
            } else {
                Cart.addCart(goods_id, "", num, this);
            }
            //            RequestParams params = new RequestParams();
            //            ApiTool2 apiTool2 = new ApiTool2();
            //            params.addBodyParameter("goods_id", goods_id);
            //            params.addBodyParameter("num", String.valueOf(num));
            //            apiTool2.postApi(Config.BASE_URL + "Cart/addCart", params, this);
            return;
        }

        if (!TextUtils.isEmpty(pro_id)) {

            if (is_go) {
                Intent intent = new Intent();
                intent.putExtra("mid", mid);
                intent.putExtra("type", type);
                //                intent.putExtra("goods_id", goods_id);
                intent.putExtra("goods_id", val.getGoods_id());

                if (group_buy_id.contains("-")) {
                    String[] string = group_buy_id.split("-");
                    group_buy_id = string[0];
                    order_id = string[1];
                }
                if (!TextUtils.isEmpty(order_id)) {
                    intent.putExtra("order_id", order_id);
                }
                if (type.equals("10")) {//积分商店
                    intent.putExtra("use_integral", val.getUse_integral());
                    intent.putExtra("integral_buy_id", val.getIntegral_buy_id());
                }
                intent.putExtra("group_buy_id", val.getGroup_buy_id());
                //                intent.putExtra("group_buy_id", group_buy_id);
                intent.putExtra("num", String.valueOf(num));
                intent.putExtra("product_id", pro_id);
                if (getIntent().hasExtra("group_type")) {//体验拼单
                    intent.putExtra("group_type", getIntent().getStringExtra("group_type"));
                }
                L.e("cccc" + mid + "--" + type + "--" + goods_id + "--" + group_buy_id + "--" + num + "--" + pro_id);
                setResult(0x0001, intent);
                finish();
                return;
            } else {
                Cart.addCart(goods_id, pro_id, num, this);
            }
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
        public FirstListBean getItem(int i) {
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

            TagAdapter tagAdapter = new TagAdapter<FirstListValBean>(getItem(i).getFirst_list_val()) {

                @Override
                public View getView(FlowLayout parent, int position, FirstListValBean goodsAttrses) {
                    TextView tv = (TextView) LayoutInflater.from(GoodsAttributeAty.this).inflate(R.layout
                                    .item_goods_attrs_tfl,
                            parent, false);
                    tv.setText(goodsAttrses.getVal());
                    if (TextUtils.isEmpty(getItem(i).getFirst_list_val().get(position).getStatus())) {
                        getItem(i).getFirst_list_val().get(position).setStatus(UNSELECT);
                    }
                    List<FirstListValBean> firstListVal = getItem(i).getFirst_list_val();
                    //                    if (getCount() == 1) {
                    //                        getItem(0).getFirst_list_val().get(0).setStatus(SELECTED);
                    //                    }
                    switch (getItem(i).getFirst_list_val().get(position).getStatus()) {
                        case SELECTED:
                            tv.setEnabled(true);
                            tv.setClickable(true);
                            tv.setBackgroundResource(R.drawable.shape_tag_checked_bg);
                            tv.setTextColor(Color.WHITE);
                            break;
                        case UNSELECT:
                            tv.setEnabled(false);
                            tv.setClickable(false);
                            tv.setBackgroundResource(R.drawable.shape_tag_normal_bg);
                            tv.setTextColor(Color.BLACK);
                            break;
                        case CANNOT_SELECT:
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
                //                getItem(i).getFirst_list_val().get(0).setStatus(SELECTED);
            }
            avh.goods_attr_tfl.setTag(i);
            avh.goods_attr_tfl.setAdapter(tagAdapter);
            avh.goods_attr_tfl.setOnTagClickListener(new FlowLitener(Integer.parseInt(String.valueOf(avh.goods_attr_tfl.getTag())), GoodsAttrsAdapter.this, i, tagAdapter));


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


    private class FlowLitener implements TagFlowLayout.OnTagClickListener {
        int tag;
        GoodsAttrsAdapter goodsAttrsAdapter;
        TagAdapter tagAdapter;
        int i;

        FlowLitener(int tag, GoodsAttrsAdapter goodsAttrsAdapter, int position, TagAdapter tagAdapter) {
            this.tag = tag;
            this.goodsAttrsAdapter = goodsAttrsAdapter;
            i = position;
            this.tagAdapter = tagAdapter;
        }

        @Override
        public boolean onTagClick(View view, int position, FlowLayout parent) {
            if (tag == 0) {
                changeTopImage(position);
            }
            TextView textView = (TextView) view;
            String valStr = textView.getText().toString().trim();
            recordMutilMap.put(tag, valStr);
            list = dealData(list, list_val, tag);

            if (list_attrs.size() == list.size()) {
                Log.i("商品属性长度", list.size() + "");
                Log.i("商品属性长度", list_attrs.size() + "");
                StringBuffer attrs = new StringBuffer();
                for (int k = 0; k < list_attrs.size(); k++) {
                    //                            if (k == list_attrs.size() - 1) {
                    attrs.append(list_attrs.get(k) + "+");
                    //                            } else {
                    //                                attrs.append(list_attrs.get(k));
                    //                            }
                }
                for (FirstValBean val : list_val) {
                    if (attrs.toString().contains(val.getArrtValue())) {
                        GoodsAttributeAty.this.val = val;
                        if (!TextUtils.isEmpty(val.getGroup_buy_id())) {
                            group_buy_id = val.getGroup_buy_id();
                        }
                        if (TextUtils.isEmpty(val.getMax_num()) || "0".equals(val.getMax_num())) {
                            tv_xg.setVisibility(View.INVISIBLE);
                        } else {
                            tv_xg.setVisibility(View.VISIBLE);
                            tv_xg.setText("限购（" + val.getMax_num() + "）");
                        }
                        tv_kucun.setText("（库存：" + val.getGoods_num() + "）");
                        maxNumber = Long.parseLong(val.getGoods_num());
                        Glide.with(GoodsAttributeAty.this).load(val.getGoods_img()).into(imageview);
                        if ("10".equals(type)) {
                            //                            ChangeTextViewStyle.getInstance().forGoodsPrice24(GoodsAttributeAty.this, goods_price_tv, price + "积分");
                            ChangeTextViewStyle.getInstance().forGoodsPrice24(GoodsAttributeAty.this, goods_price_tv, "积分" + val.getUse_integral());
                        } else {
                            //                            if (!TextUtils.isEmpty(type) && ("2".equals(type)||"3".equals(type) || "4".equals(type))) {// "group_type": "类型 1试用品拼单 2常规拼单",
                            //                                ChangeTextViewStyle.getInstance().forGoodsPrice24(GoodsAttributeAty.this, goods_price_tv, "￥" + val.getGroup_price());
                            //                            } else {
                            ChangeTextViewStyle.getInstance().forGoodsPrice24(GoodsAttributeAty.this, goods_price_tv, "￥" + val.getShop_price());
                            //                            }
                        }
                        if (val.getGoods_num().equals("0")) {//切换时候库存数目是0的时候，购买数量那块写成0
                            et_num.setText("0");
                        } else {
                            et_num.setText("1");
                        }
                        pro_value = val.getArrtValue();
                        image = val.getGoods_img();
                        pro_id = val.getId();
                        //                                num = 1;
                        position++;
                        break;
                    } else {
                        //                        et_num.setText(String.valueOf(maxNumber));
                        pro_id = "";
                        position = -1;
                    }
                }


            } else {
                //                pro_id = "";
            }
            goodsAttrsAdapter.notifyDataSetChanged();
            tagAdapter.notifyDataChanged();
            return true;
        }
    }

    /**
     * 对下面相同逻辑的逻辑封装
     * 循环遍历 颜色 尺寸  高矮
     *
     * @param valBeans
     * @param lists
     * @param type
     */
    private void fz(List<FirstListValBean> valBeans, List<String> lists, int type) {
        for (int i = 0; i < valBeans.size(); i++) {
            FirstListValBean valBean = valBeans.get(i);
            boolean falgChoice = false;
            int choicePos = 0;
            outer:
            for (int bdPos = 0; bdPos < lists.size(); bdPos++) {
                String compareStr = lists.get(bdPos);
                switch (type) {
                    case 0: {//颜色比对判断
                        if (compareStr.contains(valBean.getVal())) {
                            falgChoice = true;
                            choicePos = bdPos;
                        }
                    }
                    break;
                    case 1: {//尺寸比对判断
                        if (compareStr.contains(recordMutilMap.get(0) + "+" + valBean.getVal())) {
                            falgChoice = true;
                            choicePos = bdPos;
                        }

                    }
                    break;
                    case 2: { // 大小比对判断 颜色+尺寸+高低
                        if (recordMutilMap.size() >= 2) { // 此处上报数组下标越界，添加的判断
                            if (compareStr.contains(recordMutilMap.get(0) + "+" + recordMutilMap.get(1) + "+" + valBean.getVal())) {
                                falgChoice = true;
                                choicePos = bdPos;
                            }
                        } else {
                            if (compareStr.contains(recordMutilMap.get(0) + "+" + valBean.getVal())) {
                                falgChoice = true;
                                choicePos = bdPos;
                            }
                        }
                    }
                    break;
                    //                    break outer;
                }
            }
            if (falgChoice) {
                if (recordMutilMap.containsKey(type)) {
                    if (recordMutilMap.get(type).equals(valBean.getVal())) {
                        FirstValBean temp = list_val.get(choicePos);
                        valBean.setStatus(SELECTED);
                    } else {
                        valBean.setStatus(UNSELECT);
                    }
                }
            } else {
                valBean.setStatus(CANNOT_SELECT);
            }
        }
    }

    private List<FirstListBean> dealData(List<FirstListBean> list, List<FirstValBean> list_val, int clickWhichPos) {
        recordMutilMapList.clear();//清空记录数组
        List<String> lists = new ArrayList<>();//根据可选属性列表给记录选中状态属性map赋值 同时对可选属性进行map的转换
        for (int bd = 0; bd < list_val.size(); bd++) {//遍历属性的可选值
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(list_val.get(bd).getArrtValue());//145/80A+150cm以下,获取可选值的比较字符串
            String compareStr = String.valueOf(stringBuffer);
            lists.add(compareStr);//将遍历出的可选值装入列表
            HashMap recordMap = new HashMap();
            String[] strings = compareStr.split("\\+");//将比较字符串用+分割
            //            String[] strings = compareStr.split("@");//将比较字符串用+分割
            for (int i = 0; i < strings.length; i++) {
                recordMap.put(i, strings[i].trim());
            }
            recordMutilMapList.add(recordMap);//循环记录可选值列表
            //            if (null == recordMutilMap) {
            //                recordMutilMap = recordMutilMapList.get(0);//第一次进入选头一个
            //            } else if(clickWhichPos>0){
            //                if (recordMutilMap.get(0).equals(strings[0])) {//记录选中的那项颜色相同
            //                    for (int i = clickWhichPos + 1; i < recordMutilMap.size(); i++) {
            //                        recordMutilMap.put(i, recordMutilMapList.get(bd).get(i));
            //                    }
            //                }
            //            }
        }
        if (null == recordMutilMap) {
            recordMutilMap = recordMutilMapList.get(0);//第一次进入选头一个
            if (TextUtils.isEmpty(list_val.get(0).getMax_num()) || "0".equals(list_val.get(0).getMax_num())) {
                tv_xg.setVisibility(View.INVISIBLE);
            } else {
                tv_xg.setVisibility(View.VISIBLE);
                tv_xg.setText("限购（" + list_val.get(0).getMax_num() + "）");
            }
        } else {

            //            boolean equal = false;
            //            for (int i = 0; i < recordMutilMapList.size(); i++) {//赋值的逻辑复杂,不会搞
            //                Map<Integer, String> tempMap = recordMutilMapList.get(i);
            //
            //                for (int j = 0; j < tempMap.size() ; j++) {
            //                    if (recordMutilMap.get(j).equals(tempMap.get(j))) {
            //                        equal = true;
            //                    } else {
            //                        equal = false;
            //                    }
            //                }
            //
            //            }
            //            if (equal) {
            //                for (int i = clickWhichPos + 1; i < recordMutilMapList.size(); i++) {
            //                    recordMutilMap.put(i, recordMutilMapList.get(i).get(0));
            //                }
            //            }
        }
        Iterator iterator = recordMutilMap.keySet().iterator();
        while (iterator.hasNext()) {
            int key = (int) iterator.next();
            String tempStr = recordMutilMap.get(key);
            list_attrs.put(key, tempStr);//记录选中的属性，好在后面做比较做库存记录
        }
        for (int type = 0; type < list.size(); type++) {//根据记录属性的map对列表中的数据进行赋值
            if (type == 0) {
                //颜色
                FirstListBean goodsAttr = list.get(type);
                List<FirstListValBean> valBeans = goodsAttr.getFirst_list_val();
                fz(valBeans, lists, type);
            } else if (type == 1) {
                //尺寸
                FirstListBean goodsAttr = list.get(type);
                List<FirstListValBean> valBeans = goodsAttr.getFirst_list_val();
                fz(valBeans, lists, type);
            } else if (type == 2) {
                //身高
                FirstListBean goodsAttr = list.get(type);
                List<FirstListValBean> valBeans = goodsAttr.getFirst_list_val();
                fz(valBeans, lists, type);
            }
        }
        return list;
    }

    private void changeTopImage(int index) {
        String url = list_val.get(index).getGoods_img();
        Glide.with(this).load(url).into(imageview);
    }
}

