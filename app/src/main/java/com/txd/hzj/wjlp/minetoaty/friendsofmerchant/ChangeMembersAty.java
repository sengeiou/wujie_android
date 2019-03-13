package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/17 15:58
 * 功能描述：会员互换
 */
public class ChangeMembersAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.myMemberTv)
    private TextView myMemberTv;

    @ViewInject(R.id.changeMemberTv)
    private TextView changeMemberTv;

    @ViewInject(R.id.changeMsgTv)
    private TextView changeMsgTv;

    @ViewInject(R.id.pointTv)
    private TextView pointTv;

    private MyMemberFgt mMemberFgt;
    private ChangeMemberFgt mChangeMemberFgt;
    private ChangeMsgFgt mChangeMsgFgt;

    private String[] tags= {"MyMemberFgt","ChangeMemberFgt","ChangeMsgFgt"};

    private Bundle mSavedInstanceState;
    private String mSta_mid;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_change_members;
    }

    @Override
    protected void initialized() {
        mContext = this;
        mSta_mid = getIntent().getStringExtra("sta_mid");
    }

    public String getSta_mid() {
        return mSta_mid;
    }

    @Override
    protected void requestData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        app_member_exchange(mSta_mid,this);
    }

    void app_member_exchange(String sta_mid, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_member_exchange", params, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (Double.parseDouble(map.get("num"))>0){
           pointTv.setVisibility(View.VISIBLE);
        }else {
            pointTv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
        changeStatus(0);
    }

    private void switchFragment(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mSavedInstanceState == null){
            if (mMemberFgt == null){
                mMemberFgt = new MyMemberFgt();
                fragmentTransaction.add(R.id.contentLayout,mMemberFgt,tags[0]);
            }
            if (mChangeMemberFgt == null){
                mChangeMemberFgt = new ChangeMemberFgt();
                fragmentTransaction.add(R.id.contentLayout,mChangeMemberFgt,tags[1]);
            }

            if (mChangeMsgFgt == null){
                mChangeMsgFgt = new ChangeMsgFgt();
                fragmentTransaction.add(R.id.contentLayout,mChangeMsgFgt,tags[2]);
            }
            if (0== position) {
                fragmentTransaction.hide(mChangeMemberFgt);
                fragmentTransaction.hide(mChangeMsgFgt);
                fragmentTransaction.show(mMemberFgt);
            }else if (1 == position){
                fragmentTransaction.hide(mMemberFgt);
                fragmentTransaction.hide(mChangeMsgFgt);
                fragmentTransaction.show(mChangeMemberFgt);
            }else if (2 == position){
                fragmentTransaction.hide(mMemberFgt);
                fragmentTransaction.hide(mChangeMemberFgt);
                fragmentTransaction.show(mChangeMsgFgt);
            }
            fragmentTransaction.commit();
        }else {
            mMemberFgt = (MyMemberFgt) getSupportFragmentManager().findFragmentByTag(tags[0]);
            mChangeMemberFgt = (ChangeMemberFgt) getSupportFragmentManager().findFragmentByTag(tags[1]);
            mChangeMsgFgt = (ChangeMsgFgt) getSupportFragmentManager().findFragmentByTag(tags[2]);
            if (0== position) {
                fragmentTransaction.hide(mChangeMemberFgt);
                fragmentTransaction.hide(mChangeMsgFgt);
                fragmentTransaction.show(mMemberFgt);
            }else if (1 == position){
                fragmentTransaction.hide(mMemberFgt);
                fragmentTransaction.hide(mChangeMsgFgt);
                fragmentTransaction.show(mChangeMemberFgt);
            }else if (2 == position){
                fragmentTransaction.hide(mMemberFgt);
                fragmentTransaction.hide(mChangeMemberFgt);
                fragmentTransaction.show(mChangeMsgFgt);
            }
            fragmentTransaction.commit();
        }
    }

    @Override
    @OnClick({R.id.myMemberTv,R.id.changeMemberTv,R.id.changeMsgTv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.myMemberTv:
                changeStatus(0);
                break;
            case R.id.changeMemberTv:
                changeStatus(1);
                break;
            case R.id.changeMsgTv:
                changeStatus(2);
                break;
        }
    }

    private void changeStatus(int i) {
        myMemberTv.setBackgroundResource(R.drawable.shape_red_title_left_unselect);
        changeMemberTv.setBackgroundResource(R.drawable.shape_red_title_middle_unselect);
        changeMsgTv.setBackgroundResource(R.drawable.shape_red_title_right_unselect);
        if (i==0){
            myMemberTv.setBackgroundResource(R.drawable.shape_red_title_left_select);
            myMemberTv.setTextColor(Color.WHITE);
            changeMemberTv.setTextColor(Color.parseColor("#ffd21f18"));
            changeMsgTv.setTextColor(Color.parseColor("#ffd21f18"));
        }else if (i==1){
            changeMemberTv.setBackgroundResource(R.drawable.shape_red_title_middle_select);
            changeMemberTv.setTextColor(Color.WHITE);
            myMemberTv.setTextColor(Color.parseColor("#ffd21f18"));
            changeMsgTv.setTextColor(Color.parseColor("#ffd21f18"));
        }else if (i==2){
            changeMsgTv.setBackgroundResource(R.drawable.shape_red_title_right_select);
            changeMsgTv.setTextColor(Color.WHITE);
            myMemberTv.setTextColor(Color.parseColor("#ffd21f18"));
            changeMemberTv.setTextColor(Color.parseColor("#ffd21f18"));
        }
        switchFragment(i);
    }
}
