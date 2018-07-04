package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/7/4 17:12
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class AttrDataBean implements Serializable {

    private String goods_img;   // 商品图
    private String shop_price;   // 商品价格
    private String goods_num;   // 库存
    private String is_attr;    //是否有商品属性 0->没有   1->有
    private List<FirstListBean> first_list;
    private List<FirstValBean> first_val;

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

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getIs_attr() {
        return is_attr;
    }

    public void setIs_attr(String is_attr) {
        this.is_attr = is_attr;
    }

    public List<FirstListBean> getFirst_list() {
        return first_list;
    }

    public void setFirst_list(List<FirstListBean> first_list) {
        this.first_list = first_list;
    }

    public List<FirstValBean> getFirst_val() {
        return first_val;
    }

    public void setFirst_val(List<FirstValBean> first_val) {
        this.first_val = first_val;
    }
}
