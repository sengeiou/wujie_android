package com.txd.hzj.wjlp.http.article;

import com.ants.theantsgo.base.BasePresenter;
import com.ants.theantsgo.base.BaseView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/18 0018
 * 时间：10:09
 * 描述：
 * ===============Txunda===============
 */

public class ArticlePst extends BasePresenter {
    private Article article;

    public ArticlePst(BaseView baseView) {
        super(baseView);
        article = new Article();
    }

    /**
     * App文章
     *
     * @param type 类型
     */
    public void getArticle(String type) {
        baseView.showDialog();
        article.getArticle(type, baseView);
    }

    /**
     * 关于我们
     */
    public void aboutUs() {
        baseView.showDialog();
        article.aboutUs(baseView);
    }

    /**
     * 意见反馈类型
     */
    public void feedbackType() {
        baseView.showDialog();
        article.feedbackType(baseView);
    }

    /**
     * 意见反馈
     */
    public void feedback(String f_type_id, String content) {
        baseView.showDialog();
        article.feedback(f_type_id, content, baseView);
    }

    /**
     * 帮助中心
     * @param type 类型
     */
    public void helpCenter(String type) {
        baseView.showDialog();
        article.helpCenter(type, baseView);
    }

}
