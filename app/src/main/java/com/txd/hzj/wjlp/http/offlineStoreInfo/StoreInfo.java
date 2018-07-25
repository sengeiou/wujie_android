package com.txd.hzj.wjlp.http.offlineStoreInfo;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 创建者：Qyl
 * 创建时间：2018/7/24 0024 9:21
 * 功能描述：
 * 联系方式：无
 */
 class StoreInfo {
    private String url = Config.BASE_URL + "OfflineStore/";

    /**
     * 线下店铺详情页
     * */

    void offlineStoreInfo(String id, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("merchant_id",id);
        apiTool2.postApi(url+"offlineStoreInfo",params,baseView);

    }
}
