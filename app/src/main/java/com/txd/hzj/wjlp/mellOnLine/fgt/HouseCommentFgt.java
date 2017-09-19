package com.txd.hzj.wjlp.mellOnLine.fgt;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.mellOnLine.adapter.HouseCommentAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.GoodsAttributeAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt.HousDetailsTypeChenFgt;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.flowlayout.FlowLayout;
import com.txd.hzj.wjlp.view.flowlayout.TagAdapter;
import com.txd.hzj.wjlp.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/28 0028
 * 时间：上午 9:22
 * 描述：点评
 * ===============Txunda===============
 */
public class HouseCommentFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {

    @ViewInject(R.id.house_comment_tag)
    private TagFlowLayout house_comment_tag;

    private List<String> commentTypes;

    /**
     * 点评
     */
    @ViewInject(R.id.house_comment_lv)
    private ListViewForScrollView house_comment_lv;
    /**
     * 点评适配器
     */
    private HouseCommentAdapter commentAdapter;

    /**
     * 回到顶部
     */
    @ViewInject(R.id.hc_be_back_top_iv)
    private ImageView hc_be_back_top_iv;

    /**
     * 滚动监听的ScollView
     */
    @ViewInject(R.id.comment_sc)
    private ObservableScrollView comment_sc;

    private String house_id = "";

    public static HouseCommentFgt getFgt(String house_id) {
        HouseCommentFgt housDetailsHousesChenFgt = new HouseCommentFgt();
        housDetailsHousesChenFgt.house_id = house_id;
        return housDetailsHousesChenFgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TagAdapter<String> tabAdapter = new TagAdapter<String>(commentTypes) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_goods_attrs_tfl,
                        parent, false);
                tv.setText(s);
                return tv;
            }
        };
        house_comment_tag.setAdapter(tabAdapter);
        tabAdapter.setSelectedList(0);

        house_comment_lv.setAdapter(commentAdapter);

        comment_sc.smoothScrollTo(0, 0);
        comment_sc.setScrollViewListener(this);
    }

    @Override
    @OnClick({R.id.hc_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.hc_be_back_top_iv:
                comment_sc.smoothScrollTo(0, 0);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_house_comment;
    }

    @Override
    protected void initialized() {
        commentTypes = new ArrayList<>();
        commentAdapter = new HouseCommentAdapter(getActivity());
        commentTypes.add("全部(198)");
        commentTypes.add("精华(98)");
        commentTypes.add("有图(18)");
        commentTypes.add("户型(19)");
        commentTypes.add("5星(98)");
        commentTypes.add("4星(18)");
        commentTypes.add("3星(98)");
        commentTypes.add("2星(18)");
        commentTypes.add("1星(18)");
        commentTypes.add("交通便利(18)");
        commentTypes.add("工程质量好(8)");
        commentTypes.add("户型完美(98)");
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        L.e("=====偏移量=====", y + "");
        if (y < Settings.displayWidth) {
            hc_be_back_top_iv.setVisibility(View.GONE);
        } else {
            hc_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }
}
