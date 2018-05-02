package com.txd.hzj.wjlp.http.cart;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 购物车数据请求类
 * Created by Administrator on 2018/5/2.
 */
public class Cart {

    private static String url = Config.BASE_URL + "Cart/";

    /**
     * 添加购物车
     *
     * @param goods_id 商品id
     * @param pro_id   货品id，可为空
     * @param num      数量
     * @param baseView
     */
    public static void addCart(String goods_id, String pro_id, int num, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("product_id", pro_id);
        params.addBodyParameter("num", String.valueOf(num));
        apiTool2.postApi(url + "addCart", params, baseView);
    }

}
