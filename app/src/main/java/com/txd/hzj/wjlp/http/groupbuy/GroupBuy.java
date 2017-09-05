package com.txd.hzj.wjlp.http.groupbuy;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/5 0005
 * 时间：11:12
 * 描述：拼团购
 * ===============Txunda===============
 */

public class GroupBuy {

    private String url = Config.BASE_URL + "GroupBuy/";

    /**
     * 拼团购首页
     *
     * @param p        分页
     * @param cate_id  分类id
     * @param baseView 回调
     */
    void groupBuyIndex(int p, String cate_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(url + "groupBuyIndex", params, baseView);
    }

    /**
     * 拼团详情
     *
     * @param group_buy_id 团购id
     * @param baseView     回调
     */
    void groupBuyInfo(String group_buy_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("group_buy_id", group_buy_id);
        apiTool2.postApi(url + "groupBuyInfo", params, baseView);
    }

    /**
     * 参团页
     *
     * @param log_id   开团id
     * @param baseView 回调
     */
    void goGroup(String log_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("log_id",log_id);
        apiTool2.postApi(url+"goGroup",params,baseView);
    }
}
