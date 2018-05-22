package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.util.List;



public class CarOrder {
    private static String url = Config.BASE_URL + "CarOrder/";

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
        apiTool2.postApi(url + "balancePay", params, baseView);
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
        apiTool2.postApi(url + "integralPay", params, baseView);
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
        apiTool2.postApi(url + "orderList", params, baseView);

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
        apiTool2.postApi(url + "orderInfo", params, baseView);
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
        apiTool2.postApi(url + "commentPage", params, baseView);
    }


    /**
     * 新增评价
     *
     * @param order_id
     * @param exterior
     * @param space
     * @param controllability
     * @param consumption
     * @param label_str
     * @param list          pictures  [1]
     * @param content
     * @param baseView
     */
    public static void addComment(String order_id, String exterior, String space, String controllability,
                                  String consumption, String label_str, List<File> list, String content, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("exterior", exterior);
        params.addBodyParameter("space", space);
        params.addBodyParameter("controllability", controllability);
        params.addBodyParameter("consumption", consumption);
        params.addBodyParameter("label_str", label_str);
        for (int i = 0; i < list.size(); i++) {
            String body = "pictures[" + i + "]";
            params.addBodyParameter(body, list.get(i));
        }
        params.addBodyParameter("content", content);
        apiTool2.postApi(url + "addComment", params, baseView);
    }
}
