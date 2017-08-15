package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOffLine.OffLineMellInfoAty;
import com.txd.hzj.wjlp.mellOffLine.dialog.NoticeDialog;
import com.txd.hzj.wjlp.mellOnLine.adapter.MellOnlineGoodsAdapter;
import com.txd.hzj.wjlp.tool.GridDividerItemDecoration;
import com.txd.hzj.wjlp.view.UPMarqueeView;

import java.util.ArrayList;
import java.util.List;
/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/14 0014
 * 时间：下午 2:30
 * 描述：线上店铺详情
 * ===============Txunda===============
 */
public class MellInfoAty extends BaseAty {

    // ========人气，价格，销量，全部
    /**
     * 人气
     */
    @ViewInject(R.id.popularity_tv)
    private TextView popularity_tv;
    /**
     * 全部商品
     */
    @ViewInject(R.id.mell_price_tv)
    private TextView mell_price_tv;

    /**
     * 热销商品
     */
    @ViewInject(R.id.sales_tv)
    private TextView sales_tv;
    /**
     * 活动商品
     */
    @ViewInject(R.id.all_classify_tv)
    private TextView all_classify_tv;
    /**
     * 最新上架
     */
    @ViewInject(R.id.at_laster_tv)
    private TextView at_laster_tv;

    /**
     * 排序方式
     */
    private int soft_type = 0;

    @ViewInject(R.id.mell_goods_rv)
    private RecyclerView mell_goods_rv;
    @ViewInject(R.id.mell_goods_rv2)
    private RecyclerView mell_goods_rv2;


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

    @ViewInject(R.id.mell_noty_up_view)
    private UPMarqueeView mell_noty_up_view;

    /**
     * 误解头条数据
     */
    private List<String> data;
    /**
     * 无界头条View
     */
    private List<View> views;
    private NoticeDialog noticeDialog;

    private List<String> aty_type;

    private int pop_select = 0;

    /**
     * 数据源，此参数用于判断ReclycleView的item样式
     * 0.店铺首页，全部是图片，LinearManager方式
     * 1.全部商品，热销商品，最新上架
     * 2.限量购
     * 3.拼团购
     * 4.无界预购
     * 5.竞拍汇
     * 6.一元夺宝
     */
    private int data_type = 0;

    private MellOnlineGoodsAdapter mellOnlineGoodsAdapter;
    private boolean drawableLine = true;
    private int imageHeight = 0;

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

