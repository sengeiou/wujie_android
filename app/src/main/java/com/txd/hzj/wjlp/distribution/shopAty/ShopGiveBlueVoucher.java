package com.txd.hzj.wjlp.distribution.shopAty;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.util.PreferencesUtils;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.adapter.ShopOrderManageAdapter;
import com.txd.hzj.wjlp.distribution.bean.ShopOrderBean;
import com.txd.hzj.wjlp.distribution.presenter.ShopExhibitPst;

import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/9/11 15:34
 * 功能描述：
 */
public  class ShopGiveBlueVoucher extends BaseAty{

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titleName;
    private Context mContext;
    private ShopExhibitPst mExhibitPst;
    private String mShop_id;
    
    @ViewInject(R.id.empty_layout)
    private FrameLayout empty_layout;

    //订单列表
    @ViewInject(R.id.shop_order_re_list)
    private RecyclerView shop_order_re_list;
    //刷新
    @ViewInject(R.id.shop_order_refresh)
    private SuperSwipeRefreshLayout refreshLayout;
    private ShopOrderManageAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_give_blue_aty;
    }

    @Override
    protected void initialized() {
        mContext=this;
        titleName.setText("送代金券");
        mExhibitPst = new ShopExhibitPst(this);
        if (PreferencesUtils.containKey(mContext,"shop_id")){
            mShop_id = PreferencesUtils.getString(mContext, "shop_id");
        }
    }

    @Override
    protected void requestData() {
        if (null != mExhibitPst && !TextUtils.isEmpty(mShop_id)) {
            mExhibitPst.shopOrderList(mShop_id, "4", "");
        }
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("orders")){
            ShopOrderBean shopOrderBean = JSONObject.parseObject(jsonStr, ShopOrderBean.class);
            if (200==shopOrderBean.getCode()){
                List<ShopOrderBean.DataBean> data = shopOrderBean.getData();
                if (null != data && data.size()>0){
                    empty_layout.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.VISIBLE);
                    shop_order_re_list.setLayoutManager(new LinearLayoutManager(mContext));
                    adapter = new ShopOrderManageAdapter(data, mContext, "");
                    shop_order_re_list.setAdapter(adapter);
                }else {
                    empty_layout.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                }

            }
        }
        refreshComplete();
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        empty_layout.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.GONE);
        removeDialog();
        refreshComplete();
    }

    private void refreshComplete(){
//        if (progressBar.getVisibility()==View.VISIBLE){
//            refreshLayout.setRefreshing(false);
//            progressBar.setVisibility(View.GONE);
//        }
        //        if (footerProgressBar.getVisibility()==View.VISIBLE){
        //            refreshLayout.setLoadMore(false);
        //            progressBar.setVisibility(View.GONE);
        //        }

    }
}
