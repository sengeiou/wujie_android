package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.io.Serializable;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/22 8:43
 * 功能描述：商友资料
 */
public class BusinessFriendDataAty extends BaseAty {

    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.headImg)
    private ShapedImageView headImg;


    @ViewInject(R.id.sexTv)
    private TextView sexTv;


    @ViewInject(R.id.idTv)
    private TextView idTv;

    @ViewInject(R.id.nameTv)
    private TextView nameTv;

    @ViewInject(R.id.addressTv)
    private TextView addressTv;

    @ViewInject(R.id.shopIdTv)
    private TextView shopIdTv;


    private Map<String, String> map;
    private String mSta_mid;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_business_friend_data;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("商友资料");
        map = (Map<String, String>) getIntent().getSerializableExtra("map");
        mSta_mid = getIntent().getStringExtra("sta_mid");

        Glide.with(mContext).load(map.get("head_pic")).into(headImg);
        sexTv.setText(map.get("sex"));
        idTv.setText(map.get("id"));
        nameTv.setText(map.get("nickname"));
        addressTv.setText(map.get("area"));
        shopIdTv.setText(map.get("m_name"));
    }

    @Override
    protected void requestData() {

    }

    @Override
    @OnClick({R.id.addFriendTv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.addFriendTv:
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid",mSta_mid);
                bundle.putSerializable("map", (Serializable) map);
                startActivity(AddFriendAty.class,bundle);
                finish();
                break;
        }
    }
}
