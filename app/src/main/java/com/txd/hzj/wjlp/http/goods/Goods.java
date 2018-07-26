package com.txd.hzj.wjlp.http.goods;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/14 0014
 * 时间：17:35
 * 描述：商品列表页
 *
 */

class Goods {

    private String url = Config.BASE_URL + "Goods/";

    /**
     * 商品列表分页
     *
     * @param p        分页
     * @param cate_id  分类id
     * @param baseView 回调
     */
    void goodsList(int p, String cate_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(url + "goodsList", params, baseView);
    }

    /**
     * 商品详情
     *
     * @param goods_id 商品id
     * @param baseView 回调
     */
    void goodsInfo(String goods_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "goodsInfo", params, baseView);
    }

    /**
     * 三级分类商品列表
     *
     * @param two_cate_id   二级分类id
     * @param three_cate_id 三级分类id
     * @param p             分页
     * @param baseView      回调
     */
    void threeList(String two_cate_id, String three_cate_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("two_cate_id", two_cate_id);
        params.addBodyParameter("three_cate_id", three_cate_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "threeList", params, baseView);
    }

    /**
     * 搜索
     *
     * @param type     类型，1商品，2商家
     * @param name     搜索关键字
     * @param p        分页
     * @param baseView 回调
     */
    void search(String type, String name, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("type", type);
        params.addBodyParameter("name", name);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "search", params, baseView);
    }


}
