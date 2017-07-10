package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
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

    private List<String> attrGroup;
    private List<GoodsAttrs> attrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeTextViewStyle.getInstance().forGoodsPrice24(this, goods_price_tv, "￥49.00");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_goods_attribute;
    }

    @Override
    protected void initialized() {
        attrGroup = new ArrayList<>();
        attrs = new ArrayList<>();
        attrs.add(new GoodsAttrs("S"));
        attrs.add(new GoodsAttrs("M"));
        attrs.add(new GoodsAttrs("L"));
        attrs.add(new GoodsAttrs("XL"));
        attrs.add(new GoodsAttrs("XXL"));
        attrs.add(new GoodsAttrs("XXXL"));
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
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (null == view) {
                view = LayoutInflater.from(GoodsAttributeAty.this).inflate(R.layout.item_goods_attr_lv, null);
                avh = new AttrsVh();
                ViewUtils.inject(avh, view);
                view.setTag(avh);
            } else {
                avh = (AttrsVh) view.getTag();
            }
            avh.goods_attr_tfl.setAdapter(new TagAdapter<GoodsAttrs>(attrs) {
                @Override
                public View getView(FlowLayout parent, int position, GoodsAttrs goodsAttrses) {
                    return null;
                }
            });
            return view;
        }

        class AttrsVh {
            /**
             * TagFlowLayout
             */
            @ViewInject(R.id.goods_attr_tfl)
            private TagFlowLayout goods_attr_tfl;
        }

    }

}
