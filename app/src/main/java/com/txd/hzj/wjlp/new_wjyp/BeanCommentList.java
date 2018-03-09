package com.txd.hzj.wjlp.new_wjyp;

import java.util.List;


public class BeanCommentList {


    /**
     * code : 1
     * message : 请求成功
     * data : {"label_list":[{"label_id":"0","label_name":"全部","num":"4"},{"label_id":"2","label_name":"外观漂亮","num":"3"},{"label_id":"3","label_name":"动力十足","num":"3"},{"label_id":"5","label_name":"耐力好","num":"3"}],"comment_list":[{"user_id":"27","exterior":"4","space":"2","controllability":"4","consumption":"4","label_str":",3,","pictures":"10958","content":"考虑图兔兔，他了啊，，，，，。？。！我在学校等你回来一起吃个饭就可以啦？！？。？，。。。。！？我是基督徒伤悲催我的手机号码发给我吧谢谢","create_time":"2017-12-01 15:58","comment_star":"3.50","pictures_arr":[{"path":"http://wjyp.txunda.com/Uploads/CarOrder/2017-12-01/5a210baf329ad.png"}],"label_arr":[{"label":"动力十足"}],"nickname":"GYM","head_pic":"http://wjyp.txunda.com/Uploads/User/2017-12-04/5a24b494e3ac8.png"},{"user_id":"27","exterior":"4","space":"4","controllability":"2","consumption":"4","label_str":",2,5,","pictures":"","content":"回老家了","create_time":"2017-12-01 15:58","comment_star":"3.50","pictures_arr":[],"label_arr":[{"label":"外观漂亮"},{"label":"耐力好"}],"nickname":"GYM","head_pic":"http://wjyp.txunda.com/Uploads/User/2017-12-04/5a24b494e3ac8.png"},{"user_id":"27","exterior":"4","space":"2","controllability":"4","consumption":"4","label_str":",2,3,5,","pictures":"","content":"","create_time":"2017-12-01 15:59","comment_star":"3.50","pictures_arr":[],"label_arr":[{"label":"外观漂亮"},{"label":"动力十足"},{"label":"耐力好"}],"nickname":"GYM","head_pic":"http://wjyp.txunda.com/Uploads/User/2017-12-04/5a24b494e3ac8.png"}],"composite":"3.1","exterior":"4.3","space":"3.0","controllability":"3.8","consumption":"4.3"}
     * nums : 0
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
        /**
         * label_list : [{"label_id":"0","label_name":"全部","num":"4"},{"label_id":"2","label_name":"外观漂亮","num":"3"},{"label_id":"3","label_name":"动力十足","num":"3"},{"label_id":"5","label_name":"耐力好","num":"3"}]
         * comment_list : [{"user_id":"27","exterior":"4","space":"2","controllability":"4","consumption":"4","label_str":",3,","pictures":"10958","content":"考虑图兔兔，他了啊，，，，，。？。！我在学校等你回来一起吃个饭就可以啦？！？。？，。。。。！？我是基督徒伤悲催我的手机号码发给我吧谢谢","create_time":"2017-12-01 15:58","comment_star":"3.50","pictures_arr":[{"path":"http://wjyp.txunda.com/Uploads/CarOrder/2017-12-01/5a210baf329ad.png"}],"label_arr":[{"label":"动力十足"}],"nickname":"GYM","head_pic":"http://wjyp.txunda.com/Uploads/User/2017-12-04/5a24b494e3ac8.png"},{"user_id":"27","exterior":"4","space":"4","controllability":"2","consumption":"4","label_str":",2,5,","pictures":"","content":"回老家了","create_time":"2017-12-01 15:58","comment_star":"3.50","pictures_arr":[],"label_arr":[{"label":"外观漂亮"},{"label":"耐力好"}],"nickname":"GYM","head_pic":"http://wjyp.txunda.com/Uploads/User/2017-12-04/5a24b494e3ac8.png"},{"user_id":"27","exterior":"4","space":"2","controllability":"4","consumption":"4","label_str":",2,3,5,","pictures":"","content":"","create_time":"2017-12-01 15:59","comment_star":"3.50","pictures_arr":[],"label_arr":[{"label":"外观漂亮"},{"label":"动力十足"},{"label":"耐力好"}],"nickname":"GYM","head_pic":"http://wjyp.txunda.com/Uploads/User/2017-12-04/5a24b494e3ac8.png"}]
         * composite : 3.1
         * exterior : 4.3
         * space : 3.0
         * controllability : 3.8
         * consumption : 4.3
         */
        private String price;
        private String lot;
        private String supporting;
        private String traffic;
        private String environment;
        private String composite;
        private String exterior;
        private String space;
        private String controllability;
        private String consumption;
        private List<LabelListBean> label_list;
        private List<CommentListBean> comment_list;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getLot() {
            return lot;
        }

