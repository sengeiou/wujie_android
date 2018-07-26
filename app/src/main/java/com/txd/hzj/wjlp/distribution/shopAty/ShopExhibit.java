package com.txd.hzj.wjlp.distribution.shopAty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.adapter.ShopUpGoodsAdapet;
import com.txd.hzj.wjlp.distribution.bean.ExhibitGoodsBean;
import com.txd.hzj.wjlp.distribution.presenter.ShopExhibitPst;
import com.txd.hzj.wjlp.distribution.shopFgt.ShopExhibitFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：Zyf
 * 功能描述：小店上货
 * 联系方式：无
 */
public class ShopExhibit extends BaseAty implements AdapterView.OnItemClickListener, View.OnClickListener {

    private TextView titlt_conter_tv;
    private LinearLayout shop_person_title_manage;

    private SlidingTabLayout exhibit_tab_layout;

    private ImageView spread_img;

    private ViewPager vp_for_exhibit;

    /**
     * 前一页选中的分类标识
     */
    private int pos = 0;

    private ArrayList<Fragment> fragments;

    private ShopExhibitPst mExhibitPst;

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
    private List<ExhibitGoodsBean.DataBean.TopNavBean> mTop_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_goods_for_store;
    }



    @Override
    protected void initialized() {
        titlt_conter_tv = findViewById(R.id.titlt_conter_tv);
        shop_person_title_manage = findViewById(R.id.shop_person_title_manage);
        shop_person_title_manage.setVisibility(View.GONE);

        exhibit_tab_layout = findViewById(R.id.exhibit_tab_layout);
        spread_img = findViewById(R.id.spread_img);
        vp_for_exhibit = findViewById(R.id.vp_for_exhibit);

        rlLayout = findViewById(R.id.rl_layout);
        viewBack = findViewById(R.id.shop_view_backs);
        ll_view = findViewById(R.id.views);
        back = findViewById(R.id.popup_back);
        grView = findViewById(R.id.gr_view);
        grView.setGravity(Gravity.CENTER);
        lists = new ArrayList<>();

        titlt_conter_tv.setText("小店上货");
        mExhibitPst = new ShopExhibitPst(this);
        fragments = new ArrayList<>();

        myAdapter = new ShopUpGoodsAdapet(this, lists);
        //注册点击事件
        grView.setAdapter(myAdapter);
        spread_img.setOnClickListener(this);
        ll_view.setOnClickListener(this);
        viewBack.setOnClickListener(this);
        back.setOnClickListener(this);
        grView.setOnItemClickListener(this);
        exhibit_tab_layout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                pos=position;
//                cate_id=mTwo_cate_list.get(position).getId();
//                mExhibitPst.goodsList("1",cate_id,1);
                Toast.makeText(ShopExhibit.this, ""+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        myAdapter.setseclection(position);
        myAdapter.notifyDataSetChanged();
        exhibit_tab_layout.onPageScrolled(position, 0, 0);
        exhibit_tab_layout.onPageSelected(position);
        ll_view.setVisibility(View.GONE);
        ll_view.setAnimation(moveToViewLocation());
    }

    @Override
    protected void requestData() {
        mExhibitPst.goodsList("1", cate_id,"","",0);
        pivotY = rlLayout.getPivotY();
        Log.i("Y轴距离", pivotY + "=========");
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        ExhibitGoodsBean exhibitGoodsBean = JSONObject.parseObject(jsonStr, ExhibitGoodsBean.class);
        if (200 == exhibitGoodsBean.getCode()) {
            mTop_nav = exhibitGoodsBean.getData().getTop_nav();
            myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
            for (ExhibitGoodsBean.DataBean.TopNavBean topNavBean : mTop_nav) {
                fragments.add(ShopExhibitFragment.newInstance(topNavBean.getCate_id()));
                lists.add(topNavBean.getShort_name());
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
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);

    }

    @Override
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




    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return lists.get(position);
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
