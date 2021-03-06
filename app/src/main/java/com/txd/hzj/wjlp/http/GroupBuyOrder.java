package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

public class GroupBuyOrder {
    private static String url = Config.BASE_URL + "GroupBuyOrder/";

    /**
     * 添加订单
     *
     * @param address_id
     * @param goods_num
     * @param goods_id
     * @param product_id
     * @param order_type
     * @param group_buy_order_id
     * @param group_buy_id
     * @param leave_word         用户订单留言
     * @param baseView
     */
    public static void setOrder(String address_id, String goods_num, String goods_id, String product_id,
                                String order_type, String group_buy_order_id,
                                String group_buy_id,
                                String freight,
                                String freight_type,
                                String invoice,
                                String leave_message,
                                String goods,
                                String shipping_id,
                                String leave_word,
                                BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("address_id", address_id);
        params.addBodyParameter("goods_num", goods_num);
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("product_id", product_id);
        params.addBodyParameter("order_type", order_type);
        params.addBodyParameter("group_buy_order_id", group_buy_order_id);
        params.addBodyParameter("group_buy_id", group_buy_id);
        params.addBodyParameter("freight", freight);
        params.addBodyParameter("freight_type", freight_type);
        params.addBodyParameter("invoice", invoice);
        params.addBodyParameter("leave_message", leave_message);
        params.addBodyParameter("goods", goods);
        params.addBodyParameter("shipping_id", shipping_id);
        apiTool2.postApi(url + "setOrder", params, baseView);
    }

    /**
     * 结算页
     *
     * @param goods_id
     * @param num
     * @param order_type  order_type	1直接购买 2拼单
     * @param product_id
     * @param merchant_id
     * @param baseView
     */
    public static void shoppingCart(String goods_id, String num, String order_type,
                                    String product_id,
                                    String merchant_id,
                                    String group_buy_id,
                                    BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("num", num);
        params.addBodyParameter("order_type", order_type);
        params.addBodyParameter("product_id", product_id);
        params.addBodyParameter("group_buy_id", group_buy_id);
        params.addBodyParameter("merchant_id", merchant_id);
        apiTool2.postApi(url + "shoppingCart", params, baseView);
    }

    /**
     * 余额支付
     *
     * @param group_buy_order_id
     * @param discount_type
     * @param baseView
     */
    public static void balancePay(String group_buy_order_id, String discount_type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("group_buy_order_id", group_buy_order_id);
        params.addBodyParameter("discount_type", discount_type);
        apiTool2.postApi(url + "balancePay", params, baseView);
    }

    /**
     * 0待付款1拼单中2待发货3待收货4待评价 5 已完成 6取消 9全部
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
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_status", order_status);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "orderList", params, baseView);

    }

    /**
     * 取消订单
     *
     * @param group_buy_order_id
     * @param baseView
     */
    public static void cancelOrder(String group_buy_order_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("group_buy_order_id", group_buy_order_id);
        apiTool2.postApi(url + "cancelOrder", params, baseView);
    }

    /**
     * 删除订单
     *
     * @param group_buy_order_id
     * @param baseView
     */
    public static void deleteOrder(String group_buy_order_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("group_buy_order_id", group_buy_order_id);
        apiTool2.postApi(url + "deleteOrder", params, baseView);
    }

    /**
     * 确认收货
     *
     * @param group_buy_order_id
     * @param status   状态（2->放弃，1->确认）
     * @param baseView
     */
    public static void receiving(String group_buy_order_id, String status, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("group_buy_order_id", group_buy_order_id);
        params.addBodyParameter("status", status);
        apiTool2.postApi(url + "receiving", params, baseView);
    }

    /**
     * 订单详情
     *
     * @param group_buy_order_id
     * @param baseView
     */
    public static void details(String group_buy_order_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("group_buy_order_id", group_buy_order_id);
        apiTool2.postApi(url + "details", params, baseView);
    }

    /**
     * 我要参团
     *
     * @param group_buy_order_id
     * @param baseView
     */
    public static void offered(String group_buy_order_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("group_buy_order_id", group_buy_order_id);
        apiTool2.postApi(url + "offered", params, baseView);
    }

    /***
     *分享所需要的链接地址
     * @param type   商品详情页  1  参团页面      type 2
     * @param groupId 商品详情页 group_buy_id    参团页面    group_buy_order_id
     * @param baseView
     */
    public static void shareurl(String type, String groupId, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("type", type);
        params.addBodyParameter("id", groupId);
        apiTool2.postApi(url + "mkShareUrl", params, baseView);
    }

    /**
     * 提醒发货
     *
     * @param baseView
     * @param group_buy_order_id 订单ID
     */
    public static void remind(BaseView baseView, String group_buy_order_id) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("group_buy_order_id", group_buy_order_id);
        apiTool2.postApi(url + "remind", requestParams, baseView);
    }


    /**
     * 延长收货
     *
     * @param order_goods_id
     * @param baseView
     */
    public static void delayReceiving(String order_goods_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_goods_id", order_goods_id);
        apiTool2.postApi(url + "delayReceiving", requestParams, baseView);

    }

}
