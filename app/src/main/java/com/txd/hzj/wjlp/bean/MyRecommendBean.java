package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * 创建者：Wangjj
 * 创建时间：2018/5/3 003 15:05
 * 功能描述：我的推荐对象
 * 联系方式：jingjie.office@qq.com
 */
public class MyRecommendBean {

    /**
     * code : 1
     * message : 获取成功
     * data : [{"id":"6","nickname":"智慧人生","head_pic":"http://test.wujiemall.com/Uploads/Goods/2018-02-27/5a94d0385ea58.jpg","phone":"186****4806","create_time":"1523157208","enjoy_members_time":"0","member_coding":"3","grow_point":"0","time":"2018-04-08 11:13:28","umbrella_coding_one":"35","umbrella_coding_two":"0","umbrella_coding_three":"1","coding_one":"31","coding_two":"0","coding_three":"1","umbrella_icon":"","straight_icon":""},{"id":"7","nickname":"度安","head_pic":"","phone":"136****0177","create_time":"1523157208","enjoy_members_time":"1523160784","member_coding":"3","grow_point":"0","time":"2018-04-08 12:13:04","umbrella_coding_one":"0","umbrella_coding_two":"0","umbrella_coding_three":"0","coding_one":"0","coding_two":"0","coding_three":"0","umbrella_icon":"","straight_icon":""},{"id":"8","nickname":"陈震","head_pic":"","phone":"183****5689","create_time":"1523157208","enjoy_members_time":"0","member_coding":"3","grow_point":"0","time":"2018-04-08 11:13:28","umbrella_coding_one":"192","umbrella_coding_two":"0","umbrella_coding_three":"0","coding_one":"30","coding_two":"0","coding_three":"0","umbrella_icon":"","straight_icon":""},{"id":"9","nickname":"无界优品商城","head_pic":"","phone":"186****4712","create_time":"1523157208","enjoy_members_time":"0","member_coding":"3","grow_point":"0","time":"2018-04-08 11:13:28","umbrella_coding_one":"19","umbrella_coding_two":"0","umbrella_coding_three":"0","coding_one":"16","coding_two":"0","coding_three":"0","umbrella_icon":"","straight_icon":""},{"id":"10","nickname":"刘连娜","head_pic":"","phone":"176****9064","create_time":"1523157208","enjoy_members_time":"1523160784","member_coding":"3","grow_point":"0","time":"2018-04-08 12:13:04","umbrella_coding_one":"2","umbrella_coding_two":"0","umbrella_coding_three":"0","coding_one":"2","coding_two":"0","coding_three":"0","umbrella_icon":"","straight_icon":""},{"id":"11","nickname":"路过","head_pic":"http://test.wujiemall.com/Uploads/Goods/2016-12-30/58661b41b941c.jpg","phone":"139****7943","create_time":"1523157208","enjoy_members_time":"1523160784","member_coding":"3","grow_point":"0","time":"2018-04-08 12:13:04","umbrella_coding_one":"103","umbrella_coding_two":"0","umbrella_coding_three":"0","coding_one":"101","coding_two":"0","coding_three":"0","umbrella_icon":"","straight_icon":""},{"id":"12","nickname":"无界小杨","head_pic":"","phone":"156****7958","create_time":"1523157208","enjoy_members_time":"1523160784","member_coding":"3","grow_point":"0","time":"2018-04-08 12:13:04","umbrella_coding_one":"91","umbrella_coding_two":"0","umbrella_coding_three":"0","coding_one":"80","coding_two":"0","coding_three":"0","umbrella_icon":"","straight_icon":""},{"id":"13","nickname":"刘月恩","head_pic":"","phone":"156****3262","create_time":"1523157208","enjoy_members_time":"1523160784","member_coding":"3","grow_point":"0","time":"2018-04-08 12:13:04","umbrella_coding_one":"0","umbrella_coding_two":"0","umbrella_coding_three":"0","coding_one":"0","coding_two":"0","coding_three":"0","umbrella_icon":"","straight_icon":""},{"id":"14","nickname":"小耗子","head_pic":"http://test.wujiemall.com/Uploads/Goods/2018-02-24/5a91150d493b4.jpg","phone":"182****2610","create_time":"1523157208","enjoy_members_time":"1523160784","member_coding":"3","grow_point":"0","time":"2018-04-08 12:13:04","umbrella_coding_one":"4","umbrella_coding_two":"0","umbrella_coding_three":"0","coding_one":"2","coding_two":"0","coding_three":"0","umbrella_icon":"","straight_icon":""},{"id":"15","nickname":"王文强","head_pic":"","phone":"135****1279","create_time":"1523157208","enjoy_members_time":"1523160784","member_coding":"3","grow_point":"0","time":"2018-04-08 12:13:04","umbrella_coding_one":"1","umbrella_coding_two":"0","umbrella_coding_three":"0","coding_one":"1","coding_two":"0","coding_three":"0","umbrella_icon":"","straight_icon":""}]
     * nums : 89
     */

