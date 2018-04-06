package com.txd.hzj.wjlp.new_wjyp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.http.RequestParams;

/**
 * 开发者： Txd_WangJJ
 * 创建时间： 2018/3/22 022 18:55:17.
 * 功能描述：会员余额模块
 * 联系方式： jingjie.office@qq.com
 */

public class UserBalance {

    private static String url = Config.BASE_URL + "UserBalance/";

    /**
     * 提现操作
     *
     * @param baseView
     * @param pay_password 支付密码
     * @param money        金额
     * @param rate         手续费率
     * @param bank_card_id 银行卡id
     */
    public static void getCash(BaseView baseView, String pay_password, String money, String rate, String bank_card_id) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("pay_password", pay_password);
        params.addBodyParameter("money", money);
        params.addBodyParameter("rate", rate);
        params.addBodyParameter("bank_card_id", bank_card_id);
        apiTool2.postApi(url + "getCash", params, baseView);

    }

    /**
     * 提现操作
     *
     * @param baseView
     */
    public static void cashIndex(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "cashIndex", params, baseView);
    }

    /**
     * 获取充值订单列表
     * @param type 订单类型
     * @param baseView
     */
    public static void userBalanceHjs(String type, BaseView baseView){
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("pay_status", type);
        apiTool2.postApi(url + "userBalanceHjs", params, baseView);
    }

    /**
     * 删除订单
     * @param order_id 订单id
     * @param order_status 删除类型 5取消 9删除
     * @param baseView
     */
    public static void delHjsInfo(String order_id, String order_status, BaseView baseView){
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("order_status", order_status);
        apiTool2.postApi(url + "delHjsInfo", params, baseView);
    }

}
