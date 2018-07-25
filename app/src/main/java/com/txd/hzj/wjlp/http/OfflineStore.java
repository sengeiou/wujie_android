package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 线下店铺模块
 */
public class OfflineStore {
    public static String url = Config.BASE_URL + "OfflineStore/";

    public static void Index(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "Index",new RequestParams() , baseView);
    }

    /**
     *
     * @param lng  经度
     * @param lat  纬度
     * @param p   列表页面p递增 详情页面没有下拉刷新 只是p=1
     * @param merchant_id  店铺ID （s_id是店铺ID） 如果是店铺详情页面需要传 防止店铺详情页面的附近商家有当前点击的店铺
     */
    public  static void  offlineStoreList(String lng,String lat,int p,String  merchant_id,BaseView baseView){
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("lng", lng);
        requestParams.addBodyParameter("lat", lat);
        requestParams.addBodyParameter("p", String.valueOf(p));
        requestParams.addBodyParameter("merchant_id",merchant_id);
        apiTool2.postApi(url + "offlineStoreList",requestParams , baseView);
    }
}
