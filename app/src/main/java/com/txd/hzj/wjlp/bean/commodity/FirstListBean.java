package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:53
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class FirstListBean implements Serializable {
    private String first_list_name;
    private List<FirstListValBean> first_list_val;

    public String getFirst_list_name() {
        return first_list_name;
    }

    public void setFirst_list_name(String first_list_name) {
        this.first_list_name = first_list_name;
    }

    public List<FirstListValBean> getFirst_list_val() {
        return first_list_val;
    }

    public void setFirst_list_val(List<FirstListValBean> first_list_val) {
        this.first_list_val = first_list_val;
    }
}
