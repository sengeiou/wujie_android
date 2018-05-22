package com.txd.hzj.wjlp.http;

import android.text.TextUtils;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.http.RequestParams;


public class BalancePay {
    private static String url = Config.BASE_URL + "BalancePay/";

    /**
     * @param order_id 订单id
     * @param order_type 订单类型
     * @param discount_type
     * @param inte_id 商品数量
     * @param baseView
     */
    public static void BalancePay(String order_id, String order_type, String discount_type, String inte_id, BaseView baseView) {
        L.e("order"+order_id+"--"+order_type+"--"+discount_type+"--"+inte_id);
        if (TextUtils.isEmpty(order_type)) {
            order_type = "1";
        }
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("order_type", order_type);
        params.addBodyParameter("discount_type", discount_type);
        params.addBodyParameter("goods_num", inte_id);
        apiTool2.postApi(url + "BalancePay", params, baseView);

    }
}
