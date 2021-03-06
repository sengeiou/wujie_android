package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.util.List;



public class HouseOrder {
    private static String url = "HouseOrder/";

    /**
     * 余额支付
     *
     * @param order_id
     * @param discount_type 使用代金券： 0不使用代金券 1使用红券 2使用黄券 3使用蓝券
     * @param baseView
     */
    public static void balancePay(String order_id, String discount_type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("discount_type", discount_type);
        apiTool2.postApi(Config.BASE_URL + url + "balancePay", params, baseView);
    }

    /**
     * 积分
     *
     * @param order_id
     * @param baseView
     */
    public static void integralPay(String order_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        apiTool2.postApi(Config.BASE_URL + url + "integralPay", params, baseView);
    }

    /**
     * 订单列表
     *
     * @param type
     * @param p
     * @param baseView
     */
    public static void orderList(String type, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("type", type);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(Config.BASE_URL + url + "orderList", params, baseView);

    }

    /**
     * 订单详情
     *
     * @param order_id
     * @param baseView
     */
    public static void orderInfo(String order_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        apiTool2.postApi(Config.BASE_URL + url + "orderInfo", params, baseView);
    }

    /**
     * 评价界面
     *
     * @param order_id
     * @param baseView
     */
    public static void commentPage(String order_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        apiTool2.postApi(Config.BASE_URL + url + "commentPage", params, baseView);
    }

    /**
     * 新增评价
     *
     * @param order_id
     * @param price
     * @param lot
     * @param supporting
     * @param traffic
     * @param environment
     * @param label_str
     * @param list        [pictures]
     * @param content
     * @param baseView
     */
    public static void addComment(String order_id, String price, String lot, String supporting,
                                  String traffic, String environment, String label_str, List<File> list, String content, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("price", price);
        params.addBodyParameter("lot", lot);
        params.addBodyParameter("supporting", supporting);
        params.addBodyParameter("traffic", traffic);
        params.addBodyParameter("environment", environment);
        params.addBodyParameter("label_str", label_str);
        for (int i = 0; i < list.size(); i++) {
            String body = "pictures[" + i + "]";
            params.addBodyParameter(body, list.get(i));
        }
        params.addBodyParameter("content", content);
        apiTool2.postApi(Config.BASE_URL + url + "addComment", params, baseView);
    }
}
