package com.txd.hzj.wjlp.txunda_lh.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * by Txunda_LH on 2017/12/6.
 */

public class Order {
    private static String url = Config.BASE_URL + "/Order/";

    /**
     * 购物车结算页
     *
     * @param cart_id
     * @param p
     * @param merchant_id
     * @param baseView
     */
    public static void shoppingCart(String cart_id, int p, String merchant_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("cart_id", cart_id);
        requestParams.addBodyParameter("p", String.valueOf(p));
        requestParams.addBodyParameter("merchant_id", merchant_id);
        apiTool2.postApi(url + "shoppingCart", requestParams, baseView);
    }

}
