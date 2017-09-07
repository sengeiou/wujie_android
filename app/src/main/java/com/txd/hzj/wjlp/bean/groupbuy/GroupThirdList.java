package com.txd.hzj.wjlp.bean.groupbuy;

import com.txd.hzj.wjlp.bean.AllGoodsBean;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/7 0007
 * 时间：17:01
 * 描述：
 * ===============Txunda===============
 */

public class GroupThirdList {


    /**
     * code : 1
     * message : 获取成功
     * data : {"three_cate_list":[{"three_cate_id":0,"short_name":"全部","name":"全部"},{"three_cate_id":"三级分类id",
     * "short_name":"分类简称","name":"分类名称"}],"group_buy_list":[{"group_buy_id":"团购ID","group_price":"团购价",
     * "group_num":"团购所需人数","total":"已被团数量","integral":"积分","goods_name":"商品名称","goods_img":"商品图片g",
     * "country_id":"国家ID","ticket_buy_id":"抵扣券id","country_logo":"国家图片","ticket_buy_discount":"折扣率",
     * "append_person":[{"log_id":"团编号","user_id":"开团者id","head_pic":"开团者头像"}]}]}
     * nums : 商品数量
     */

    private String code;
    private String message;
    private Data data;
    private int nums;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public static class Data {
        private List<ThreeCateListBean> three_cate_list;
        private List<AllGoodsBean> group_buy_list;

        public List<ThreeCateListBean> getThree_cate_list() {
            return three_cate_list;
        }

        public void setThree_cate_list(List<ThreeCateListBean> three_cate_list) {
            this.three_cate_list = three_cate_list;
        }

        public List<AllGoodsBean> getGroup_buy_list() {
            return group_buy_list;
        }

        public void setGroup_buy_list(List<AllGoodsBean> group_buy_list) {
            this.group_buy_list = group_buy_list;
        }

        public static class ThreeCateListBean {
            /**
             * three_cate_id : 0
             * short_name : 全部
             * name : 全部
             */

            private int three_cate_id;
            private String short_name;
            private String name;

            public int getThree_cate_id() {
                return three_cate_id;
            }

            public void setThree_cate_id(int three_cate_id) {
                this.three_cate_id = three_cate_id;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
