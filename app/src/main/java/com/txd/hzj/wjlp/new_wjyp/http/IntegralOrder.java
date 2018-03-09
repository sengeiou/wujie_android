package com.txd.hzj.wjlp.new_wjyp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;


public class IntegralOrder {
    private static String url = Config.BASE_URL + "IntegralOrder/";

    /**
     * @param merchant_id
     * @param num
     * @param integral_id
     * @param baseView
     */
    public static void ShoppingCart(String merchant_id, String num, String integral_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("merchant_id", merchant_id);
        requestParams.addBodyParameter("num", num);
        requestParams.addBodyParameter("integral_id", integral_id);
        apiTool2.postApi(url + "ShoppingCart", requestParams, baseView);
    }

    /**
     * 添加订单
     *
     * @param goods_num
     * @param address_id
     * @param order_id
     * @param integral_id
     * @param order_type
     * @param baseView
     */
    public static void SetOrder(String goods_num, String address_id,
                                String order_id, String integral_id,
                                String order_type,
                                String freight,
                                String freight_type,
                                String invoice,
                                String leave_message,
                                String goods,
                                BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("goods_num", goods_num);
        requestParams.addBodyParameter("address_id", address_id);
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("integral_id", integral_id);
        requestParams.addBodyParameter("order_type", order_type);
        requestParams.addBodyParameter("freight", freight);
        requestParams.addBodyParameter("invoice", invoice);
        requestParams.addBodyParameter("leave_message", leave_message);
        requestParams.addBodyParameter("goods", goods);
        requestParams.addBodyParameter("freight_type", freight_type);
        apiTool2.postApi(url + "SetOrder", requestParams, baseView);
    }

    /**
     * 订单列表
     *
     * @param order_status
     * @param p
     * @param baseView
     */
    public static void orderList(String order_status, int p, BaseView baseView) {
        switch (order_status) {
            case "0":
                order_status = "9";
                break;
            case "1":
                order_status = "10";
                break;
            case "2":
                order_status = "11";
                break;
            case "3":
                order_status = "12";
                break;
        }
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_status", order_status);
        requestParams.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "orderList", requestParams, baseView);
    }

    /**
     * 删除订单
     *
     * @param order_id
     * @param baseView
     */
    public static void DeleteOrder(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "DeleteOrder", requestParams, baseView);
    }

    /**
     * 删除订单
     *
     * @param order_id
     * @param baseView
     */
    public static void details(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "details", requestParams, baseView);
    }
}
