package com.txd.hzj.wjlp.new_wjyp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * by Txunda_LH on 2018/2/9.
 */

public class Invoice {
    private static String url = Config.BASE_URL + "Invoice/";

    public static void invoice(String goods, BaseView baseView) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("goods", goods);
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "invoice", params, baseView);
    }

    public static void type(String goods_id, String invoice_type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("invoice_type", invoice_type);
        apiTool2.postApi(url + "type", params, baseView);

    }
}
