package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/9/6 0006
 * 时间：17:43
 * 描述：产品规格列表
 *
 */

public class GoodsCommonAttr {
    private String title;
    private List<list> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GoodsCommonAttr.list> getList() {
        return list;
    }

    public void setList(List<GoodsCommonAttr.list> list) {
        this.list = list;
    }

    public class list {

        /**
         * id : 规格编号
         * attr_name : 规格名称
         * attr_value : 规格值
         */

        private String id;
        private String attr_name;
        private String attr_value;
        private String specification;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAttr_name() {
            return attr_name;
        }

        public void setAttr_name(String attr_name) {
            this.attr_name = attr_name;
        }

        public String getAttr_value() {
            return attr_value;
        }

        public void setAttr_value(String attr_value) {
            this.attr_value = attr_value;
        }

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }
    }

}
