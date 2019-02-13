package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：首页面抓中记录对象
 */
public class HomeVictoryBean {

    /**
     * nickname : 无界新人168
     * goods_name : 娃娃
     * head_pic : http://test.wujiemall.com/Uploads/User/2018-12-25/5c2222a433ca0.gif
     */

    private String nickname; // 用户昵称
    private String goods_name; // 商品名称
    private String head_pic; // 头像

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    @Override
    public String toString() {
        return "HomeVictoryBean{" +
                "nickname='" + nickname + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", head_pic='" + head_pic + '\'' +
                '}';
    }
}