        public void setLot(String lot) {
            this.lot = lot;
        }

        public String getSupporting() {
            return supporting;
        }

        public void setSupporting(String supporting) {
            this.supporting = supporting;
        }

        public String getTraffic() {
            return traffic;
        }

        public void setTraffic(String traffic) {
            this.traffic = traffic;
        }

        public String getEnvironment() {
            return environment;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        public String getComposite() {
            return composite;
        }

        public void setComposite(String composite) {
            this.composite = composite;
        }

        public String getExterior() {
            return exterior;
        }

        public void setExterior(String exterior) {
            this.exterior = exterior;
        }

        public String getSpace() {
            return space;
        }

        public void setSpace(String space) {
            this.space = space;
        }

        public String getControllability() {
            return controllability;
        }

        public void setControllability(String controllability) {
            this.controllability = controllability;
        }

        public String getConsumption() {
            return consumption;
        }

        public void setConsumption(String consumption) {
            this.consumption = consumption;
        }

        public List<LabelListBean> getLabel_list() {
            return label_list;
        }

        public void setLabel_list(List<LabelListBean> label_list) {
            this.label_list = label_list;
        }

        public List<CommentListBean> getComment_list() {
            return comment_list;
        }

        public void setComment_list(List<CommentListBean> comment_list) {
            this.comment_list = comment_list;
        }

        public static class LabelListBean {
            /**
             * label_id : 0
             * label_name : 全部
             * num : 4
             */

            private String label_id;
            private String label_name;
            private String num;

            public String getLabel_id() {
                return label_id;
            }

            public void setLabel_id(String label_id) {
                this.label_id = label_id;
            }

            public String getLabel_name() {
                return label_name;
            }

            public void setLabel_name(String label_name) {
                this.label_name = label_name;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }

        public static class CommentListBean {
            /**
             * user_id : 27
             * exterior : 4
             * space : 2
             * controllability : 4
             * consumption : 4
             * label_str : ,3,
             * pictures : 10958
             * content : 考虑图兔兔，他了啊，，，，，。？。！我在学校等你回来一起吃个饭就可以啦？！？。？，。。。。！？我是基督徒伤悲催我的手机号码发给我吧谢谢
             * create_time : 2017-12-01 15:58
             * comment_star : 3.50
             * pictures_arr : [{"path":"http://wjyp.txunda.com/Uploads/CarOrder/2017-12-01/5a210baf329ad.png"}]
             * label_arr : [{"label":"动力十足"}]
             * nickname : GYM
             * head_pic : http://wjyp.txunda.com/Uploads/User/2017-12-04/5a24b494e3ac8.png
             */

            private String user_id;
            private String exterior;
            private String space;
            private String controllability;
            private String consumption;
            private String label_str;
            private String pictures;
            private String content;
            private String create_time;
            private String comment_star;
            private String nickname;
            private String head_pic;
            private List<PicturesArrBean> pictures_arr;
            private List<LabelArrBean> label_arr;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getExterior() {
                return exterior;
            }

            public void setExterior(String exterior) {
                this.exterior = exterior;
            }

            public String getSpace() {
                return space;
            }

            public void setSpace(String space) {
                this.space = space;
            }

            public String getControllability() {
                return controllability;
            }

            public void setControllability(String controllability) {
                this.controllability = controllability;
            }

            public String getConsumption() {
                return consumption;
            }

            public void setConsumption(String consumption) {
                this.consumption = consumption;
            }

            public String getLabel_str() {
                return label_str;
            }

            public void setLabel_str(String label_str) {
                this.label_str = label_str;
            }

            public String getPictures() {
                return pictures;
            }

            public void setPictures(String pictures) {
                this.pictures = pictures;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getComment_star() {
                return comment_star;
            }

            public void setComment_star(String comment_star) {
                this.comment_star = comment_star;
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

            public List<PicturesArrBean> getPictures_arr() {
                return pictures_arr;
            }

            public void setPictures_arr(List<PicturesArrBean> pictures_arr) {
                this.pictures_arr = pictures_arr;
            }

            public List<LabelArrBean> getLabel_arr() {
                return label_arr;
            }

            public void setLabel_arr(List<LabelArrBean> label_arr) {
                this.label_arr = label_arr;
            }

            public static class PicturesArrBean {
                /**
                 * path : http://wjyp.txunda.com/Uploads/CarOrder/2017-12-01/5a210baf329ad.png
                 */

                private String path;

                public String getPath() {
                    return path;
                }

                public void setPath(String path) {
                    this.path = path;
                }
            }

            public static class LabelArrBean {
                /**
                 * label : 动力十足
                 */

                private String label;

                public String getLabel() {
                    return label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }
            }
        }
    }
}
