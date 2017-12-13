package com.txd.hzj.wjlp.http.carbuy;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/18 0018
 * 时间：10:46
 * 描述：汽车购
 * ===============Txunda===============
 */

public class CarBuy {

    private String url = Config.BASE_URL + "CarBuy/";

    /**
     * 汽车筛选页
     *
     * @param baseView 回调
     */
    void carSelect(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "carSelect", params, baseView);
    }

    /**
     * 汽车列表页
     *
     * @param min_price 最小售价
     * @param max_price 最高售价
     * @param p         分页
     * @param style_id  车型id
     * @param brand_id  品牌id
     * @param baseView  回调
     */
    void carList(String min_price, String max_price, int p, String style_id, String brand_id, BaseView baseView, String lng, String lat) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("min_price", min_price);
        params.addBodyParameter("max_price", max_price);
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("style_id", style_id);
        params.addBodyParameter("brand_id", brand_id);
        params.addBodyParameter("lng", lng);
        params.addBodyParameter("lat", lat);
        apiTool2.postApi(url + "carList", params, baseView);
    }

    /**
     * 汽车详情
     *
     * @param car_id   id
     * @param baseView 回调
     */
    void carInfo(String car_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("car_id", car_id);
        apiTool2.postApi(url + "carInfo", params, baseView);
    }

    /**
     * 评价列表【/CarBuy/commentList】
     *
     * @param car_id
     * @param label_id
     * @param p
     * @param baseView
     */
    public static void commentList(String car_id, String label_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("car_id", car_id);
        params.addBodyParameter("label_id", label_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(Config.BASE_URL + "CarBuy/commentList", params, baseView);

    }

}
