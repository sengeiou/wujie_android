package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/9 13:34
 * 功能描述：
 */
public class CoinPay {
    private static String url = Config.BASE_URL + "Pay/";

    /**
     * @param order_id      订单id
     * @param order_type    订单类型
     * @param baseView
     */
    public static void coinPay(String order_id, String order_type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("order_type", order_type);
        apiTool2.postApi(url + "coinPay", params, baseView);

    }
}
