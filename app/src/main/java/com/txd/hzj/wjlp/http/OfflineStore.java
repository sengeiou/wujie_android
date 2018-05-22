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
        apiTool2.postApi(url + "Index", new RequestParams(), baseView);
    }
}
