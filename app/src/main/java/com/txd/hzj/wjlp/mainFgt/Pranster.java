package com.txd.hzj.wjlp.mainFgt;

import com.ants.theantsgo.base.BaseView;
import com.txd.hzj.wjlp.*;
import com.txd.hzj.wjlp.http.OfflineStore;

import java.util.Map;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/7/23 14:25
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class Pranster implements Constant.Pranster,BaseView{
    Constant.View view;

    @Override
    public void setView(Constant.View view) {
        this.view = view;
    }

    /**
     *  获取线下店铺信息数据
     * @param p
     */
    private void requestStoreData(int p,String lng,String lat,String merchant_id){
        OfflineStore.offlineStoreList(lng,lat,p,merchant_id,this);
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

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
            if(requestUrl.contains("offlineStoreList")){

            }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {

    }

    @Override
    public void onErrorTip(String tips) {

    }
}
