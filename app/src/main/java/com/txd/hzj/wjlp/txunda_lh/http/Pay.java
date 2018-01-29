package com.txd.hzj.wjlp.txunda_lh.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * by Txunda_LH on 2017/11/28.
 */

public class Pay {
    private static String url = "Pay/";

    /**
     * 获取支付宝支付参数
     *
     * @param order_id
     * @param discount_type
     * @param type
     * @param baseView
     * @return pay_string;
     */
    public static void getAlipayParam(String order_id, String discount_type, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("discount_type", discount_type);
        params.addBodyParameter("type", type);
        apiTool2.postApi(Config.BASE_URL + url + "getAlipayParam", params, baseView);
    }


    /**
     * 查询订单
     *
     * @param order_id
     * @param type
     * @param baseView
     */
    public static void findPayResult(String order_id, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("type", type);
        apiTool2.postApi(Config.BASE_URL + url + "findPayResult", params, baseView);
    }

    /**
     * @param order_id
     * @param discount_type
     * @param type
     * @param baseView
     */
    public static void getJsTine(String order_id, String discount_type, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("discount_type", discount_type);
        params.addBodyParameter("type", type);
        apiTool2.postApi(Config.BASE_URL + url + "getJsTine", params, baseView);
    }

    public static void getHjsp(String totalPrice, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("totalPrice", totalPrice);
        apiTool2.postApi(Config.BASE_URL + url + "getHjsp", params, baseView);

    }

}
