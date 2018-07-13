package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.http.RequestParams;

public class MemberOrder {

    private static String url = Config.BASE_URL + "MemberOrder/";


    public static void settlement(String rank_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("member_coding", rank_id);
        apiTool2.postApi(url + "settlement", params, baseView);
    }

    public static void ticket(String rank_id, String number, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("member_coding", rank_id);
        params.addBodyParameter("number", number);
        apiTool2.postApi(url + "ticket", params, baseView);
    }

    public static void setOrder(String rank_id, String number, String discount_type, String pay_type,String order_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("member_coding", rank_id);
        params.addBodyParameter("number", number);
        params.addBodyParameter("discount_type", discount_type);
        params.addBodyParameter("pay_type", pay_type);
        params.addBodyParameter("order_id", order_id);
        apiTool2.postApi(url + "setOrder", params, baseView);

    }

    /**
     * 购买会员卡订单列表
     * @param pay_status    默认不传 0未支付 1已支付
     * @param p 	分页参数
     * @param baseView
     */
    public static void memberOrderList(String pay_status,String p,BaseView baseView){
        RequestParams params=new RequestParams();
        ApiTool2 apiTool2=new ApiTool2();
        params.addBodyParameter("pay_status",pay_status);
        params.addBodyParameter("p",p);
        apiTool2.postApi(url+"memberOrderList",params,baseView);
    }

    /**
     * 订单详情
     * @param order_id  订单ID
     * @param baseView
     */
    public static void memberOrderInfo(String order_id,BaseView baseView){
        RequestParams params=new RequestParams();
        ApiTool2 apiTool2=new ApiTool2();
        params.addBodyParameter("order_id",order_id);
        apiTool2.postApi(url+"memberOrderInfo",params,baseView);
    }

    /**
     * 删除会员卡订单
     * @param order_id  订单ID
     * @param order_status  必须传 5已取消 9 已删除
     * @param baseView
     */
    public static void delMemberOrder(String order_id,String order_status,BaseView baseView){
        RequestParams params=new RequestParams();
        ApiTool2 apiTool2=new ApiTool2();
        params.addBodyParameter("order_id",order_id);
        params.addBodyParameter("order_status",order_status);
        apiTool2.postApi(url+"delMemberOrder",params,baseView);
    }



}
