package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/22 9:52
 * 功能描述：加好友
 */
public class AddFriendAty extends BaseAty {

    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.headImg)
    private ShapedImageView headImg;

    @ViewInject(R.id.nameTv)
    private TextView nameTv;

    @ViewInject(R.id.remarksEdit)
    private EditText remarksEdit;

    @ViewInject(R.id.classifyTv)
    private TextView classifyTv;

    @ViewInject(R.id.vInfoEdit)
    private EditText vInfoEdit;

    private Map<String, String> map;
    private String mSta_mid;
    private String mCate_id;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("加好友");
        map = (Map<String, String>) getIntent().getSerializableExtra("map");
        mSta_mid = getIntent().getStringExtra("sta_mid");

        Glide.with(mContext).load(map.get("head_pic")).into(headImg);
        nameTv.setText(map.get("nickname"));
    }

    @Override
    protected void requestData() {

    }

    void get_bfriend(String sta_mid, String cate_id, String vinfo, String nickname, String bid, String mid, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("cate_id", cate_id);
        params.addBodyParameter("vinfo", vinfo);
        params.addBodyParameter("nickname", nickname);
        params.addBodyParameter("bid", bid);
        params.addBodyParameter("mid", mid);
        params.addBodyParameter("type", type);
        apiTool2.postApi(Config.BASE_URL + "OsManager/get_bfriend", params, baseView);
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("get_bfriend")) {
            showToast(map.get("message"));
            if (map.get("code").equals("1")){
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid",mSta_mid);
                startActivity(NewFriendAty.class,bundle);
            }
        }
    }

    @Override
    @OnClick({R.id.classifyLayout, R.id.addFriendTv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.classifyLayout:
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid", mSta_mid);
                bundle.putBoolean("isBack",true);
                startActivity(GroupManagementAty.class, bundle);
                break;
            case R.id.addFriendTv:
                get_bfriend(mSta_mid, mCate_id, vInfoEdit.getText().toString(), remarksEdit.getText().toString(), map.get("id"), map.get("merchant_id"), "1", this);
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        String message = messageEvent.getMessage();
        if (messageEvent.getLabel().equals("GroupManagementAty")) {
            String[] split = message.split(";");
            classifyTv.setText(split[0]);
            mCate_id = split[1];
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
