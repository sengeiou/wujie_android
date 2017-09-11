package com.txd.hzj.wjlp.http.integralbuy;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/11 0011
 * 时间：16:52
 * 描述：无界商店
 * ===============Txunda===============
 */

class IntegralBuy {

    private String url = Config.BASE_URL + "IntegralBuy/";

    /**
     * 无界商店首页
     *
     * @param p        分页
     * @param cate_id  分类id默认显示第一个顶级分类的推荐
     * @param baseView 回调
     */
    void integralBuyIndex(int p, String cate_id, BaseView baseView) {

        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(url + "integralBuyIndex", params, baseView);

    }

    /**
     * 无界商店详情
     *
     * @param integral_buy_id 无界商店商品编号
     * @param baseView        回调
     */
    void integralBuyInfo(String integral_buy_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("integral_buy_id", integral_buy_id);
        apiTool2.postApi(url + "integralBuyInfo", params, baseView);
    }

    /**
     * 三积分类商品
     *
     * @param two_cate_id   二级分类id
     * @param p             分页
     * @param three_cate_id 三级分类id
     * @param baseView      回调
     */
    void threeList(String two_cate_id, int p, String three_cate_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("two_cate_id", two_cate_id);
        params.addBodyParameter("three_cate_id", three_cate_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "threeList", params, baseView);
    }
}
