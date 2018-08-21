package com.txd.hzj.wjlp.minetoAty.collect.fgt;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.offline.OffLineDataBean;
import com.txd.hzj.wjlp.bean.offline.OffLineListBean;
import com.txd.hzj.wjlp.http.collect.UserCollectPst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.mellOffLine.ShopMallDetailsAty;
import com.txd.hzj.wjlp.mellOffLine.adapter.MellOffLineListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/21 16:29
 * 功能描述：线下店铺收藏
 */
public class CollectOffLineshopFgt extends BaseFgt {

    private boolean status;
    /**
     * 数据类型
     * 0.足迹
     * 1.收藏
     */
    private int dataType=0;

    @ViewInject(R.id.mell_lv)
    private ListView mell_lv;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;


    private List<OffLineListBean.DataBean> mells;

    private MellOffLineListAdapter mlAdapter;

    private UserPst userPst;
    private UserCollectPst collectPst;
    private int p=1;
    private int allNum;


    public static CollectOffLineshopFgt newInstance(boolean param1, int dataType) {
        CollectOffLineshopFgt fragment = new CollectOffLineshopFgt();
        fragment.status = param1;
        fragment.dataType = dataType;
        return fragment;
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_collect_offlineshop;
    }

    @Override
    protected void initialized() {
        mells=new ArrayList<>();
        userPst = new UserPst(this);
        collectPst = new UserCollectPst(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (0 == dataType) {
            userPst.myfooter(p, "5");
        } else {
            collectPst.collectList(p, "5");
        }
    }

    public void r() {
        if (0 == dataType) {
            userPst.myfooter(p, "5");
        } else {
            collectPst.collectList(p, "5");
        }
    }

    public void setStatus(boolean status) {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("collectList")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if (map.containsKey("code") && "1".equals(map.get("code"))){
                if (map.containsKey("data")){
                    ArrayList<Map<String, String>> dataList = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    if (dataList.size()>0){
                        mell_lv.setVisibility(View.VISIBLE);
                        no_data_layout.setVisibility(View.GONE);
                        if (p==1){
                            mells.clear();
                        }
                        for (int i = 0; i < dataList.size(); i++) {
                            OffLineListBean.DataBean dataBean=new OffLineListBean.DataBean();
                            Map<String, String> dataMap = dataList.get(i);
                            if (dataMap.containsKey("s_id")){
                                dataBean.setMerchant_id(dataMap.get("s_id"));
                            }
                            if (dataMap.containsKey("logo")){
                                dataBean.setLogo(dataMap.get("logo"));
                            }
                            if (dataMap.containsKey("merchant_desc")){
                                dataBean.setMerchant_desc(dataMap.get("merchant_desc"));
                            }
                            if (dataMap.containsKey("merchant_name")){
                                dataBean.setMerchant_name(dataMap.get("merchant_name"));
                            }
                            if (dataMap.containsKey("months_order")){
                                dataBean.setMonths_order(dataMap.get("months_order"));
                            }
                            if (dataMap.containsKey("score")){
                                dataBean.setScore(dataMap.get("score"));
                            }
                            mells.add(dataBean);
                        }
                        mlAdapter = new MellOffLineListAdapter(getActivity(), mells);
                        mlAdapter.setOnItemClickListener(new MellOffLineListAdapter.OnItemClickListener() {
                            @Override
                            public void itemClick(int position) {
                                Intent intent = new Intent(getActivity(), ShopMallDetailsAty.class);
                                Bundle options = new Bundle();
                                OffLineListBean.DataBean dataBean = mells.get(position);
                                OffLineDataBean offLineDataBea=new OffLineDataBean();
                                offLineDataBea.setS_id(dataBean.getMerchant_id());
                                offLineDataBea.setMerchant_name(dataBean.getMerchant_name());
                                offLineDataBea.setMerchant_desc(dataBean.getMerchant_desc());
                                offLineDataBea.setLogo(dataBean.getLogo());
                                offLineDataBea.setScore(dataBean.getScore());
                                offLineDataBea.setMonths_order(dataBean.getMonths_order());
                                offLineDataBea.setDistance("");
                                Map<String, String> locInfo = DemoApplication.getInstance().getLocInfo();
                                offLineDataBea.setLat(locInfo.containsKey("lat")?locInfo.get("lat"):"");
                                offLineDataBea.setLng(locInfo.containsKey("lon")?locInfo.get("lon"):"");
                                offLineDataBea.setProportion("");
                                offLineDataBea.setShow(false);
                                offLineDataBea.setTicket(null);
                                offLineDataBea.setUser_id("");
                                options.putSerializable("mellInfo", offLineDataBea);
                                intent.putExtras(options);
                                startActivity(intent);
                            }
                        });
                        mell_lv.setAdapter(mlAdapter);

                    }else {
                        mell_lv.setVisibility(View.GONE);
                        no_data_layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }


}
