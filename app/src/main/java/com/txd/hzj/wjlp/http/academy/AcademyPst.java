package com.txd.hzj.wjlp.http.academy;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/8/21 0021
 * 时间：09:29
 * 描述：
 */

public class AcademyPst extends BasePresenter {
    private Academy academy;

    public AcademyPst(BaseView baseView) {
        super(baseView);
        academy = new Academy();
    }

    public void academyIndex(int p, String ac_type_id) {
        baseView.showDialog();
        academy.academyIndex(p, ac_type_id, baseView);
    }

    /**
     * 文章详情
     *
     * @param academy_id id
     */
    public void academyInfo(String academy_id) {
        baseView.showDialog();
        academy.academyInfo(academy_id, baseView);
    }

}
