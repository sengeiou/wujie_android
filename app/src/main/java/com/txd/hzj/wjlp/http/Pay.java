package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

public class Pay {
    private static String url = Config.BASE_URL + "Pay/";

    /**
     * 获取支付宝支付参数
     *
     * @param order_id
     * @param discount_type  使用代金券：discount_type 0不使用代金券 1使用红券 2使用黄券 3使用蓝券(多个用','隔开)
     * @param type  	类型：type 1.充值,2汽车购订单，3房产购 4 订单支付(限量购) 5 预购 6拼单购 7限量购(作废) 8竞拍汇 9 线下店铺 10无界驿店
     * @return pay_string;
     */
    public static void getAlipayParam(String order_id, String discount_type, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("discount_type", discount_type);
        params.addBodyParameter("type", type);
        apiTool2.postApi(url + "getAlipayParam", params, baseView);
    }


    /**
     * 查询订单
     *
     * @param order_id  	订单ID
     * @param type  类型：type 1.充值,2汽车购订单，3房产购 4 订单支付(限量购) 5 预购 6拼单购 7限量购(作废) 8竞拍汇 9线下店铺 10积分商店
     * @param baseView
     */
    public static void findPayResult(String order_id, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("type", type);
        apiTool2.postApi(url + "findPayResult", params, baseView);
    }

    /**
     * 微信支付
     * @param order_id 订单id
     * @param discount_type  	使用代金券：discount_type 0不使用代金券 1使用红券 2使用黄券 3使用蓝券(多个用','隔开)
     * @param type  类型：type 2汽车购订单，3房产购 4 订单支付(限量购) 5 预购 6拼单购 8竞拍汇
     * @param baseView
     */
    public static void getJsTine(String order_id, String discount_type, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("discount_type", discount_type);
        params.addBodyParameter("type", type);
        apiTool2.postApi(url + "getJsTine", params, baseView);
    }


    public static void getWeChat(String divination_id, String divination_type, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("divination_id", divination_id);
        params.addBodyParameter("divination_type", divination_type);
        params.addBodyParameter("type", type);
        apiTool2.postApi(url + "getJsTine", params, baseView);
    }

    public static void getAlipay(String divination_id, String divination_type, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("divination_id", divination_id);
        params.addBodyParameter("divination_type", divination_type);
        params.addBodyParameter("type", type);
        apiTool2.postApi(url + "getAlipayParam", params, baseView);
    }

    /**
     * 微信充值
     * @param order_id  	订单ID再次支付时传
     * @param totalPrice  充值金额
     */
    public static void getHjsp(String order_id, String totalPrice, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("totalPrice", totalPrice);
        params.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "getHjsp", params, baseView);

    }

}
