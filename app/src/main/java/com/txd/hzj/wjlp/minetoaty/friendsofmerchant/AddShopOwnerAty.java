package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/21 17:05
 * 功能描述：添加店主
 */
public class AddShopOwnerAty extends BaseAty{
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.searchEdit)
    private EditText searchEdit;

    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;


    private String mSta_mid;
    private ArrayList<Map<String, String>> mCateData;
    private ScreeningResultAty.ScreeningResultAdapter mScreeningResultAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_shop_owner;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("添加店主");
        mSta_mid = getIntent().getStringExtra("sta_mid");
        searchEdit.setOnKeyListener(mOnKeyListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mScreeningResultAdapter = new ScreeningResultAty.ScreeningResultAdapter();
        mRecyclerView.setAdapter(mScreeningResultAdapter);
    }

    @Override
    protected void requestData() {
        bfriend(mSta_mid,this);
    }

    void bfriend( String sta_mid, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        apiTool2.postApi(Config.BASE_URL + "OsManager/bfriend", params, baseView);
    }

    void get_bfriend( String sta_mid,String phone,String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("type", type);
        apiTool2.postApi(Config.BASE_URL + "OsManager/get_bfriend", params, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.equals(Config.BASE_URL + "OsManager/bfriend")){
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            final ArrayList<Map<String, String>> list = JSONUtils.parseKeyAndValueToMapList(data.get("list"));
            mCateData = JSONUtils.parseKeyAndValueToMapList(data.get("cate_data"));
            mScreeningResultAdapter.setItemList(list);
            mScreeningResultAdapter.setOnItemViewClickLisener(new ScreeningResultAty.ScreeningResultAdapter.onItemViewClickLisener() {
                @Override
                public void onViewClick(int positon) {
                    Map<String, String> map = list.get(positon);
                    Bundle bundle = new Bundle();
                    bundle.putString("sta_mid",mSta_mid);
                    bundle.putSerializable("map", (Serializable) map);
                    startActivity(BusinessFriendDataAty.class,bundle);
                }
            });
            return;
        }

        if (requestUrl.equals(Config.BASE_URL + "OsManager/get_bfriend")){
            final ArrayList<Map<String, String>> list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            mScreeningResultAdapter.setItemList(list);
            mScreeningResultAdapter.setOnItemViewClickLisener(new ScreeningResultAty.ScreeningResultAdapter.onItemViewClickLisener() {
                @Override
                public void onViewClick(int positon) {
                    Map<String, String> map = list.get(positon);
                    Bundle bundle = new Bundle();
                    bundle.putString("sta_mid",mSta_mid);
                    bundle.putSerializable("map", (Serializable) map);
                    startActivity(BusinessFriendDataAty.class,bundle);
                }
            });
            return;
        }

    }

    View.OnKeyListener mOnKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                /*隐藏软键盘*/
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(AddShopOwnerAty.this.getCurrentFocus().getWindowToken(), 0);
                }
                get_bfriend(mSta_mid,searchEdit.getText().toString(),"0",AddShopOwnerAty.this);
                return true;
            }
            return false;
        }
    };

    @Override
    @OnClick({R.id.chooseLayout,R.id.searchTv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.chooseLayout:
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid",mSta_mid);
                bundle.putSerializable("cate_data", mCateData);
                startActivity(ScreeningConditionsAty.class,bundle);
                break;
            case R.id.searchTv:
                /*隐藏软键盘*/
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(AddShopOwnerAty.this.getCurrentFocus().getWindowToken(), 0);
                }
                get_bfriend(mSta_mid,searchEdit.getText().toString(),"0",AddShopOwnerAty.this);
                break;
        }
    }
}
