package com.txd.hzj.wjlp.http.balance;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.tools.RegexUtils;

import java.io.File;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/23 0023
 * 时间：13:24
 * 描述：用户余额模块
 * ===============Txunda===============
 */

public class BalancePst extends BasePresenter {

    private UserBalance userBalance;

    public BalancePst(BaseView baseView) {
        super(baseView);
        userBalance = new UserBalance();
    }

    // 余额首页
    public void balanceIndex() {
        baseView.showDialog();
        userBalance.balanceIndex(baseView);
    }

    // 线上充值
    public void upMoney(String order_id, String money, String pay_type, String note) {
        baseView.showDialog();
        userBalance.upMoney(order_id, money, pay_type, note, baseView);
    }

    // 线下充值
    public void underMoney(String bank_card_id, String act_time, String money, String name, File pic, String desc,
                           String pay_password,String id) {

        if (bank_card_id.equals("")) {
            baseView.onErrorTip("请选择银行");
            return;
        }
        if (act_time.equals("")) {
            baseView.onErrorTip("请选择汇款时间");
            return;
        }
        if (money.equals("") || money.equals("0") || money.equals("0.0") || money.equals("0.00")) {
            baseView.onErrorTip("请输入汇款金额");
            return;
        }
        if (name.equals("")) {
            baseView.onErrorTip("请输入汇款人");
            return;
        }
        if (pic == null || !pic.exists()) {
            baseView.onErrorTip("请上传凭证");
            return;
        }
        if (pay_password.equals("")) {
            baseView.onErrorTip("请输入密码");
            return;
        }

        baseView.showDialog();
        userBalance.underMoney(bank_card_id, act_time, money, name, pic, desc, pay_password,id, baseView);

    }

    // 获取银行卡类型
    public void getBankType() {
        baseView.showDialog();
        userBalance.getBankType(baseView);
    }

    // 添加银行卡类型
    public void addBank(String name, String bank_type_id, String open_bank, String card_number, String phone) {


        if (bank_type_id.equals("")) {
            baseView.onErrorTip("请选择卡类型");
            return;
        }

        if (open_bank.equals("")) {
            baseView.onErrorTip("请输入开户行");
            return;
        }
        if (card_number.equals("") || card_number.length() < 16) {
            baseView.onErrorTip("请检查卡号");
            return;
        }
        if (!RegexUtils.checkMobile(phone)) {
            baseView.onErrorTip("请核对手机号");
            return;
        }
        baseView.showDialog();
        userBalance.addBank(name, bank_type_id, open_bank, card_number, phone, baseView);
    }

    // 银行卡列表
    public void bankList() {
        baseView.showDialog();
        userBalance.bankList(baseView);
    }
    public void platformAccount() {
        baseView.showDialog();
        userBalance.platformAccount(baseView);
    }

    // 删除银行卡
    public void delBank(String bank_card_id) {
        baseView.showDialog();
        userBalance.delBank(bank_card_id, baseView);
    }

    // 提现首页
    public void cashIndex() {
        baseView.showDialog();
        userBalance.cashIndex(baseView);
    }

    // 提现
    public void getCash(String pay_password, String money, String rate, String bank_card_id) {
        baseView.showDialog();
        userBalance.getCash(pay_password, money, rate, bank_card_id, baseView);
    }

    // 转账金额
    public void changeMoney(String code, String money, String real_name, String pay_password) {
        baseView.showDialog();
        userBalance.changeMoney(code, money, real_name, pay_password, baseView);
    }

    // 余额明细
    public void balanceLog(int p) {
        baseView.showDialog();
        userBalance.balanceLog(p, baseView);
    }
    // 线下充值明细列表
    public void underMoneys(int p) {
        baseView.showDialog();
        userBalance.underMoneys(p, baseView);
    }
    // 根据ID或者手机获取真实姓名
    public void getUserName(String code) {
        baseView.showDialog();
        userBalance.getUserName(code, baseView);
    }

    // 线下充值详情
    public void getUnderInfo(String act_id) {
        baseView.showDialog();
        userBalance.getUnderInfo(act_id, baseView);
    }

}
