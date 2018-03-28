package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 开发者： Txd_WangJJ
 * 创建时间： 2018/3/28 028 17:06:24.
 * 功能描述： 售后
 * 联系方式： jingjie.office@qq.com
 */
public class AfterSale {

    private static String url = Config.BASE_URL + "AfterSale/";

    /**
     * 售后类型及货物状态
     * @param baseView
     * @param order_goods_id 订单物品Id
     */
    public static void backApplyType(BaseView baseView, String order_goods_id){
        RequestParams params = new RequestParams();
        params.addBodyParameter("order_goods_id", order_goods_id);
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "backApplyType", params, baseView);
    }

}
