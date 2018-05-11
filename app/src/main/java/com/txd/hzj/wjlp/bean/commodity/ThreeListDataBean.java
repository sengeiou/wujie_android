package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:50
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */

public class ThreeListDataBean implements Serializable{
   List<ThreeCateBean> three_cate_list; //三级分类列表
    List<AllGoodsBean> group_buy_list;//团购列表

    public List<ThreeCateBean> getThree_cate_list() {
        return three_cate_list;
    }

    public void setThree_cate_list(List<ThreeCateBean> three_cate_list) {
        this.three_cate_list = three_cate_list;
    }

    public List<AllGoodsBean> getGroup_buy_list() {
        return group_buy_list;
    }

    public void setGroup_buy_list(List<AllGoodsBean> group_buy_list) {
        this.group_buy_list = group_buy_list;
    }
}
