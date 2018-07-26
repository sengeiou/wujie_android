package com.txd.hzj.wjlp.bean;

import java.io.File;
import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/20 0020
 * 时间：10:08
 * 描述：商品评价
 */

public class GoodsEvaluation {
    /**
     * 商品图片
     */
    private String goodsPic;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 单件商品评分
     */
    private String goodsGrade;
    /**
     * 商品评价内容
     */
    private String goodsContent;
    /**
     * 上传的图片
     */
    private List<File> goodsPicByBuyer;

    public GoodsEvaluation(String goodsPic, String goodsId, String goodsGrade, String goodsContent, List<File>
            goodsPicByBuyer) {
        this.goodsPic = goodsPic;
        this.goodsId = goodsId;
        this.goodsGrade = goodsGrade;
        this.goodsContent = goodsContent;
        this.goodsPicByBuyer = goodsPicByBuyer;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsGrade() {
        return goodsGrade;
    }

    public void setGoodsGrade(String goodsGrade) {
        this.goodsGrade = goodsGrade;
    }

    public String getGoodsContent() {
        return goodsContent;
    }

    public void setGoodsContent(String goodsContent) {
        this.goodsContent = goodsContent;
    }

    public List<File> getGoodsPicByBuyer() {
        return goodsPicByBuyer;
    }

    public void setGoodsPicByBuyer(List<File> goodsPicByBuyer) {
        this.goodsPicByBuyer = goodsPicByBuyer;
    }

    @Override
    public String toString() {
        return "GoodsEvaluation{" +
                "goodsPic='" + goodsPic + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsGrade='" + goodsGrade + '\'' +
                ", goodsContent='" + goodsContent + '\'' +
                ", goodsPicByBuyer=" + goodsPicByBuyer +
                '}';
    }
}
