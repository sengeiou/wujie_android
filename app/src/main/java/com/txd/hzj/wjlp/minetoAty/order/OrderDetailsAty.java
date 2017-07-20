package com.txd.hzj.wjlp.minetoAty.order;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：下午 3:02
 * 描述：订单详情
 * ===============Txunda===============
 */
public class OrderDetailsAty extends BaseAty {


    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.goods_for_this_order_lv)
    private ListViewForScrollView goods_for_this_order_lv;

    @ViewInject(R.id.order_price_info_tv)
    private TextView order_price_info_tv;

    @ViewInject(R.id.bot_for_order)
    private LinearLayout bot_for_order;

    private ThisGoodsAdapter thisAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("订单详情");
        bot_for_order.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        goods_for_this_order_lv.setAdapter(thisAdapter);
        ChangeTextViewStyle.getInstance().forOrderPrice2(this, order_price_info_tv, "共1件商品 合计：￥14.80");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_order_details;
    }

    @Override
    protected void initialized() {
        thisAdapter = new ThisGoodsAdapter();
    }

    @Override
    protected void requestData() {

    }

    private class ThisGoodsAdapter extends BaseAdapter {

        private TGVH tgvh;

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = LayoutInflater.from(OrderDetailsAty.this).inflate(R.layout.item_goods_for_this_order_lv, null);
                tgvh = new TGVH();
                ViewUtils.inject(tgvh, view);
                view.setTag(tgvh);
            } else {
                tgvh = (TGVH) view.getTag();
            }

            return view;
        }

        class TGVH {

        }
    }

}
