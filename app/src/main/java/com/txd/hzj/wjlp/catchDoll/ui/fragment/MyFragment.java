package com.txd.hzj.wjlp.catchDoll.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.base.BaseFgt;
import com.txd.hzj.wjlp.catchDoll.ui.activity.AttentionActivity;
import com.txd.hzj.wjlp.catchDoll.ui.activity.FriendBoostActivity;
import com.txd.hzj.wjlp.catchDoll.ui.activity.GameRecordingActivity;
import com.txd.hzj.wjlp.catchDoll.ui.activity.MoneyActivity;
import com.txd.hzj.wjlp.catchDoll.ui.activity.MyDollActivity;
import com.txd.hzj.wjlp.catchDoll.ui.activity.RedemptionRecordActivity;
import com.txd.hzj.wjlp.catchDoll.util.SharedPreferencesUtils;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：我的
 */
public class MyFragment extends BaseFgt implements CompoundButton.OnCheckedChangeListener {

    @ViewInject(R.id.my_header_imgv)
    public ImageView my_header_imgv;
    @ViewInject(R.id.my_name_tv)
    public TextView my_name_tv;
    @ViewInject(R.id.my_myMoney_llayout)
    public LinearLayout my_myMoney_llayout;
    @ViewInject(R.id.my_myMoney_tv)
    public TextView my_myMoney_tv;
    @ViewInject(R.id.my_myDoll_llayout)
    public LinearLayout my_myDoll_llayout;
    @ViewInject(R.id.my_myDoll_tv)
    public TextView my_myDoll_tv;
    @ViewInject(R.id.my_myCollection_rlayout)
    public RelativeLayout my_myCollection_rlayout;
    @ViewInject(R.id.my_myAddress_rlayout)
    public RelativeLayout my_myAddress_rlayout;
    @ViewInject(R.id.my_gameRecord_rlayout)
    public RelativeLayout my_gameRecord_rlayout;
    @ViewInject(R.id.my_exchangeRecord_rlayout)
    public RelativeLayout my_exchangeRecord_rlayout;
    @ViewInject(R.id.my_friendBoost_rlayout)
    public RelativeLayout my_friendBoost_rlayout;
    @ViewInject(R.id.my_bgMusic_rlayout)
    public RelativeLayout my_bgMusic_rlayout;
    @ViewInject(R.id.my_bgMusic_swich)
    public Switch my_bgMusic_swich;
    @ViewInject(R.id.my_operatingSound_rlayout)
    public RelativeLayout my_operatingSound_rlayout;
    @ViewInject(R.id.my_operatingSound_swich)
    public Switch my_operatingSound_swich;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {
        GlideUtils.urlCirclePicNoBg("http://s1.sinaimg.cn/bmiddle/970b3da2xb221409472d0&690", 60, 60, my_header_imgv);
        my_name_tv.setText("null");
        my_myMoney_tv.setText("200");
        my_myDoll_tv.setText("8");
        my_bgMusic_swich.setChecked(SharedPreferencesUtils.getBoolean(getActivity(), "bgMusic"));
        my_operatingSound_swich.setChecked(SharedPreferencesUtils.getBoolean(getActivity(), "soundEffect"));
        my_bgMusic_swich.setOnCheckedChangeListener(this);
        my_operatingSound_swich.setOnCheckedChangeListener(this);
    }

    @Override
    protected void immersionInit() {
    }

    @OnClick({R.id.my_myMoney_llayout, R.id.my_myDoll_llayout, R.id.my_myCollection_rlayout, R.id.my_myAddress_rlayout, R.id.my_gameRecord_rlayout, R.id.my_exchangeRecord_rlayout,
            R.id.my_friendBoost_rlayout, R.id.my_bgMusic_rlayout, R.id.my_operatingSound_rlayout})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.my_myMoney_llayout: // 我的银两
                startActivity(MoneyActivity.class, null);
                break;
            case R.id.my_myDoll_llayout: // 我的娃娃
                startActivity(MyDollActivity.class, null);
                break;
            case R.id.my_myCollection_rlayout:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 2); // 收藏
                startActivity(AttentionActivity.class, bundle);
                break;
            case R.id.my_myAddress_rlayout:
                showToast("收货地址");
                break;
            case R.id.my_gameRecord_rlayout:
                startActivity(GameRecordingActivity.class, null);
                break;
            case R.id.my_exchangeRecord_rlayout:
                startActivity(RedemptionRecordActivity.class, null);
                break;
            case R.id.my_friendBoost_rlayout:
                startActivity(FriendBoostActivity.class, null);
                break;
            case R.id.my_bgMusic_rlayout:
                my_bgMusic_swich.setChecked(!my_bgMusic_swich.isChecked());
                break;
            case R.id.my_operatingSound_rlayout:
                my_operatingSound_swich.setChecked(!my_operatingSound_swich.isChecked());
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.my_bgMusic_swich:
                SharedPreferencesUtils.putBoolean(getActivity(), "bgMusic", isChecked);
                break;
            case R.id.my_operatingSound_swich:
                SharedPreferencesUtils.putBoolean(getActivity(), "soundEffect", isChecked);
                break;
        }
    }
}
