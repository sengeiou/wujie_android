package com.txd.hzj.wjlp.new_wjyp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.util.List;


public class AfterSale {
    private static String url = Config.BASE_URL + "AfterSale/";

    /**
     * 售后原因
     *
     * @param baseView
     */
    public static void cause(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "cause", new RequestParams(), baseView);
    }

    /**
     * 发起售后
     *
     * @param reason
     * @param back_money
     * @param back_desc
     * @param list
     * @param cause
     * @param goods_status
     * @param order_id
     * @param order_type
     * @param order_goods_id
     * @param baseView
     */
    public static void backApply(String reason, String back_money, String back_desc, List<File> list, String cause,
                                 String goods_status, String order_id, String order_type, String order_goods_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();

        L.e("reason=" + reason + ", back_money=" + back_money + ", back_desc=" + back_desc + ", list=" + list
                + ", cause=" + cause + ", goods_status=" + goods_status + ", order_id=" + order_id + ", order_type=" + order_type + ", order_goods_id=" + order_goods_id);

        requestParams.addBodyParameter("reason", reason);
        requestParams.addBodyParameter("back_money", back_money);
        requestParams.addBodyParameter("back_desc", back_desc);
        requestParams.addBodyParameter("reason", reason);
        requestParams.addBodyParameter("cause", cause);
        requestParams.addBodyParameter("goods_status", goods_status);
        requestParams.addBodyParameter("order_id", order_id);
        requestParams.addBodyParameter("order_type", order_type);
        requestParams.addBodyParameter("order_goods_id", order_goods_id);
        for (int i = 0; i < list.size(); i++) {
            requestParams.addBodyParameter("back_img" + i, list.get(i));
        }
        apiTool2.postApi(url + "backApply", requestParams, baseView);
    }

    /**
     * 展示售后信息
     *
     * @param order_goods_id
     * @param back_apply_id
     * @param baseView
     */
    public static void showAfter(String order_goods_id, String back_apply_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_goods_id", order_goods_id);
        requestParams.addBodyParameter("back_apply_id", back_apply_id);
        apiTool2.postApi(url + "showAfter", requestParams, baseView);
    }

    /**
     * @param back_apply_id
     * @param baseView
     */
    public static void cancelAfter(String back_apply_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("back_apply_id", back_apply_id);
        apiTool2.postApi(url + "cancelAfter", requestParams, baseView);

    }

    /**
     * 快递列表
     *
     * @param baseView
     */
    public static void shipping(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "shipping", new RequestParams(), baseView);
    }

    /**
     * 添加快递单号
     *
     * @param shipping_id
     * @param invoice
     * @param back_apply_id
     * @param baseView
     */
    public static void addShipping(String shipping_id, String invoice, String back_apply_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("invoice", invoice);
        requestParams.addBodyParameter("shipping_id", shipping_id);
        requestParams.addBodyParameter("back_apply_id", back_apply_id);
        apiTool2.postApi(url + "addShipping", requestParams, baseView);
    }


    //售后类型及货物状态
    public static void backApplyType(String order_goods_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("order_goods_id", order_goods_id);

        apiTool2.postApi(url + "backApplyType", requestParams, baseView);
    }

}
