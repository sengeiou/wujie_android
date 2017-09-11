package com.txd.hzj.wjlp.http.street;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/9 0009
 * 时间：17:04
 * 描述：主题街模块
 * ===============Txunda===============
 */

class Theme {

    private String url = Config.BASE_URL + "Theme/";

    /**
     * 主题街展示页
     *
     * @param p        分页
     * @param baseView 回调
     */
    void themeList(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "themeList", params, baseView);
    }

    /**
     * 主题街商品页
     *
     * @param theme_id 主题id
     * @param p        分页
     * @param baseView 回调
     */
    void themeGoods(String theme_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("theme_id", theme_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "themeGoods", params, baseView);
    }

    /**
     * 商品详情页
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

}
