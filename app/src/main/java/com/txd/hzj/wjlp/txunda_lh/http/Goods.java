package com.txd.hzj.wjlp.txunda_lh.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * by Txunda_LH on 2018/1/12.
 */

public class Goods {
    private static String url = Config.BASE_URL + "Goods/";

    /**
     * 公共商品属性
     *
     * @param goods_id
     * @param product_id
     * @param baseView
     */
    public static void attrApi(String goods_id, String product_id, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("goods_id", goods_id);
        requestParams.addBodyParameter("product_id", product_id);
        apiTool2.postApi(url + "attrApi", requestParams, baseView);
    }
}
