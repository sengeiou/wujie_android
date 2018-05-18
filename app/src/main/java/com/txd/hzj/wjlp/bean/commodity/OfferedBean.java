package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/15 9:22
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class OfferedBean implements Serializable {
    private String goods_name;//美式球形爆米花 香甜酥脆到爆 看片好伙伴 休闲零食小吃",//商品名称
    private String goods_img;//http://wjyp.txunda.com/Uploads/Goods/2017-12-01/5a20b9c3370d7.jpg",//封面图
    private String shop_price;//26.60",//价格
    private String already;//已团1件",//已团几件
    private String number;//1人团",//几人团
    private String start_time;//开始时间",
    private String end_time;//截止时间",
    private String end_true_time;//延迟后截止时间（若无延迟，此时间等于截止时间）",
    private String sys_time;//系统参照时间",
    private String colonel_head_pic;//http://www.tocolor.cn/Uploads/Goods/2016-12-01/583f0aa6dbe8f.jpg",//团长头像
    private String status;//1", //0团未满 1团已满
    private String m_short;//还差1人",//还差几人
    private List<HeadPicBean> head_pic;
    private String is_colonel;//1是团长0不是团长
    private String is_member;// 1是团员 0不是团员
    private List<OfferedOfferBean> offered;//"参团说明"
    private List<GroupBuyIdsBean> group_buy_ids;//商品团购订单表（相同商品的不同制品）
    private String group_type;//类型 1试用品拼单 2常规拼单",
    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getAlready() {
        return already;
    }

    public void setAlready(String already) {
        this.already = already;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getEnd_true_time() {
        return end_true_time;
    }

    public void setEnd_true_time(String end_true_time) {
        this.end_true_time = end_true_time;
    }

    public String getSys_time() {
        return sys_time;
    }

    public void setSys_time(String sys_time) {
        this.sys_time = sys_time;
    }

    public String getColonel_head_pic() {
        return colonel_head_pic;
    }

    public void setColonel_head_pic(String colonel_head_pic) {
        this.colonel_head_pic = colonel_head_pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getM_short() {
        return m_short;
    }

    public void setM_short(String m_short) {
        this.m_short = m_short;
    }

    public List<HeadPicBean> getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(List<HeadPicBean> head_pic) {
        this.head_pic = head_pic;
    }

    public String getIs_colonel() {
        return is_colonel;
    }

    public void setIs_colonel(String is_colonel) {
        this.is_colonel = is_colonel;
    }

    public String getIs_member() {
        return is_member;
    }

    public void setIs_member(String is_member) {
        this.is_member = is_member;
    }

    public List<OfferedOfferBean> getOffered() {
        return offered;
    }

    public void setOffered(List<OfferedOfferBean> offered) {
        this.offered = offered;
    }

    public List<GroupBuyIdsBean> getGroup_buy_ids() {
        return group_buy_ids;
    }

    public void setGroup_buy_ids(List<GroupBuyIdsBean> group_buy_ids) {
        this.group_buy_ids = group_buy_ids;
    }

    public String getGroup_type() {
        return group_type;
    }

    public void setGroup_type(String group_type) {
        this.group_type = group_type;
    }
}
