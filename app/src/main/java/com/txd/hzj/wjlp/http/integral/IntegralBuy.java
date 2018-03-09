package com.txd.hzj.wjlp.http.integral;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/9 0009
 * 时间：17:18
 * 描述：
 * ===============Txunda===============
 */

public class IntegralBuy {

    private String url = Config.BASE_URL + "IntegralBuy/";

    /**
<<<<<<< HEAD
     * 无界商店首页
=======
     * xfte商店首页
>>>>>>> master
     *
     * @param p
     * @param cate_id
     * @param baseView
     */
    void integralBuyIndex(int p, String cate_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(url + "integralBuyIndex", params, baseView);
    }

    /**
<<<<<<< HEAD
     * 无界商店详情
=======
     * xfte商店详情
>>>>>>> master
     *
     * @param integral_buy_id 商店商品编号
     * @param baseView        回调
     */
    void integralBuyInfo(String integral_buy_id, int page,BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("integral_buy_id", integral_buy_id);
        params.addBodyParameter("p", String.valueOf(page));
        apiTool2.postApi(url + "integralBuyInfo", params, baseView);
    }

    /**
     * 三积分类商品
     *
     * @param two_cate_id   二级
     * @param three_cate_id 三级
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
