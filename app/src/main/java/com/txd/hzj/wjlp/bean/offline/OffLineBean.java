package com.txd.hzj.wjlp.bean.offline;

import com.txd.hzj.wjlp.bean.commodity.DataBaseBean;

import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/7/23 14:42
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class OffLineBean extends DataBaseBean {
    private List<OffLineDataBean> data;
    private List<NumsBean> nums;
    private String tip_num;

    public String getTip_num() {
        return tip_num;
    }

    public void setTip_num(String tip_num) {
        this.tip_num = tip_num;
    }

    public List<OffLineDataBean> getData() {
        return data;
    }

    public void setData(List<OffLineDataBean> data) {
        this.data = data;
    }

    public List<NumsBean> getNums() {
        return nums;
    }

    public void setNums(List<NumsBean> nums) {
        this.nums = nums;
    }

    public static class NumsBean {

        /**
         * rec_type_id : 63
         * type : 其它
         * cate_img :
         */

        private String rec_type_id;
        private String type;
        private String cate_img;

        public String getRec_type_id() {
            return rec_type_id;
        }

        public void setRec_type_id(String rec_type_id) {
            this.rec_type_id = rec_type_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCate_img() {
            return cate_img;
        }

        public void setCate_img(String cate_img) {
            this.cate_img = cate_img;
        }
    }


}
