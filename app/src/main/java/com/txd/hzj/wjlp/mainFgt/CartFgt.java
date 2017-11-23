package com.txd.hzj.wjlp.mainFgt;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.CartGoods;
import com.txd.hzj.wjlp.bean.ShopingCart;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodsAttributeAty;
import com.txd.hzj.wjlp.shoppingCart.BuildOrderAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/12 0012
 * 时间：上午 10:44
 * 描述：购物车
 * ===============Txunda===============
 */
public class CartFgt extends BaseFgt {

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
    private ListView cart_lv;

    private List<ShopingCart> shopingCarts;

    private CartAdapter cartAdapter;
    private int all = 0;

    /**
     * 选中的商品价格
     */
    private BigDecimal all_price;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titlt_conter_tv.setText("购物车");
        title_be_back_iv.setVisibility(View.GONE);
        titlt_right_tv.setVisibility(View.VISIBLE);
        toChangePrice();
        cart_lv.setAdapter(cartAdapter);
    }

    /**
     * 修改商品价格
     */
    private void toChangePrice() {
        ChangeTextViewStyle.getInstance().forCartPrice(getActivity(), all_goods_price_tv, "￥" + all_price + "\n不含配送费");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_cart;
    }

    @Override
    protected void initialized() {
        shopingCarts = new ArrayList<>();
        getDate();
        cartAdapter = new CartAdapter();
        all_price = new BigDecimal("0.00");
    }

    private void getDate() {

        CartGoods cartGoods1 = new CartGoods("1", "1", "", "M 黑色", "太平鸟女装2017夏季新款短胶印红色宽松T恤女短袖",
                "120.00", 1, false);
        CartGoods cartGoods2 = new CartGoods("2", "1", "", "S 黑色", "太平鸟女装2017夏季新款短袖后背印花纯棉T恤",
                "170.00", 1, false);
        List<CartGoods> cartGoodses1 = new ArrayList<>();
        cartGoodses1.add(cartGoods1);
        cartGoodses1.add(cartGoods2);
        ShopingCart shopingCart1 = new ShopingCart("太平鸟", "1", false, cartGoodses1);


        CartGoods cartGoods3 = new CartGoods("3", "2", "", "S 黑色", "春晓折扣ZARA女装侧边花朵刺绣中腰牛仔裤",
                "150.00", 1, false);
        CartGoods cartGoods4 = new CartGoods("4", "2", "", "L 黑色", "春季折扣ZARA 女装 补丁加大码衬衫",
                "120.00", 1, false);
        List<CartGoods> cartGoodses2 = new ArrayList<>();
        cartGoodses2.add(cartGoods3);
        cartGoodses2.add(cartGoods4);
        ShopingCart shopingCart2 = new ShopingCart("ZARA", "2", false, cartGoodses2);

        shopingCarts.add(shopingCart1);
        shopingCarts.add(shopingCart2);
    }

    @Override
    protected void requestData() {

    }

    @Override
    @OnClick({R.id.titlt_right_tv, R.id.operation_goods_tv, R.id.cart_select_all_cb})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlt_right_tv:// 编辑，保存
                String str = titlt_right_tv.getText().toString();
                if (str.equals("编辑")) {// 之前是不可编辑状态，点击之后是可编辑状态
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
                }
                cartAdapter.notifyDataSetChanged();
                break;
            case R.id.operation_goods_tv:// 去结算，删除

                if (canEdit) {// 删除
                    new MikyouCommonDialog(getActivity(), "确定要删除商品么?", "提示", "确定", "取消").setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {

                        @Override
                        public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int
                                which) {
                            switch (btnType) {
                                case MikyouCommonDialog.OK:// 确定
                                    break;
                                case MikyouCommonDialog.NO:// 取消
                                    break;
                            }
                        }
                    }).showDialog();
                } else {// 去结算
                    startActivity(BuildOrderAty.class, null);
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
                    sc.setMellAllCheck(b);
                    List<CartGoods> cartGoodses = sc.getGoodsInfo();
                    for (CartGoods cg : cartGoodses) {
                        cg.setSelect(b);
                        if (b) {
                            BigDecimal price = new BigDecimal(cg.getPrice());
                            price = price.multiply(new BigDecimal(cg.getNum()));
                            all_price = all_price.add(price);
                        }
                    }
                }
                toChangePrice();
                cartAdapter.notifyDataSetChanged();

                break;
        }
    }

    @Override
    protected void immersionInit() {
        showStatusBar(R.id.title_re_layout);
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ShopingCart sc = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_cart_lv_hzj, null);
                cartVh = new CartVh();
                ViewUtils.inject(cartVh, view);
                view.setTag(cartVh);
            } else {
                cartVh = (CartVh) view.getTag();
            }

            // 判断店铺中的商品是否被全部选中
            if (sc.isMellAllCheck()) {
                cartVh.mell_goods_all_select_iv.setImageResource(R.drawable.icon_cart_goods_selected);
            } else {
                cartVh.mell_goods_all_select_iv.setImageResource(R.drawable.icon_cart_goods_unselect);
            }

            cartVh.cart_mell_name_tv.setText(sc.getMellName());
            cartVh.cart_mell_goods_lv.setAdapter(new CartGoodsAdapter(shopingCarts, i));

            cartVh.mell_goods_all_select_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sc.isMellAllCheck()) {
                        sc.setMellAllCheck(false);
                        for (CartGoods cg : sc.getGoodsInfo()) {
                            cg.setSelect(false);
                            BigDecimal price = new BigDecimal(cg.getPrice());
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
                        sc.setMellAllCheck(true);
                        for (CartGoods cg : sc.getGoodsInfo()) {
                            if (!cg.isSelect()) {
                                cg.setSelect(true);
                                BigDecimal price = new BigDecimal(cg.getPrice());
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
            cartGoodses = list.get(groupPosion).getGoodsInfo();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            final CartGoods cg = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_child_cart_lv_hzj, null);
                cgvh = new CGVH();
                ViewUtils.inject(cgvh, view);
                view.setTag(cgvh);
            } else {
                cgvh = (CGVH) view.getTag();
            }

            if (cg.isSelect()) {
                cgvh.cart_goods_select_iv.setImageResource(R.drawable.icon_cart_goods_selected);
            } else {
                cgvh.cart_goods_select_iv.setImageResource(R.drawable.icon_cart_goods_unselect);
            }
            cgvh.goods_name_tv.setText(cg.getTitle());
            // 属性
            cgvh.goods_attrs_tv.setText(cg.getAttrs());
            cgvh.reset_goods_attrs_tv.setText(cg.getAttrs());
            cgvh.cart_goods_price_tv.setText(cg.getPrice());

            // 数量
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
                    if (cg.isSelect()) {
                        cg.setSelect(false);
                        // 获取价格
                        BigDecimal price = new BigDecimal(cg.getPrice());
                        // 计算选中商品价格
                        price = price.multiply(new BigDecimal(cg.getNum()));
                        // 计算总价
                        all_price = all_price.subtract(price);
                        if (all_price.compareTo(new BigDecimal("0.00")) <= 0) {
                            all_price = new BigDecimal("0.00");
                        }
                    } else {
                        cg.setSelect(true);
                        // 获取价格
                        BigDecimal price = new BigDecimal(cg.getPrice());
                        // 计算单件商品价格
                        price = price.multiply(new BigDecimal(cg.getNum()));
                        // 计算总价
                        all_price = all_price.add(price);
                    }
                    // 重新加载数据
                    for (CartGoods cartGoods : list.get(groupPosion).getGoodsInfo()) {
                        if (cartGoods.isSelect()) {
                            select_num++;
                        }
                    }
                    if (select_num >= getCount()) {
                        list.get(groupPosion).setMellAllCheck(true);
                    } else {
                        list.get(groupPosion).setMellAllCheck(false);
                    }
                    toChangePrice();
                    cartAdapter.notifyDataSetChanged();
                }
            });

            // 数量加
            cgvh.goods_num_increase_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = cg.getNum();
                    num++;
                    cg.setNum(num);
                    notifyDataSetChanged();
                }
            });
            // 数量减
            cgvh.goods_num_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = cg.getNum();
                    if (num > 1) {
                        num--;
                        cg.setNum(num);
                        notifyDataSetChanged();
                    }
                }
            });

            // 选择商品属性
            cgvh.reset_goods_attrs_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("from", 1);
                    startActivity(GoodsAttributeAty.class, bundle);
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
            private FrameLayout cart_goods_info_layout;
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
        }

    }

}
