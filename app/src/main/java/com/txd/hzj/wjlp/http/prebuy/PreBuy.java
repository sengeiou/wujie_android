package com.txd.hzj.wjlp.http.prebuy;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/8 0008
 * 时间：10:46
<<<<<<< HEAD
 * 描述：无界预购
=======
 * 描述：xfte预购
>>>>>>> master
 * ===============Txunda===============
 */
class PreBuy {
    private String url = Config.BASE_URL + "PreBuy/";

    /**
<<<<<<< HEAD
     * 无界预购首页
=======
     * xfte预购首页
>>>>>>> master
     *
     * @param p        分页
     * @param cate_id  分类id
     * @param baseView 回调
     */
    void preBuyIndex(int p, String cate_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(url + "preBuyIndex", params, baseView);
    }

    /**
     * 三级分类商品列表
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
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("three_cate_id", three_cate_id);
        apiTool2.postApi(url + "threeList", params, baseView);
    }

    /**
<<<<<<< HEAD
     * 无界预购详情
=======
     * xfte预购详情
>>>>>>> master
     * @param pre_buy_id    预购id
     * @param baseView  回调
     */
    void preBuyInfo(String pre_buy_id, int page,BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("pre_buy_id", pre_buy_id);
        params.addBodyParameter("p", String.valueOf(page));
        apiTool2.postApi(url + "preBuyInfo", params, baseView);
    }

}
