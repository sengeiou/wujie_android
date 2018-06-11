package com.txd.hzj.wjlp.distribution.shopAty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.adapter.ShopUpGoodsAdapet;
import com.txd.hzj.wjlp.distribution.shopFgt.ShopExhibitFragment;
import com.txd.hzj.wjlp.http.goods.GoodsPst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 小店上货
 */
public class ShopExhibit extends BaseAty implements AdapterView.OnItemClickListener, View.OnClickListener {

    @ViewInject(R.id.titlt_conter_tv)
    TextView titlt_conter_tv;

    @ViewInject(R.id.exhibit_tab_layout)
    SlidingTabLayout exhibit_tab_layout;

    @ViewInject(R.id.spread_img)
    ImageView spread_img;

    @ViewInject(R.id.vp_for_exhibit)
    ViewPager vp_for_exhibit;

    /**
     * 前一页选中的分类标识
     */
    private int pos = 0;

    private ArrayList<Fragment> fragments;

    private GoodsPst goodsPst;

    /**
     * 分类列表
     */
    private List<Map<String, String>> horizontal_classify;

    private String cate_id = "";

    private MyPagerAdapter myPagerAdapter;
    /**
     *
     * */
    private RelativeLayout ll_view;
    private ImageView back;
    private GridView grView;
    private ArrayList<String> lists;
    private ShopUpGoodsAdapet myAdapter;
    private View viewBack;
    private RelativeLayout rlLayout;
    private float pivotY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_goods_for_store;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("小店上货");
        goodsPst = new GoodsPst(this);
        fragments = new ArrayList<>();


        rlLayout = findViewById(R.id.rl_layout);
        viewBack = findViewById(R.id.shop_view_backs);
        ll_view = findViewById(R.id.views);
        back = findViewById(R.id.popup_back);
        grView = findViewById(R.id.gr_view);
        grView.setGravity(Gravity.CENTER);
        lists = new ArrayList<>();

        myAdapter = new ShopUpGoodsAdapet(this, lists);
        //注册点击事件
        grView.setAdapter(myAdapter);
        ll_view.setOnClickListener(this);
        viewBack.setOnClickListener(this);
        back.setOnClickListener(this);
        grView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        myAdapter.setseclection(position);
        myAdapter.notifyDataSetChanged();
        exhibit_tab_layout.onPageScrolled(position,0,0);
        exhibit_tab_layout.onPageSelected(position);
        ll_view.setVisibility(View.GONE);
        ll_view.setAnimation(moveToViewLocation());
    }

    @Override
    protected void requestData() {
        goodsPst.goodsList(1, cate_id, 0);
        pivotY = rlLayout.getPivotY();
        Log.i("Y轴距离", pivotY + "=========");
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        if (ToolKit.isList(data, "top_nav")) {
            horizontal_classify = JSONUtils.parseKeyAndValueToMapList(data.get("top_nav"));
            myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
            for (Map<String, String> title : horizontal_classify) {
//                fragments.add(ClassifyFgt.newInstance(title.get("cate_id")));
                fragments.add(ShopExhibitFragment.newInstance(title.get("cate_id"), ""));
            }
            for (int i = 0; i <horizontal_classify.size(); i++) {
                lists.add(horizontal_classify.get(i).get("short_name"));
            }
            // ViewPager设置适配器
            vp_for_exhibit.setAdapter(myPagerAdapter);
            // TabLayout绑定ViewPager
            exhibit_tab_layout.setViewPager(vp_for_exhibit);
            // 设置选中第pos个Tab
            exhibit_tab_layout.setCurrentTab(pos);
        }
    }

    @Override
    @OnClick({R.id.spread_img})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            //tablayout右边的展开按钮
            case R.id.spread_img:

                ll_view.setVisibility(View.VISIBLE);
                ll_view.setAnimation(moveToViewUp());
                viewBack.setVisibility(View.VISIBLE);
                viewBack.setAlpha(0.5f);
                break;
            case R.id.popup_back:
                ll_view.setVisibility(View.GONE);
                ll_view.setAnimation(moveToViewLocation());
                viewBack.setVisibility(View.GONE);
                viewBack.setAlpha(0f);
                break;
            case R.id.shop_view_backs:
                ll_view.setVisibility(View.GONE);
                ll_view.setAnimation(moveToViewLocation());
                viewBack.setVisibility(View.GONE);
                viewBack.setAlpha(0f);
        }

    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return horizontal_classify.get(position).get("short_name");
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

    public Animation moveToViewUp() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(400);
        return mHiddenAction;
    }

    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    public Animation moveToViewLocation() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(400);
        return mHiddenAction;
    }


}
