package com.txd.hzj.wjlp.new_wjyp.http;

import android.text.TextUtils;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.util.List;



public class Order {
    private static String url = Config.BASE_URL + "Order/";

    /**
     * 购物车结算页
     *
     * @param cart_id
     * @param p
     * @param merchant_id
     * @param baseView
     */
    public static void shoppingCart(String cart_id, int p, String merchant_id, String goods_id, String num, String order_type, String product_id,String goods, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("cart_id", cart_id);
        requestParams.addBodyParameter("p", String.valueOf(p));
        requestParams.addBodyParameter("merchant_id", merchant_id);
        requestParams.addBodyParameter("goods_id", goods_id);
        requestParams.addBodyParameter("num", num);
        requestParams.addBodyParameter("order_type", order_type);
        requestParams.addBodyParameter("goods", goods);
        requestParams.addBodyParameter("product_id", TextUtils.isEmpty(product_id)?"0":product_id);
        apiTool2.postApi(url + "shoppingCart", requestParams, baseView);
    }

    /**
     * 添加订单
     *
     * @param address_id   地址id
     * @param goods_num    商品数量(直接购买时传)
     * @param goods_id     商品id(直接购买时传)
     * @param product_id   商品属性id(直接购买时传)
     * @param cart_ids     购物车商品id(逗号分隔 购物车结算时传)
     * @param order_type   订单类型（ 0:普通 1：团购 2：预购 3：竞拍 4：一元夺宝 5：无界商店 8：线下商城 购物车购买传0）
     * @param order_id     订单id(订单支付时传)
     * @param limit_buy_id
     * @param baseView
     */
    public static void setOrder(String address_id, String goods_num, String goods_id,
                                String product_id, String cart_ids, String order_type, String order_id, String limit_buy_id,
                                String freight,
                                String freight_type,
                                String collocation,
                                String invoice,
                                String leave_message,
                                BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("cart_ids", cart_ids);
        requestParams.addBodyParameter("address_id", address_id);
        requestParams.addBodyParameter("goods_num", goods_num);
        requestParams.addBodyParameter("goods_id", goods_id);
        requestParams.addBodyParameter("product_id", product_id);
        requestParams.addBodyParameter("order_type", order_type);
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("limit_buy_id", limit_buy_id);
        requestParams.addBodyParameter("freight", freight);
        requestParams.addBodyParameter("freight_type", freight_type);
        requestParams.addBodyParameter("collocation", collocation);
        requestParams.addBodyParameter("invoice", invoice);
        requestParams.addBodyParameter("leave_message", leave_message);
        apiTool2.postApi(url + "setOrder", requestParams, baseView);
    }

    /**
     * 余额支付
     *
     * @param order_id
     * @param discount_type
     * @param baseView
     */
    public static void balancePay(String order_id, String discount_type, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("discount_type", discount_type);
        apiTool2.postApi(url + "balancePay", requestParams, baseView);
    }

    /**
     * @param order_status 订单状态（'0': '待支付‘ ； '1': '待发货' ； '2': '待收货' ；'3': '待评价'；'4': '已完成；‘5’：取消订单） 默认9（全部）
     * @param order_type   购买渠道（0:普通 1：团购 2：预购 3：竞拍 4：一元夺宝 5：无界商店 8：线下商城）
     * @param p
     * @param baseView
     */
    public static void orderList(String order_status, String order_type, int p, BaseView baseView) {

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
        requestParams.addBodyParameter("order_type", order_type);
        requestParams.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "orderList", requestParams, baseView);
    }

    /**
     * 取消订单
     *
     * @param order_id
     * @param baseView
     */
    public static void cancelOrder(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "cancelOrder", requestParams, baseView);
    }

    /**
     * 删除订单
     *
     * @param order_id
     * @param baseView
     */
    public static void deleteOrder(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "deleteOrder", requestParams, baseView);
    }

    /**
     * 详情页面
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

    /**
     * 确认收货
     *
     * @param order_id
     * @param baseView
     */
    public static void receiving(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "receiving", requestParams, baseView);
    }

    /**
     * 评论主页
     *
     * @param order_id
     * @param baseView
     */
    public static void Commentindex(String order_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("order_type", "1");
        apiTool2.postApi(url + "Commentindex", requestParams, baseView);
    }

    /**
     * 评论商品
     *
     * @param goods_id
     * @param content
     * @param list
     * @param all_star
     * @param order_id
     * @param order_type
     * @param baseView
     */
    public static void CommentGoods(String goods_id, String content, List<File> list,
                                    String all_star, String order_id, String order_type, BaseView baseView) {

        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_goods_id", goods_id);
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("content", content);
        requestParams.addBodyParameter("all_star", all_star);
        requestParams.addBodyParameter("order_type", order_type);
        for (int i = 0; i < list.size(); i++) {
            requestParams.addBodyParameter("pictures" + i, list.get(i));
        }
        apiTool2.postApi(url + "CommentGoods", requestParams, baseView);
    }

    /**
     * 评论订单
     *
     * @param order_id
     * @param merchant_star
     * @param delivery_star
     * @param order_type
     * @param baseView
     */
    public static void CommentOrder(String order_id, String merchant_star, String delivery_star, String order_type, BaseView baseView) {

        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("merchant_star", merchant_star);
        requestParams.addBodyParameter("delivery_star", delivery_star);
        requestParams.addBodyParameter("order_type", order_type);
        apiTool2.postApi(url + "CommentOrder", requestParams, baseView);
    }
}
