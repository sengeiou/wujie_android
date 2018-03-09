package com.txd.hzj.wjlp.http.welfare;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/15 0015
 * 时间：14:11
 * 描述：福利社
 * ===============Txunda===============
 */

class Welfare {

    private String url = Config.BASE_URL + "Welfare/";

    /**
     * 优惠券
     *
     * @param p        分页
     * @param cate_id  分类id
     * @param baseView 回调
     */
    void ticketList(int p, String cate_id, BaseView baseView) {

        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(url + "ticketList", params, baseView);

    }

    /**
     * 领取优惠券
     *
     * @param ticket_id 优惠券id
     * @param baseView  回调
     */
    void getTicket(String ticket_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("ticket_id", ticket_id);
        apiTool2.postApi(url + "getTicket", params, baseView);
    }

    /**
     * 红包列表
     *
     * @param p        分页
     * @param baseView 回调
     */
    void faceList(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "faceList", params, baseView);
    }

    /**
     * 红包广告
     *
     * @param bonus_id 红包id
     * @param baseView 回调
     */
    void bonusList(String bonus_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("bonus_id", bonus_id);
        apiTool2.postApi(url + "bonusList", params, baseView);
    }

    /**
     * 红包广告
     *
     * @param bonus_id 红包id
     * @param baseView 回调
     */
    void getBonus(String bonus_id, String bonus_val, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("bonus_id", bonus_id);
        params.addBodyParameter("bonus_val", bonus_val);
        apiTool2.postApi(url + "getBonus", params, baseView);
    }

    /**
     * 获取分享红包的内容
     * @param bonus_id  红包id
     * @param baseView  回调
     */
    void shareContent(String bonus_id,BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("bonus_id", bonus_id);
        apiTool2.postApi(url + "shareContent", params, baseView);
    }

}
