package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsEvalusteAdapter;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.flowlayout.FlowLayout;
import com.txd.hzj.wjlp.view.flowlayout.TagAdapter;
import com.txd.hzj.wjlp.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：下午 5:49
 * 描述：商品评价
 * ===============Txunda===============
 */
public class GoodsEvaluateAty extends BaseAty implements ObservableScrollView.ScrollViewListener {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 商品评价类型
     */
    @ViewInject(R.id.goods_comment_tag)
    private TagFlowLayout goods_comment_tag;

    @ViewInject(R.id.goods_evaluste_lv)
    private ListViewForScrollView goods_evaluste_lv;

    private List<String> data;
    private List<String> pic;
    /**
     * 0.商品全部评价
     * 1.我的全部评价
     * 2.店铺全部评价
     */
    private int from = 0;

    @ViewInject(R.id.evaluate_lin_layout)
    private LinearLayout evaluate_lin_layout;

    private GoodsEvalusteAdapter goodsEvalusteAdapter;

    private List<String> goodsTypes;

    /**
     * 滚动监听
     */
    @ViewInject(R.id.goods_comment_sc)
    private ObservableScrollView goods_comment_sc;

    /**
     * 回到顶部
     */
    @ViewInject(R.id.gc_be_back_top_iv)
    private ImageView gc_be_back_top_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        if (0 == from) {
            titlt_conter_tv.setText("全部评价(45)");
            evaluate_lin_layout.setVisibility(View.GONE);
            goods_comment_tag.setVisibility(View.VISIBLE);
        } else if(1 == from){
            titlt_conter_tv.setText("我的评价");
            evaluate_lin_layout.setVisibility(View.VISIBLE);
            goods_comment_tag.setVisibility(View.GONE);
        } else{
            titlt_conter_tv.setText("店铺评价");
            evaluate_lin_layout.setVisibility(View.VISIBLE);
            goods_comment_tag.setVisibility(View.GONE);
        }
        goods_evaluste_lv.setAdapter(goodsEvalusteAdapter);

        TagAdapter<String> tagAdapter = new TagAdapter<String>(goodsTypes) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(GoodsEvaluateAty.this).inflate(R.layout
                                .item_goods_attrs_tfl,
                        parent, false);
                tv.setText(s);
                return tv;
            }
        };
        goods_comment_tag.setAdapter(tagAdapter);
        tagAdapter.setSelectedList(0);

        // 滚动到顶部
        goods_comment_sc.smoothScrollTo(0, 0);
        // 滚动监听
        goods_comment_sc.setScrollViewListener(this);
    }

    @Override
    @OnClick({R.id.gc_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.gc_be_back_top_iv:
                goods_comment_sc.smoothScrollTo(0, 0);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_goods_evaluate;
    }

    @Override
    protected void initialized() {
        from = getIntent().getIntExtra("from", 0);
        data = new ArrayList<>();
        pic = new ArrayList<>();
        goodsEvalusteAdapter = new GoodsEvalusteAdapter(this, data, pic, from);
        goodsTypes = new ArrayList<>();
        goodsTypes.add("全部(198)");
        goodsTypes.add("精华(98)");
        goodsTypes.add("有图(18)");
        goodsTypes.add("户型(19)");
        goodsTypes.add("5星(98)");
        goodsTypes.add("4星(18)");
        goodsTypes.add("3星(98)");
        goodsTypes.add("2星(18)");
        goodsTypes.add("1星(18)");
        goodsTypes.add("交通便利(18)");
        goodsTypes.add("工程质量好(8)");
        goodsTypes.add("户型完美(98)");
    }

    @Override
    protected void requestData() {
    }


    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y < Settings.displayWidth) {
            gc_be_back_top_iv.setVisibility(View.GONE);
        } else {
            gc_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }
}
