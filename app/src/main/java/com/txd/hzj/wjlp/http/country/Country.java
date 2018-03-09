package com.txd.hzj.wjlp.http.country;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/11 0011
 * 时间：17:34
 * 描述：进口馆
 * ===============Txunda===============
 */

public class Country {

    private String url = Config.BASE_URL + "Country/";

    /**
     * 进口馆首页
     *
     * @param p        分页
     * @param baseView 回调
     */
    void countryIndex(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "countryIndex", params, baseView);
    }

    /**
     * 选定国家商品列表
     *
     * @param p          分页
     * @param country_id 国家id
     * @param cate_id    分类id 默认显示第一个顶级分类的推荐
     * @param baseView   回调
     */
    void countryGoods(int p, String country_id, String cate_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("country_id", country_id);
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(url + "countryGoods", params, baseView);
    }

    /**
     * 商品详情
     *
     * @param goods_id 商品id
     * @param baseView 回调
     */
    void goodsInfo(String goods_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        apiTool2.postApi(url + "goodsInfo", params, baseView);
    }

    /**
     * 三级分类商品
     *
     * @param two_cate_id   二级分类
     * @param country_id    国家id
     * @param p             分页
     * @param three_cate_id 三级分类id
     * @param baseView      回调
     */
    void threeList(String two_cate_id, String country_id, int p, String three_cate_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("two_cate_id", two_cate_id);
        params.addBodyParameter("country_id", country_id);
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("three_cate_id", three_cate_id);
        apiTool2.postApi(url + "threeList", params, baseView);
    }

}
