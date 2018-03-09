package com.txd.hzj.wjlp.new_wjyp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

public class MemberOrder {

    private static String url = Config.BASE_URL + "MemberOrder/";


    public static void settlement(String rank_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("rank_id", rank_id);
        apiTool2.postApi(url + "settlement", params, baseView);
    }

    public static void ticket(String rank_id, String number, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("rank_id", rank_id);
        params.addBodyParameter("number", number);
        apiTool2.postApi(url + "ticket", params, baseView);
    }

    public static void setOrder(String rank_id, String number, String discount_type, String pay_type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("rank_id", rank_id);
        params.addBodyParameter("number", number);
        params.addBodyParameter("discount_type", discount_type);
        params.addBodyParameter("pay_type", pay_type);
        apiTool2.postApi(url + "setOrder", params, baseView);

    }



}
