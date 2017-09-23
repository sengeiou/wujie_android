package com.txd.hzj.wjlp.mellOnLine.gridClassify.hous;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ants.theantsgo.tools.AlertDialog;
import com.flyco.tablayout.utils.FragmentChangeManager;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.fgt.HouseCommentFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt.HousDetailsHousesChenFgt;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.fgt.HousDetailsTypeChenFgt;

import java.util.ArrayList;

/**
 * ===============Txunda===============
 * 作者：chen
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：房产购详情页(10-2房产购)
 * ===============Txunda===============
 */

public class HousDetailsChenAty extends BaseAty implements RadioGroup.OnCheckedChangeListener,
        HousDetailsHousesChenFgt.SkipToComment, HousDetailsHousesChenFgt.SetPhone {

    @ViewInject(R.id.tv_detail_num)//电话号码
    private TextView tv_detail_num;
    @ViewInject(R.id.rg_hous_detail)//RadioGroup
    private RadioGroup rg_hous_detail;

    private String phone;//电话号码

    private FragmentChangeManager fcm;

    private ArrayList<Fragment> fragments;


    @OnClick({R.id.tv_detail_phone})
    public void onClick(View v) {
        switch (v.getId()) {
            //打电话
            case R.id.tv_detail_phone:
                phone = tv_detail_num.getText().toString().trim();
                new AlertDialog(this).builder().setTitle("提示").setMsg(phone).setPositiveButton("拨打", new View
                        .OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * 沉浸式解决顶部标题重叠
         */
        showStatusBar(R.id.rl_hous_detail);

        rg_hous_detail.setOnCheckedChangeListener(this);
        fcm = new FragmentChangeManager(getSupportFragmentManager(), R.id.fl_hous_detail, fragments);
        ((RadioButton) findViewById(R.id.rb_detail_houses)).setChecked(true);//默认选中
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_hous_detail_chen;
    }

    @Override
    protected void initialized() {
        String house_id = getIntent().getStringExtra("house_id");

        fragments = new ArrayList<>();
        fragments.add(HousDetailsHousesChenFgt.getFgt(house_id));
        fragments.add(HousDetailsTypeChenFgt.getFgt(house_id));
        fragments.add(HouseCommentFgt.getFgt(house_id));

    }


    @Override
    protected void requestData() {

    }

    /**
     * RaidioGroup 监听
     *
     * @param radioGroup RadioGroup
     * @param i          id
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            //楼盘
            case R.id.rb_detail_houses:
                fcm.setFragments(0);
                break;
            //户型
            case R.id.rb_detail_type:
                fcm.setFragments(1);
                break;
            case R.id.rb_comment_type:
                fcm.setFragments(2);
                break;
        }

    }

    @Override
    public void beSkip(boolean skip) {
        if (skip) {
            ((RadioButton) findViewById(R.id.rb_comment_type)).setChecked(true);//默认选中
        }
    }

    @Override
    public void setPhone(String phone) {
        tv_detail_num.setText(phone);
    }
}
