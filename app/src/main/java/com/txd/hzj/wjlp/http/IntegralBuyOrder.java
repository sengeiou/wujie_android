package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;


public class IntegralBuyOrder {

    private static String url = Config.BASE_URL + "IntegralBuyOrder/";

    /**
     * 积分商店结算页
     *
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
     * 积分商店添加订单
     *
     * @param integralBuy_id
     * @param address_id
     * @param goods_num
     * @param order_id
     * @param leave_message  用户订单留言
     * @param shipping_id    用户订单留言
     * @param invoice        json , 请按顺序传入！！！ [{"发票类型id":"1","发票抬头（1->个人，2->公司）":"1","发票抬头名":"name","发票明细":"detail","发票id":"1","识别号”:1111,"是否开发票（1->是，0->否）”:1}, {"t_id":"1","rise":"1","rise_name":"name","invoice_detail":"detail","invoice_id":"3",,"recognition”:1111,"is_invoice”:1}]
     * @param baseView
     */
    public static void SetOrder(String integralBuy_id, String address_id,
                                String goods_num, String order_id, String freight, String freight_type, String leave_message, String shipping_id, String invoice, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("integralBuy_id", integralBuy_id);
        params.addBodyParameter("address_id", address_id);
        params.addBodyParameter("goods_num", goods_num);
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("freight", freight);
        params.addBodyParameter("freight_type", freight_type);
        params.addBodyParameter("leave_message", leave_message);
        params.addBodyParameter("shipping_id", shipping_id);
        params.addBodyParameter("invoice", invoice);
        apiTool2.postApi(url + "SetOrder", params, baseView);
    }

    /**
     * 积分商店订单列表
     *
     * @param order_status 订单状态（'0': '待付款‘ ； '1': '待发货' ； '2': '待收货' ；'3': '待评价'；'4': '已完成；‘5’：取消订单） 默认9（全部）
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
     * 积分商店订单详情
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
     * 积分商店删除订单
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
     * 积分商店取消订单
     *
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
     * 积分商店确认收货
     *
     * @param order_id
     * @param status  状态（2->放弃，1->确认）
     * @param baseView
     */
    public static void Receiving(String order_id, String status, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("status", status);//status	状态（2->放弃，1->确认）
        apiTool2.postApi(url + "Receiving", requestParams, baseView);
    }

    /**
     *
     * @param order_id  订单ID
     * @param baseView
     */
    public static void remind(String order_id, BaseView baseView){
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "remind", requestParams, baseView);
    }
}
