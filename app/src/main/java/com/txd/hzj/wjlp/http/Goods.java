package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.http.RequestParams;


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

    /**
     * 商品列表页
     * @param p 	分页参数u
     * @param cate_id   分类id 默认显示分类的推荐(0为全部)
     * @param baseView
     */
    public static void goodsList(String p,String cate_id,BaseView baseView){
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("p", p);
        requestParams.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(url + "goodsList", requestParams, baseView);
    }
}
