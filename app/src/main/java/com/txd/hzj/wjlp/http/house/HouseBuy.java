package com.txd.hzj.wjlp.http.house;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/19 0019
 * 时间：09:57
 * 描述：房产购
 *
 */

public class HouseBuy {

    private String url = Config.BASE_URL + "HouseBuy/";

    /**
     * 楼盘列表
     *
     * @param p             分页
     * @param lng           经度
     * @param lat           纬度
     * @param integral_sort 积分排序 1 正序 2倒序
     * @param distance_sort 距离排序 1 正序 2倒序
     * @param price_sort    价格 1 正序 2倒序
     * @param sort          综合排序(默认)
     * @param baseView      回调
     */
    void houseList(int p, String lng, String lat, String integral_sort, String distance_sort, String price_sort,
                   String sort, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("lng", lng);
        params.addBodyParameter("lat", lat);
        params.addBodyParameter("integral_sort", integral_sort);
        params.addBodyParameter("distance_sort", distance_sort);
        params.addBodyParameter("price_sort", price_sort);
        params.addBodyParameter("sort", sort);
        apiTool2.postApi(url + "houseList", params, baseView);
    }

    /**
     * 楼盘详情
     *
     * @param house_id 房产id
     * @param baseView 回调
     */
    void houseInfo(String house_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("house_id", house_id);
        apiTool2.postApi(url + "houseInfo", params, baseView);
    }

    /**
     * 户型列表页
     *
     * @param house_id 户型ID
     * @param p        分页
     * @param baseView 回调
     */
    void houseStyleList(String house_id, int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("house_id", house_id);
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "houseStyleList", params, baseView);
    }

    /**
     * 户型详情
     *
     * @param style_id 户型id
     * @param baseView 回调
     */
    void styleInfo(String style_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("style_id", style_id);
        apiTool2.postApi(url + "styleInfo", params, baseView);
    }

    /**
     * @param house_id
     * @param p
     * @param label_id
     * @param baseView
     */
    public static void commentList(String house_id, int p, String label_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("house_id", house_id);
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("label_id", label_id);
        apiTool2.postApi(Config.BASE_URL + "HouseBuy/commentList", params, baseView);
    }

}
