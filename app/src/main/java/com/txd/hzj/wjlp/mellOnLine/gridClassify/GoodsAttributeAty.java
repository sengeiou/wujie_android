package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.content.Intent;
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
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.ListUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.GoodsAttrs;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.view.flowlayout.FlowLayout;
import com.txd.hzj.wjlp.view.flowlayout.TagAdapter;
import com.txd.hzj.wjlp.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：下午 7:45
 * 描述：商品属性页面
 * ===============Txunda===============
 */
public class GoodsAttributeAty extends BaseAty {
    SparseArray<Integer> pos = new SparseArray<Integer>();
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
    private List<GoodsAttrs> selectAttrs = new ArrayList<GoodsAttrs>();
    private List<GoodsAttrs.product> goods_product = new ArrayList<GoodsAttrs.product>();
    private int from = 0;
    private String price = "";
    private String imageurl = "";
    private StringBuffer goods_str;
    @ViewInject(R.id.tv_num)
    private TextView tv_num;
    private int num = 1;

    @Override
    @OnClick({R.id.to_buy_must_tv, R.id.im_jian, R.id.im_jia})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_buy_must_tv:// 立即购买，确定
                if (2 == from) {
                    Intent intent = new Intent();
                    intent.putExtra("num", num);
                    if (!ListUtils.isEmpty(goods_product)) {
                        intent.putExtra("product_id", "");
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    goods_str = new StringBuffer();
                    for (int i = 0; i < goods_product.size(); i++) {
                        for (int j = 0; j < pos.size(); j++) {
                            if (j == pos.size() - 1) {
                                goods_str.append(pos.get(j));
                            } else {
                                goods_str.append(pos.get(j) + "|");
                            }
                        }
                        if (goods_product.get(i).getGoods_attr_str().equals(goods_str.toString())) {
                            intent.putExtra("product_id", goods_product.get(i).getId());
                            intent.putExtra("product", goods_product.get(i).getGoods_attr_str());
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            showToast("请选择商品属性！");
                        }
                    }

                    return;
                }
                goodAttChange();
                break;
            case R.id.im_jia:
                num++;
                tv_num.setText(String.valueOf(num));
                break;
            case R.id.im_jian:
                if (num == 1) {
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
        if (1 == from) {
            to_buy_must_tv.setText("加入购物车");
            goods_into_cart_tv.setVisibility(View.GONE);
            at_left_lin_layout.setVisibility(View.GONE);
        }
        if (2 == from) {
            to_buy_must_tv.setText("修改");
            goods_into_cart_tv.setVisibility(View.GONE);
            at_left_lin_layout.setVisibility(View.GONE);
            num = getIntent().getIntExtra("num", 1);
            tv_num.setText(String.valueOf(num));
        }
    }

    @Override
    protected void requestData() {
        imageurl = getIntent().getStringExtra("imageurl");
        price = getIntent().getStringExtra("price");
        Glide.with(this).load(imageurl).into(imageview);
        ChangeTextViewStyle.getInstance().forGoodsPrice24(this, goods_price_tv, "￥" + price);
        selectAttrs = getIntent().getParcelableArrayListExtra("list");
        goods_product = getIntent().getParcelableArrayListExtra("list_p");
        goodsAttrsAdapter = new GoodsAttrsAdapter();
        goods_attr_lv.setAdapter(goodsAttrsAdapter);

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        showToast("添加成功");
        finish();
    }

    private void goodAttChange() {
        if (ListUtils.isEmpty(goods_product)) {
            RequestParams params = new RequestParams();
            ApiTool2 apiTool2 = new ApiTool2();
            params.addBodyParameter("goods_id", getIntent().getStringExtra("goods_id"));
            params.addBodyParameter("num", String.valueOf(num));
            apiTool2.postApi(Config.BASE_URL + "Cart/addCart", params, this);
        }
        goods_str = new StringBuffer();
        for (int i = 0; i < goods_product.size(); i++) {
            for (int j = 0; j < pos.size(); j++) {
                if (j == pos.size() - 1) {
                    goods_str.append(pos.get(j));
                } else {
                    goods_str.append(pos.get(j) + "|");
                }
            }
            if (goods_product.get(i).getGoods_attr_str().equals(goods_str.toString())) {
                RequestParams params = new RequestParams();
                ApiTool2 apiTool2 = new ApiTool2();
                params.addBodyParameter("goods_id", goods_product.get(i).getGoods_id());
                params.addBodyParameter("product_id", goods_product.get(i).getId());
                params.addBodyParameter("num", String.valueOf(num));
                apiTool2.postApi(Config.BASE_URL + "Cart/addCart", params, this);
            } else {
                showToast("请选择商品属性");
            }
        }
    }

    private class GoodsAttrsAdapter extends BaseAdapter {

        private AttrsVh avh;

        @Override
        public int getCount() {
            return selectAttrs.size();
        }

        @Override
        public GoodsAttrs getItem(int i) {
            return selectAttrs.get(i);
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
            avh.goods_attrs_title.setText(getItem(i).getAttr_name());
            avh.goods_attr_tfl.setAdapter(new TagAdapter<GoodsAttrs.AttrListBean>(selectAttrs.get(i).getAttr_list()) {
                @Override
                public View getView(FlowLayout parent, int position, GoodsAttrs.AttrListBean goodsAttrses) {
                    TextView tv = (TextView) LayoutInflater.from(GoodsAttributeAty.this).inflate(R.layout
                                    .item_goods_attrs_tfl,
                            parent, false);
                    tv.setText(goodsAttrses.getAttr_value());
                    return tv;
                }
            });

            avh.goods_attr_tfl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    pos.append(i, Integer.parseInt(getItem(i).getAttr_list().get(position).getGoods_attr_id()));
                    return true;
                }
            });
            // 这个在单选中貌似并没有用(多选记录下标)
            //多选用的
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

}
