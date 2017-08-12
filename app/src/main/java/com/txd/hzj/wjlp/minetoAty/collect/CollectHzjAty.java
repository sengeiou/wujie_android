package com.txd.hzj.wjlp.minetoAty.collect;

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
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：上午 9:12
 * 描述：我都收藏
 * ===============Txunda===============
 */
public class CollectHzjAty extends BaseAty {
    /**
     * 中间标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 编辑，完成
     */
    @ViewInject(R.id.foot_right_tv)
    public TextView titlt_right_tv;

    /**
     * 商品
     */
    @ViewInject(R.id.left_goods_tv)
    private TextView left_goods_tv;
    @ViewInject(R.id.left_goods_view)
    private View left_goods_view;
    /**
     * 商家
     */
    @ViewInject(R.id.middle_mell_tv)
    private TextView middle_mell_tv;
    @ViewInject(R.id.middle_mell_view)
    private View middle_mell_view;
    /**
     * 书院
     */
    @ViewInject(R.id.right_books_tv)
    private TextView right_books_tv;
    @ViewInject(R.id.right_books_view)
    private View right_books_view;

    private int selected = 0;

    private ArrayList<Fragment> mFragment;

    private FragmentChangeManager fcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.collect_title_layout);

        fcm = new FragmentChangeManager(this.getSupportFragmentManager(), R.id.collect_frame_layout, mFragment);

        setTvAndViewStyle(0);
    }

    private void setTvAndViewStyle(int position) {
        selected = position;
        left_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        middle_mell_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        right_books_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        left_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        middle_mell_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        right_books_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        if (0 == position) {
            left_goods_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            left_goods_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else if ((1 == position)) {
            middle_mell_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            middle_mell_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            right_books_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            right_books_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
        if (position < 3)
            fcm.setFragments(position);
    }

    @Override
    @OnClick({R.id.foot_right_tv, R.id.collect_left_layout, R.id.collect_middle_layout, R.id.collect_right_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.foot_right_tv:// 编辑，完成
                Fragment f = mFragment.get(selected);
                String status = titlt_right_tv.getText().toString();
                if (0 == selected) {// 商品
                    ((CollectGoodsHzjFgt) f).setStatus(status);
                } else if (1 == selected) {// 商家
                    ((CollectMellHzjFgt) f).setStatus(status);
                } else {// 书院
                    ((CollectBooksFgt) f).setStatus(status);
                }
                if (status.equals("编辑")) {
                    titlt_right_tv.setText("完成");
                } else {
                    titlt_right_tv.setText("编辑");
                }
                break;
            case R.id.collect_left_layout:// 商品
                setTvAndViewStyle(0);
                break;
            case R.id.collect_middle_layout:// 商家
                setTvAndViewStyle(1);
                break;
            case R.id.collect_right_layout:// 书院
                setTvAndViewStyle(2);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_collect_hzj;
    }

    @Override
    protected void initialized() {
        mFragment = new ArrayList<>();
        mFragment.add(CollectGoodsHzjFgt.newInstance("编辑"));
        mFragment.add(CollectMellHzjFgt.newInstance("编辑"));
        mFragment.add(CollectBooksFgt.newInstance("编辑"));
    }

    @Override
    protected void requestData() {

    }
}
