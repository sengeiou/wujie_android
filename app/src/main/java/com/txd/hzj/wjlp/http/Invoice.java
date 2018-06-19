package com.txd.hzj.wjlp.http;

import android.text.TextUtils;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * by Txunda_LH on 2018/2/9.
 */

public class Invoice {
    private static String url = Config.BASE_URL + "Invoice/";

    public static void invoice(String goods, String shop_price, BaseView baseView) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("goods", goods);
        if (!TextUtils.isEmpty(shop_price))
            params.addBodyParameter("shop_price", shop_price);//	价格（无界商店商品积分价格不从商品或制品表中取值时，用此参数设定价格）
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
