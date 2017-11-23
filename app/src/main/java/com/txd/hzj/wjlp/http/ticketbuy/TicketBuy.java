package com.txd.hzj.wjlp.http.ticketbuy;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/9 0009
 * 时间：16:50
 * 描述：票券区模块
 * ===============Txunda===============
 */
class TicketBuy {

    private String url = Config.BASE_URL + "TicketBuy/";

    /**
     * 票券区首页
     *
     * @param p        分页
     * @param cate_id  分类id
     * @param baseView 回调
     */
    void ticketBuyIndex(int p, String cate_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(url + "ticketBuyIndex", params, baseView);
    }

    /**
     * 票券区商品详情
     *
     * @param ticket_buy_id 票券区id
     * @param baseView      回调
     */
    void ticketBuyInfo(String ticket_buy_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("ticket_buy_id", ticket_buy_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "ticketBuyInfo", params, baseView);
    }

    /**
     * 三级分类商品
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
}