    private String code;
    private String message;
    private String nums;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6
         * nickname : 智慧人生
         * head_pic : http://test.wujiemall.com/Uploads/Goods/2018-02-27/5a94d0385ea58.jpg
         * "num":"89",
         * phone : 186****4806
         * create_time : 1523157208
         * enjoy_members_time : 0
         * member_coding : 3  1无界 2无忧 3优享
         * grow_point : 0
         * time : 2018-04-08 11:13:28
         * umbrella_coding_one : 35
         * umbrella_coding_two : 0
         * umbrella_coding_three : 1
         * coding_one : 31
         * coding_two : 0
         * coding_three : 1
         * umbrella_icon :
         * straight_icon :
         */

        private String id;
        private String nickname;
        private String head_pic;
        private String num;
        private String phone;
        private String create_time;
        private String enjoy_members_time;
        private String member_coding;
        private String grow_point;
        private String time;
        private String umbrella_coding_one;
        private String umbrella_coding_two;
        private String umbrella_coding_three;
        private String coding_one;
        private String coding_two;
        private String coding_three;
        private String umbrella_icon;
        private String straight_icon;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getEnjoy_members_time() {
            return enjoy_members_time;
        }

        public void setEnjoy_members_time(String enjoy_members_time) {
            this.enjoy_members_time = enjoy_members_time;
        }

        public String getMember_coding() {
            return member_coding;
        }

        public void setMember_coding(String member_coding) {
            this.member_coding = member_coding;
        }

        public String getGrow_point() {
            return grow_point;
        }

        public void setGrow_point(String grow_point) {
            this.grow_point = grow_point;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUmbrella_coding_one() {
            return umbrella_coding_one;
        }

        public void setUmbrella_coding_one(String umbrella_coding_one) {
            this.umbrella_coding_one = umbrella_coding_one;
        }

        public String getUmbrella_coding_two() {
            return umbrella_coding_two;
        }

        public void setUmbrella_coding_two(String umbrella_coding_two) {
            this.umbrella_coding_two = umbrella_coding_two;
        }

        public String getUmbrella_coding_three() {
            return umbrella_coding_three;
        }

        public void setUmbrella_coding_three(String umbrella_coding_three) {
            this.umbrella_coding_three = umbrella_coding_three;
        }

        public String getCoding_one() {
            return coding_one;
        }

        public void setCoding_one(String coding_one) {
            this.coding_one = coding_one;
        }

        public String getCoding_two() {
            return coding_two;
        }

        public void setCoding_two(String coding_two) {
            this.coding_two = coding_two;
        }

        public String getCoding_three() {
            return coding_three;
        }

        public void setCoding_three(String coding_three) {
            this.coding_three = coding_three;
        }

        public String getUmbrella_icon() {
            return umbrella_icon;
        }

        public void setUmbrella_icon(String umbrella_icon) {
            this.umbrella_icon = umbrella_icon;
        }

        public String getStraight_icon() {
            return straight_icon;
        }

        public void setStraight_icon(String straight_icon) {
            this.straight_icon = straight_icon;
        }
    }
}
