package com.txd.hzj.wjlp.http.message;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/27 0027
 * 时间：09:47
 * 描述：会员消息
 * ===============Txunda===============
 */

class UserMessage {
    private String url = Config.BASE_URL + "UserMessage/";

    /**
     * 消息中心
     *
     * @param account_json 最近联系人账号
     * @param p            分页
     * @param baseView     回调
     */
    void newMsg(String account_json, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("account_json", account_json);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "newMsg", params, baseView);
    }

    /**
     * 通知列表
     *
     * @param p        分页
     * @param baseView 回调
     */
    void msgList(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "msgList", params, baseView);
    }

    /**
     * 订单列表
     *
     * @param p        分页
     * @param baseView 回调
     */
    void orderMsgList(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "orderMsgList", params, baseView);
    }

    /**
     * 公告列表
     *
     * @param p        分页
     * @param baseView 回调
     */
    void announceList(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "announceList", params, baseView);
    }

    /**
     * 获取公告内容
     *
     * @param id       id
     * @param baseView 回调
     */
    void announceInfo(String id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("id", id);
        apiTool2.postApi(url + "announceInfo", params, baseView);
    }

}
