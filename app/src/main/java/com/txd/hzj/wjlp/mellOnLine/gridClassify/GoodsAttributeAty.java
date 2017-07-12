package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.lidroid.xutils.ViewUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.media.CamcorderProfile.get;

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

    private List<String> attrGroup;
    private List<GoodsAttrs> attrs;
    private List<GoodsAttrs> attrs2;

    private GoodsAttrsAdapter goodsAttrsAdapter;

    private List<GoodsAttrs> selectAttrs;
    private int from = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeTextViewStyle.getInstance().forGoodsPrice24(this, goods_price_tv, "￥49.00");
        goods_attr_lv.setAdapter(goodsAttrsAdapter);
        if (1 == from) {
            to_buy_must_tv.setText("确定");
            goods_into_cart_tv.setVisibility(View.GONE);
            at_left_lin_layout.setVisibility(View.GONE);
        }
    }

    @Override
    @OnClick({R.id.to_buy_must_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_buy_must_tv:// 立即购买，确定
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
        attrGroup = new ArrayList<>();
        attrs = new ArrayList<>();
        attrs2 = new ArrayList<>();
        selectAttrs = new ArrayList<>();
        attrs.add(new GoodsAttrs("S"));
        attrs.add(new GoodsAttrs("M"));
        attrs.add(new GoodsAttrs("L"));
        attrs.add(new GoodsAttrs("XL"));
        attrs.add(new GoodsAttrs("XXL"));
        attrs.add(new GoodsAttrs("XXXL"));
        attrs2.add(new GoodsAttrs("红色"));
        attrs2.add(new GoodsAttrs("藏青色"));
        attrs2.add(new GoodsAttrs("白色"));
        attrs2.add(new GoodsAttrs("蓝色"));
        attrs2.add(new GoodsAttrs("军绿色"));
        attrs2.add(new GoodsAttrs("黑色"));
        goodsAttrsAdapter = new GoodsAttrsAdapter();
    }

    @Override
    protected void requestData() {

    }

    private class GoodsAttrsAdapter extends BaseAdapter {

        private AttrsVh avh;

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object getItem(int i) {
            return attrGroup.get(i);
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
                ViewUtils.inject(avh, view);
                view.setTag(avh);
            } else {
                avh = (AttrsVh) view.getTag();
            }
            if (0 == i) {
                avh.goods_attrs_title.setText("尺寸");
                avh.goods_attr_tfl.setAdapter(new TagAdapter<GoodsAttrs>(attrs) {
                    @Override
                    public View getView(FlowLayout parent, int position, GoodsAttrs goodsAttrses) {
                        TextView tv = (TextView) LayoutInflater.from(GoodsAttributeAty.this).inflate(R.layout
                                        .item_goods_attrs_tfl,
                                parent, false);
                        tv.setText(goodsAttrses.getValues());
                        return tv;
                    }
                });
            } else {
                avh.goods_attrs_title.setText("颜色");
                avh.goods_attr_tfl.setAdapter(new TagAdapter<GoodsAttrs>(attrs2) {
                    @Override
                    public View getView(FlowLayout parent, int position, GoodsAttrs goodsAttrses) {
                        TextView tv = (TextView) LayoutInflater.from(GoodsAttributeAty.this).inflate(R.layout
                                        .item_goods_attrs_tfl,
                                parent, false);
                        tv.setText(goodsAttrses.getValues());
                        return tv;
                    }
                });
            }
            avh.goods_attr_tfl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
//                    get(i).get(position).getXXX();
                    if (0 == i) {
                        showRightTip(attrs.get(position).getValues());
                    } else {
                        showRightTip(attrs2.get(position).getValues());
                    }
                    return true;
                }
            });
            // 这个在单选中貌似并没有用(多选记录下标)
            avh.goods_attr_tfl.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    L.e("=====选中的=====", selectPosSet.toString());
                }
            });
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
