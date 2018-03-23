package com.txd.hzj.wjlp.new_wjyp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
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

}
