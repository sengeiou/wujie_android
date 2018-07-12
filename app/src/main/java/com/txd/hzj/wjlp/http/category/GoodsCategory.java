package com.txd.hzj.wjlp.http.category;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/1 0001
 * 时间：09:26
 * 描述：商品描述
 *
 */

public class GoodsCategory {
    // 分类页面
    private String url = Config.BASE_URL + "GoodsCategory/";

    void cateIndex(String cate_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(url + "cateIndex", params, baseView);
    }
    public static void cateIndexs(String cate_id, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(Config.BASE_URL + "GoodsCategory/" + "cateIndex", params, baseView);
    }

}
