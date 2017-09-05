package com.txd.hzj.wjlp.bean;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/4 0004
 * 时间：11:39
 * 描述：一元夺宝
 * ===============Txunda===============
 */

public class OneBuyBean {

    /**
     * code : 1
     * message : 获取成功
     * data : {"ads":[{"ads_id":"广告id","picture":"广告图片","desc":"图片描述","href":"图片链接"}],"news":[{"headlines_id":"头条id",
     * "title":"头条标题"}],"oneBuyList":[{"one_buy_id":"一元购ID","person_num":"需参与人数","add_num":"已参与人数","integral":"积分",
     * "goods_name":"商品名称","goods_img":"商品图片","country_id":"国家ID","ticket_buy_id":"抵扣券id","diff_num":"还剩是多少人数",
     * "country_logo":"国家logo"}],"rules":[{"title":"规则标题","content":"规则内容"}]}
     * nums : 1
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
        private List<Banner> ads;
        private List<NewsBean> news;
        private List<OneBuyListBean> oneBuyList;
        private List<RulesBean> rules;

        public List<Banner> getAds() {
            return ads;
        }

        public void setAds(List<Banner> ads) {
            this.ads = ads;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public List<OneBuyListBean> getOneBuyList() {
            return oneBuyList;
        }

        public void setOneBuyList(List<OneBuyListBean> oneBuyList) {
            this.oneBuyList = oneBuyList;
        }

        public List<RulesBean> getRules() {
            return rules;
        }

        public void setRules(List<RulesBean> rules) {
            this.rules = rules;
        }

        public static class Banner {
            /**
             * ads_id : 广告id
             * picture : 广告图片
             * desc : 图片描述
             * href : 图片链接
             */

            private String ads_id;
            private String picture;
            private String desc;
            private String href;

            public String getAds_id() {
                return ads_id;
            }

            public void setAds_id(String ads_id) {
                this.ads_id = ads_id;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            @Override
            public String toString() {
                return "Banner{" +
                        "ads_id='" + ads_id + '\'' +
                        ", picture='" + picture + '\'' +
                        ", desc='" + desc + '\'' +
                        ", href='" + href + '\'' +
                        '}';
            }
        }

        public static class NewsBean {
            /**
             * headlines_id : 头条id
             * title : 头条标题
             */

            private String headlines_id;
            private String title;

            public String getHeadlines_id() {
                return headlines_id;
            }

            public void setHeadlines_id(String headlines_id) {
                this.headlines_id = headlines_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public String toString() {
                return "NewsBean{" +
                        "headlines_id='" + headlines_id + '\'' +
                        ", title='" + title + '\'' +
                        '}';
            }
        }

        public static class RulesBean {
            /**
             * title : 规则标题
             * content : 规则内容
             */

            private String title;
            private String content;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            @Override
            public String toString() {
                return "RulesBean{" +
                        "title='" + title + '\'' +
                        ", content='" + content + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Data{" +
                    "ads=" + ads +
                    ", news=" + news +
                    ", oneBuyList=" + oneBuyList +
                    ", rules=" + rules +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OneBuyBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", nums=" + nums +
                '}';
    }
}
