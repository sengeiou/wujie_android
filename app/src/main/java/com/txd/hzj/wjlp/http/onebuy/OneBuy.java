package com.txd.hzj.wjlp.http.onebuy;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/9/4 0004
 * 时间：10:34
 * 描述：一元购
 */

public class OneBuy {
    private String url = Config.BASE_URL + "OneBuy/";

    /**
     * 一元夺宝首页
     *
     * @param p          分页
     * @param add_num    进度 1正序 2倒序
     * @param person_num 人气 1正序 2倒序
     * @param integral   积分 1正序 2倒序
     * @param baseView   回调
     */
    void oneBuyIndex(int p, String add_num, String person_num, String integral, String is_hot,
                     BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("add_num", add_num);
        params.addBodyParameter("person_num", person_num);
        params.addBodyParameter("integral", integral);
        params.addBodyParameter("is_hot", is_hot);
        apiTool2.postApi(url + "oneBuyIndex", params, baseView);
    }

    /**
     * 一元购详情
     *
     * @param one_buy_id id
     * @param baseView   回调
     */
    void oneBuyInfo(String one_buy_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("one_buy_id", one_buy_id);
        apiTool2.postApi(url + "oneBuyInfo", params, baseView);
    }

}
