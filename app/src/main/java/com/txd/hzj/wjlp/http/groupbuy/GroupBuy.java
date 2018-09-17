package com.txd.hzj.wjlp.http.groupbuy;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/9/5 0005
 * 时间：11:12
 * 描述：拼团购
 */

public class GroupBuy {

    private String url = Config.BASE_URL + "GroupBuy/";

    /**
     * 拼团购首页
     *
     * @param p        分页
     * @param cate_id  分类id
     * @param baseView 回调
     *                 <p>
     *                 http://wjapi.wujiemall.com/index.php/Function/index/
     *                 p_id/71
     *                 /mo_id/913
     *                 /f_id/5093
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
    void groupBuyInfo(String group_buy_id, int page, String a_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("group_buy_id", group_buy_id);
        params.addBodyParameter("p", String.valueOf(page));
        if (!android.text.TextUtils.isEmpty(a_id)) {
            params.addBodyParameter("a_id", a_id);
        }
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
        params.addBodyParameter("log_id", log_id);
        apiTool2.postApi(url + "goGroup", params, baseView);
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
     * 进店逛逛拼单购专用
     *
     * @param merchant_id
     * @param p
     * @param baseView
     */
    void merchantGroupBuyList(String merchant_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("merchant_id", merchant_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "merchantGroupBuyList", params, baseView);
    }

    /**
     * 拼单购属性api
     *
     * @param goods_id
     * @param product_id
     * @param group_type 拼单类型(1:试验品拼单 2：普通拼单）
     * @param baseView
     */
    public void attrApi(String goods_id, String product_id, int group_type, BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        requestParams.addBodyParameter("goods_id", goods_id);
        if (!android.text.TextUtils.isEmpty(product_id)) {
            requestParams.addBodyParameter("product_id", product_id);
        }
        requestParams.addBodyParameter("group_type", String.valueOf(group_type));
        apiTool2.postApi(url + "attrApi", requestParams, baseView);
    }


    /**
     * 拼单购规则显示
     */
    public void changeShowStatus(BaseView baseView) {
        RequestParams requestParams = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "changeShowStatus", requestParams, baseView);
    }

}
