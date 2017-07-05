package com.txd.hzj.wjlp.mainFgt.mellOnLine;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mainFgt.adapter.GVClassifyAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.HorizontalAdapter;
import com.txd.hzj.wjlp.mainFgt.adapter.RacycleAllAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/5 0005
 * 时间：上午 11:48
 * 描述：线上商城分类(1-1-1 首页 2)
 * ===============Txunda===============
 */
public class MellOnLineClassifyAty extends BaseAty {
    /**
     * 标题栏
     */
    @ViewInject(R.id.search_title_layout)
    public RelativeLayout search_title_layout;
    /**
     * 扫一扫
     */
    @ViewInject(R.id.title_scan_tv)
    public TextView title_scan_tv;

    @ViewInject(R.id.title_search_tv)
    public TextView title_search_tv;
    /**
     * 分类
     */
    @ViewInject(R.id.title_classify_tv)
    public TextView title_classify_tv;
    /**
     * 消息
     */
    @ViewInject(R.id.search_title_message_tv)
    public TextView search_title_message_tv;
    /**
     * 消息顶部的图标
     */
    private Drawable top;
    /**
     * 搜索左侧的图标
     */
    private Drawable search_left;

    /**
     * 横向滑动的分类
     */
    @ViewInject(R.id.on_line_rv)
    private RecyclerView on_line_rv;
    /**
     * 分类列表
     */
    private List<String> horizontal_classify;

    /**
     * 横向滑动的分类适配器
     */
    private HorizontalAdapter horizontalAdapter;

    /**
     * GridView分类
     */
    @ViewInject(R.id.on_lin_classify_gv)
    private GridViewForScrollView on_lin_classify_gv;
    /**
     * GridView数据列表
     */
    private List<String> gv_classify;

    private GVClassifyAdapter gvClassifyAdapter;
    /**
     * 前一页选中的分类标识
     */
    private int pos = 0;

    @ViewInject(R.id.classify_goods_rv)
    private RecyclerView classify_goods_rv;

    private RacycleAllAdapter racycleAllAdapter;

    private List<String> data;

    private int height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forTitle();
        showStatusBar(R.id.search_title_layout);
        forClassify();
        classify_goods_rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        classify_goods_rv.setItemAnimator(new DefaultItemAnimator());
        classify_goods_rv.setHasFixedSize(true);
        classify_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
        classify_goods_rv.setAdapter(racycleAllAdapter);
    }

    /**
     * 标题栏
     */
    private void forTitle() {
        search_title_layout.setBackgroundColor(Color.WHITE);
        title_scan_tv.setVisibility(View.GONE);
        title_classify_tv.setVisibility(View.GONE);

        title_search_tv.setBackgroundResource(R.drawable.shape_search_tv_bg);
        title_search_tv.setCompoundDrawables(search_left, null, null, null);
        title_search_tv.setHintTextColor(Color.parseColor("#9E9E9E"));

        search_title_message_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        search_title_message_tv.setCompoundDrawables(null, top, null, null);
    }

    /**
     * 分类
     */
    private void forClassify() {
        // 设置布局方式
        on_line_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // 默认分割线
        on_line_rv.setItemAnimator(new DefaultItemAnimator());
        on_line_rv.setHasFixedSize(true);
        on_line_rv.setAdapter(horizontalAdapter);
        horizontalAdapter.setSelected(pos);
        horizontalAdapter.setListener(new HorizontalAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                horizontalAdapter.setSelected(position);
                horizontalAdapter.notifyDataSetChanged();
            }
        });
        on_lin_classify_gv.setAdapter(gvClassifyAdapter);
        on_lin_classify_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(SubclassificationAty.class, null);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_on_line_classify;
    }

    @Override
    protected void initialized() {
        pos = getIntent().getIntExtra("pos", 0);
        top = ContextCompat.getDrawable(this, R.drawable.icon_message_gray);
        top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
        search_left = ContextCompat.getDrawable(this, R.drawable.icon_search_gray);
        search_left.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
        horizontal_classify = new ArrayList<>();
        horizontal_classify.add("推荐");
        horizontal_classify.add("食品");
        horizontal_classify.add("生鲜");
        horizontal_classify.add("服饰");
        horizontal_classify.add("家居");
        horizontal_classify.add("进口");
        horizontal_classify.add("美妆");
        horizontal_classify.add("母婴");
        horizontal_classify.add("电子");
        horizontalAdapter = new HorizontalAdapter(horizontal_classify, this);
        gvClassifyAdapter = new GVClassifyAdapter(this, gv_classify);
        data = new ArrayList<>();
        racycleAllAdapter = new RacycleAllAdapter(this, data);
        height = ToolKit.dip2px(this, 4);
    }

    @Override
    protected void requestData() {

    }
}
