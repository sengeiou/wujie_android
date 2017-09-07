package com.txd.hzj.wjlp.bean.groupbuy;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/7 0007
 * 时间：11:04
 * 描述：参团页
 * ===============Txunda===============
 */

public class GroupPager {


    /**
     * code : 1
     * message : 获取成功
     * data : {"info":{"log_id":"开团id","user_id":"团长id","group_num":"团购所需人数","group_price":"团购价","total":"已经被团的数量",
     * "goods_id":"商品id","goods_img":"商品图片","goods_name":"商品名称"},"person":[{"head_pic":"头像","nickname":"昵称",
     * "is_first":"是否是团长"},{"head_pic":"头像","nickname":"昵称","is_first":"0"}],"diff":"还差3人","log_id":"1","rule":"拼团须知"}
     * nums : 0
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
        /**
         * info : {"log_id":"开团id","user_id":"团长id","group_num":"团购所需人数","group_price":"团购价","total":"已经被团的数量",
         * "goods_id":"商品id","goods_img":"商品图片","goods_name":"商品名称"}
         * person : [{"head_pic":"头像","nickname":"昵称","is_first":"是否是团长"},{"head_pic":"头像","nickname":"昵称",
         * "is_first":"0"}]
         * diff : 还差3人
         * log_id : 1
         * rule : 拼团须知
         */

        private GroupInfo info;
        private String diff;
        private String log_id;
        private String rule;
        private List<PersonBean> person;

        public GroupInfo getInfo() {
            return info;
        }

        public void setInfo(GroupInfo info) {
            this.info = info;
        }

        public String getDiff() {
            return diff;
        }

        public void setDiff(String diff) {
            this.diff = diff;
        }

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public List<PersonBean> getPerson() {
            return person;
        }

        public void setPerson(List<PersonBean> person) {
            this.person = person;
        }

        public static class GroupInfo {
            /**
             * log_id : 开团id
             * user_id : 团长id
             * group_num : 团购所需人数
             * group_price : 团购价
             * total : 已经被团的数量
             * goods_id : 商品id
             * goods_img : 商品图片
             * goods_name : 商品名称
             */

            private String log_id;
            private String user_id;
            private String group_num;
            private String group_price;
            private String total;
            private String goods_id;
            private String goods_img;
            private String goods_name;

            public String getLog_id() {
                return log_id;
            }

            public void setLog_id(String log_id) {
                this.log_id = log_id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getGroup_num() {
                return group_num;
            }

            public void setGroup_num(String group_num) {
                this.group_num = group_num;
            }

            public String getGroup_price() {
                return group_price;
            }

            public void setGroup_price(String group_price) {
                this.group_price = group_price;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            @Override
            public String toString() {
                return "GroupInfo{" +
                        "log_id='" + log_id + '\'' +
                        ", user_id='" + user_id + '\'' +
                        ", group_num='" + group_num + '\'' +
                        ", group_price='" + group_price + '\'' +
                        ", total='" + total + '\'' +
                        ", goods_id='" + goods_id + '\'' +
                        ", goods_img='" + goods_img + '\'' +
                        ", goods_name='" + goods_name + '\'' +
                        '}';
            }
        }

        public static class PersonBean {
            /**
             * head_pic : 头像
             * nickname : 昵称
             * is_first : 是否是团长
             */

            private String head_pic;
            private String nickname;
            private String is_first;

            public String getHead_pic() {
                return head_pic;
            }

            public void setHead_pic(String head_pic) {
                this.head_pic = head_pic;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getIs_first() {
                return is_first;
            }

            public void setIs_first(String is_first) {
                this.is_first = is_first;
            }

            @Override
            public String toString() {
                return "PersonBean{" +
                        "head_pic='" + head_pic + '\'' +
                        ", nickname='" + nickname + '\'' +
                        ", is_first='" + is_first + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Data{" +
                    "info=" + info +
                    ", diff='" + diff + '\'' +
                    ", log_id='" + log_id + '\'' +
                    ", rule='" + rule + '\'' +
                    ", person=" + person +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GroupPager{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", nums=" + nums +
                '}';
    }
}
