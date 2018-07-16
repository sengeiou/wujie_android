package com.txd.hzj.wjlp.distribution.model;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/16 8:39
 * 功能描述：小店上货Model
 * 联系方式：
 */
public class ExhibitModel {
    //分销的base_url
    public static final String DISTRIBUTION_URL="http://test2.wujiemall.com/Api/Distribution/";
    public void postExhibitData(String p, String cate_id, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", p);
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(DISTRIBUTION_URL+"goodsList", params, baseView);
    }
}
