package com.txd.hzj.wjlp.new_wjyp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;


public class User {
    private static String url = Config.BASE_URL + "User/";

    /**
     * 我的积分
     *
     * @param baseView
     */
    public static void myIntegral(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "myIntegral", new RequestParams(), baseView);

    }

    /**
     *  个人中心
     * @param baseView
     */
    public static void userCenter(BaseView baseView){
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "userCenter", new RequestParams(), baseView);
    }
}
