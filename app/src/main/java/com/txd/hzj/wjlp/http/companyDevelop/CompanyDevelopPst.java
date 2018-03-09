package com.txd.hzj.wjlp.http.companyDevelop;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/20 0020
 * 时间：17:06
 * 描述：企业孵化
 * ===============Txunda===============
 */

public class CompanyDevelopPst extends BasePresenter {
    private CompanyDevelop companyDevelop;

    public CompanyDevelopPst(BaseView baseView) {
        super(baseView);
        companyDevelop = new CompanyDevelop();
    }

    // 企业列表
    public void companyList(int p, int type) {
        if (0 == type) {
            baseView.showDialog();
        }
        companyDevelop.companyList(p, baseView);
    }

    // 企业简介
    public void companyInfo(String company_id) {
        baseView.showDialog();
        companyDevelop.companyInfo(company_id, baseView);
    }
}
