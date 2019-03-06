package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.ui.fragment.FindFragment;
import com.txd.hzj.wjlp.catchDoll.ui.fragment.HomeFragment;
import com.txd.hzj.wjlp.catchDoll.ui.fragment.MyFragment;

/**
 * 抓娃娃首页面
 */
public class CatchDollMainActivity extends BaseAty {

    @ViewInject(R.id.fragment_container)
    public FrameLayout fragment_container;
    @ViewInject(R.id.main_tableHome_rlayout)
    public RelativeLayout main_tableHome_rlayout;
    @ViewInject(R.id.main_tableHome_imgv)
    public ImageView main_tableHome_imgv;
    @ViewInject(R.id.main_tableFind_rlayout)
    public RelativeLayout main_tableFind_rlayout;
    @ViewInject(R.id.main_tableFind_imgv)
    public ImageView main_tableFind_imgv;
    @ViewInject(R.id.main_tableMoney_rlayout)
    public RelativeLayout main_tableMoney_rlayout;
    @ViewInject(R.id.main_tableMoney_imgv)
    public ImageView main_tableMoney_imgv;
    @ViewInject(R.id.main_tableMy_rlayout)
    public RelativeLayout main_tableMy_rlayout;
    @ViewInject(R.id.main_tableMy_imgv)
    public ImageView main_tableMy_imgv;

    private Fragment[] fragments; // Fragment列表数组
    private HomeFragment homeFragment; // 首页Fragment
    private FindFragment findFragment; // 发现Fragment
    private MyFragment myFragment; // 我的Fragment
    private Fragment currentFragment; // 当前显示的Fragment

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialized() {
        fragments = new Fragment[3];
        homeFragment = new HomeFragment();
        findFragment = new FindFragment();
        myFragment = new MyFragment();
        fragments[0] = homeFragment;
        fragments[1] = findFragment;
        fragments[2] = myFragment;
        setTabImage(R.id.main_tableHome_rlayout);
        switchFragment(homeFragment).commit();
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.main_tableHome_rlayout, R.id.main_tableFind_rlayout, R.id.main_tableMoney_rlayout, R.id.main_tableMy_rlayout})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.main_tableHome_rlayout:
                setTabImage(R.id.main_tableHome_rlayout);
                switchFragment(homeFragment).commit();
                break;
            case R.id.main_tableFind_rlayout:
                setTabImage(R.id.main_tableFind_rlayout);
                switchFragment(findFragment).commit();
                break;
            case R.id.main_tableMoney_rlayout:
                setTabImage(R.id.main_tableMoney_rlayout);
                startActivity(MoneyActivity.class, null);
                break;
            case R.id.main_tableMy_rlayout:
                setTabImage(R.id.main_tableMy_rlayout);
                switchFragment(myFragment).commit();
                break;
        }
    }

    private FragmentTransaction switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.fragment_container, fragment, fragment.getClass().getName());
        } else {
            transaction.hide(currentFragment).show(fragment);
        }
        currentFragment = fragment;
        return transaction;
    }

    public void setTabImage(int tabImage) {
        if (tabImage != R.id.main_tableMoney_rlayout) {
            main_tableHome_imgv.setImageResource(R.mipmap.icon_home);
            main_tableFind_imgv.setImageResource(R.mipmap.icon_find);
            main_tableMoney_imgv.setImageResource(R.mipmap.icon_money);
            main_tableMy_imgv.setImageResource(R.mipmap.icon_my);
            switch (tabImage) {
                case R.id.main_tableHome_rlayout:
                    main_tableHome_imgv.setImageResource(R.mipmap.icon_home_select);
                    break;
                case R.id.main_tableFind_rlayout:
                    main_tableFind_imgv.setImageResource(R.mipmap.icon_find_select);
                    break;
                case R.id.main_tableMy_rlayout:
                    main_tableMy_imgv.setImageResource(R.mipmap.icon_my_select);
                    break;
            }
        }
    }
}
