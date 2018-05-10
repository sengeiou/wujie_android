package com.txd.hzj.wjlp.bean.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/10 15:55
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class CommentBean implements Serializable {
    /**
     * body : {"comment_id":"9","goods_id":"12","goods_name":"香脆小麻花 50根/100g根/200根多规格可选 就是酥脆好吃","user_id":"27","nickname":"GYM","pictures":[{"path":"http://wjyp.txunda.com/Uploads/Comment/2017-12-09/5a2b7055d2116.png"}],"content":"该用户未做出任何评论","all_star":"4","product_id":"","order_goods_id":"","create_time":"1512796245","user_head_pic":"http://wjyp.txunda.com/Uploads/User/2017-12-04/5a24b494e3ac8.png","good_attr":"","goods_num":"","shop_price":"","goods_img":"http://wjyp.txunda.com/Uploads/Goods/2017-12-01/5a20b882f1e70.jpg","order_type":""}
     * total : 1
     */
    private BodyBean body;
    private String total;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
