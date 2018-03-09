package com.txd.hzj.wjlp.http.limit;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/8 0008
 * 时间：15:01
 * 描述：限量购
 * ===============Txunda===============
 */

class LimitBuy {
    private String url = Config.BASE_URL + "LimitBuy/";

    /**
     * 限量购首页
     *
     * @param p        分页
     * @param stage_id 场次id (默认是当前时间所在场次)
     * @param baseView 回调
     */
    void limitBuyIndex(int p, String stage_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("stage_id", stage_id);
        apiTool2.postApi(url + "limitBuyIndex", params, baseView);
    }

    /**
     * 限量购详情
     *
     * @param limit_buy_id 限量购id
     * @param baseView     回调
     */
    void limitBuyInfo(String limit_buy_id,int page ,BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("limit_buy_id", limit_buy_id);
        params.addBodyParameter("p", String.valueOf(page));
        apiTool2.postApi(url + "limitBuyInfo", params, baseView);
    }

    /**
     * 限量购详情
     *
     * @param limit_buy_id 限量购id
     * @param baseView     回调
     */
    void remindMe(String limit_buy_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("limit_buy_id", limit_buy_id);
        apiTool2.postApi(url + "remindMe", params, baseView);
    }

}
