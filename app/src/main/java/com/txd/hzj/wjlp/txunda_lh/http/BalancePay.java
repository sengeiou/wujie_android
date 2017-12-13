package com.txd.hzj.wjlp.txunda_lh.http;

import android.text.TextUtils;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * by Txunda_LH on 2017/12/11.
 */

public class BalancePay {
    private static String url = Config.BASE_URL + "/BalancePay/";

    /**
     * @param order_id      订单id
     * @param order_type    订单类型1普通订单 2拼单购 3预购 4比价购 5限量购
     * @param discount_type 使用代金券：0不使用代金券 1使用红券 2使用黄券 3使用蓝(多个选择用','隔开)
     * @param baseView
     */
    public static void BalancePay(String order_id, String order_type, String discount_type, String inte_id, BaseView baseView) {
        if (TextUtils.isEmpty(order_type)) {
            order_type = "1";
        }
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("order_type", order_type);
        params.addBodyParameter("discount_type", discount_type);
        params.addBodyParameter("inte_id", inte_id);
        apiTool2.postApi(url + "BalancePay", params, baseView);

    }
}
