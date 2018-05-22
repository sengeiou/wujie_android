package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:41
 * 功能描述：团购
 * 联系方式：常用邮箱或电话
 */
public class GroupBean implements Serializable {

    private String id;//"团购ID",
    private String start_time;//"开始时间",
    private String end_time;//"截止时间",
    private String end_true_time;// "延迟后截止时间（若无延迟，此时间等于截止时间）", 2018-5-14  参团添加二次截止时间
    private String sys_time;//"系统参照时间",  2018-5-14  参团添加二次截止时间
    private String status;// "状态", //0待成团  1已成团 2未成团
    ////团购
    private String user_id;
    private HeadUserBean head_user;
    private String diff;//"参团信息" //"还差1人" ,"团已满"
    private String group_buy_id;// "组团ID",
    private String group_num;//"需参团人数",
    ///////////////////////体验拼单
    private String max_num;
    private String award_num;
    private String return_num;
    private List<String> memo;//活动说明
    private String is_member;//判断是否是团员(是团员(!"0")不能买东西)

    public String getIs_member() {
        return is_member;
    }

    public void setIs_member(String is_member) {
        this.is_member = is_member;
    }

    public String getAward_num() {
        return award_num;
    }

    public void setAward_num(String award_num) {
        this.award_num = award_num;
    }

    public String getReturn_num() {
        return return_num;
    }

    public void setReturn_num(String return_num) {
        this.return_num = return_num;
    }

    public List<String> getMemo() {
        return memo;
    }

    public void setMemo(List<String> memo) {
        this.memo = memo;
    }

    public String getMax_num() {
        return max_num;
    }

    public void setMax_num(String max_num) {
        this.max_num = max_num;
    }

    public String getEnd_true_time() {
        return end_true_time;
    }

    public void setEnd_true_time(String end_true_time) {
        this.end_true_time = end_true_time;
    }

    public String getSys_time() {
        return sys_time;
    }

    public void setSys_time(String sys_time) {
        this.sys_time = sys_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup_buy_id() {
        return group_buy_id;
    }

    public void setGroup_buy_id(String group_buy_id) {
        this.group_buy_id = group_buy_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public HeadUserBean getHead_user() {
        return head_user;
    }


    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public void setHead_user(HeadUserBean head_user) {
        this.head_user = head_user;
    }
}
