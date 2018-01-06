package com.txd.hzj.wjlp.txunda_lh.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * by Txunda_LH on 2018/1/2.
 */

public class Freight {
    private static String url = Config.BASE_URL + "Freight/";

    /**
     * 商品详情运费
     *
     * @param goods_id
     * @param address
     * @param baseView
     */
    public static void freight(String goods_id, String address, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("address", address);
        apiTool2.postApi(url + "freight", params, baseView);
    }


    /***
     *订单运费
     * @param address_id
     * @param goods
     * @param baseView
     */
    public static void split(String address_id, String goods, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("address_id", address_id);
        params.addBodyParameter("goods", goods);
        apiTool2.postApi(url + "split", params, baseView);
    }
}
