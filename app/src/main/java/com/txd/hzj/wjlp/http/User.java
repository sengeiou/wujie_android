package com.txd.hzj.wjlp.http;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;
import com.txd.hzj.wjlp.tool.TimeStampUtil;

import java.io.File;


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
     * 积分兑换余额
     *
     * @param baseView
     * @param integral 要兑换的积分
     */
    public static void changeIntegral(BaseView baseView, String integral) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("integral", integral);
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "changeIntegral", params, baseView);
    }

    /**
     * 验证支付密码
     *
     * @param baseView
     * @param payPwd   待验证的密码
     */
    public static void verificationPayPwd(BaseView baseView, String payPwd) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("PayPwd", payPwd);
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "verificationPayPwd", params, baseView);
    }

    /**
     * 个人中心
     *
     * @param baseView
     */
    public static void userCenter(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "userCenter", new RequestParams(), baseView);
    }

    /**
     * 积分自动兑换
     *
     * @param baseView
     * @param status   自动兑换状态 0关闭 1开启
     */
    public static void changeIntegralStatus(BaseView baseView, String status) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("status", status);
        apiTool2.postApi(url + "changeIntegralStatus", params, baseView);
    }

    /**
     * 获取个人资料
     *
     * @param baseView
     */
    public static void userInfo(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "userInfo", new RequestParams(), baseView);
    }

    /**
     * 获取积分兑换金额详细信息
     *
     * @param baseView
     * @param integral
     */
    public static void autoChange(BaseView baseView, String integral) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("integral", integral);
        apiTool2.postApi(url + "autoChange", params, baseView);
    }

    /**
     * 获取个人认证详情
     *
     * @param baseView
     */
    public static void personalAuthInfo(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "personalAuthInfo", new RequestParams(), baseView);
    }

    /**
     * 个人认证
     *
     * @param baseView
     * @param real_name          真实姓名
     * @param sex                性别
     * @param id_card_num        身份证号
     * @param id_card_start_time 身份证开始时间
     * @param id_card_end_time   身份证结束时间
     * @param auth_province_id   个人认证省ID
     * @param auth_city_id       个人认证市ID
     * @param auth_area_id       个人认证地区ID
     * @param auth_street_id     个人认证街道ID
     * @param positive_id_card   身份证正面照
     * @param back_id_card       身份证背面照
     */
    public static void personalAuth(BaseView baseView, String real_name, String sex, String id_card_num, String id_card_start_time, String id_card_end_time, String auth_province_id, String auth_city_id, String auth_area_id, String auth_street_id, File positive_id_card, File back_id_card) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("real_name", real_name);
        params.addBodyParameter("sex", sex);
        params.addBodyParameter("id_card_num", id_card_num);
        params.addBodyParameter("id_card_start_time", id_card_start_time);
        params.addBodyParameter("id_card_end_time", id_card_end_time);
        params.addBodyParameter("auth_province_id", auth_province_id);
        params.addBodyParameter("auth_city_id", auth_city_id);
        params.addBodyParameter("auth_area_id", auth_area_id);
        params.addBodyParameter("auth_street_id", auth_street_id);
        params.addBodyParameter("positive_id_card", positive_id_card);
        params.addBodyParameter("back_id_card", back_id_card);
        apiTool2.postApi(url + "personalAuth", params, baseView);
    }

    /**
     * 企业认证
     *
     * @param baseView
     * @param com_name              企业名称
     * @param comp_reg_num          注册号
     * @param comp_start_time       开始时间
     * @param comp_end_time         结束时间
     * @param comp_province_id      省id
     * @param comp_city_id          市id
     * @param comp_area_id          县id
     * @param comp_street_id        街道id
     * @param comp_business_license 营业执照
     */
    public static void compAuth(BaseView baseView, String com_name, String comp_reg_num, String comp_start_time, String comp_end_time, String comp_province_id, String comp_city_id, String comp_area_id, String comp_street_id, File comp_business_license) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("com_name", com_name);
        params.addBodyParameter("comp_reg_num", comp_reg_num);
        params.addBodyParameter("comp_start_time", comp_start_time);
        params.addBodyParameter("comp_start_time", comp_start_time);
        params.addBodyParameter("comp_end_time", comp_end_time);
        params.addBodyParameter("comp_province_id", comp_province_id);
        params.addBodyParameter("comp_city_id", comp_city_id);
        params.addBodyParameter("comp_area_id", comp_area_id);
        params.addBodyParameter("comp_street_id", comp_street_id);
        params.addBodyParameter("comp_business_license", comp_business_license);
        apiTool2.postApi(url + "compAuth", params, baseView);
    }

    /**
     * 绑定第三方
     *
     * @param openid
     * @param type
     * @param nickname
     * @param baseView
     */
    public static void bindOther(String openid, String type, String nickname, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("openid", openid);
        params.addBodyParameter("type", type);
        params.addBodyParameter("nickname", nickname);
        apiTool2.postApi(url + "bindOther", params, baseView);
    }

    /**
     * 解除绑定
     *
     * @param type
     * @param baseView
     */
    public static void removeBind(String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("type", type);
        apiTool2.postApi(url + "removeBind", params, baseView);
    }

    /**
     * 三方账户绑定
     *
     * @param baseView
     */
    public static void payeeBind(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "payee_bind", new RequestParams(), baseView);
    }

}
