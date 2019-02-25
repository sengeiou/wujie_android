package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.adapter.MyDollViewPagerAdapter;
import com.txd.hzj.wjlp.catchDoll.ui.fragment.MyDollFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class MyDollActivity extends BaseAty {

    @ViewInject(R.id.titleView_title_tv)
    public TextView titleView_title_tv;
    @ViewInject(R.id.myDoll_header_imgv)
    public ImageView myDoll_header_imgv;
    @ViewInject(R.id.myDoll_name_tv)
    public TextView myDoll_name_tv;
    @ViewInject(R.id.myDoll_count_tv)
    public TextView myDoll_count_tv;
    @ViewInject(R.id.myDoll_table_tablayout)
    public TabLayout myDoll_table_tablayout;
    @ViewInject(R.id.myDoll_content_vp)
    public ViewPager myDoll_content_vp;

    private List<Fragment> fragmentList;
    private List<String> titleList;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_doll;
    }

    @Override
    protected void initialized() {
        titleView_title_tv.setText("我的娃娃");

        titleList = new ArrayList<>();
        titleList.add("寄存中(4)");
        titleList.add("待邮寄(0)");
        titleList.add("已发货(0)");
        titleList.add("已兑换(0)");

        fragmentList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fragmentList.add(new MyDollFragment(i));
        }
        myDoll_content_vp.setAdapter(new MyDollViewPagerAdapter(getSupportFragmentManager(), fragmentList));

        for (int i = 0; i < titleList.size(); i++) {
            myDoll_table_tablayout.addTab(myDoll_table_tablayout.newTab());
        }

        myDoll_table_tablayout.setupWithViewPager(myDoll_content_vp); // 关联viewpager

        for (int i = 0; i < titleList.size(); i++) {
            myDoll_table_tablayout.getTabAt(i).setText(titleList.get(i));
        }
    }

    @Override
    protected void requestData() {
    }

    @OnClick({R.id.titleView_goback_imgv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleView_goback_imgv:
                finish();
                break;
        }
    }
}
