package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;


public class IntegralPay {
    private static String url = Config.BASE_URL + "IntegralPay/";


    /**
     * @param order_id
     * @param order_type 订单类型1普通订单 2拼单购 3无界预购 4比价购 5限量购 6积分抽奖
     * @param auct_id
     * @param goods_num
     * @param baseView
     */
    public static void integralPay(String order_id, String order_type, String auct_id, String goods_num, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("order_type", order_type);
        params.addBodyParameter("auct_id", auct_id);
        params.addBodyParameter("goods_num", goods_num);
        apiTool2.postApi(url + "integralPay", params, baseView);
    }
}
