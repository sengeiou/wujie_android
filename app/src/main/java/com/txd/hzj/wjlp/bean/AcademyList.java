package com.txd.hzj.wjlp.bean;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/21 0021
 * 时间：10:57
 * 描述：无界书院
 * ===============Txunda===============
 */

public class AcademyList {
    /**
     * academy_id : 文章id
     * title : 文章标题
     * logo : 文章封面图
     * page_views : 浏览量
     * collect_num : 收藏数
     */

    private String academy_id;
    private String title;
    private String logo;
    private String page_views;
    private String collect_num;

    private boolean select = false;

    /**
     * 收藏编号
     */
    private String collect_id;
    /**
     * 文章id
     */
    private String aid;

    public String getAcademy_id() {
        return academy_id;
    }

    public void setAcademy_id(String academy_id) {
        this.academy_id = academy_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPage_views() {
        return page_views;
    }

    public void setPage_views(String page_views) {
        this.page_views = page_views;
    }

    public String getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(String collect_num) {
        this.collect_num = collect_num;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(String collect_id) {
        this.collect_id = collect_id;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    @Override
    public String toString() {
        return "AcademyList{" +
                "academy_id='" + academy_id + '\'' +
                ", title='" + title + '\'' +
                ", logo='" + logo + '\'' +
                ", page_views='" + page_views + '\'' +
                ", collect_num='" + collect_num + '\'' +
                ", select=" + select +
                ", collect_id='" + collect_id + '\'' +
                ", aid='" + aid + '\'' +
                '}';
    }
}
