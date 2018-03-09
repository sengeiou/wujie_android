package com.txd.hzj.wjlp.new_wjyp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

public class Easemob {

    private static String url = Config.BASE_URL + "Easemob/";

    public static void bind(String merchant_id, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("merchant_id", merchant_id);
        apiTool2.postApi(url + "bind", params, baseView);
    }
}
