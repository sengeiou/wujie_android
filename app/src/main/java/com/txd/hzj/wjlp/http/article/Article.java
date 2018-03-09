package com.txd.hzj.wjlp.http.article;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/18 0018
 * 时间：09:53
 * 描述：文章模块
 * ===============Txunda===============
 */

public class Article {
    private String url = Config.BASE_URL + "Article/";

    /**
     * App文章
     *
     * @param type     类型
     * @param baseView 回调
     */
    void getArticle(String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("type", type);
        apiTool2.postApi(url + "getArticle", params, baseView);
    }

    /**
     * 关于我们
     *
     * @param baseView 回调
     */
    void aboutUs(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "aboutUs", params, baseView);
    }

    /**
     * 意见反馈类型
     *
     * @param baseView 回调
     */
    void feedbackType(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "feedbackType", params, baseView);
    }

    /**
     * 意见反馈
     * @param f_type_id 意见反馈类型id
     * @param content   意见反馈内容
     * @param baseView  回调
     */
    void feedback(String f_type_id, String content, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("f_type_id", f_type_id);
        params.addBodyParameter("content", content);
        apiTool2.postApi(url + "feedback", params, baseView);
    }

    /**
     * 帮助中心
     *
     * @param type     类型 1商家篇，2用户篇，3运营中心篇
     * @param baseView 回调
     */
    void helpCenter(String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("type", type);
        apiTool2.postApi(url + "helpCenter", params, baseView);
    }

}