        setAdapterType(data_type);
        // 公告
        setView();
        mell_noty_up_view.setViews(views);
//        onChangeTitleColor();
    }


    @Override
    @OnClick({R.id.popularity_tv, R.id.mell_price_tv, R.id.sales_tv, R.id.at_laster_tv, R.id.all_classify_tv, R.id
            .mell_info_by_off_line})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.popularity_tv:// 店铺首页
                soft_type = 0;
                setStyle(soft_type);
                mCurPopupWindow = null;
                break;
            case R.id.mell_price_tv:// 全部商品
                soft_type = 1;
                setStyle(soft_type);
                mCurPopupWindow = null;
                break;
            case R.id.sales_tv:// 热销商品
                soft_type = 2;
                setStyle(soft_type);
                mCurPopupWindow = null;
                break;
            case R.id.at_laster_tv:// 最新上架
                soft_type = 3;
                setStyle(soft_type);
                mCurPopupWindow = null;
                break;
            case R.id.all_classify_tv:// 分类

                if (mCurPopupWindow == null) {
                    mCurPopupWindow = showPop(v);
                    mCurPopupWindow.update();
                } else {
                    mCurPopupWindow.dismiss();
                    mCurPopupWindow = null;
                }
                soft_type = 4;
                setStyle(soft_type);
                break;
            case R.id.mell_info_by_off_line:// 详情
                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
                startActivity(OffLineMellInfoAty.class, bundle);
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
        data = new ArrayList<>();
        views = new ArrayList<>();
        data.add("这是公告，这只是一个公告1");
        data.add("这是公告，这只是一个公告2");
        data.add("这是公告，这只是一个公告3");
        data.add("这是公告，这只是一个公告4");
        data.add("这是公告，这只是一个公告5");

        aty_type = new ArrayList<>();
        aty_type.add("限量购");
        aty_type.add("拼团购");
        aty_type.add("无界预购");
        aty_type.add("竞拍汇");
        aty_type.add("一元夺宝");


    }

    @Override
    protected void requestData() {

    }

    private void setStyle(int type) {
        pop_select = 0;
        // 店铺详情
        popularity_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        // 全部商品
        mell_price_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        // 热销商品
        sales_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        // 最新上架
        at_laster_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        // 活动商品
        all_classify_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        if (0 == type) {
            popularity_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            data_type = 0;
            setAdapterType(data_type);
        } else if (1 == type) {
            mell_price_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            data_type = 1;
            setAdapterType(1);
        } else if (2 == type) {
            sales_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            data_type = 1;
            setAdapterType(1);
        } else if (3 == type) {
            at_laster_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            all_classify_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
    }

    private PopupWindow showPop(View view) {
        final View contentView = LayoutInflater.from(view.getContext()).inflate(
                R.layout.pop_classify_layout, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);

        GridView classify_gv = contentView.findViewById(R.id.classify_gv);
        final ClassIfyAdapter classIfyAdapter = new ClassIfyAdapter();
        classify_gv.setAdapter(classIfyAdapter);
        classIfyAdapter.setSelected(pop_select);
        classify_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                all_classify_tv.setText(aty_type.get(i));
                all_classify_tv.setTextColor(ContextCompat.getColor(MellInfoAty.this, R.color.theme_color));
                classIfyAdapter.setSelected(i);
                classIfyAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                pop_select = i;
                mCurPopupWindow = null;
                data_type = i + 2;
                setAdapterType(data_type);

            }
        });
        contentView.findViewById(R.id.be_dissmis_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                mCurPopupWindow = null;
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

    public void setAdapterType(int adapterType) {
        mellOnlineGoodsAdapter = new MellOnlineGoodsAdapter(this, adapterType);
        if (0 == adapterType) {
            mell_goods_rv2.setVisibility(View.VISIBLE);
            mell_goods_rv.setVisibility(View.GONE);
            mell_goods_rv2.setLayoutManager(new LinearLayoutManager(this));
            mell_goods_rv2.setAdapter(mellOnlineGoodsAdapter);
        } else {

            mell_goods_rv.setVisibility(View.VISIBLE);
            mell_goods_rv2.setVisibility(View.GONE);
            mell_goods_rv.setLayoutManager(new GridLayoutManager(this, 2));
            if (drawableLine) {
                drawableLine = false;
                mell_goods_rv.addItemDecoration(new GridDividerItemDecoration(height, Color.parseColor("#F6F6F6")));
            }
            mell_goods_rv.setAdapter(mellOnlineGoodsAdapter);
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
            return aty_type.size();
        }

        @Override
        public String getItem(int i) {
            return aty_type.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            String type = getItem(i);
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
            cvh.classify_text_tv.setText(type);
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

    /**
     * 初始化需要循环的View
     * 为了灵活的使用滚动的View，所以把滚动的内容让用户自定义
     * 假如滚动的是三条或者一条，或者是其他，只需要把对应的布局，和这个方法稍微改改就可以了，
     */
    private void setView() {
        for (int i = 0; i < data.size(); i++) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(MellInfoAty.this).inflate(
                    R.layout.item_view, null);
            //初始化布局的控件
            TextView tv1 = moreView.findViewById(R.id.tv1);
            tv1.setTextColor(Color.WHITE);
            tv1.setGravity(Gravity.CENTER_VERTICAL);
            tv1.setTextSize(10);
            /**
             * 设置监听
             */
            tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noticeDialog = new NoticeDialog(MellInfoAty.this);
                    noticeDialog.show();
                }
            });
            //进行对控件赋值
            tv1.setText(data.get(i));
            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    private void onChangeTitleColor() {
        ViewTreeObserver vto = mell_info_app_bar_layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                mell_info_app_bar_layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                imageHeight = mell_info_app_bar_layout.getHeight();
            }
        });
        L.e("=====高度=====", imageHeight + "");
    }
}
