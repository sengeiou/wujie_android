package com.txd.hzj.wjlp.txunda_lh.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * by Txunda_LH on 2017/12/14.
 */

public class AuctionOrder {
    private static String url = Config.BASE_URL + "AuctionOrder/";

    /**
     * 保证金判断（结算页前调用）
     *
     * @param auction_id
     * @param baseView
     */
    public static void auct(String auction_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("auction_id", auction_id);
        apiTool2.postApi(url + "auct", requestParams, baseView);
    }

    /**
     * 结算页
     *
     * @param merchant_id
     * @param auction_id
     * @param bid
     * @param buy_type
     * @param baseView
     */
    public static void ShoppingCart(String merchant_id, String auction_id, String bid, String buy_type, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("merchant_id", merchant_id);
        requestParams.addBodyParameter("auction_id", auction_id);
        requestParams.addBodyParameter("bid", bid);
        requestParams.addBodyParameter("buy_type", buy_type);
        apiTool2.postApi(url + "ShoppingCart", requestParams, baseView);
    }

    /**
     * 添加订单
     *
     * @param address_id
     * @param auction_id
     * @param buy_type
     * @param bid
     * @param order_id
     * @param baseView
     */
    public static void SetOrder(String address_id, String auction_id, String buy_type, String bid, String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("address_id", address_id);
        requestParams.addBodyParameter("auction_id", auction_id);
        requestParams.addBodyParameter("buy_type", buy_type);
        requestParams.addBodyParameter("bid", bid);
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "SetOrder", requestParams, baseView);
    }

    /**
     * 订单列表
     *
     * @param order_status
     * @param p
     */
    public static void OrderList(String order_status, int p, BaseView baseView) {
        switch (order_status) {
            case "0":
                order_status = "9";
                break;
            case "2":
                order_status = "3";
                break;
            case "3":
                order_status = "4";
                break;
            case "4":
                order_status = "8";
                break;
        }
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_status", order_status);
        requestParams.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "OrderList", requestParams, baseView);
    }

    public static void preDetails(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "preDetails", requestParams, baseView);
    }

    public static void CancelOrder(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "CancelOrder", requestParams, baseView);
    }

    public static void DeleteOrder(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "DeleteOrder", requestParams, baseView);
    }

    public static void Receiving(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "Receiving", requestParams, baseView);
    }
}
