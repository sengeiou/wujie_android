package com.txd.hzj.wjlp.http.user;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;
import com.txd.hzj.wjlp.shoppingCart.BuildOrderAty;

import java.io.File;
import java.util.List;

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
        //  params.addBodyParameter("sex", sex);
        params.addBodyParameter("email", email);
        params.addBodyParameter("province_id", province_id);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("area_id", area_id);
        params.addBodyParameter("street_id", street_id);
        if (head_pic != null && head_pic.exists())
            params.addBodyParameter("head_pic", head_pic);
        apiTool2.postApi(url + "editInfo", params, baseView);
    }

    /**
     * 实名认证
     *
     * @param real_name   真实姓名
     * @param id_card_num 省份证号
     * @param id_card_pic 身份证照
     * @param baseView    回调
     */
    void addAuth(String real_name, String id_card_num, File id_card_pic, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("real_name", real_name);
        params.addBodyParameter("id_card_num", id_card_num);
        params.addBodyParameter("id_card_pic", id_card_pic);
        apiTool2.postApi(url + "addAuth", params, baseView);
    }

    /**
     * 修改登录密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param rePassword  确认密码
     * @param baseView    回调
     */
    void changePassword(String oldPassword, String newPassword, String rePassword, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("oldPassword", oldPassword);
        params.addBodyParameter("newPassword", newPassword);
        params.addBodyParameter("rePassword", rePassword);
        apiTool2.postApi(url + "changePassword", params, baseView);
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

    /**
     * 获取认证信息
     *
     * @param baseView 回调
     */
    void getAuth(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "getAuth", params, baseView);
    }

    /**
     * 修改支付密码
     *
     * @param newPayPwd 新密码
     * @param rePayPwd  确认密码
     * @param oldPayPwd 旧密码
     * @param baseView  回调
     */
    void rePayPwd(String newPayPwd, String rePayPwd, String oldPayPwd, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("newPayPwd", newPayPwd);
        params.addBodyParameter("rePayPwd", rePayPwd);
        params.addBodyParameter("oldPayPwd", oldPayPwd);
        apiTool2.postApi(url + "rePayPwd", params, baseView);
    }

    /**
     * 换绑手机号
     *
     * @param newPhone 新手机
     * @param verify   验证码
     * @param baseView 回调
     */
    void changePhone(String newPhone, String verify, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("newPhone", newPhone);
        params.addBodyParameter("verify", verify);
        apiTool2.postApi(url + "changePhone", params, baseView);
    }

    /**
     * 我的评价
     *
     * @param p        分页
     * @param baseView 回调
     */
    void myCommentList(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "myCommentList", params, baseView);
    }

    /**
     * 足迹
     *
     * @param p        分页
     * @param type     类型
     * @param baseView 回调
     */
    void myfooter(int p, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("type", type);
        apiTool2.postApi(url + "myfooter", params, baseView);
    }

    /**
     * 购物券
     *
     * @param p        分页
     * @param baseView 回调
     */
    void vouchersList(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "vouchersList", params, baseView);
    }

    /**
     * 购物券明细
     *
     * @param p        分页
     * @param baseView 回调
     */
    void vouchersLog(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "vouchersLog", params, baseView);
    }

    /**
     * 设置登录密码
     *
     * @param newPassword 新密码
     * @param rePassword  确认密码
     * @param baseView    回调
     */
    void setPassword(String newPassword, String rePassword, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("newPassword", newPassword);
        params.addBodyParameter("rePassword", rePassword);
        apiTool2.postApi(url + "setPassword", params, baseView);
    }

    /**
     * 设置支付密码
     *
     * @param newPayPwd 新密码
     * @param rePayPwd  确认
     * @param baseView  回调
     */
    void setPayPwd(String newPayPwd, String rePayPwd, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("newPayPwd", newPayPwd);
        params.addBodyParameter("rePayPwd", rePayPwd);
        apiTool2.postApi(url + "setPayPwd", params, baseView);
    }

    /**
     * 个人中心
     *
     * @param baseView 回调
     */
    void userCenter(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "userCenter", params, baseView);
    }

    /**
     * 卡券包-优惠券
     *
     * @param baseView 回调
     */
    void myTicket(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "myTicket", params, baseView);
    }

    /**
     * 获取经营范围
     *
     * @param baseView 回调
     */
    void getRange(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "getRange", params, baseView);
    }

    /**
     * 会员推荐商户
     *
     * @param name             商户名称
     * @param range_id         经营范围
     * @param link_man         联系人
     * @param link_phone       联系电话
     * @param job              职位
     * @param tmail_url        天猫网站
     * @param jd_url           京东网站
     * @param other_url        其他网站
     * @param product_desc     产品描述
     * @param product_pic      产品图片
     * @param business_license 营业执照
     * @param other_license    其他证件照
     * @param baseView         回调
     */
    void merchantRefer(String name, String range_id, String link_man, String link_phone, String job, String tmail_url,
                       String jd_url, String other_url, String product_desc, List<File> product_pic,
                       File business_license, List<File> other_license, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("name", name);
        params.addBodyParameter("range_id", range_id);
        params.addBodyParameter("link_man", link_man);
        params.addBodyParameter("link_phone", link_phone);
        params.addBodyParameter("job", job);
        params.addBodyParameter("tmail_url", tmail_url);
        params.addBodyParameter("jd_url", jd_url);
        params.addBodyParameter("other_url", other_url);
        params.addBodyParameter("product_desc", product_desc);
        for (int i = 0; i < product_pic.size(); i++) {
            File file = product_pic.get(i);
            if (file != null && file.exists()) {
                params.addBodyParameter("product_pic[" + i + "]", file);
            }
        }
        if (business_license != null && business_license.exists())
            params.addBodyParameter("business_license[0]", business_license);
        for (int j = 0; j < other_license.size(); j++) {
            File file2 = other_license.get(j);
            if (file2 != null && file2.exists()) {
                params.addBodyParameter("other_license[" + j + "]", file2);
            }
        }
        apiTool2.postApi(url + "merchantRefer", params, baseView);
    }

    /**
     * 获取推荐商家列表
     *
     * @param baseView 回调
     */
    void referList(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "referList", params, baseView);
    }

    /**
     * 会员成长
     *
     * @param baseView 回调
     */
    void userDevelop(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "userDevelop", params, baseView);
    }

    /**
     * 会员成长值明细
     *
     * @param baseView 回调
     */
    void userDevelopLog(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "userDevelopLog", params, baseView);
    }

    /**
     * 增加成长值
     *
     * @param reason    原因
     * @param get_point 成长值
     * @param baseView  回调
     */
    void addPoint(String reason, String get_point, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("reason", reason);
        params.addBodyParameter("get_point", get_point);
        apiTool2.postApi(url + "addPoint", params, baseView);
    }

    /**
     * 会员等级
     *
     * @param baseView 回调
     */
    void userRank(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "userRank", params, baseView);
    }

    /**
     * 工作成绩
     *
     * @param baseView 回调
     */
    void gradeRank(int p, String city_id, String type, String city_name, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("type", type);
        params.addBodyParameter("city_name", city_name);
        apiTool2.postApi(url + "gradeRank", params, baseView);
    }

    /**
     * 我的分享
     *
     * @param p        分页
     * @param baseView 回调
     */
    void myShare(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "myShare", params, baseView);
    }

    /**
     * 我的推荐
     *
     * @param p        分页
     * @param baseView 回调
     */
    void myRecommend(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "myRecommend", params, baseView);
    }

    /**
     * 忘记支付密码
     *
     * @param phone     手机号
     * @param verify    验证码 验证码标识 type=re_pay_pwd
     * @param newPayPwd 新密码
     * @param rePayPwd  确认密码
     * @param baseView  回调
     */
    void resetPayPwd(String phone, String verify, String newPayPwd, String rePayPwd, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("verify", verify);
        params.addBodyParameter("newPayPwd", newPayPwd);
        params.addBodyParameter("rePayPwd", rePayPwd);
        apiTool2.postApi(url + "myRecommend", params, baseView);
    }

    /**
     * 积分说明
     *
     * @param baseView 回调
     */
    void integralLog(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "integralLog", params, baseView);
    }

    /**
     * 分享好友
     *
     * @param baseView 回调
     */
    void shareFriend(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "shareFriend", params, baseView);
    }

    /**
     * @param type       分享类型 1微信 2微博 3qq
     * @param content    分享内容
     * @param id_val     对应的id 商品传商品id 依次.... 个人中心的分享id我给你们
     * @param share_type 1 商品 2商家 3书院 4红包 5其他(个人中心)
     * @param share_url  分享链接
     * @param baseView   回调
     */
    void shareBack(String type, String content, String id_val, String share_type, String share_url, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("type", type);
        params.addBodyParameter("content", content);
        params.addBodyParameter("id_val", id_val);
        params.addBodyParameter("share_type", share_type);
        params.addBodyParameter("url", share_url);
        apiTool2.postApi(url + "shareBack", params, baseView);
    }

    /**
     * 删除足迹
     *
     * @param footer_ids 足迹id
     * @param baseView   回调
     */
    void delFooter(String footer_ids, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("footer_ids", footer_ids);
        apiTool2.postApi(url + "delFooter", params, baseView);
    }

    /**
     * 推荐商家详情
     *
     * @param refer_id 推荐商家id
     * @param baseView 回调
     */
    void referInfo(String refer_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("refer_id", refer_id);
        apiTool2.postApi(url + "referInfo", params, baseView);
    }

    /**
     * 绑定第三方
     *
     * @param openid
     * @param type
     * @param nickname
     * @param baseView
     */
    void bindOther(String openid, String type, String nickname, BaseView baseView) {
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
    void removeBind(String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("type", type);
        apiTool2.postApi(url + "removeBind", params, baseView);
    }

    /**
     * 会员卡列表
     *
     * @param view
     */

    public static void userCard(BaseView view) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(Config.BASE_URL + "User/userCard", params, view);
    }

    public static void verificationPayPwd(String PayPwd, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("PayPwd", PayPwd);
        apiTool2.postApi(Config.BASE_URL + "User/verificationPayPwd", params, baseView);
    }

}
