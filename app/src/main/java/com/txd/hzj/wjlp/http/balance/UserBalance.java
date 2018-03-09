package com.txd.hzj.wjlp.http.balance;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/23 0023
 * 时间：13:24
 * 描述：余额模块
 * ===============Txunda===============
 */

class UserBalance {
    private String url = Config.BASE_URL + "UserBalance/";

    /**
     * 余额首页
     *
     * @param baseView 回调
     */
    void balanceIndex(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "balanceIndex", params, baseView);
    }

    /**
     * 线上充值
     *
     * @param money    金额
     * @param pay_type 1微信 2支付宝
     * @param note     备注
     * @param baseView 回调
     */
    void upMoney(String money, String pay_type, String note, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("money", money);
        params.addBodyParameter("pay_type", pay_type);
        params.addBodyParameter("note", note);
        apiTool2.postApi(url + "upMoney", params, baseView);
    }

    /**
     * 线下充值
     *
     * @param bank_card_id 汇款银行id
     * @param act_time     汇款时间戳
     * @param money        汇款金额
     * @param name         汇款人
     * @param pic          凭证
     * @param desc         汇款说明
     * @param pay_password 密码
     * @param baseView     回调
     */
    void underMoney(String bank_card_id, String act_time, String money, String name, File pic, String desc,
<<<<<<< HEAD
                    String pay_password, BaseView baseView) {
=======
                    String pay_password,String id ,BaseView baseView) {
>>>>>>> master
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("bank_card_id", bank_card_id);
        params.addBodyParameter("act_time", act_time);
        params.addBodyParameter("money", money);
        params.addBodyParameter("name", name);
        params.addBodyParameter("pic", pic);
        params.addBodyParameter("desc", desc);
        params.addBodyParameter("pay_password", pay_password);
<<<<<<< HEAD
=======
        params.addBodyParameter("platform_account_id", id);
>>>>>>> master
        apiTool2.postApi(url + "underMoney", params, baseView);
    }

    /**
     * 获取银行卡类型
     *
     * @param baseView 回调
     */
    void getBankType(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "getBankType", params, baseView);
    }

    /**
     * 添加银行卡
     *
     * @param name         持卡人姓名
     * @param bank_type_id 银行卡类型id
     * @param open_bank    开户行
     * @param card_number  银行卡号
     * @param phone        银行预留手机
     * @param baseView     回调
     */
    void addBank(String name, String bank_type_id, String open_bank, String card_number, String phone,
                 BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("bank_type_id", bank_type_id);
        params.addBodyParameter("open_bank", open_bank);
        params.addBodyParameter("card_number", card_number);
        params.addBodyParameter("name", name);
        params.addBodyParameter("phone", phone);
        apiTool2.postApi(url + "addBank", params, baseView);
    }

    /**
     * 获取银行卡列表
     *
     * @param baseView 回调
     */
    void bankList(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
<<<<<<< HEAD
        apiTool2.postApi(url + "bankList", params, baseView);
    }

=======
//        apiTool2.postApi(url + "bankList", params, baseView);
        apiTool2.postApi(url + "bankList", params, baseView);
    }

    void platformAccount(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
//        apiTool2.postApi(url + "bankList", params, baseView);
        apiTool2.postApi(url + "platformAccount", params, baseView);
    }
>>>>>>> master
    /**
     * 删除银行卡号
     *
     * @param bank_card_id 银行卡id
     * @param baseView     回调
     */
    void delBank(String bank_card_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("bank_card_id", bank_card_id);
        apiTool2.postApi(url + "delBank", params, baseView);
    }

    /**
     * 提现首页
     *
     * @param baseView 回调
     */
    void cashIndex(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        apiTool2.postApi(url + "cashIndex", params, baseView);
    }

    /**
     * 提现操作
     *
     * @param pay_password 支付密码
     * @param money        金额
     * @param rate         手续费
     * @param bank_card_id 银行卡号
     * @param baseView     回调
     */
    void getCash(String pay_password, String money, String rate, String bank_card_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("pay_password", pay_password);
        params.addBodyParameter("money", money);
        params.addBodyParameter("rate", rate);
        params.addBodyParameter("bank_card_id", bank_card_id);
        apiTool2.postApi(url + "getCash", params, baseView);
    }

    /**
     * 转账金额
     *
     * @param code         对方电话，或者会员id
     * @param money        转账金额
     * @param real_name    对方姓名
     * @param pay_password 支付密码
     * @param baseView     回调
     */
    void changeMoney(String code, String money, String real_name, String pay_password, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("code", code);
        params.addBodyParameter("money", money);
        params.addBodyParameter("real_name", real_name);
        params.addBodyParameter("pay_password", pay_password);
        apiTool2.postApi(url + "changeMoney", params, baseView);
    }

    /**
     * 余额明细
     *
     * @param p        分页
     * @param baseView 回调
     */
    void balanceLog(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "balanceLog", params, baseView);
    }

<<<<<<< HEAD
=======

    /**
     * 余额明细
     *
     * @param p        分页
     * @param baseView 回调
     */
    void underMoneys(int p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(url + "underMoneys", params, baseView);
    }

>>>>>>> master
    /**
     * 根据ID或者手机获取真实姓名
     *
     * @param code     会员id或者手机号
     * @param baseView 回调
     */
    void getUserName(String code, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("code", code);
        apiTool2.postApi(url + "getUserName", params, baseView);
    }

    /**
     * 线下充值详情
     *
     * @param act_id   线下充值2
     * @param baseView 回调
     */
    void getUnderInfo(String act_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("act_id", act_id);
        apiTool2.postApi(url + "getUnderInfo", params, baseView);
    }

}
