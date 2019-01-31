package com.txd.hzj.wjlp.catchDoll.bean;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：银两变动记录对象
 */
public class MoneyRecordingBean {

    private String content; // 签到抽奖或抓娃娃消费
    private long time; // 记录时间
    private float price; // 变动金额

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MoneyRecordingBean{" +
                "content='" + content + '\'' +
                ", time=" + time +
                ", price=" + price +
                '}';
    }
}
