package com.txd.hzj.wjlp.http.user;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/22 0022
 * 时间：14:58
 * 描述：会员模块
 * ===============Txunda===============
 */

public class User {

    private String url = Config.BASE_URL + "User/";

    /**
     * 获取个人资料
     *
     * @param baseView 回调
     */
    void userInfo(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "userInfo", params, baseView);
    }

    /**
     * 修改个人资料
     *
     * @param nickname    昵称
     * @param sex         性别2 女 1男
     * @param email       邮箱
     * @param province_id 省id
     * @param city_id     市id
     * @param area_id     区县id
     * @param street_id   街道
     * @param head_pic    头像
     * @param baseView    回调
     */
    void editInfo(String nickname, String sex, String email, String province_id, String city_id, String area_id,
                  String street_id, File head_pic, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("nickname", nickname);
        params.addBodyParameter("sex", sex);
        params.addBodyParameter("email", email);
        params.addBodyParameter("province_id", province_id);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("area_id", area_id);
        params.addBodyParameter("street_id", street_id);
        if (head_pic != null && head_pic.exists())
            params.addBodyParameter("head_pic", head_pic);
        apiTool2.postApi(url + "editInfo", params, baseView);
    }

    void addAuth(String real_name, String id_card_num, File id_card_pic){

    }

    /**
     * 个人设置
     *
     * @param baseView 回调
     */
    void setting(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "setting", params, baseView);
    }

}
