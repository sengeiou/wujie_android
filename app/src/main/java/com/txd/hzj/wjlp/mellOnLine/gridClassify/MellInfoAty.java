package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.MellGoodsAdapter;
import com.txd.hzj.wjlp.mellOnLine.fgt.mellFgt.MellAllGoodsFgt;
import com.txd.hzj.wjlp.mellOnLine.fgt.mellFgt.MellGuideFgt;
import com.txd.hzj.wjlp.mellOnLine.fgt.mellFgt.MellInfoFgt;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MellInfoAty extends BaseAty {

    // ========人气，价格，销量，全部
    /**
     * 人气
     */
    @ViewInject(R.id.popularity_tv)
    private TextView popularity_tv;
    /**
     * 价格
     */
    @ViewInject(R.id.mell_price_tv)
    private TextView mell_price_tv;
    @ViewInject(R.id.mell_price_iv)
    private ImageView mell_price_iv;

    /**
     * 销量
     */
    @ViewInject(R.id.sales_tv)
    private TextView sales_tv;
    /**
     * 全部
     */
    @ViewInject(R.id.all_classify_tv)
    private TextView all_classify_tv;

    /**
     * 排序方式
     */
    private int soft_type = 0;
    /**
     * 价格排序方式
     * 偶数升序
     * 奇数降序
     */
    private int price_type = 0;

    @ViewInject(R.id.mell_goods_rv)
    private RecyclerView mell_goods_rv;


    private PopupWindow mCurPopupWindow;

    /**
     * 店铺信息布局
     */
    @ViewInject(R.id.mell_info_head_layout)
    private LinearLayout mell_info_head_layout;

    @ViewInject(R.id.mell_info_app_bar_layout)
    private AppBarLayout mell_info_app_bar_layout;

    @ViewInject(R.id.mell_info_tool_layout)
    private CollapsingToolbarLayout mell_info_tool_layout;

    /**
     * TitleBar
     */
    @ViewInject(R.id.mell_tool_bar)
    private Toolbar mell_tool_bar;
    private int height;
    private MellGoodsAdapter mellGoodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mell_tool_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mell_tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        showStatusBar(R.id.mell_tool_bar);

        mell_info_app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -mell_info_head_layout.getHeight() / 2) {
                    mell_info_tool_layout.setTitle("康尔馨酒店家纺");
                } else {
                    mell_info_tool_layout.setTitle(" ");
                }
            }
        });

        mell_goods_rv.setLayoutManager(new GridLayoutManager(this, 2));
        mell_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
        mell_goods_rv.setAdapter(mellGoodsAdapter);

    }


    @Override
    @OnClick({R.id.popularity_tv, R.id.mell_price_layout, R.id.sales_tv, R.id.all_classify_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.popularity_tv:// 人气
                soft_type = 0;
                setStyle(soft_type);
                break;
            case R.id.mell_price_layout:// 价格
                soft_type = 1;
                setStyle(soft_type);
                break;
            case R.id.sales_tv:// 销量
                soft_type = 2;
                setStyle(soft_type);
                break;
            case R.id.all_classify_tv:// 分类
                if (mCurPopupWindow == null || !mCurPopupWindow.isShowing()) {
                    mCurPopupWindow = showPop(v);
                } else {
                    mCurPopupWindow.dismiss();
                }
                break;
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_info_hzj;
    }

    @Override
    protected void initialized() {

        height = ToolKit.dip2px(this, 4);
        mellGoodsAdapter = new MellGoodsAdapter(this, 0);
    }

    @Override
    protected void requestData() {

    }

    private void setStyle(int type) {
        popularity_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        mell_price_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        sales_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        mell_price_iv.setImageResource(R.mipmap.icon_screen_default_chen);

        if (0 == type) {
            price_type = 0;
            popularity_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else if (1 == type) {
            mell_price_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

            if (price_type % 2 == 0) {
                mell_price_iv.setImageResource(R.mipmap.icon_screen_down_chen);
            } else {
                mell_price_iv.setImageResource(R.mipmap.icon_screen_top_chen);
            }
            price_type++;
        } else {
            sales_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            price_type = 0;
        }
    }

    private PopupWindow showPop(View view) {
        final View contentView = LayoutInflater.from(view.getContext()).inflate(
                R.layout.pop_classify_layout, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);

        GridView classify_gv = contentView.findViewById(R.id.classify_gv);
        final ClassIfyAdapter classIfyAdapter = new ClassIfyAdapter();
        classify_gv.setAdapter(classIfyAdapter);
        classify_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                all_classify_tv.setText("拼团");
                classIfyAdapter.setSelected(i);
                classIfyAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });
        contentView.findViewById(R.id.be_dissmis_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        // setOutsideTouchable设置生效的前提是setTouchable(true)和setFocusable(false)
        popupWindow.setOutsideTouchable(true);

        // 设置为true之后，PopupWindow内容区域 才可以响应点击事件
        popupWindow.setTouchable(true);

        // true时，点击返回键先消失 PopupWindow
        // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
        // false时PopupWindow不处理返回键
        popupWindow.setFocusable(false);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;   // 这里面拦截不到返回键
            }
        });
        popupWindow.showAsDropDown(view);
        return popupWindow;
    }

    @Override
    public void onBackPressed() {
        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
            mCurPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 全部筛选适配器
     */
    private class ClassIfyAdapter extends BaseAdapter {

        private CVH cvh;

        private int selected = 0;

        @Override
        public int getCount() {
            return 12;
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
                view = LayoutInflater.from(MellInfoAty.this).inflate(R.layout.item_classify_gv, null);
                cvh = new CVH();
                ViewUtils.inject(cvh, view);
                view.setTag(cvh);
            } else {
                cvh = (CVH) view.getTag();
            }

            if (selected == i) {
                cvh.classify_text_tv.setTextColor(ContextCompat.getColor(MellInfoAty.this, R.color.colorAccent));
            } else {
                cvh.classify_text_tv.setTextColor(ContextCompat.getColor(MellInfoAty.this, R.color.app_text_color));
            }

            return view;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }

        class CVH {

            @ViewInject(R.id.classify_text_tv)
            private TextView classify_text_tv;

        }
    }



}
