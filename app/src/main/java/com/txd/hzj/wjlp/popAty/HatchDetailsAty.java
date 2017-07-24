package com.txd.hzj.wjlp.popAty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.utils.FragmentChangeManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.popAty.fgt.HatchLeftFgt;
import com.txd.hzj.wjlp.popAty.fgt.HatchRightFgt;

import java.util.ArrayList;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：下午 8:10
 * 描述：上市孵化
 * ===============Txunda===============
 */
public class HatchDetailsAty extends BaseAty {

    /**
     * 请红包
     */
    @ViewInject(R.id.ws_goods_tv)
    private TextView foot_print_goods_tv;
    @ViewInject(R.id.ws_goods_view)
    private View foot_print_goods_view;
    /**
     * 领券
     */
    @ViewInject(R.id.ws_mell_tv)
    private TextView foot_print_mell_tv;
    @ViewInject(R.id.ws_mell_view)
    private View foot_print_mell_view;

    private FragmentChangeManager fcm;

    private ArrayList<Fragment> mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.ws_title_layout);
        fcm = new FragmentChangeManager(getSupportFragmentManager(), R.id.ws_frame_layout, mFragment);
        setTvAndViewStyle(0);
    }


    @Override
    @OnClick({R.id.ws_title_left_layout, R.id.ws_title_right_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ws_title_left_layout:// 领红包
                setTvAndViewStyle(0);
                break;
            case R.id.ws_title_right_layout:// 领券
                setTvAndViewStyle(1);
                break;
        }
    }

    private void setTvAndViewStyle(int position) {
        foot_print_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        foot_print_mell_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        foot_print_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        foot_print_mell_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        if (0 == position) {
            foot_print_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            foot_print_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else if ((1 == position)) {
            foot_print_mell_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            foot_print_mell_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
        if (position < 2)
            fcm.setFragments(position);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_hatch_details;
    }

    @Override
    protected void initialized() {
        mFragment = new ArrayList<>();
        mFragment.add(new HatchLeftFgt());
        mFragment.add(new HatchRightFgt());
    }

    @Override
    protected void requestData() {

    }
}
