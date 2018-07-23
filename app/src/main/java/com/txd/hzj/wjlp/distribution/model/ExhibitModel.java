package com.txd.hzj.wjlp.distribution.model;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/16 8:39
 * 功能描述：分销Model
 * 联系方式：
 */
public class ExhibitModel {
    //分销的base_url
    public static final String DISTRIBUTION_URL="http://test2.wujiemall.com/Api/Distribution/";

    /**
     * 小店上货接口
     * @param p  页数
     * @param cate_id  小店点标id
     * @param name  排序字段名 积分：’red_return_integral’ 代金券：’discount’， 价格：’shop_price’, 销量：’new_sell_num
     * @param flag  正序（asc） 倒序 (desc)
     */
    public void postExhibitData(String p, String cate_id, String name,String flag,BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", p);
        params.addBodyParameter("cate_id", cate_id);
        params.addBodyParameter("name", name);
        params.addBodyParameter("flag", flag);
        apiTool2.postApi(DISTRIBUTION_URL+"goodsList", params, baseView);
    }

    /**
     *  顾客管理接口
     * @param id shop_id小店id
     * @param type 1：暂定为顾客信息查询
     */
    public void getShopPersonData(String  id, String type,BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addQueryStringParameter("id", id);
        params.addQueryStringParameter("type", type);
        apiTool2.getApi(DISTRIBUTION_URL+"orders", params, baseView);
    }


    public void postShopsData(String cate_id, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(DISTRIBUTION_URL+"shops", params, baseView);
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

        apiTool2.postApis(DISTRIBUTION_URL+"shops", params, baseView);
    }


}
