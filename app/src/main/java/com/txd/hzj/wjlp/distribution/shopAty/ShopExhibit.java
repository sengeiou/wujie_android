package com.txd.hzj.wjlp.distribution.shopAty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.shopFgt.ShopExhibitFragment;
import com.txd.hzj.wjlp.http.goods.GoodsPst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 小店上货
 */
public class ShopExhibit extends BaseAty {

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
    }

    @Override
    protected void requestData() {
        goodsPst.goodsList(1, cate_id, 0);
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
                fragments.add(ShopExhibitFragment.newInstance(title.get("cate_id"),""));
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
        switch (v.getId()){
            //tablayout右边的展开按钮
            case R.id.spread_img:
                Toast.makeText(ShopExhibit.this, "1111", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private   class  MyPagerAdapter extends FragmentPagerAdapter{
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
}
