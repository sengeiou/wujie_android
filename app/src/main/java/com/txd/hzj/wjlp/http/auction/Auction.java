package com.txd.hzj.wjlp.http.auction;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/7 0007
 * 时间：13:15
 * 描述：竞拍汇模块
 *
 */

public class Auction {

    private String url = Config.BASE_URL + "Auction/";

    /**
     * 竞拍汇首页
     *
     * @param next     固定值 1 当next为1时获取的是拍卖预展
     * @param p        分页
     * @param baseView 回调
     */
    void auctionIndex(int next, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("next", String.valueOf(next));
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "auctionIndex", params, baseView);
    }

    /**
     * 竞拍商品详情
     *
     * @param auction_id 竞拍id
     * @param baseView   回调
     */
    void auctionInfo(String auction_id, int page, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("auction_id", auction_id);
        params.addBodyParameter("p", String.valueOf(page));
        apiTool2.postApi(url + "auctionInfo", params, baseView);
    }

    /**
     * 设置提醒
     *
     * @param auction_id 竞拍id
     * @param baseView   回调
     */
    void remindMe(String auction_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("auction_id", auction_id);
        apiTool2.postApi(url + "remindMe", params, baseView);
    }

}
