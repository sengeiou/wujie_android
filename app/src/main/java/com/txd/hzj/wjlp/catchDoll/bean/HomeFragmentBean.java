package com.txd.hzj.wjlp.catchDoll.bean;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class HomeFragmentBean {

    /**
     * code : 200
     * message : 获取成功
     * data : {"list":[{"id":"2","pid":"0","status":"1","pic":"28539","des":"水电费","name":"抓手机","create_time":"1547887462","update_time":"1548396727","other":null,"mac":"","price":"0.00","exchange_price":"0.00","room_pic":"http://test.wujiemall.com/Uploads/Catcher/2019-01-19/5c42e35bc07b0.jpg","status_ming":"热抓中"},{"id":"3","pid":"123456454","status":"1","pic":"28552","des":"456456","name":"抓飘飘","create_time":"1547891803","update_time":"1547891803","other":null,"mac":"","price":"0.00","exchange_price":"0.00","room_pic":"http://test.wujiemall.com/Uploads/Catcher/2019-01-19/5c42f425875a0.jpg","status_ming":"热抓中"},{"id":"4","pid":"12332","status":"1","pic":"28729","des":"是的发生的","name":"发给对方","create_time":"1548400134","update_time":"1548400134","other":null,"mac":"","price":"0.00","exchange_price":"0.00","room_pic":"http://test.wujiemall.com/Uploads/Catcher/2019-01-25/5c4ab5f857b20.jpg","status_ming":"热抓中"},{"id":"5","pid":"123312312","status":"1","pic":"28730","des":"12312312321","name":"水电费","create_time":"1548400286","update_time":"1548400286","other":null,"mac":"","price":"0.00","exchange_price":"0.00","room_pic":"http://test.wujiemall.com/Uploads/Catcher/2019-01-25/5c4ab62a2eae0.jpg","status_ming":"热抓中"},{"id":"13","pid":"0","status":"0","pic":"28822","des":"沃天娃娃机，品牌值得信赖！","name":"沃天娃娃机","create_time":"1548729347","update_time":"1548733511","other":null,"mac":"32336AC82E68","price":"12.00","exchange_price":"12.00","room_pic":"http://test.wujiemall.com/Uploads/Catcher/2019-01-29/5c4fcbfd9d677.jpg","status_ming":"有空间"},{"id":"14","pid":"0","status":"0","pic":"28932","des":"大幅度发给对方的","name":"水电费","create_time":"1548811122","update_time":"1548811122","other":null,"mac":"1010101","price":"100.00","exchange_price":"10.00","room_pic":"http://test.wujiemall.com/Uploads/Catcher/2019-01-30/5c50fb6b15630.jpg","status_ming":"有空间"}],"victory":[{"nickname":"无界新人168","create_time":"1548833661","goods_name":"娃娃","head_pic":"http://test.wujiemall.com/Uploads/User/2018-12-25/5c2222a433ca0.gif"},{"nickname":"无界新人168","create_time":"1548833597","goods_name":"娃娃","head_pic":"http://test.wujiemall.com/Uploads/User/2018-12-25/5c2222a433ca0.gif"},{"nickname":"无界新人168","create_time":"1548833537","goods_name":"娃娃","head_pic":"http://test.wujiemall.com/Uploads/User/2018-12-25/5c2222a433ca0.gif"},{"nickname":"无界新人168","create_time":"1548833513","goods_name":"娃娃","head_pic":"http://test.wujiemall.com/Uploads/User/2018-12-25/5c2222a433ca0.gif"},{"nickname":"无界新人168","create_time":"1548832877","goods_name":"娃娃","head_pic":"http://test.wujiemall.com/Uploads/User/2018-12-25/5c2222a433ca0.gif"}],"banner":[{"ads_id":"77","picture":"http://test.wujiemall.com/Uploads/Ads/2019-01-25/5c4a7ce8cce20.jpg","desc":"送钻免费夹娃娃","href":"","position":"77","merchant_id":"1235646","goods_id":"1235614546"},{"ads_id":"143","picture":"http://test.wujiemall.com/Uploads/Ads/2019-01-25/5c4a7d61d9d28.jpg","desc":"送钻免费抓娃娃","href":"","position":"77","merchant_id":"132132","goods_id":"12312331"}]}
     * nums : 6
     */

    private String code;
    private String message;
    private DataBean data;
    private String nums;

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
        private List<RoomBean> list;
        private List<HomeVictoryBean> victory;
        private List<HomeBannerBean> banner;

        public List<RoomBean> getList() {
            return list;
        }

        public void setList(List<RoomBean> list) {
            this.list = list;
        }

        public List<HomeVictoryBean> getVictory() {
            return victory;
        }

        public void setVictory(List<HomeVictoryBean> victory) {
            this.victory = victory;
        }

        public List<HomeBannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<HomeBannerBean> banner) {
            this.banner = banner;
        }
    }
}
