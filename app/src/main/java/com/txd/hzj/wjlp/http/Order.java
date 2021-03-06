package com.txd.hzj.wjlp.http;

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
     * @param cart_id     购物车id 多个用','开(购物车结算时传)
     * @param p           分页
     * @param merchant_id 商家id
     * @param goods_id
     * @param num         数量(直接购买时传)
     * @param order_type  订单类型（ 0:普通 1限量购 2积分商店 3进口馆 4搭配购）
     * @param product_id
     * @param goods       json(商品id，属性id),(除购物车结算之外，其他全部需要传) 若无属性id，则不传 格式：[{"product_id":"0","goods_id":"5"},{"product_id":"1","goods_id":"6"}]
     * @param baseView
     */
    public static void shoppingCart(String cart_id, int p, String merchant_id, String goods_id, String num, String order_type, String product_id, String goods, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("cart_id", cart_id);
        requestParams.addBodyParameter("p", String.valueOf(p));
        requestParams.addBodyParameter("merchant_id", merchant_id);
        requestParams.addBodyParameter("goods_id", goods_id);
        requestParams.addBodyParameter("num", num);
        requestParams.addBodyParameter("order_type", order_type);
        requestParams.addBodyParameter("goods", goods); // json(商品id，属性id),(除购物车结算之外，其他全部需要传) 若无属性id，则不传 格式：[{"product_id":"0","goods_id":"5"},{"product_id":"1","goods_id":"6"}]
        requestParams.addBodyParameter("product_id", TextUtils.isEmpty(product_id) ? "0" : product_id);
        apiTool2.postApi(url + "shoppingCart", requestParams, baseView);
    }

    /**
     * 设置订单
     *
     * @param address_id    地址id
     * @param order_type    订单类型
     * @param order_id      订单编号
     * @param limit_buy_id
     * @param collocation
     * @param invoice
     * @param leave_message
     * @param goods
     * @param baseView
     */
    public static void setOrder(String address_id, String order_type, String order_id,
                                String limit_buy_id, String collocation, String invoice, String leave_message, String goods,
                                BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("address_id", address_id);
        requestParams.addBodyParameter("order_type", order_type);
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("limit_buy_id", limit_buy_id);
        requestParams.addBodyParameter("collocation", collocation);
        requestParams.addBodyParameter("invoice", invoice);
        requestParams.addBodyParameter("leave_message", leave_message);
        requestParams.addBodyParameter("goods", goods);
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
     * @param order_type   购买渠道（0:普通 1：团购 2：预购 3：竞拍 4：一元夺宝 5：积分商店 8：线下商城 12 399专区）
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
    public static void receiving(String order_id, String order_goods_id, String status, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("order_goods_id", order_goods_id);
        requestParams.addBodyParameter("status", status);
        apiTool2.postApi(url + "receiving", requestParams, baseView);
    }

    /**
     * 积分商店确认收货
     *
     * @param order_id
     * @param baseView
     */
    public static void shopReceiving(String order_id, String order_goods_id, String status, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("order_goods_id", order_goods_id);
        requestParams.addBodyParameter("status", status);
        apiTool2.postApi(Config.BASE_URL + "IntegralBuyOrder/receiving", requestParams, baseView);
    }

    /**
     * 评论主页
     *
     * @param order_id
     * @param baseView
     */
    public static void Commentindex(String order_id, String type, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        //order_type	订单类型 1普通订单 2拼单购 3无界预购 4比价购 5积分商店
        if ("0".equals(type)) {
            requestParams.addBodyParameter("order_type", "1");
        } else if ("3".equals(type)) {
            requestParams.addBodyParameter("order_type", "2");
        } else if ("4".equals(type)) {
            requestParams.addBodyParameter("order_type", "3");
        } else if ("6".equals(type)) {
            requestParams.addBodyParameter("order_type", "4");
        } else if ("10".equals(type)) {
            requestParams.addBodyParameter("order_type", "5");
        }else if ("16".equals(type)) {
            requestParams.addBodyParameter("order_type", "16");
        }

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
        //order_type	订单类型 1普通订单 2拼单购 3无界预购 4比价购 5限量购 6积分抽奖
        if ("0".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "1");
        } else if ("3".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "2");
        } else if ("4".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "3");
        } else if ("6".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "4");
        } else if ("5".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "6");
        } else if ("10".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "5");
        }else if ("16".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "16");
        }
        //todo 限量购的类型需要确定
        //        else if("5".equals(order_type)){
        //            requestParams.addBodyParameter("order_type", "5");
        //        }
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
        //order_type	订单类型 1普通订单 2拼单购 3无界预购 4比价购 5限量购 6积分抽奖
        if ("0".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "1");
        } else if ("3".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "2");
        } else if ("4".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "3");
        } else if ("6".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "4");
        } else if ("5".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "6");
        } else if ("10".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "5");
        }else if ("16".equals(order_type)) {
            requestParams.addBodyParameter("order_type", "16");
        }
        //todo 限量购的类型需要确定
        //        else if("5".equals(order_type)){
        //            requestParams.addBodyParameter("order_type", "5");
        //        }

        apiTool2.postApi(url + "CommentOrder", requestParams, baseView);
    }

    /**
     * 订单物流
     * 接口地址
     * http://wjapi.wujiemall.com/index.php/Function/index/p_id/71/mo_id/1015/f_id/5995
     *
     * @param order_id
     * @param type     0 普通商品 1拼单购订单
     * @param baseView
     */
    public static void orderLogistics(String order_id, String type, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("type", type);
        apiTool2.postApi(url + "orderLogistics", requestParams, baseView);
    }

    /**
     * 提醒发货
     *
     * @param baseView
     * @param order_goods_id 订单商品id
     */
    public static void remind(BaseView baseView, String order_goods_id) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_goods_id", order_goods_id);
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

    /**
     * 积分商店延长收货(接口不一样)
     *
     * @param order_goods_id
     * @param baseView
     */
    public static void delayShopReceiving(String order_goods_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_goods_id", order_goods_id);
        apiTool2.postApi(Config.BASE_URL + "IntegralBuyOrder/delayReceiving", requestParams, baseView);

    }

}
