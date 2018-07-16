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
    public static String url="http://test2.wujiemall.com/Api/Distribution/";
    //分销的base_url
    public static final String DISTRIBUTION_URL="http://test2.wujiemall.com/Api/Distribution/";
    public void postExhibitData(String p, String cate_id, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", p);
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(url+"goodsList", params, baseView);
    }
    public void postShopsData(String cate_id, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(url+"shops", params, baseView);
    }

    public void postShopsSetData(String id,String shop_name,String shop_pic,String shop_desc,String user_id,String set_id,String shop_status,String pay_money,String pay_orders,String visit_nums,String update_time, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("id", id);
        params.addBodyParameter("shop_name", shop_name);
        params.addBodyParameter("shop_pic", shop_pic);
        params.addBodyParameter("shop_desc", shop_desc);
        params.addBodyParameter("user_id", user_id);
        params.addBodyParameter("set_id", set_id);
        params.addBodyParameter("shop_status", shop_status);
        params.addBodyParameter("pay_money", pay_money);
        params.addBodyParameter("pay_orders", pay_orders);
        params.addBodyParameter("visit_nums", visit_nums);
        params.addBodyParameter("update_time", update_time);

        apiTool2.postApis(url+"shops", params, baseView);
    }


}
