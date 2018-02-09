package com.txd.hzj.wjlp.new_wjyp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

public class PreOrder {
    private static String url = Config.BASE_URL + "PreOrder/";

    /**
     * 预购结算页
     *
     * @param pre_id
     * @param num
     * @param
     * @param baseView
     */
    public static void preShoppingCart(String pre_id, String num, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("pre_id", pre_id);
        params.addBodyParameter("num", num);
//        params.addBodyParameter("order_type", order_type);
        apiTool2.postApi(url + "preShoppingCart", params, baseView);

    }

    /**
     * 添加订单
     *
     * @param goods_num
     * @param address_id
     * @param order_id
     * @param pre_id
     * @param baseView
     */
    public static void preSetOrder(String goods_num, String address_id, String order_id,
                                   String pre_id,
                                   String freight,
                                   String freight_type,
                                   String invoice,
                                   String leave_message,
                                   BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("pre_id", pre_id);
        params.addBodyParameter("goods_num", goods_num);
        params.addBodyParameter("address_id", address_id);
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("freight", freight);
        params.addBodyParameter("invoice", invoice);
        params.addBodyParameter("leave_message", leave_message);
        params.addBodyParameter("freight_type", freight_type);
        apiTool2.postApi(url + "preSetOrder", params, baseView);
    }

    /**
     * 订单列表
     *
     * @param order_status 订单状态（ '0': '预购中' ； '1': '待付尾款' ；'2': '待发货；'3': '待收货；‘4’：待评价；‘5’：‘已取消’；‘6’：已完成；‘7’ ：待付定金） 默认9（全部）
     * @param p
     */
    public static void preOrderList(String order_status, int p, BaseView baseView) {
        switch (order_status) {
            case "0":
                order_status = "9";
                break;
            case "1":
                order_status = "0";
                break;
            case "2":
                order_status = "1";
                break;
            case "3":
                order_status = "2";
                break;
            case "4":
                order_status = "3";
                break;
            case "5":
                order_status = "4";
                break;
        }
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_status", order_status);
        requestParams.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "preOrderList", requestParams, baseView);
    }

    /**
     * 确认收货
     *
     * @param order_id
     * @param baseView
     */
    public static void preReceiving(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "preReceiving", requestParams, baseView);
    }

    /**
     * 取消
     *
     * @param order_id
     * @param baseView
     */
    public static void preCancelOrder(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "preCancelOrder", requestParams, baseView);
    }

    /**
     * 取消
     *
     * @param order_id
     * @param baseView
     */
    public static void preDeleteOrder(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "preDeleteOrder", requestParams, baseView);
    }

    /**
     * 详情
     *
     * @param order_id
     * @param baseView
     */
    public static void preDetails(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "preDetails", requestParams, baseView);
    }
}
