package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;


public class IntegralBuyOrder {

    private static String url = Config.BASE_URL + "IntegralBuyOrder/";

    /**
     * 无界商店结算页
     * @param merchant_id
     * @param integralBuy_id
     * @param num
     * @param baseView
     */
    public static void ShoppingCart(String merchant_id, String integralBuy_id, String num, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("merchant_id", merchant_id);
        params.addBodyParameter("integralBuy_id", integralBuy_id);
        params.addBodyParameter("num", num);
        apiTool2.postApi(url + "ShoppingCart", params, baseView);
    }

    /**
     * 无界商店添加订单
     * @param integralBuy_id
     * @param address_id
     * @param goods_num
     * @param order_id
     * @param baseView
     */
    public static void SetOrder(String integralBuy_id, String address_id,
                                String invoice,
                                String leave_message, String goods_num, String order_id, String goods,BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("integralBuy_id", integralBuy_id);
        params.addBodyParameter("address_id", address_id);
        params.addBodyParameter("goods_num", goods_num);
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("invoice", invoice);
        params.addBodyParameter("leave_message", leave_message);
        params.addBodyParameter("goods", goods);
        apiTool2.postApi(url + "SetOrder", params, baseView);
    }

    /**
     * 无界商店订单列表
     * @param order_status
     * @param p
     * @param baseView
     */
    public static void OrderList(String order_status, int p, BaseView baseView) {

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
        }
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_status", order_status);
        requestParams.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "OrderList", requestParams, baseView);

    }

    /**
     * 无界商店订单详情
     * @param order_id
     * @param baseView
     */
    public static void details(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "details", requestParams, baseView);
    }

    /**
     * 无界商店删除订单
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
     * 无界商店取消订单
     * @param order_id
     * @param baseView
     */
    public static void CancelOrder(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "CancelOrder", requestParams, baseView);
    }

    /**
     * 无界商店确认收货
     * @param order_id
     * @param baseView
     */
    public static void Receiving(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "Receiving", requestParams, baseView);
    }

}
