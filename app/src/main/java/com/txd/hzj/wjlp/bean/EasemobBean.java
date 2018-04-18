package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * 开发者： Txd_WangJJ
 * 创建时间： 2018/4/12 012 19:08:46.
 * 功能描述： 环信账号
 * 联系方式： jingjie.office@qq.com
 */
public class EasemobBean {

    /**
     * code : 1
     * message : 获取成功
     * data : {"easemob_account_num":2,"easemob_account":[{"hx":"151237607243994","nickname":"无界新人","head_pic":"","position":"职位","department_name":""}]}
     * nums : 0
     */
    private String code; // 获取成功失败
    private String message; // 显示信息
    private DataBean data; // 获取的数据
    private String nums; // 保留字段

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public static class DataBean {
        /**
         * easemob_account_num : 2
         * easemob_account : [{"hx":"151237607243994","nickname":"无界新人","head_pic":"","position":"职位","department_name":""}]
         */
        private int easemob_account_num; // 在线客服数量
        private List<EasemobAccountBean> easemob_account; // 客服联系人列表

        public int getEasemob_account_num() {
            return easemob_account_num;
        }

        public void setEasemob_account_num(int easemob_account_num) {
            this.easemob_account_num = easemob_account_num;
        }

        public List<EasemobAccountBean> getEasemob_account() {
            return easemob_account;
        }

        public void setEasemob_account(List<EasemobAccountBean> easemob_account) {
            this.easemob_account = easemob_account;
        }

        public static class EasemobAccountBean {
            /**
             * hx : 151237607243994
             * nickname : 无界新人
             * head_pic :
             * position : 职位
             * department_name :
             */
            private String hx; // 商家客服账号
            private String nickname; // 商家客服昵称
            private String head_pic; // 商家客服账号头像
            private String position; // 商家客服职位
            private String department_name; // 商家客服部门

            public String getHx() {
                return hx;
            }

            public void setHx(String hx) {
                this.hx = hx;
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

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getDepartment_name() {
                return department_name;
            }

            public void setDepartment_name(String department_name) {
                this.department_name = department_name;
            }
        }
    }
}
