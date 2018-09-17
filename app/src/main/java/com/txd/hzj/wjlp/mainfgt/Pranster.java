package com.txd.hzj.wjlp.mainfgt;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.tools.ObserTool;
import com.txd.hzj.wjlp.bean.offline.OffLineBean;
import com.txd.hzj.wjlp.bean.offline.OffLineDataBean;
import com.txd.hzj.wjlp.http.OfflineStore;
import com.txd.hzj.wjlp.mainfgt.adapter.MellNearByHzjAdapter;

import java.util.Map;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/7/23 14:25
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class Pranster implements Constant.Pranster, BaseView {
    private Constant.View constantView;
    private Context context;
    private ListView listView;
    private int currentPage;

    @Override
    public void setView(Constant.View view) {
        this.constantView = view;
    }

    private MellNearByHzjAdapter mellNearByHzjAdapter;

    /**
     * 获取线下店铺信息数据
     *
     * @param p
     * @param lng
     * @param lat
     * @param merchant_id
     * @param context
     * @param listView
     */
    public void requestStoreData(int p, String lng, String lat, String merchant_id, String top_cate, String little_cate, Context context, ListView listView) {
        currentPage = p;
        OfflineStore.offlineStoreList(lng, lat, p, merchant_id, top_cate, little_cate, this);
        this.context = context;
        this.listView = listView;
        if (null == mellNearByHzjAdapter) {
            mellNearByHzjAdapter = new MellNearByHzjAdapter(context);
            listView.setAdapter(mellNearByHzjAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    OffLineDataBean offLineDataBea = mellNearByHzjAdapter.getItem(position);
                    constantView.onItemClickListener(offLineDataBea, position);
                }
            });
        }
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showDialog(String text) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void removeDialog() {

    }

    @Override
    public void removeContent() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onLoading(long total, long current, boolean isUploading) {

    }

    @Override
    public void onException(Exception exception) {
        constantView.loadComplate();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        if (requestUrl.contains("offlineStoreList")) {

            ObserTool.gainInstance().jsonToBean(jsonStr, OffLineBean.class, new ObserTool.BeanListener() {
                @Override
                public void returnObj(Object t) {
                    OffLineBean offLineBean = (OffLineBean) t;
                    if ("1".equals(offLineBean.getCode())) {
                        if (currentPage == 1) {
                            mellNearByHzjAdapter.getList().clear();
                        }
                        if (null != offLineBean.getData() && offLineBean.getData().size() > 0) {
                            mellNearByHzjAdapter.getList().addAll(offLineBean.getData());
                            mellNearByHzjAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });

            constantView.loadComplate();

        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {

    }

    @Override
    public void onErrorTip(String tips) {

    }
}
