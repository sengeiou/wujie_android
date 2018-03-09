package com.txd.hzj.wjlp.new_wjyp.http;

import android.text.TextUtils;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;


public class BalancePay {
    private static String url = Config.BASE_URL + "/BalancePay/";

    /**
     * @param order_id      订单id
<<<<<<< HEAD
     * @param order_type   订单类型1普通订单 2拼单购 3无界预购 4比价购 5限量购 6积分抽奖
=======
     * @param order_type   订单类型1普通订单 2拼单购 3xfte预购 4比价购 5限量购 6积分抽奖
>>>>>>> master
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
        params.addBodyParameter("goods_num", inte_id);
        apiTool2.postApi(url + "BalancePay", params, baseView);

    }
}
