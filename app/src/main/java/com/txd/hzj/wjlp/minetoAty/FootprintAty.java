package com.txd.hzj.wjlp.minetoAty;

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
import com.txd.hzj.wjlp.minetoAty.collect.fgt.CollectBooksFgt;
import com.txd.hzj.wjlp.minetoAty.collect.fgt.CollectGoodsHzjFgt;
import com.txd.hzj.wjlp.minetoAty.collect.fgt.CollectMellHzjFgt;

import java.util.ArrayList;

import static com.txd.hzj.wjlp.R.id.left_goods_tv;
import static com.txd.hzj.wjlp.R.id.middle_mell_tv;
import static com.txd.hzj.wjlp.R.id.right_books_tv;

public class FootprintAty extends BaseAty {

    /**
     * 编辑,完成
     */
    @ViewInject(R.id.foot_right_tv)
    private TextView foot_right_tv;
    /**
     * 商品
     */
    @ViewInject(R.id.foot_print_goods_tv)
    private TextView foot_print_goods_tv;
    @ViewInject(R.id.foot_print_mell_tv)
    private TextView foot_print_mell_tv;
    /**
     * 商家
     */
    @ViewInject(R.id.foot_print_goods_view)
    private View foot_print_goods_view;
    @ViewInject(R.id.foot_print_mell_view)
    private View foot_print_mell_view;

    private FragmentChangeManager fcm;

    private ArrayList<Fragment> mFragment;

    private int selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.foot_print_title_layout);
        fcm = new FragmentChangeManager(this.getSupportFragmentManager(), R.id.foot_print_layout, mFragment);
        setTvAndViewStyle(0);
    }

    @Override
    @OnClick({R.id.title_left_layout, R.id.title_right_layout, R.id.foot_right_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_left_layout:// 商品
                setTvAndViewStyle(0);
                break;
            case R.id.title_right_layout:// 商家
                setTvAndViewStyle(1);
                break;
            case R.id.foot_right_tv:// 商家
                Fragment f = mFragment.get(selected);
                String status = foot_right_tv.getText().toString();
                if (0 == selected) {// 商品
                    ((CollectGoodsHzjFgt) f).setStatus(status);
                } else if (1 == selected) {// 商家
                    ((CollectMellHzjFgt) f).setStatus(status);
                } else {// 书院
                    ((CollectBooksFgt) f).setStatus(status);
                }
                if(status.equals("编辑")){
                    foot_right_tv.setText("完成");
                } else{
                    foot_right_tv.setText("编辑");
                }
                break;
        }
    }

    private void setTvAndViewStyle(int position) {
        selected = position;
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
        return R.layout.aty_footprint_hzj;
    }

    @Override
    protected void initialized() {
        mFragment = new ArrayList<>();
        mFragment.add(CollectGoodsHzjFgt.newInstance("编辑"));
        mFragment.add(CollectMellHzjFgt.newInstance("编辑"));
    }

    @Override
    protected void requestData() {

    }
}
