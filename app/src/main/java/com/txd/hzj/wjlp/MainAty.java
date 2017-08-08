package com.txd.hzj.wjlp;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.flyco.tablayout.utils.FragmentChangeManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.UpdataApp;
import com.txd.hzj.wjlp.http.updataApp.UpdataPst;
import com.txd.hzj.wjlp.login.LoginAty;
import com.txd.hzj.wjlp.mainFgt.CartFgt;
import com.txd.hzj.wjlp.mainFgt.MellOffLineFgt;
import com.txd.hzj.wjlp.mainFgt.MellonLineFgt;
import com.txd.hzj.wjlp.mainFgt.MineFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketZoonAty;
import com.txd.hzj.wjlp.minetoAty.order.OnlineShopAty;
import com.txd.hzj.wjlp.minetoAty.order.OrderCenterAty;
import com.txd.hzj.wjlp.popAty.WJHatchAty;
import com.txd.hzj.wjlp.popAty.WelfareServiceAty;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/3 0003
 * 时间：下午 1:16
 * 描述：无界优品主页
 * ===============Txunda===============
 */
public class MainAty extends BaseAty implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.app_main_rg)
    private RadioGroup app_main_rg;

    /**
     * 线上商城
     */
    @ViewInject(R.id.home_pager_rb)
    private RadioButton home_pager_rb;
    /**
     * 线下商城
     */
    @ViewInject(R.id.mell_offline_rb)
    private RadioButton mell_offline_rb;
    /**
     * 购物车
     */
    @ViewInject(R.id.cart_rb)
    private RadioButton cart_rb;
    /**
     * 我的
     */
    @ViewInject(R.id.mine_rb)
    private RadioButton mine_rb;

    /**
     * 更多
     */
    @ViewInject(R.id.mach_more_iv)
    private ImageView mach_more_iv;
    /**
     * 更多
     */
    @ViewInject(R.id.mach_more_tv)
    private TextView mach_more_tv;

    private int page_index = 0;
    // 碎片
    private MellonLineFgt mellonLineFgt;
    private MellOffLineFgt mellOffLineFgt;
    private CartFgt cartFgt;
    private MineFgt mineFgt;
    private ArrayList<Fragment> fragments;
    private FragmentChangeManager fragmentChangeManager;

    private PopupWindow mCurPopupWindow;

    // 更新
    private UpdataPst updataPst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_main_rg.setOnCheckedChangeListener(this);
        fragmentChangeManager = new FragmentChangeManager(this.getSupportFragmentManager(), R.id.main_content,
                fragments);
        L.e("=====手机厂商=====", android.os.Build.BRAND);
        //Huawei,,
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_main;
    }

    @Override
    protected void initialized() {
        mellonLineFgt = new MellonLineFgt();
        mellOffLineFgt = new MellOffLineFgt();
        cartFgt = new CartFgt();
        mineFgt = new MineFgt();
        fragments = new ArrayList<>();
        fragments.add(mellonLineFgt);
        fragments.add(mellOffLineFgt);
        fragments.add(cartFgt);
        fragments.add(mineFgt);

        updataPst = new UpdataPst(this);
    }

    @Override
    protected void requestData() {
        updataPst.toUpdata();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Config.isLogin()) {
            switch (page_index) {
                case 0:// 线上商城
                    home_pager_rb.setChecked(true);
                    break;
                case 1:// 线下商城
                    mell_offline_rb.setChecked(true);
                    break;
                case 2:// 购物车
                    cart_rb.setChecked(true);
                    break;
                case 3:// 我的
                    mine_rb.setChecked(true);
                    break;
            }
        } else {
            switch (page_index) {
                case 0:// 线上商城
                case 2:// 购物车
                case 3:// 我的
                    home_pager_rb.setChecked(true);
                    break;
                case 1:// 线下商城
                    mell_offline_rb.setChecked(true);
                    break;
            }
        }
    }

    @Override
    @OnClick({R.id.mach_more_lin_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.mach_more_lin_layout:// 更多
                if (mCurPopupWindow == null) {
                    mCurPopupWindow = showPopupWindow(v);
                    mach_more_tv.setText("关闭");
                    mach_more_iv.setImageResource(R.drawable.icon_main_close_hzj);
                } else {
                    setMoreStatus();
                    mCurPopupWindow.dismiss();
                    mCurPopupWindow = null;
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.home_pager_rb:// 线上商城
                page_index = 0;
                fragmentChangeManager.setFragments(0);
                break;
            case R.id.mell_offline_rb:// 线下商城
                page_index = 1;
                fragmentChangeManager.setFragments(1);
                break;
            case R.id.cart_rb:// 购物车
                if (!Config.isLogin()) {
                    startActivity(LoginAty.class, null);
                    break;
                }
                page_index = 2;
                fragmentChangeManager.setFragments(2);
                break;
            case R.id.mine_rb:// 我的
                if (!Config.isLogin()) {
                    startActivity(LoginAty.class, null);
                    break;
                }
                page_index = 3;
                fragmentChangeManager.setFragments(3);
                break;
        }
    }

    private long firstTime;

    @Override
    public void onBackPressed() {
        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
            mCurPopupWindow.dismiss();
            setMoreStatus();
        } else {
            if (System.currentTimeMillis() - firstTime < 1500) {
                hasAnimiation = false;
                AppManager.getInstance().killAllActivity();
            } else {
                firstTime = System.currentTimeMillis();
                showToast("再按一次返回桌面");
            }
        }
    }

    /**
     * 更多
     */
    private void setMoreStatus() {
        mach_more_tv.setText("更多");
        mach_more_iv.setImageResource(R.drawable.icon_main_more);
    }

    /**
     * 更多弹窗
     *
     * @param anchorView 点击的按钮
     * @return PopupWindow
     */
    private PopupWindow showPopupWindow(View anchorView) {
        final View contentView = LayoutInflater.from(anchorView.getContext()).inflate(
                R.layout.main_popup_windeow_layout, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, Settings.displayWidth, false);

        // setOutsideTouchable设置生效的前提是setTouchable(true)和setFocusable(false)
        popupWindow.setOutsideTouchable(false);

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
        /**
         * 无界商城
         */
        contentView.findViewById(R.id.main_wj_shop_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMoreStatus();
                Bundle mBundle = new Bundle();
//                mBundle.putString("title", "爱心商店");
//                startActivity(OnlineShopAty.class, mBundle);

                mBundle.putInt("type", 10);
                mBundle.putString("title", "无界商店");
                startActivity(TicketZoonAty.class, mBundle);

                popupWindow.dismiss();
            }
        });
        /**
         * 福利社
         */
        contentView.findViewById(R.id.welfare_service_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMoreStatus();
                startActivity(WelfareServiceAty.class, null);
                popupWindow.dismiss();
            }
        });
        /**
         * 上市孵化
         */
        contentView.findViewById(R.id.main_wj_hatch_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMoreStatus();
                startActivity(WJHatchAty.class, null);
                popupWindow.dismiss();
            }
        });
        /**
         * 股东招募
         */
        contentView.findViewById(R.id.recruit_stockholder_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMoreStatus();
                popupWindow.dismiss();
            }
        });
        popupWindow.showAsDropDown(anchorView);
        return popupWindow;
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        UpdataApp updataApp = GsonUtil.GsonToBean(jsonStr, UpdataApp.class);
        L.e("=====更新=====", updataApp.toString());
    }
}
