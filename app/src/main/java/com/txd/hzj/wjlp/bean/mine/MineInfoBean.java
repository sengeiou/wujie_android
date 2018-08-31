package com.txd.hzj.wjlp.bean.mine;

import java.io.Serializable;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/30 17:04
 * 功能描述：
 */
public class MineInfoBean implements Serializable{

    /**
     * stage_merchant_id : 39
     * wxpay_accounts : 213qweqweqe
     * alipay_accounts : 27336157@qq.com
     * default_account : 1
     * change_account_status : 2
     * merchant_name : 达令服饰
     * logo : http://www.wujiemall.com/Uploads/Merchant/2018-07-24/5b56ca1a10d09.jpg
     */

    private String stage_merchant_id;
    private String wxpay_accounts;
    private String alipay_accounts;
    private String default_account;
    private String change_account_status;
    private String merchant_name;
    private String logo;


    public MineInfoBean(String stage_merchant_id, String wxpay_accounts, String alipay_accounts, String default_account, String change_account_status, String merchant_name, String logo) {
        this.stage_merchant_id = stage_merchant_id;
        this.wxpay_accounts = wxpay_accounts;
        this.alipay_accounts = alipay_accounts;
        this.default_account = default_account;
        this.change_account_status = change_account_status;
        this.merchant_name = merchant_name;
        this.logo = logo;
    }

    public String getStage_merchant_id() {
        return stage_merchant_id;
    }

    public void setStage_merchant_id(String stage_merchant_id) {
        this.stage_merchant_id = stage_merchant_id;
    }

    public String getWxpay_accounts() {
        return wxpay_accounts;
    }

    public void setWxpay_accounts(String wxpay_accounts) {
        this.wxpay_accounts = wxpay_accounts;
    }

    public String getAlipay_accounts() {
        return alipay_accounts;
    }

    public void setAlipay_accounts(String alipay_accounts) {
        this.alipay_accounts = alipay_accounts;
    }

    public String getDefault_account() {
        return default_account;
    }

    public void setDefault_account(String default_account) {
        this.default_account = default_account;
    }

    public String getChange_account_status() {
        return change_account_status;
    }

    public void setChange_account_status(String change_account_status) {
        this.change_account_status = change_account_status;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
